package networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import model.ClientList;
import model.CommandReceivedListener;

public class TCPServer implements Runnable {
	private Thread thread;
	private ClientList clientList;
	private CommandReceivedListener commandReceivedListener;
	private int port;

	public TCPServer(int port, ClientList clientList, CommandReceivedListener commandReceivedListener) {
		this.port = port;
		this.clientList = clientList;
		this.commandReceivedListener = commandReceivedListener;
	}

	public void start() {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	@Override
	public void run() {
		System.out.println("Starting TCP Server!");
		ServerSocket welcomeSocket = null;
		try {
			welcomeSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (thread != null) {
			try {
				Socket clientSocket = welcomeSocket.accept();
				System.out.println("TCP Server connected to " + clientSocket.getInetAddress() + " on port "
						+ clientSocket.getPort());
				clientList.addClient(clientSocket, commandReceivedListener);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			welcomeSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void stop() {
		thread = null;
	}
}
