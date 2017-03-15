package model;

import java.util.EventObject;

public class CommandReceivedEvent extends EventObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6206943855744126338L;
	String command;

	public CommandReceivedEvent(Object source) {
		super(source);
	}
	
	public CommandReceivedEvent(Object source, String command) {
		super(source);
		this.command = command;
	}
	
	public String getCommand() {
		return command;
	}

}
