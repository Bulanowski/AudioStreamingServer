package networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import model.MusicLibraryManager;
import model.UserList;

//TODO: Rename this class.
public class TCPServer extends Thread {
	private MusicLibraryManager manager;
	private UserList userList;

	public TCPServer(MusicLibraryManager manager) {
		this.manager = manager;
	}

	public void setUserList(UserList userList) {
		this.userList = userList;
	}

	public void run() {
		System.out.println("Starting TCP server!");
		ServerSocket welcomeSocket = null;
		try {
			welcomeSocket = new ServerSocket(6789);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		while (true) {
			try {
				Socket socket = welcomeSocket.accept();
				userList.addUser(socket);
				System.out.println("Client IP & PORT: " + socket.getInetAddress() + ":" + socket.getPort());

				welcomeSocket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}
