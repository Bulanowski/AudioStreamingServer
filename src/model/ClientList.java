package model;

import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;

public class ClientList {

	// TODO: Change to HashMap of INetAddress and Client
	private HashMap<InetAddress, Client> clients;
//	private LinkedList<Client> clients;
	private AudioPacketListener audioBufferListener;
	private byte[] buffer;
//	private MusicLibraryManager manager;
//	private SongQueue queue;

	public ClientList(MusicLibraryManager manager) {
		buffer = new byte[20];
//		this.manager = manager;
//		clients = new LinkedList<>();
		clients = new HashMap<>();
		audioBufferListener = new AudioPacketListener() {

			@Override
			public void audioBufferFilled(AudioPacketEvent ev) {
//				ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
//				byteBuffer.put(ev.getBuffer());
				for (Client client : clients.values()) {
					boolean userConnected = client.sendAudioBuffer(ev.getBuffer());
					if (!userConnected) {
						clients.remove(client.getInetAddress());
					}
					client.setNewBuffer(true);
				}
			}
		};
	}

	public AudioPacketListener getAudioBufferListener() {
		return audioBufferListener;
	}
	
	public byte[] getBuffer() {
		return buffer;
	}
	
	public Client getByInetAddress(InetAddress inetAddress) {
//		clients.get(inetAddress);
//		for (Client client : clients) {
//			if (client.getIpAddress().equals(inetAddress)) {
//				return client;
//			}
//		}
		return clients.get(inetAddress);
	}

//	public void setSongQueue(SongQueue queue) {
//		this.queue = queue;
//	}
	
	public void addUser(Socket commandSocket, CommandReceivedListener commandReceivedListener) {
		Client client = new Client(commandSocket, buffer);
		client.setCommandThreadReceivedListener(commandReceivedListener);
		clients.put(client.getInetAddress(), client);
	}

//	public void addUser(Socket commandSocket, Socket audioSocket, CommandReceivedListener listener) {
//		Client client = new Client(commandSocket, audioSocket, buffer);
//		client.setCommandThreadReceivedListener(listener);
////		client.setCommandThreadManager(manager);
////		client.setCommandThreadQueue(queue);
//		clients.add(client);
//	}
}
