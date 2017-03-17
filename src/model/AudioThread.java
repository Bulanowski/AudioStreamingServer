package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class AudioThread extends Thread {
	// private Client client;
	// private InetAddress address;
	private Socket socket;
	// private byte[] buffer;
	// private int id = -1;
	private int packetSize = 10;
	private File songFile;
	private OutputStream outputStream;
	@SuppressWarnings("unused")
	private volatile boolean audioSending = false;
	private long startingBytes;

	public AudioThread(Socket socket) {
		this.socket = socket;
		try {
			outputStream = socket.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isConnected() {
		return socket.isConnected();
	}

	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendAudioBuffer(byte[] buffer) {
		try {
			outputStream.write(buffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendSongFile(File songFile) {
		this.songFile = songFile;
	}

	public void run() {
		try {
			while (!this.isInterrupted()) {
				Thread.sleep(500);
				if (songFile != null) {

					audioSending = true;
					FileInputStream fileInStream = new FileInputStream(songFile);
					AudioInputStream in = AudioSystem.getAudioInputStream(fileInStream);
					// AudioFormat baseFormat = in.getFormat();
					in.close();
					fileInStream = new FileInputStream(songFile);
					// sameSizeInBits = 16, 8 bits per byte
					// float bytesPerMillisecond = ((baseFormat.getSampleRate()
					// *
					// 16.0f) / 8.0f) * 0.5f;
					// int loopAmount = (int) (bytesPerMillisecond/packetSize);
					fileInStream.skip(startingBytes);
					// int i = 0;
					// while (fileInStream.available()!=0) {
					// for (int j = 0; j < loopAmount; j++) {
					// byte[] buffer = new byte[packetSize];
					// fileInStream.read(buffer);
					// outputStream.write(buffer);
					// }
					// System.out.println(++i);
					// Thread.sleep(100);
					// }
					Integer size = fileInStream.available();
					byte[] number = new byte[4];
					ByteBuffer.wrap(number).putInt(size);
					System.out.println(size);
					outputStream.write(number);
					while (fileInStream.available() != 0) {
						byte[] buffer = new byte[packetSize];
						fileInStream.read(buffer);
						outputStream.write(buffer);
					}

					System.out.println("Finished sending.");
					fileInStream.close();
					audioSending = false;
					songFile = null;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
