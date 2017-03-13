package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;

public class SongQueue extends Thread {
	private LinkedList<String> songPaths;
	private int packetSize = 20;
	private volatile int size = 0;
	private AudioPacketListener audioPacketListener;

	public SongQueue() {
		songPaths = new LinkedList<>();
	}

	public void addSong(String song) {
		songPaths.add(song);
		System.out.println("Added Song to Queue :" + songPaths.isEmpty());
		size++;
	}

	public void setAudioBufferListener(AudioPacketListener audioBufferListner) {
		this.audioPacketListener = audioBufferListner;
	}

	public void run() {
		while (true) {
			if (size > 0) {
				size--;
				File file = new File(songPaths.pop());
				try {
					FileInputStream fileInput = new FileInputStream(file);
					while (fileInput.available() >= 20) {
						byte[] buffer = new byte[packetSize];
						fileInput.read(buffer);
						AudioPacketEvent ev = new AudioPacketEvent(this, buffer);
						if (audioPacketListener != null) {
							audioPacketListener.audioBufferFilled(ev);
						}

					}

					byte[] buffer = new String("end").getBytes();
					AudioPacketEvent ev = new AudioPacketEvent(this, buffer);
					if (audioPacketListener != null) {
						audioPacketListener.audioBufferFilled(ev);
					}
					fileInput.close();

				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}
}
