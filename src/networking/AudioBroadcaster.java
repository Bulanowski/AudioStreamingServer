package networking;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.util.Pair;
import model.*;

public class AudioBroadcaster implements Runnable {

	private Thread thread;
	private final ClientList clientList;
	private volatile File songFile;
	private volatile Song song;
	private final GlobalValues globalValues;
	private final int PACKET_SIZE = 32;

	public AudioBroadcaster(ClientList clientList, GlobalValues globalValues) {
		this.clientList = clientList;
		this.globalValues = globalValues;
		globalValues.addValueChangedListener(GlobalValueType.SONG_TO_PLAY, (observable, oldValue, newValue) -> {
			Song newSong = (Song) newValue;
			if (songFile == null) {
				songFile = new File(newSong.getPath());
				song = newSong;
				clientList.startPlaying();
			} else {
				System.err.println("Tried to change songFile in AudioBroadcaster from " + songFile.getAbsolutePath()
						+ " to " + new File(newSong.getPath()));
			}
        });
	}

	public void start() {
		if (thread == null) {
			thread = new Thread(this, "Audio-Broadcaster");
			thread.start();
			System.out.println("Starting Audio Broadcaster!");
		}
	}

	@Override
	public void run() {
		while (thread != null) {
			if (songFile != null) {
				try {
					FileInputStream fileInStream = new FileInputStream(songFile);
					Integer size = fileInStream.available() + PACKET_SIZE;
					clientList.sendAll(PackageType.SONG, new Pair<>(size, song));
					while (fileInStream.available() != 0 && !(Boolean) globalValues.getValue(GlobalValueType.SKIP_SONG, false)) {
						byte[] buffer = new byte[PACKET_SIZE];
						fileInStream.read(buffer);
						clientList.sendAll(PackageType.SONG, buffer);
					}
					Logger.getGlobal().log(Level.INFO, "Finished sending");
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
