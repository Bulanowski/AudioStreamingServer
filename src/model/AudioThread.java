package model;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class AudioThread extends Thread {
	private User user;
	private InetAddress address;
	private Socket audioSocket;
	private byte[] buffer;
	private int id = -1;
	private OutputStream outputStream;

	public AudioThread(User user, InetAddress address) {
		this.user = user;
		this.address = address;

	}

	public void setAudioBuffer(byte[] buffer) {
		this.buffer = buffer;
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
			ServerSocket serverSocket = new ServerSocket(8796);
			audioSocket = serverSocket.accept();
			id = audioSocket.getPort();
			outputStream = audioSocket.getOutputStream();
			
			
			while (user.isConnected()) {
				if (user.getNewBuffer()) {
//					outputStream.write(buffer);
					user.setNewBuffer(false);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
