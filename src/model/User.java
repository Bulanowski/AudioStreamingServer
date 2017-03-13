package model;

import java.net.Socket;
//TODO: rename User to Client
public class User {
	private String name;
	private int id;
	private String ipAddress;
	private AudioThread audioThread;
	private CommandThread commandThread;
	private volatile boolean connected;
	private volatile boolean newBuffer;

	public User(String name, int id) {
		this.name = name;
		this.id = id;

	}

	public User(String name, Socket socket) {
		audioThread = new AudioThread(this, socket.getInetAddress());
		audioThread.start();
		connected = true;
	}
	
	public User(Socket socket, byte[] buffer) {
		commandThread = new CommandThread(this, socket);
		commandThread.start();
		audioThread = new AudioThread(this, socket.getInetAddress());
		audioThread.setAudioBuffer(buffer);
		audioThread.start();
		connected = true;
	}

	public User(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getIpAddress() {
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
	
	
	public void setCommandThreadQueue(SongQueue queue) {
		commandThread.setQueue(queue);
	}
	
	public void setCommandThreadManager(MusicLibraryManager manager) {
		commandThread.setManager(manager);
	}

}
