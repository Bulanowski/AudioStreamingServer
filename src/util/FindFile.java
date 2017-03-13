package util;

import java.io.File;
import java.util.ArrayList;

import model.Config;

public class FindFile {
	
	public static ArrayList<File> find(String s) {
		return recFind(new File(Config.MUSICDIR),s);
	}
	
	
	public static boolean isAudioFile(File f) {
		
		if(f.getName().contains(".mp3")||f.getName().contains(".wav")||f.getName().contains(".flac")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isPlaylistFile(File f) {
		if(f.getName().contains(".plz")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean check(File f) {
		if(isAudioFile(f)||isPlaylistFile(f)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static ArrayList<File> recFind(File f,String s) {
			File startDir = f;
			File[] startList = startDir.listFiles();
			ArrayList<File> endList = new ArrayList<>();
			for (int i = 0; i < startList.length; i++) {
				File song = startList[i];
				if(song.isDirectory()) {
					endList.addAll(recFind(song,s));
				}
				else if(song.getName().toLowerCase().contains(s)&&isAudioFile(song)) {
					endList.add(song);
				}
			}
			
			return endList;
		
	}
	
}
