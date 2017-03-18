package model;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;

public class ClientList {

	private HashMap<InetAddress, Client> clients;

	public ClientList() {
		clients = new HashMap<>();
	}
	
//	public void sendAllCatchAll(byte packageType, Object information) {
//		System.out.println("Sending data to connected clients: " + information);
//		for (Client c : clients.values()) {
//			if (c.isConnected()) {
//				System.out.println(
//						"Sending to client " + c.getInetAddress().getHostAddress() + " at time " + System.nanoTime());
//				c.sendCatchAll(packageType, information);;
//			} else {
//				c.stop();
//				clients.remove(c.getInetAddress());
//			}
//		}
//	}

	// GET RID OF THESE THROWS
	public void sendAll(byte packageType, Object obj) throws IOException {
		for (Client c : clients.values()) {
			if (c.isConnected()) {
				c.send(packageType, obj);
			} else {
				c.stop();
				clients.remove(c.getInetAddress());
			}
		}
	}
	
	public void startPlaying() {
		for (Client c : clients.values()) {
			c.startPlaying();
		}
	}
	
	public boolean isPlaying() {
		for (Client c : clients.values()) {
			if (c.getPlaying()) {
				return true;
			}
		}
		return false;
	}

	public Collection<Client> clients() {
		return clients.values();
	}

	public Client remove(InetAddress inetAddress) {
		return clients.remove(inetAddress);
	}

	public Client getByInetAddress(InetAddress inetAddress) {
		return clients.get(inetAddress);
	}

	public void addClient(Socket clientSocket, CommandReceivedListener commandReceivedListener) {
		try {
			Client client = new Client(clientSocket, commandReceivedListener);
//			client.setCommandReceivedListener(commandReceivedListener);
			client.start();
			clients.put(client.getInetAddress(), client);
		} catch (IOException e) {
			System.err.println("Failed to add new Client");
			e.printStackTrace();
		}
	}
}
