package model;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class AudioThread extends Thread {
	private Client client;
	private InetAddress address;
	private Socket socket;
	private byte[] buffer;
	private int id = -1;
	private OutputStream outputStream;

//	public AudioThread(Client client, InetAddress address) {
//		this.client = client;
//		this.address = address;
//
//	}
	
	public AudioThread(Client client, Socket socket) {
		this.client = client;
		this.socket = socket;
		id = socket.getPort();
	}

	public void setAudioBuffer(byte[] buffer) {
		this.buffer = buffer;
	}
	
	
	public boolean sendAudioBuffer(byte[] buffer) {
		try {
			if (client.isConnected()) {
				outputStream.write(buffer);
				return true;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public void run() {
		try {
//			ServerSocket serverSocket = new ServerSocket(8796);
//			socket = serverSocket.accept();
//			id = socket.getPort();
			outputStream = socket.getOutputStream();
			
			
			while (client.isConnected()) {
				if (client.getNewBuffer()) {
//					outputStream.write(buffer);
					client.setNewBuffer(false);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
