package networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import model.ClientList;
import model.CommandReceivedListener;

//TODO: Rename this class.
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
//		ServerSocket audioServer = null;
		try {
			commandServer = new ServerSocket(6789);
//			audioServer = new ServerSocket(8796);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		while (true) {
			try {
				Socket commandSocket = commandServer.accept();
				System.out.println("Command Server connected to " + commandSocket.getInetAddress());
//				Socket audioSocket = audioServer.accept();
//				System.out.println("Audio Server connected to " + audioSocket.getInetAddress());
//				clientList.addUser(commandSocket, audioSocket, listener);
				clientList.addUser(commandSocket, listener);
//				System.out.println("Client IP & PORT: " + commandSocket.getInetAddress() + ":" + commandSocket.getPort());

//				welcomeSocket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}
