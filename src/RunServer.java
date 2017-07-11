
import controller.ChatController;
import controller.CommandController;
import model.ClientList;
import model.CommandReceivedListener;
import model.MusicLibraryManager;
import model.SongQueue;
import networking.AudioBroadcaster;
import networking.ClientQueueUpdater;
import networking.TCPServer;

public class RunServer {

	public static void main(String[] args) {

		MusicLibraryManager manager = new MusicLibraryManager();
		if (args.length > 0) {
			if (args[0].equals("reread")) {
				manager.rereadWholeLibrary();
			}
		}
		manager.openMusicLibrary();

		ClientList clientList = new ClientList();
		SongQueue songQueue = new SongQueue(clientList,manager);
		ChatController chat = new ChatController(clientList);
		CommandReceivedListener commandReceivedListener = new CommandController(manager, songQueue, chat);
		// CommandServer commandServer = new CommandServer(listener);
		// AudioServer audioServer = new AudioServer();
		songQueue.start();
		// songQueue.setSendFileListener(clientList.getSendFileListener());
		AudioBroadcaster audioBroadcaster = new AudioBroadcaster(clientList);
		audioBroadcaster.start();
		ClientQueueUpdater queueUpdater = new ClientQueueUpdater(clientList);
		queueUpdater.start();
		queueUpdater.setSongQueue(songQueue.getSongQueue());
		songQueue.setSendFileListener(audioBroadcaster.getSendFileListener());
		songQueue.addSongQueueListener(queueUpdater.getListener());
		TCPServer tcpServer = new TCPServer(53308, clientList, commandReceivedListener);
		tcpServer.start();
		// commandServer.setClientList(clientList);
		// audioServer.setClientList(clientList);
		// commandServer.start();
		// audioServer.start();


	}

}
