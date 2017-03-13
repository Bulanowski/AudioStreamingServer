package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class CommandThread extends Thread {
	private User user;
	private Socket socket;
	private int id = -1;
	private MusicLibraryManager manager;
	private SongQueue queue;

	public CommandThread(User user, Socket socket) {
		this.user = user;
		this.socket = socket;
		id = socket.getPort();
	}

	public void setManager(MusicLibraryManager manager) {
		this.manager = manager;
	}
	
	public void setQueue(SongQueue queue) {
		this.queue = queue;
	}

	public void run() {
		try {
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

			while (true) {

				String inputCommand = ois.readUTF();
				System.out.println(inputCommand);
				
				switch (inputCommand.split(" ")[0]) {
				case "hello":
					break;
				case "request_songs":
					System.out.println("requested song list");
					oos.writeObject(manager.listSong());
					break;
				case "get_song":
					System.out.println("song request");
					break;
				case "add_to_queue":
					queue.addSong(inputCommand.substring(inputCommand.indexOf(' ') + 1));
					break;
				case "chat":
					// System.out.println(inputCommand);

					// String msg = input.substring(5);

					// for (User user : users) {
					// connectionSocket = new
					// Socket(user.getIpAddress(), 6789);
					// welcomeSocket.close();
					// connectionSocket = new Socket("71.190.190.19", 6790);
					// outToClient = new
					// ObjectOutputStream(connectionSocket.getOutputStream());
					// outToClient.writeObject(msg);
					// }
					break;
				default:
					System.out.println("Invalid input command received: " + inputCommand);
					break;
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
