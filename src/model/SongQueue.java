package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;

import java.util.*;

public class SongQueue implements Runnable {
	private Thread thread;
	private SendSongListener sendFileListener;
	private final ClientList clientList;
	private final LinkedHashMap<Integer, Song> songs;
	private final MusicLibraryManager manager;
	private int key = 0;
	private volatile SimpleIntegerProperty size = new SimpleIntegerProperty(0);

	public SongQueue(ClientList clientList, MusicLibraryManager manager) {
		this.clientList = clientList;
		this.manager = manager;
		songs = new LinkedHashMap<>();
	}

	public void addSongQueueListener(ChangeListener listener) {
	    size.addListener(listener);
    }

	public LinkedHashMap<Integer, Song> getSongQueue() {
		return songs;
	}


	public synchronized void addSong(int id) {
		key = key < 100000 ? key + 1 : 0;
		songs.put(key,manager.listSong().get(id));
		size.set(size.add(1).get());
        System.out.println("Added song to queue: " + id);
    }

	public synchronized Song removeFirst() {
		Iterator iterator = songs.entrySet().iterator();
		Map.Entry entry = (Map.Entry) iterator.next();
		iterator.remove();


		return (Song) entry.getValue();
	}

	public synchronized void removeSong(int id) {
		Iterator iterator = songs.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			if(((int) entry.getKey()) == id) {
				iterator.remove();
			}
		}

		size.set(size.subtract(1).get());

	}

	public void setSendFileListener(SendSongListener sendFileListener) {
		this.sendFileListener = sendFileListener;
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
			if (!clientList.isAnyonePlaying() && size.getValue() > 0) {
				Song song = removeFirst();
				size.set(size.subtract(1).get());

				SendSongEvent ev = new SendSongEvent(this, song);
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
