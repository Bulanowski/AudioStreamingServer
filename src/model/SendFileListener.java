package model;

import java.util.EventListener;

public interface SendFileListener extends EventListener {
	public void fileReady(SendFileEvent ev);
}
