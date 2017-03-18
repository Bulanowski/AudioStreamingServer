
import controller.CommandController;
import model.ClientList;
import model.CommandReceivedListener;
import model.MusicLibraryManager;
import model.SongQueue;
import networking.AudioBroadcaster;
import networking.TCPServer;

public class RunServer {

	public static void main(String[] args) {

		MusicLibraryManager manager = new MusicLibraryManager();
		manager.openMusicLibrary();
		if (args.length > 0) {
			if (args[0].equals("reread")) {
				manager.rereadWholeLibrary();
			}
		}

		ClientList clientList = new ClientList();
		SongQueue songQueue = new SongQueue();
		CommandReceivedListener commandReceivedListener = new CommandController(manager, songQueue);
		// CommandServer commandServer = new CommandServer(listener);
		// AudioServer audioServer = new AudioServer();
		songQueue.start();
		// songQueue.setSendFileListener(clientList.getSendFileListener());
		AudioBroadcaster audioBroadcaster = new AudioBroadcaster(clientList);
		audioBroadcaster.start();
		songQueue.setSendFileListener(audioBroadcaster.getSendFileListener());
		audioBroadcaster.setAudioBroadcasterStateChangedListener(songQueue.getAudioBroadcasterStateChangedListener());
		TCPServer tcpServer = new TCPServer(53308, clientList, commandReceivedListener);
		tcpServer.start();
		// commandServer.setClientList(clientList);
		// audioServer.setClientList(clientList);
		// commandServer.start();
		// audioServer.start();

		// TeamspeakQuery query = new TeamspeakQuery();
		// query.start();

	}

}
