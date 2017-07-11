package networking;

import javafx.beans.value.ChangeListener;
import javafx.util.Pair;
import model.ClientList;
import model.PackageType;
import model.Song;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by phil on 7/1/17.
 */
public class ClientQueueUpdater implements Runnable {

    private final ClientList clientList;
    private Thread thread;
    private ChangeListener listener;
    private LinkedHashMap<Integer, Song> songs;
    private boolean gotUpdate;

    public ClientQueueUpdater(ClientList clientList) {


        this.clientList = clientList;

        listener = (observableValue, o, t1) -> {
            gotUpdate = true;
        };
    }

    //TODO: Replace sharing the song queue with just sending clients single song queue updates
    public void setSongQueue(LinkedHashMap<Integer,Song> songs) {
        this.songs = songs;
    }

    public ChangeListener getListener() {
        return listener;
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
            if(gotUpdate) {
                System.out.println("Got queue update!");
                System.out.println("Queue size: "+songs.size());
                Iterator iterator = songs.entrySet().iterator();
                ArrayList<Pair<Integer,Song>> temp = new ArrayList<>();
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();

                    temp.add(new Pair<>((Integer)entry.getKey(),(Song)entry.getValue()));
                }
                clientList.sendAll(PackageType.SONG_QUEUE.getByte(), temp);
                gotUpdate = false;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
