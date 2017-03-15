package networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import model.ClientList;

public class AudioServer extends Thread {
	private ClientList clientList;

	public void setClientList(ClientList clientList) {
		this.clientList = clientList;
	}

	public void run() {
		System.out.println("Starting Audio server!");
		ServerSocket audioServer = null;
		try {
			audioServer = new ServerSocket(8796);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		while (true) {
			try {
				Socket audioSocket = audioServer.accept();
				System.out.println("Audio Server connected to " + audioSocket.getInetAddress());
				clientList.getByInetAddress(audioSocket.getInetAddress()).startAudioThread(audioSocket, clientList.getBuffer());
//				clientList.addUser(audioSocket, listener);
//				System.out.println("Client IP & PORT: " + audioSocket.getInetAddress() + ":" + audioSocket.getPort());

//				welcomeSocket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
}
