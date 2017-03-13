package model;

import java.net.Socket;
import java.util.LinkedList;

public class UserList {

	private LinkedList<User> users;
	private AudioPacketListener audioBufferListener;
	private byte[] buffer;
	private MusicLibraryManager manager;
	private SongQueue queue;

	public UserList(MusicLibraryManager manager) {
		buffer = new byte[20];
		this.manager = manager;
		users = new LinkedList<>();
		audioBufferListener = new AudioPacketListener() {

			@Override
			public void audioBufferFilled(AudioPacketEvent ev) {
//				ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
//				byteBuffer.put(ev.getBuffer());
				for (User user : users) {
					user.sendAudioBuffer(ev.getBuffer());
					user.setNewBuffer(true);
				}
			}
		};
	}

	public AudioPacketListener getAudioBufferListener() {
		return audioBufferListener;
	}

	public void setSongQueue(SongQueue queue) {
		this.queue = queue;
	}

	public void addUser(Socket socket) {
		User user = new User(socket, buffer);
		user.setCommandThreadManager(manager);
		user.setCommandThreadQueue(queue);
		users.add(user);
	}
}
