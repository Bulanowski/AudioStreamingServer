package model;

import java.util.concurrent.ConcurrentLinkedQueue;

public class SongQueue implements Runnable {
	private Thread thread;
	private SendSongListener sendFileListener;
	private final ClientList clientList;
	private final ConcurrentLinkedQueue<Integer> songIDs;
	private final MusicLibraryManager manager;
	private volatile int size = 0;

	public SongQueue(ClientList clientList, MusicLibraryManager manager) {
		this.clientList = clientList;
		this.manager = manager;
		songIDs = new ConcurrentLinkedQueue<>();
	}

	public synchronized void addSong(int id) {
		songIDs.offer(id);
		size++;
		System.out.println("Added song to queue: " + id);
	}

	public void setSendFileListener(SendSongListener sendFileListner) {
		this.sendFileListener = sendFileListner;
	}

	public void start() {
		if (thread == null) {
			System.out.println("Starting Song Queue!");
			thread = new Thread(this,"Song-Queue");
			thread.start();
		}
	}

	// TODO: Evaluate if this really needs to be in a separate thread
	// For now probably unless a signal can be returned at the end of the song (which we can do actually)
	// However, that does not account for when there is an empty queue when a song ends.
	// But that can be countered by adding functionality to the addSong method to start playing if the queue is empty and nothing is playing
	@Override
	public void run() {
		while (thread != null) {
			if (!clientList.isAnyonePlaying() && size > 0) {
				size--;
				int id = songIDs.poll();
				
				SendSongEvent ev = new SendSongEvent(this, manager.listSong().get(id));
				if (sendFileListener != null) {
					sendFileListener.fileReady(ev);
				}
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void stop() {
		thread = null;
	}
}
