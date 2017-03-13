package model;

import java.util.EventObject;

public class AudioPacketEvent extends EventObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2561243231333905476L;
	private byte[] buffer;
	
	
	public AudioPacketEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}
	
	public AudioPacketEvent(Object source, byte[] buffer) {
		super(source);
		this.buffer = buffer;
	}
	
	public byte[] getBuffer() {
		return buffer;
	}

}
