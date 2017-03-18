package model;

import java.util.concurrent.ConcurrentLinkedQueue;

public class SongQueue implements Runnable {
	private Thread thread;
	private final ConcurrentLinkedQueue<String> songPaths;
	private SendFileListener sendFileListener;
	private final AudioBroadcasterStateChangedListener audioBroadcasterStateChangedListener;
	private boolean broadcasting = false;
	private volatile int size = 0;

	public SongQueue() {
		songPaths = new ConcurrentLinkedQueue<>();
		audioBroadcasterStateChangedListener = new AudioBroadcasterStateChangedListener() {

			@Override
			public void changedState(AudioBroadcasterStateChangedEvent ev) {
				broadcasting = ev.getState();
			}
			
		};
	}

	public synchronized void addSong(String song) {
		songPaths.offer(song);
		size++;
		System.out.println("Added song to queue: " + song);
	}

	public void setSendFileListener(SendFileListener sendFileListner) {
		this.sendFileListener = sendFileListner;
	}
	
	public AudioBroadcasterStateChangedListener getAudioBroadcasterStateChangedListener() {
		return audioBroadcasterStateChangedListener;
	}

	public void start() {
		if (thread == null) {
			System.out.println("Starting Song Queue!");
			thread = new Thread(this);
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
			if (!broadcasting && size > 0) {
				size--;
				String songPath = songPaths.poll();
				SendFileEvent ev = new SendFileEvent(this, songPath);
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
