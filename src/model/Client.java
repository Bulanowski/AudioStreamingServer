package model;

import java.net.InetAddress;
import java.net.Socket;

public class Client {
	private String name;
	private int id;
	private InetAddress inetAddress;
	private AudioThread audioThread;
	private CommandThread commandThread;
	
	public Client(Socket commandSocket, byte[] buffer) {
		inetAddress = commandSocket.getInetAddress();
		commandThread = new CommandThread(commandSocket);
		commandThread.start();
	}
	
	public void startAudioThread(Socket audioSocket, byte[] buffer) {
		audioThread = new AudioThread(audioSocket);
//		audioThread.setAudioBuffer(buffer);
		audioThread.start();
	}

	public InetAddress getInetAddress() {
		return inetAddress;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}
	
	public boolean isCommandConnected() {
		return commandThread.isConnected();
	}
	
	public boolean isAudioConnected() {
		return audioThread.isConnected();
	}
	
	public void disconnect() {
		commandThread.close();
		audioThread.close();
	}
	
	public void sendAudioBuffer(byte[] buffer) {
		audioThread.sendAudioBuffer(buffer);
	}
	
	public void setCommandThreadReceivedListener(CommandReceivedListener listener) {
		commandThread.setCommandReceivedListener(listener);
	}

}
