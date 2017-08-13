package controller;

import model.Command;
import model.CommandReceivedEvent;
import model.CommandReceivedListener;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Alex on 8/12/2017.
 */
public class ConsoleInput implements Runnable{
    private Thread thread;
    private final CommandReceivedListener commandReceivedListener;

    public ConsoleInput(CommandReceivedListener commandReceivedListener) {
        this.commandReceivedListener = commandReceivedListener;
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this,"Console Input");
            thread.start();
            System.out.println("Starting Console Input!");
        }
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (thread != null) {
            String input = scanner.nextLine();
            try {
                CommandReceivedEvent ev = new CommandReceivedEvent(new Command(this, input));
                if (commandReceivedListener != null) {
                    commandReceivedListener.commandReceived(ev);
                }
            } catch (IllegalArgumentException ex) {
                Logger.getGlobal().log(Level.WARNING, "Invalid console command " + input);
            }
        }
    }

    public void stop() {
        thread = null;
    }

}
