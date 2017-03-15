package controller;

import java.io.IOException;
import java.io.ObjectOutputStream;

import model.CommandReceivedEvent;
import model.CommandReceivedListener;
import model.CommandThread;
import model.MusicLibraryManager;
import model.SongQueue;

public class CommandController implements CommandReceivedListener {

//	private ClientList clients;
	private MusicLibraryManager manager;
	private SongQueue queue;
//	private CommandReceivedListener commandReceivedListener;
	
//	public CommandReceivedListener getCommandReceivedListener() {
//		return commandReceivedListener;
//	}

	public CommandController(MusicLibraryManager manager, SongQueue queue) {
//		this.clients = clients;
		this.manager = manager;
		this.queue = queue;
	}

//	public CommandReceivedListener getCommandReceivedListener() {
//		return commandReceivedListener;
//	}

	@Override
	public void commandReceived(CommandReceivedEvent ev) {
		try {
			String command = ev.getCommand();
			switch (command.split(" ")[0]) {
			case "hello":
				break;
			case "request_songs":
				System.out.println("requested song list");
				ObjectOutputStream oos = ((ObjectOutputStream) ((CommandThread) ev.getSource())
						.getObjectOutputStream());
				oos.writeObject(manager.listSong());
				break;
			case "get_song":
				System.out.println("song request");
				break;
			case "add_to_queue":
				queue.addSong(command.substring(command.indexOf(' ') + 1));
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
				System.out.println("Invalid input command received: " + command);
				break;
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
