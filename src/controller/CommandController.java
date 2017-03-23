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
	private ChatController chat;

	public CommandController(MusicLibraryManager manager, SongQueue queue, ChatController chat) {
		this.manager = manager;
		this.queue = queue;
		this.chat = chat;
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

					c.send(PackageType.SONG_LIST.getByte(), manager.listSongNoPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			break;
		case "get_song":
			System.out.println("song request");
			break;
		case "add_to_queue":
			Integer id;
			id = Integer.valueOf(command.substring(command.indexOf(' ') + 1));
			if(id != null) {
			queue.addSong(id);
			} else {
				System.err.println("add_to_queue command was used incorrectly: "+ command.substring(command.indexOf(' ') + 1));
			}
			break;
		case "chat":
			if (ev.getSource() instanceof Client) {
				Client c = (Client) ev.getSource();
				chat.sendChat(command.substring(command.indexOf(' ') + 1), c);
			}
			break;
		case "song_end":
			if (ev.getSource() instanceof Client) {
				Client c = (Client) ev.getSource();
				c.stopPlaying();
			}
			break;
		case "username":
			if (ev.getSource() instanceof Client) {
				Client c = (Client) ev.getSource();
				c.setName(command.substring(command.indexOf(' ') + 1));
			}
			break;
		default:
			System.out.println("Invalid input command received: " + command);
			break;
		}
	}

}
