package util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.Config;
import model.Playlist;

public class moveFiles {
	File musicFolder = new File(Config.MUSICDIR);
	static File playlistFolder = new File(Config.PLAYLISTDIR);
	
	
	public static void savePlaylist(File dir) {
		String playlistName = dir.getName();
		Playlist pl = new Playlist();
		pl.addDirectory(dir);
		try {
		FileOutputStream fos = new FileOutputStream(playlistFolder.getAbsolutePath()+"/"+playlistName+".plz");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(pl);
		oos.close();
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Playlist openPlaylist(File playlist) {
		try {
			FileInputStream fis = new FileInputStream(playlist);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Playlist pl = (Playlist) ois.readObject();
			ois.close();
			return pl;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public void move(File dir) {
		if(dir.isDirectory()) {
//			Files.move(source, target, options)
		}
	}
	
	
}
