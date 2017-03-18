package networking;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import model.AudioBroadcasterStateChangedEvent;
import model.AudioBroadcasterStateChangedListener;
import model.ClientList;
import model.PackageType;
import model.SendFileEvent;
import model.SendFileListener;

public class AudioBroadcaster implements Runnable {

	private Thread thread;
	private final ClientList clientList;
	private final SendFileListener sendFileListener;
	private AudioBroadcasterStateChangedListener audioBroadcasterStateChangedListener;
	private volatile File songFile;
	private final int PACKET_SIZE = 10;

	public AudioBroadcaster(ClientList clientList) {
		this.clientList = clientList;
		sendFileListener = new SendFileListener() {

			@Override
			public void fileReady(SendFileEvent ev) {
				if (songFile == null) {
					songFile = new File(ev.getFilePath());
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
	
	public void setAudioBroadcasterStateChangedListener(AudioBroadcasterStateChangedListener audioBroadcasterStateChangedListener) {
		this.audioBroadcasterStateChangedListener = audioBroadcasterStateChangedListener;
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
					AudioBroadcasterStateChangedEvent ev = new AudioBroadcasterStateChangedEvent(this, true);
					if (audioBroadcasterStateChangedListener != null) {
						audioBroadcasterStateChangedListener.changedState(ev);
					}
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
					AudioBroadcasterStateChangedEvent ev = new AudioBroadcasterStateChangedEvent(this, false);
					if (audioBroadcasterStateChangedListener != null) {
						audioBroadcasterStateChangedListener.changedState(ev);
					}
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
