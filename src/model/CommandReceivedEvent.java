package model;

import java.util.EventObject;

public class CommandReceivedEvent extends EventObject {

	private static final long serialVersionUID = 6206943855744126338L;
	Command command;

	public CommandReceivedEvent(Object source) {
		super(source);
	}

	public CommandReceivedEvent(Command command) {
		super(command.getSource());
		this.command = command;
	}

	public Command getCommand() {
		return command;
	}

}
