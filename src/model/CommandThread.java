package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class CommandThread extends Thread {
//	private Client client;
	private Socket socket;
//	private int id = -1;
	private CommandReceivedListener commandReceivedListener;
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;

//	public CommandThread(Client client, Socket socket) {
//		this.client = client;
//		this.socket = socket;
//		id = socket.getPort();
//	}
	
	public CommandThread(Socket socket) {
		this.socket = socket;
	}
	
	public void setCommandReceivedListener(CommandReceivedListener commandReceivedListener) {
		this.commandReceivedListener = commandReceivedListener;
	}
	
	public ObjectOutputStream getObjectOutputStream() {
		return objectOutputStream;
	}
	
	public Socket getSocket() {
		return socket;
	}

	public void run() {
		try {
			objectInputStream = new ObjectInputStream(socket.getInputStream());
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

			while (true) {

				String inputCommand = objectInputStream.readUTF();
				System.out.println(inputCommand);
				
				CommandReceivedEvent ev = new CommandReceivedEvent(this, inputCommand);
				if (commandReceivedListener != null) {
					commandReceivedListener.commandReceived(ev);
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
