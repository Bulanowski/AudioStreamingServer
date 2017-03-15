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
			e1.printStackTrace();
		}

		while (true) {
			try {
				Socket audioSocket = audioServer.accept();
				System.out.println("Audio Server connected to " + audioSocket.getInetAddress());
				clientList.getByInetAddress(audioSocket.getInetAddress()).startAudioThread(audioSocket, clientList.getBuffer());
			} catch (Exception e) {
				try {
					audioServer.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}

		}

	}
}
