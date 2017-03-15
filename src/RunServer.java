
import model.MusicLibraryManager;
import model.SongQueue;

import java.io.ObjectOutputStream;

import controller.CommandController;
import model.ClientList;
import model.CommandReceivedEvent;
import model.CommandReceivedListener;
import model.CommandThread;
import networking.AudioServer;
import networking.CommandServer;

public class RunServer {

	public static void main(String[] args) {

		MusicLibraryManager manager = new MusicLibraryManager();
		manager.openMusicLibrary();
		if (args.length > 0) {
			if (args[0].equals("reread")) {
				manager.rereadWholeLibrary();
			}
		}

		ClientList clientList = new ClientList(manager);
		SongQueue songQueue = new SongQueue();
		CommandReceivedListener listener = new CommandController(manager, songQueue);
		CommandServer commandServer = new CommandServer(listener);
		AudioServer audioServer = new AudioServer();
//		clientList.setSongQueue(songQueue);
		songQueue.start();
		songQueue.setAudioBufferListener(clientList.getAudioBufferListener());
		commandServer.setClientList(clientList);
		audioServer.setClientList(clientList);
		commandServer.start();
		audioServer.start();

//		TeamspeakQuery query = new TeamspeakQuery();
//		query.start();

	}

}
