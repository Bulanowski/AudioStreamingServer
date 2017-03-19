package controller;

import java.io.IOException;

import model.Client;
import model.CommandReceivedEvent;
import model.CommandReceivedListener;
import model.MusicLibraryManager;
import model.PackageType;
import model.SongQueue;

public class CommandController implements CommandReceivedListener {

	private MusicLibraryManager manager;
	private SongQueue queue;

	public CommandController(MusicLibraryManager manager, SongQueue queue) {
		this.manager = manager;
		this.queue = queue;
	}

	@Override
	public void commandReceived(CommandReceivedEvent ev) {
		String command = ev.getCommand();
		switch (command.split(" ")[0]) {
		case "hello":
			break;
		case "request_songs":
			System.out.println("Requested song list");
			if (ev.getSource() instanceof Client) {
				Client c = (Client) ev.getSource();
				try {
					c.send(PackageType.SONG_LIST.getByte(), manager.listSong());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
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
		case "song_end":
			if (ev.getSource() instanceof Client) {
				Client c = (Client) ev.getSource();
				c.stopPlaying();
			}
			break;
		default:
			System.out.println("Invalid input command received: " + command);
			break;
		}
	}

}
