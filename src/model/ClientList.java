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
	
	public void sendAll(byte packageType, Object obj) {
		for (Client c : clients.values()) {
			try {
				if (c.isConnected()) {
					c.send(packageType, obj);
				} else {
					c.stop();
					clients.remove(c.getInetAddress());
				}
			} catch (IOException e) {
				e.printStackTrace();
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
