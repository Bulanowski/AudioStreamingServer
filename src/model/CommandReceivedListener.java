package model;

import java.util.EventListener;

public interface CommandReceivedListener extends EventListener {
	void commandReceived(CommandReceivedEvent ev);
}
