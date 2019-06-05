package song;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class BeatMap {

	private Rail[] rails;
	private int lengthInTicks;
	
	BeatMap(boolean[][] railData) {
		rails = new Rail[4];
		for(int x = 0; x < 4; x++)
			rails[x] = new Rail();

		lengthInTicks = railData[0].length;
		setRailData(railData);
	}
	
	private void setRailData(boolean[][] railData) {
		for(int tickIndex = 0; tickIndex < this.lengthInTicks; tickIndex++) {
			for(int railIndex = 0; railIndex < 4; railIndex++) {
				rails[railIndex].setBitAt(tickIndex, railData[railIndex][tickIndex]);
			}
		}
	}

	public boolean[] getRailBitsAt(int index) {
		boolean[] railBits = new boolean[4];
		
		for(int x = 0; x < 4; x++) 
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
}
