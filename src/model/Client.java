package model;

import java.net.InetAddress;
import java.net.Socket;

public class Client {
	private String name;
	private int id;
	private InetAddress ipAddress;
	private AudioThread audioThread;
	private CommandThread commandThread;
	private volatile boolean connected;
	private volatile boolean newBuffer;

//	public Client(String name, int id) {
//		this.name = name;
//		this.id = id;
//
//	}

//	public Client(String name, Socket audioSocket) {
//		audioThread = new AudioThread(this, audioSocket);
//		audioThread.start();
//		connected = true;
//	}
	
	public Client(Socket commandSocket, byte[] buffer) {
		ipAddress = commandSocket.getInetAddress();
		commandThread = new CommandThread(this, commandSocket);
		commandThread.start();
//		audioThread = new AudioThread(this, audioSocket);
//		audioThread.setAudioBuffer(buffer);
//		audioThread.start();
		connected = true;
	}
	
//	public Client(Socket commandSocket, Socket audioSocket, byte[] buffer) {
//		commandThread = new CommandThread(this, commandSocket);
//		commandThread.start();
//		audioThread = new AudioThread(this, audioSocket);
//		audioThread.setAudioBuffer(buffer);
//		audioThread.start();
//		connected = true;
//	}

	public Client(InetAddress ipAddress) {
		this.ipAddress = ipAddress;
	}

	public InetAddress getIpAddress() {
		return ipAddress;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public void setConnected(boolean bool) {
		connected = bool;
	}

	public boolean isConnected() {
		return connected;
	}

	public void setNewBuffer(boolean bool) {
		newBuffer = bool;
	}
	
	public boolean getNewBuffer() {
		return newBuffer;
	}
	
	public void setAudioThreadBuffer(byte[] buffer) {
		audioThread.setAudioBuffer(buffer);
	}
	
	public void sendAudioBuffer(byte[] buffer) {
		audioThread.sendAudioBuffer(buffer);
	}
	
	public void setCommandThreadReceivedListener(CommandReceivedListener listener) {
		commandThread.setCommandReceivedListener(listener);
	}
	
	public void startAudioThread(Socket audioSocket, byte[] buffer) {
		audioThread = new AudioThread(this, audioSocket);
		audioThread.setAudioBuffer(buffer);
		audioThread.start();
	}
	
	
//	public void setCommandThreadQueue(SongQueue queue) {
//		commandThread.setQueue(queue);
//	}
//	
//	public void setCommandThreadManager(MusicLibraryManager manager) {
//		commandThread.setManager(manager);
//	}

}
