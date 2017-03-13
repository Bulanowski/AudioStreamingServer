package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Config {
	
	public static final String IPADDRESS = searchConfig("ipaddress:");
	public static final String MUSICDIR = searchConfig("musicDirectory:");
	public static final String MUSICLIBPATH = searchConfig("musicLibraryPath:");
	public static final String PLAYLISTDIR = searchConfig("playlistFolder:");
	public static final String QUERYUSER = searchConfig("TeamspeakQueryUsername:");
	public static final String QUERYPASS = searchConfig("TeamspeakQueryPassword:");
	
	
	private static String searchConfig(String s) {
		try {
			Scanner scan = new Scanner(new File("config.cfg"));
			while(scan.hasNextLine()) {
				String line = scan.nextLine();
				if(line.startsWith(s)) {
					line = line.replaceFirst(s, "");
					scan.close();
					return line;
				}
			}
			scan.close();
			
		} catch (FileNotFoundException e) {
			System.err.println("Configuration File not found.");
		}
		System.err.println(s+" is not set in the configuration file.");
		return null;
	}

}
