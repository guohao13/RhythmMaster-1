package song;

import java.io.File;

public class Song {
	
	private static final int NUM_RAILS = 4;
	
	private String songName;
	private File musicFile;
	private BeatMap beatMap;
	
	Song(String name, File music, BeatMap bm) {
		this.songName = name;
		this.musicFile = music;
		this.beatMap = bm;
	}

	public void load() {
		
	}
	
	public void play() {
		
	}
	
	public void stop() {
		
	}
	
	public void setSong(String songName) {
		
	}
	
	public boolean[] getBitsAt(int index) {
		return beatMap.getRailBitsAt(index);
	}
	
}