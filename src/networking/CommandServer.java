package networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import model.ClientList;
import model.CommandReceivedListener;

public class CommandServer extends Thread {
	private ClientList clientList;
	private CommandReceivedListener listener;

	public CommandServer(CommandReceivedListener listener) {
		this.listener = listener;
	}

	public void setClientList(ClientList clientList) {
		this.clientList = clientList;
	}

	public void run() {
		System.out.println("Starting Command server!");
		ServerSocket commandServer = null;
		try {
			commandServer = new ServerSocket(6789);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while (true) {
			try {
				Socket commandSocket = commandServer.accept();
				System.out.println("Command Server connected to " + commandSocket.getInetAddress() + " on port " + commandSocket.getPort());
				clientList.addUser(commandSocket, listener);
			} catch (Exception e) {
				try {
					commandServer.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}

		}

	}

}
