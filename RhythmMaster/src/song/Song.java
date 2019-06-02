package song;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

public class Song {
	static final String[] SONG_PATHS = { 	"src/levels/butterfly.txt",	// TODO: change to relative paths
											"src/levels/badapple.txt" };
	private static final int NUM_RAILS = 4;
	
	private String songName;
	private Scanner fileScanner;
	private int bpm = 0;
	private int lengthInTicks;
	private BeatMap beatMap;
	private ArrayList<ArrayDeque<Integer>> timeMap;
	
	public Song(int selection) {	
		processFile(SONG_PATHS[selection]);
	}
	
	private void processFile(String s) {
		File musicFile;
		
		try {
			musicFile = new File(s);
		} catch (NullPointerException e) {
			musicFile = null;
			e.printStackTrace();
		}
		
		if(musicFile != null) {
			try {
				fileScanner = new Scanner(musicFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			songName = fileScanner.nextLine();
			bpm = Integer.parseInt(fileScanner.nextLine());
			lengthInTicks = Integer.parseInt(fileScanner.nextLine());
					
			boolean[][] railData = new boolean[NUM_RAILS][lengthInTicks];
			for(int tickIndex = 0; tickIndex < lengthInTicks; tickIndex++) {
				for(int railIndex = 0; railIndex < NUM_RAILS; railIndex++) {
					int x = fileScanner.nextInt();
					railData[railIndex][tickIndex] = intToBool(x);
				}
			}
			beatMap = new BeatMap(railData);
			timeMap = beatMap.getTimeMap();
		}
	}
	
	private boolean intToBool(int x) {
		return x == 1;
	}

	public boolean[] getBitsAt(int index) {
		return beatMap.getRailBitsAt(index);
	}

	public int getBpm() {
		return bpm;
	}
	
	public ArrayList<ArrayDeque<Integer>> getTimeMap() {
		return timeMap;
	}
	
	public void print() {
		System.out.println("Song name: " + this.songName);
		System.out.println("BPM: " + this.bpm);
		beatMap.print();
	}

}