package screens;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import screens.Marker.ObservableYCoord;

public class MissedNoteObserver implements Observer {
	
	private GameScreenController parent;
	private int[] railStatus = {0,0,0,0};	// index = rail #
										// 0 = inactive
										// 1 = in window,no keypress
										// 2 = in window, key pressed
	
	MissedNoteObserver(GameScreenController gsc) {
		this.parent = gsc;
	}

	@Override
	public void update(Observable o, Object obj) {
		int railIndex = ((Marker) obj).getRailIndex();
		
		// entering keypress window
		if(((Marker) obj).isNearHitbar && railStatus[railIndex] == 0) {	
			railStatus[railIndex] = 1;
		}
		// inside window, no keypress
		else if(((Marker) obj).isNearHitbar && railStatus[railIndex] == 1 ) {
				// do nothing
		}
		// inside window, yes keypress
		else if(((Marker) obj).isNearHitbar && railStatus[railIndex] == 2) {	
				// do nothing
		}
		// exiting window, no keypress
		else if( !((Marker) obj).isNearHitbar && railStatus[railIndex] == 1) {
			parent.playerStatus.updateStatus(false);
			System.out.println("   missed no press");
			railStatus[railIndex] = 0;
		}
		// exiting window, yes keypress
		else if( !((Marker) obj).isNearHitbar && railStatus[railIndex] == 2) {
			railStatus[railIndex] = 0;
		}
	}
	
	public void registerKeypress(int railIndex) {
		if(railStatus[railIndex] == 1)
			railStatus[railIndex] = 2;
	}
}
