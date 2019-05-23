package song;

import java.awt.Color;

public class BeatMap {
	
	private static final int NUM_RAILS = 4;
	private static final Color[] colorArray = {Color.green, Color.yellow, 
			Color.blue, Color.red, Color.orange, Color.cyan, Color.magenta};

	private Rail[] rails;
	private Color[] railColors;
	private int lengthInTicks;
	
	BeatMap(boolean[][] railData) {
		this.rails = new Rail[NUM_RAILS];
		for(int x = 0; x < NUM_RAILS; x++) {
			rails[x] = new Rail();
		}
		this.lengthInTicks = railData[0].length;
		setRailData(railData);
		
		this.railColors = new Color[NUM_RAILS];
		setRailColors();
	}
	
	private void setRailData(boolean[][] railData) {
		// for each line AKA tick
		for(int tickIndex = 0; tickIndex < this.lengthInTicks; tickIndex++) {
			// for each rail set the bits @ tick
			for(int railIndex = 0; railIndex < NUM_RAILS; railIndex++) {
				this.rails[railIndex].setBitAt(tickIndex, railData[railIndex][tickIndex]);
			}
		}
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

	public void print() {
		for(int x = 0; x < lengthInTicks; x++) {
			System.out.print(this.rails[0].getBitAt(x) + " ");
			System.out.print(this.rails[1].getBitAt(x) + " ");
			System.out.print(this.rails[2].getBitAt(x) + " ");
			System.out.println(this.rails[3].getBitAt(x));
		}
	}
}
