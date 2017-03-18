package model;

import java.util.EventListener;

public interface AudioBroadcasterStateChangedListener extends EventListener {
	public void changedState(AudioBroadcasterStateChangedEvent ev);
}
