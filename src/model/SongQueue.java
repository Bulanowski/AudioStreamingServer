package model;

import javafx.util.Pair;
import networking.ClientQueueUpdater;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SongQueue implements Runnable {
	private Thread thread;
	private ClientQueueUpdater clientQueueUpdater;
	private final ClientList clientList;
	private final MusicLibraryManager manager;
	private final GlobalValues globalValues;
	private final LinkedHashMap<Integer, QueuedSong> songs; // Integer is key, should be unique ID
	private int key = 0;

	public SongQueue(ClientList clientList, MusicLibraryManager manager, GlobalValues globalValues) {
		this.clientList = clientList;
		this.manager = manager;
		this.globalValues = globalValues;
		songs = new LinkedHashMap<>();
	}

	public LinkedHashMap<Integer, QueuedSong> getSongQueue() {
		return songs;
	}

	public List<Pair<Integer, Song>> getSerializableQueue() {
		Iterator iterator = songs.entrySet().iterator();
		ArrayList<Pair<Integer,Song>> temp = new ArrayList<>();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			Integer thisKey = (Integer) entry.getKey();
			Song thisSong = ((QueuedSong)entry.getValue()).getSong();
			temp.add(new Pair<>(thisKey, thisSong));
		}
		return temp;
	}

	public synchronized void addSong(Client client, int id) throws NullPointerException {
		if (manager.listSong().get(id) == null) {
			throw new NullPointerException("Tried to add to queue null song id " + id);
		}
		key = key < 100000 ? key + 1 : 0;
		songs.put(key, new QueuedSong(client, manager.listSong().get(id)));
		Logger.getGlobal().log(Level.INFO, "Adding song to queue: " + manager.listSong().get(id));
        clientQueueUpdater.addQueueUpdate(true, key, manager.listSong().get(id));
    }

	public synchronized QueuedSong removeFirst() {
		Iterator iterator = songs.entrySet().iterator();
		Map.Entry entry = (Map.Entry) iterator.next();
		removeSong((Integer) entry.getKey());
		return (QueuedSong) entry.getValue();
	}

	public void voteToRemove(Client client, int key) {
		if (songs.get(key).addRemoveVote() > ((double) clientList.clients().size()) / 2.0 || client.equals(songs.get(key).getClient())) {
			removeSong(key);
		}
	}

	private synchronized void removeSong(int key) {
		Logger.getGlobal().log(Level.INFO, "Removing song from queue: " + songs.get(key).getSong());
		clientQueueUpdater.addQueueUpdate(false, key, songs.remove(key).getSong());
	}

	public void setClientQueueUpdater(ClientQueueUpdater clientQueueUpdater) {
		this.clientQueueUpdater = clientQueueUpdater;
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
			if (!clientList.isAnyonePlaying() && songs.size() > 0) {
				Song song = removeFirst().getSong();
				globalValues.setValue(GlobalValueType.SKIP_SONG, false);
				globalValues.setValue(GlobalValueType.SONG_TO_PLAY, song);
			} else {
					int votes = 0;
					for (Client c : clientList.clients()) {
						if (c.getVoteToSkip()) {
							votes++;
						}
					}
					if (votes > ((double) clientList.clients().size()) / 2.0) {
						Logger.getGlobal().log(Level.INFO, "Vote to skip passed");
						globalValues.setValue(GlobalValueType.SKIP_SONG, true);
						clientList.sendAll(PackageType.SONG, "stop");
						for (Client c : clientList.clients()) {
							c.stopPlaying();
						}
					}
				try {
					Thread.sleep(100);
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
