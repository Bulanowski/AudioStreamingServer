package model;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import util.FindFile;

public class Playlist implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6235933546213398317L;
	ArrayList<File> files;
	
	public Playlist() {
		files = new ArrayList<>();
	}
	
	public File getFile(int i) {
		return files.get(i);
	}
	
	public void addFile(File f) {
		files.add(f);
	}
	
	public void addDirectory(File dir) {
		if(dir.isDirectory()) {
			File[] temp = dir.listFiles();
			for (int i = 0; i < temp.length; i++) {
				if(!temp[i].isDirectory()&&FindFile.isAudioFile(temp[i])) {
					files.add(temp[i]);
				}//Maybe implement recursion for sub directories?
			}
			
			
		}
	}

	
}
