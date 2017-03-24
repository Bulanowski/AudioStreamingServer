package model;

import java.util.EventListener;

public interface SendSongListener extends EventListener {
	public void fileReady(SendSongEvent ev);
}
