package networking;

import javafx.util.Pair;
import model.ClientList;
import model.PackageType;
import model.Song;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by phil on 7/1/17.
 */
public class ClientQueueUpdater implements Runnable {

    private final ClientList clientList;
    private final LinkedBlockingQueue<Pair<Boolean, Pair<Integer, Song>>> queueUpdates; //Pair<(true=add/false=delete), Pair<key, Song>>
    private Thread thread;

    public ClientQueueUpdater(ClientList clientList) {
        this.clientList = clientList;
        queueUpdates = new LinkedBlockingQueue<>(100000);
    }

    public void addQueueUpdate(boolean action, int key, Song song) {
        try {
            queueUpdates.put(new Pair<>(action, new Pair<>(key, song)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this, "Client Queue Updater");
            thread.start();
            System.out.println("Starting Client Queue Updater!");
        }
    }

    @Override
    public void run() {
        while(thread != null) {
            try {
                clientList.sendAll(PackageType.SONG_QUEUE, queueUpdates.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
