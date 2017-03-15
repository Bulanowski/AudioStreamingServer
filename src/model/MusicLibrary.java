package model;

import java.io.Serializable;
import java.util.ArrayList;

public class MusicLibrary implements Serializable {

	private static final long serialVersionUID = -4059770274326642864L;
	ArrayList<Song> songs;

	public MusicLibrary() {
		songs = new ArrayList<>();
	}

	public void addSong(Song a) {
		songs.add(a);
	}

	public ArrayList<Song> getSongList() {
		return songs;
	}

	public ArrayList<Song> findArtist(String s) {
		s = s.toLowerCase();
		ArrayList<Song> a = new ArrayList<>();
		for (Song song : songs) {
			if (song.getName().toLowerCase().contains(s)) {
				a.add(song);
			}
		}
		return a;
	}

	public Song getName(String name) {
		for (Song song : songs) {
			if (song.getName().equals(name)) {
				return song;
			}
		}
		return null;
	}

}
