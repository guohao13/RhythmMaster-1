package song;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Song {
	
	private static final int NUM_RAILS = 4;
	
	private String songName;
	private Scanner fileScanner;
	private int bpm = 0;
	private int lengthInTicks;
	private BeatMap beatMap;
	
	
	public Song(String fileName) {
		processFile(fileName);
	}
	
	private void processFile(String s) {
		File musicFile;
		
		try {
			musicFile = new File(s);
		} catch (NullPointerException e) {
			// can't find song file
			musicFile = null;
			e.printStackTrace();
		}
		
		if(musicFile != null) {
			try {
				this.fileScanner = new Scanner(musicFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			this.songName = fileScanner.nextLine();
			this.bpm = Integer.parseInt(fileScanner.nextLine());
			this.lengthInTicks = Integer.parseInt(fileScanner.nextLine());
					
			boolean[][] railData = new boolean[NUM_RAILS][this.lengthInTicks];
			for(int tickIndex = 0; tickIndex < lengthInTicks; tickIndex++) {
				for(int railIndex = 0; railIndex < NUM_RAILS; railIndex++) {
					int x = fileScanner.nextInt();
					railData[railIndex][tickIndex] = intToBool(x);
				}
			}
			this.beatMap = new BeatMap(railData);
		}
	}
	
	private boolean intToBool(int x) {
		return x == 1;
	}

	
	public boolean[] getBitsAt(int index) {
		return beatMap.getRailBitsAt(index);
	}
	
	public void print() {
		System.out.println("Song name: " + this.songName);
		System.out.println("BPM: " + this.bpm);
		this.beatMap.print();
	}
	
	public static void main(String[] args) {
		Song s = new Song("testSong");
		s.print();
	}
}