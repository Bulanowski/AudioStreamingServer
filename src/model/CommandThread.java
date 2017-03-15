package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class CommandThread extends Thread {
	private Client client;
	private Socket socket;
	private int id = -1;
	private CommandReceivedListener commandReceivedListener;
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;
//	private MusicLibraryManager manager;
//	private SongQueue queue;

	public CommandThread(Client client, Socket socket) {
		this.client = client;
		this.socket = socket;
		id = socket.getPort();
	}

//	public void setManager(MusicLibraryManager manager) {
//		this.manager = manager;
//	}
//	
//	public void setQueue(SongQueue queue) {
//		this.queue = queue;
//	}
	
	public void setCommandReceivedListener(CommandReceivedListener commandReceivedListener) {
		this.commandReceivedListener = commandReceivedListener;
	}
	
	public ObjectOutputStream getObjectOutputStream() {
		return objectOutputStream;
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
				
//				switch (inputCommand.split(" ")[0]) {
//				case "hello":
//					break;
//				case "request_songs":
//					System.out.println("requested song list");
//					oos.writeObject(manager.listSong());
//					break;
//				case "get_song":
//					System.out.println("song request");
//					break;
//				case "add_to_queue":
//					queue.addSong(inputCommand.substring(inputCommand.indexOf(' ') + 1));
//					break;
//				case "chat":
//					// System.out.println(inputCommand);
//
//					// String msg = input.substring(5);
//
//					// for (User user : users) {
//					// connectionSocket = new
//					// Socket(user.getIpAddress(), 6789);
//					// welcomeSocket.close();
//					// connectionSocket = new Socket("71.190.190.19", 6790);
//					// outToClient = new
//					// ObjectOutputStream(connectionSocket.getOutputStream());
//					// outToClient.writeObject(msg);
//					// }
//					break;
//				default:
//					System.out.println("Invalid input command received: " + inputCommand);
//					break;
//				}

			}
		} catch (NullPointerException e) {
			System.out.println("Client: " + client);
			System.out.println("Socket: " + socket);
			System.out.println("ID: " + id);
//			System.out.println("MusicLibraryManager: " + manager);
//			System.out.println("SongQueue: " + queue);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
