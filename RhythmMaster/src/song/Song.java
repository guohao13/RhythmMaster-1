package song;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import screens.Main;

public class Song {
	private String songName;
	private Scanner fileScanner;
	private int microsecPerBeat = 0;
	private int delay = 0;
	private int lengthInTicks = 0;
	private BeatMap beatMap;
	private static final String[] SONG_PATHS = {
													"../levels/butterfly.txt",
													"../levels/wiimenu.txt" 	};
	
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
			microsecPerBeat = Integer.parseInt(fileScanner.nextLine());
			delay = Integer.parseInt(fileScanner.nextLine());
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

	public int getMicroSecPerBeat() {
		return microsecPerBeat;
	}
	
	public int getDelay() {
		return delay;
	}
}