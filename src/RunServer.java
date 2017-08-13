
import controller.ChatController;
import controller.CommandController;
import controller.ConsoleInput;
import model.*;
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

		GlobalValues globalValues = new GlobalValues();
		ClientList clientList = new ClientList();
		SongQueue songQueue = new SongQueue(clientList, manager, globalValues);
		ChatController chat = new ChatController(clientList);
		CommandReceivedListener commandReceivedListener = new CommandController(manager, songQueue, chat);
		songQueue.start();
		AudioBroadcaster audioBroadcaster = new AudioBroadcaster(clientList, globalValues);
		audioBroadcaster.start();
		ClientQueueUpdater queueUpdater = new ClientQueueUpdater(clientList);
		queueUpdater.start();
		songQueue.setClientQueueUpdater(queueUpdater);
		TCPServer tcpServer = new TCPServer(53308, clientList, commandReceivedListener);
		tcpServer.start();
		ConsoleInput consoleInput = new ConsoleInput(commandReceivedListener);
		consoleInput.start();
	}

}
