package controller;

import model.*;

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
		Command command = ev.getCommand();
		switch (command.getCommandType()) {
			case HELLO:
				break;
			case REQUEST_SONGS:
				System.out.println("Requested song list");
				command.reply(PackageType.SONG_LIST, manager.listSongNoPath());
				break;
			case REQUEST_QUEUE:
				command.reply(PackageType.SONG_QUEUE, queue.getSerializableQueue());
				break;
			case GET_SONG:
				System.out.println("song request");
				break;
			case ADD_TO_QUEUE:
				Integer id = Integer.valueOf(command.getCommandArg(0));
				if (id != null) {
					queue.addSong((command.fromClient() ? (Client) command.getSource() : null), id);
				}
				break;
			case VOTE_TO_SKIP:
				if (command.fromClient()) {
					((Client) command.getSource()).voteToSkip();
				}
				break;
			case VOTE_TO_REMOVE:
				Integer key = Integer.valueOf(command.getCommandArg(0));
				if (key != null) {
					if (command.fromClient()) {
						queue.voteToRemove((Client) command.getSource(), key);
					}
				}
				break;
			case CHAT:
				if (command.fromClient()) {
					chat.sendChat(command.getCommandArgsAsString(), (Client) command.getSource());
				} else if (command.getSource() instanceof ConsoleInput) {
					chat.sendChat(command.getCommandArgsAsString());
				}
				break;
			case SONG_END:
				if (command.fromClient()) {
					((Client) command.getSource()).stopPlaying();
				}
				break;
			case USERNAME:
				if (command.fromClient()) {
					((Client) command.getSource()).setName(command.getCommandArgsAsString());
				}
				break;
			default:
				System.out.println("Invalid input command received: " + command);
				break;
		}
	}

}
