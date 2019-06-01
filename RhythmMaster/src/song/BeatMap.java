package song;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class BeatMap {
	
	private static final int NUM_RAILS = 4;
	private static final Color[] colorArray = {Color.green, Color.yellow, 
			Color.blue, Color.red, Color.orange, Color.cyan, Color.magenta};

	private Rail[] rails;
	private Color[] railColors;
	private int lengthInTicks;
	private ArrayList<ArrayDeque<Integer>> timeMap;
	
	BeatMap(boolean[][] railData) {
		rails = new Rail[NUM_RAILS];
		for(int x = 0; x < NUM_RAILS; x++)
			rails[x] = new Rail();

		lengthInTicks = railData[0].length;
		setRailData(railData);
		
		railColors = new Color[NUM_RAILS];
		setRailColors();
		
		timeMap = new ArrayList<>();
		for(int x = 0; x < NUM_RAILS; x++) {
			timeMap.add(rails[x].getTimeMap());
		}
	}
	
	private void setRailData(boolean[][] railData) {
		for(int tickIndex = 0; tickIndex < this.lengthInTicks; tickIndex++) {
			for(int railIndex = 0; railIndex < NUM_RAILS; railIndex++) {
				rails[railIndex].setBitAt(tickIndex, railData[railIndex][tickIndex]);
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
			System.out.print(rails[0].getBitAt(x) + " ");
			System.out.print(rails[1].getBitAt(x) + " ");
			System.out.print(rails[2].getBitAt(x) + " ");
			System.out.println(rails[3].getBitAt(x));
		}
	}
	
	public ArrayList<ArrayDeque<Integer>> getTimeMap() {
		return timeMap;
	}
}
