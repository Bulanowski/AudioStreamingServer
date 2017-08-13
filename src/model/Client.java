package model;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

public class Client implements Runnable {
	private Thread thread;
	private final Socket socket;
	private final ObjectOutputStream output;
	private final ObjectInputStream input;
	private final CommandReceivedListener commandReceivedListener;
	private final String hostAddress;
	private final int id;
	private String username;
	private volatile boolean playing = false;
	private volatile boolean skip = false;

	public Client(Socket socket, CommandReceivedListener commandReceivedListener) throws IOException {
		this.socket = socket;
		this.commandReceivedListener = commandReceivedListener;
		output = new ObjectOutputStream(socket.getOutputStream());
		input = new ObjectInputStream(socket.getInputStream());
		hostAddress = socket.getInetAddress().getHostAddress();
		id = socket.getPort();
	}

	public void start() {
		if (thread == null) {
			thread = new Thread(this, "Client-" + hostAddress);
			thread.start();
		}
	}

	public boolean getPlaying() {
		return playing;
	}

	public void startPlaying() {
		playing = true;
	}

	public void stopPlaying() {
		playing = false;
		skip = false;
	}

	public void voteToSkip() {
		skip = true;
	}

	public boolean getVoteToSkip() {
		return skip;
	}

	// TODO: Read input from Client
	// Each client will have their own thread that handles commands received
	// from the client
	// This thread will also be responsible for sending data back to the client
	// that the client requests
	@Override
	public void run() {
		while (socket.isConnected()) {
			try {
				String inFromClient = input.readUTF();
				CommandReceivedEvent ev = new CommandReceivedEvent(new Command(this, inFromClient));
				if (commandReceivedListener != null) {
					commandReceivedListener.commandReceived(ev);
				}
			} catch (SocketException e) {
				System.err.println(e.getMessage());
			} catch (EOFException e) {
				try {
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized void send(PackageType packageType, Object obj) throws IOException {
		output.writeByte(packageType.getByte());
		output.writeObject(obj);
	}

	public void stop() {
		try {
			System.out.println("Closing connection with " + hostAddress);
			if (socket != null) {
				socket.close();
			}
			thread = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isConnected() {
		return socket.isConnected();
	}

	public InetAddress getInetAddress() {
		return socket.getInetAddress();
	}

	public String getName() {
		return username;
	}

	public void setName(String newUsername) {
		if (username != null) {
			System.out.println(hostAddress + " changed username from " + username + " to " + newUsername);
			username = newUsername;
		} else {
			System.out.println(hostAddress + " set username to " + newUsername);
			username = newUsername;
		}
		
	}

	public int getId() {
		return id;
	}

}
