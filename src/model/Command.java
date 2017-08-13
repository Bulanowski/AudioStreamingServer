package model;

import controller.ConsoleInput;

import java.io.IOException;

/**
 * Created by Alex on 8/12/2017.
 */
public class Command {

    private final Object source;
    private final String commandString;
    private final CommandType commandType;
    private final String[] commandArgs;

    public Command(Object source, String commandString) throws IllegalArgumentException {
        this.source = source;
        this.commandString = commandString;
        commandType = CommandType.valueOf(commandString.split(" ")[0].toUpperCase());
        commandArgs = commandString.substring(commandString.indexOf(' ')+1).split(" ");
    }

    public Object getSource() {
        return source;
    }

    public boolean fromClient() {
        return source instanceof Client;
    }

    public void reply(PackageType packageType, Object object) {
        if (source instanceof Client) {
            try {
                ((Client) source).send(packageType, object);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (source instanceof ConsoleInput){

        }
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public String getCommandArgsAsString() {
        return commandString.substring(commandString.indexOf(' ') + 1);
    }

    public String getCommandArg(int i) {
        return commandArgs[i];
    }

    public String toString() {
        return commandString;
    }

}
