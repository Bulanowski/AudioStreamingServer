
import model.MusicLibraryManager;
import model.SongQueue;
import model.UserList;
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

		TCPServer server = new TCPServer(manager);
		UserList userList = new UserList(manager);
		SongQueue songQueue = new SongQueue();
		userList.setSongQueue(songQueue);
		songQueue.start();
		songQueue.setAudioBufferListener(userList.getAudioBufferListener());
		server.setUserList(userList);
		server.start();

//		TeamspeakQuery query = new TeamspeakQuery();
//		query.start();

	}

}
