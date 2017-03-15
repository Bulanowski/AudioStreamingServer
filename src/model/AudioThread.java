package model;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class AudioThread extends Thread {
//	private Client client;
//	private InetAddress address;
	private Socket socket;
//	private byte[] buffer;
//	private int id = -1;
	private OutputStream outputStream;

//	public AudioThread(Client client, InetAddress address) {
//		this.client = client;
//		this.address = address;
//
//	}
	
//	public AudioThread(Client client, Socket socket) {
//		this.client = client;
//		this.socket = socket;
//		id = socket.getPort();
//	}
	
	public AudioThread(Socket socket) {
		this.socket = socket;
	}

//	public void setAudioBuffer(byte[] buffer) {
//		this.buffer = buffer;
//	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public void sendAudioBuffer(byte[] buffer) {
		try {
			outputStream.write(buffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {
		try {
//			ServerSocket serverSocket = new ServerSocket(8796);
//			socket = serverSocket.accept();
//			id = socket.getPort();
			outputStream = socket.getOutputStream();
			
			
//			while (socket.isConnected()) {
//				if (client.getNewBuffer()) {
////					outputStream.write(buffer);
//					client.setNewBuffer(false);
//				}
//			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
