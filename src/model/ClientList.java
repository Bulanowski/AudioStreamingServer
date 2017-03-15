package model;

import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;

public class ClientList {

	private HashMap<InetAddress, Client> clients;
	private AudioPacketListener audioBufferListener;
	private byte[] buffer;

	public ClientList(MusicLibraryManager manager) {
		buffer = new byte[20];
		clients = new HashMap<>();
		audioBufferListener = new AudioPacketListener() {

			@Override
			public void audioBufferFilled(AudioPacketEvent ev) {
//				ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
//				byteBuffer.put(ev.getBuffer());
				for (Client client : clients.values()) {
					if (client.isAudioConnected()) {
						client.sendAudioBuffer(ev.getBuffer());
//						client.setNewBuffer(true);
					} else if (client.isCommandConnected()) {
						// make connection to audio server
					} else {
						client.disconnect();
						clients.remove(client.getInetAddress());
					}
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
		return clients.get(inetAddress);
	}
	
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
