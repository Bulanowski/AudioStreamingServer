package model;

/**
 * Created by Alex on 8/12/2017.
 */
public class QueuedSong {

    private final Client client;
    private final Song song;
    private int removeVotes = 0;

    public QueuedSong(Client client, Song song) {
        this.client = client;
        this.song = song;
    }

    public Client getClient() {
        return client;
    }

    public Song getSong() {
        return song;
    }

    public int getRemoveVotes() {
        return removeVotes;
    }

    public int addRemoveVote() {
        return ++removeVotes;
    }
}
