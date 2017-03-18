package model;

import java.util.EventObject;

public class AudioBroadcasterStateChangedEvent extends EventObject {

	private static final long serialVersionUID = -9001672316258093848L;
	private boolean state;

	public AudioBroadcasterStateChangedEvent(Object source) {
		super(source);
	}

	public AudioBroadcasterStateChangedEvent(Object source, boolean state) {
		super(source);
		this.state = state;
	}

	public boolean getState() {
		return state;
	}

}
