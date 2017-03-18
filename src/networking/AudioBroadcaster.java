package networking;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import model.ClientList;
import model.PackageType;
import model.SendFileEvent;
import model.SendFileListener;

public class AudioBroadcaster implements Runnable {

	private Thread thread;
	private final ClientList clientList;
	private final SendFileListener sendFileListener;
	private volatile File songFile;
	private final int PACKET_SIZE = 10;

	public AudioBroadcaster(ClientList clientList) {
		this.clientList = clientList;
		sendFileListener = new SendFileListener() {

			@Override
			public void fileReady(SendFileEvent ev) {
				if (songFile == null) {
					songFile = new File(ev.getFilePath());
					clientList.startPlaying();
				} else {
					System.out.println("Tried to change songFile in AudioBroadcaster from " + songFile.getAbsolutePath() + " to " + new File(ev.getFilePath()));
				}
			}
		};
	}

	public void start() {
		if (thread == null) {
			System.out.println("Starting Audio Broadcaster!");
			thread = new Thread(this);
			thread.start();
		}
	}

	public SendFileListener getSendFileListener() {
		return sendFileListener;
	}
	
	public boolean isBroadcasting() {
		return (songFile != null);
	}

//	public void setBroadcastedFile(File songFile) {
//		this.songFile = songFile;
//	}

	@Override
	public void run() {
		while (thread != null) {
			if (songFile != null) {
				try {
					FileInputStream fileInStream = new FileInputStream(songFile);
					Integer size = fileInStream.available();
//					byte[] number = new byte[4];
//					ByteBuffer.wrap(number).putInt(size);
					System.out.println(size);
					clientList.sendAll(PackageType.BUFFER_SIZE.getByte(), size);
					while (fileInStream.available() != 0) {
						byte[] buffer = new byte[PACKET_SIZE];
						fileInStream.read(buffer);
						clientList.sendAll(PackageType.AUDIO.getByte(), buffer);
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
