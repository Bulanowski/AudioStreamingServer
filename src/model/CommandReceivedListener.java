package model;

import java.util.EventListener;

public interface CommandReceivedListener extends EventListener {
	public void commandReceived(CommandReceivedEvent ev);
}
