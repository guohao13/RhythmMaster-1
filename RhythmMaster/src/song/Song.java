package song;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

import screens.ApplicationManager;
import screens.Main;

public class Song {
	private String songName;
	private Scanner fileScanner;
	private int msPerBeat = 0;
	private int lengthInTicks;
	private BeatMap beatMap;
	private ArrayList<ArrayDeque<Integer>> timeMap;
	private static final String[] SONG_PATHS = {
				"../levels/butterfly.txt",
				"../levels/wiimenu.txt" 		};
	
	public Song(int selection) {	
		processFile(SONG_PATHS[selection]);
	}
	
	private void processFile(String s) {
		File musicFile;
		
		try {
			musicFile = new File(Main.class.getResource(s).getPath());
		} catch (NullPointerException e) {
			musicFile = null;
			e.printStackTrace();
		}
		
		if(musicFile != null) {
			try {
				fileScanner = new Scanner(musicFile);
			} catch (FileNotFoundException e) {
				System.out.println("Song - file not found");
				e.printStackTrace();
			}
			
			songName = fileScanner.nextLine();
			msPerBeat = Integer.parseInt(fileScanner.nextLine());
			lengthInTicks = Integer.parseInt(fileScanner.nextLine());
					
			boolean[][] railData = new boolean[4][lengthInTicks];
			for(int tickIndex = 0; tickIndex < lengthInTicks; tickIndex++) {
				for(int railIndex = 0; railIndex < 4; railIndex++) {
					int x = fileScanner.nextInt();
					railData[railIndex][tickIndex] = intToBool(x);
				}
			}
			beatMap = new BeatMap(railData);
		}
	}
	
	private boolean intToBool(int x) {
		return x == 1;
	}

	public boolean[] getBitsAt(int index) {
		return beatMap.getRailBitsAt(index);
	}

	public int getMSPerBeat() {
		return msPerBeat;
	}
	
	public ArrayList<ArrayDeque<Integer>> getTimeMap() {
		return timeMap;
	}
	
	public void print() {
		System.out.println("Song name: " + this.songName);
		System.out.println("ms/beat: " + this.msPerBeat);
		beatMap.print();
	}

}