package model;

import java.util.EventObject;

public class SendSongEvent extends EventObject {

	private static final long serialVersionUID = 2561243231333905476L;
	private Song song;

	public SendSongEvent(Object source) {
		super(source);
	}

	public SendSongEvent(Object source, Song song) {
		super(source);
		this.song = song;
	}

	public Song getSong() {
		return song;
	}

}
