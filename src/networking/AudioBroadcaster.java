package networking;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.util.Pair;
import model.ClientList;
import model.PackageType;
import model.SendSongEvent;
import model.SendSongListener;
import model.Song;

public class AudioBroadcaster implements Runnable {

	private Thread thread;
	private final ClientList clientList;
	private final SendSongListener sendFileListener;
	private volatile File songFile;
	private volatile Song song;
	private final int PACKET_SIZE = 32;

	public AudioBroadcaster(ClientList clientList) {
		this.clientList = clientList;
		sendFileListener = ev -> {
            if (songFile == null) {

                songFile = new File(ev.getSong().getPath());
                song = ev.getSong();
                clientList.startPlaying();
            } else {
                System.err.println("Tried to change songFile in AudioBroadcaster from " + songFile.getAbsolutePath()
                        + " to " + new File(ev.getSong().getPath()));
            }
        };
	}

	public void start() {
		if (thread == null) {
			thread = new Thread(this, "Audio-Broadcaster");
			thread.start();
			System.out.println("Starting Audio Broadcaster!");
		}
	}

	public SendSongListener getSendFileListener() {
		return sendFileListener;
	}

	@Override
	public void run() {
		while (thread != null) {
			if (songFile != null) {
				try {
					FileInputStream fileInStream = new FileInputStream(songFile);
					Integer size = fileInStream.available() + PACKET_SIZE;
					System.out.println(size);
					clientList.sendAll(PackageType.SONG.getByte(), new Pair<>(size, song));
					while (fileInStream.available() != 0) {
						byte[] buffer = new byte[PACKET_SIZE];
						fileInStream.read(buffer);
						clientList.sendAll(PackageType.SONG.getByte(), buffer);
					}
					System.out.println("Finished sending.");
					fileInStream.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					songFile = null;
				}
			} else {
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
