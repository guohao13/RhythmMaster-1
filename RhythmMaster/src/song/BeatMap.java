package song;

import java.awt.Color;

//test comment

public class BeatMap {
	
	private static final int NUM_RAILS = 4;
	private static final Color[] colorArray = {Color.green, Color.yellow, 
			Color.blue, Color.red, Color.orange, Color.cyan, Color.magenta};

	private Rail[] rails;
	private Color[] railColors;
	//private int currIndex;
	private int bpm;
	
	BeatMap() {
		rails = new Rail[NUM_RAILS];
		railColors = new Color[NUM_RAILS];
		setRailColors();
		this.bpm = 100;
	}
	
	BeatMap(boolean[][] bits, int bpm) {
		rails = new Rail[NUM_RAILS];
		setRailData();
		railColors = new Color[NUM_RAILS];
		setRailColors();
		this.bpm = bpm;
	}
	
	private void setRailData() {
		
	}
	
	private void setRailColors() {
		for(int x = 0; x < NUM_RAILS; x++)
			railColors[x] = colorArray[x];
	}
	
	public boolean[] getRailBitsAt(int index) {
		boolean[] railBits = new boolean[NUM_RAILS];
		
		for(int x = 0; x < NUM_RAILS; x++) 
			railBits[x] = rails[x].getBitAt(index);
		
		return railBits;
	}

}
