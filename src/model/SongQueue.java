package model;

import java.util.LinkedList;

public class SongQueue extends Thread {
	private LinkedList<String> songPaths;
	private volatile int size = 0;
	private SendFileListener sendFileListener;

	public SongQueue() {
		songPaths = new LinkedList<>();
	}

	public void addSong(String song) {
		songPaths.add(song);
		size++;
	}

	public void setSendFileListener(SendFileListener sendFileListner) {
		this.sendFileListener = sendFileListner;
	}

	public void run() {
		while (true) {
			if (size > 0) {
				size--;
				String songPath = songPaths.pop();
				System.out.println(songPath);
				SendFileEvent ev = new SendFileEvent(this, songPath);
				if (sendFileListener != null) {
					sendFileListener.fileReady(ev);
				}

			}
		}
	}
}
