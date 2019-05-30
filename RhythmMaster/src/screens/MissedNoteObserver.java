package screens;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import screens.Marker.ObservableYCoord;

public class MissedNoteObserver implements Observer {
	
	private GameScreenController parent;
	private int[] status = {0,0,0,0};	// index = rail #
										// 0 = inactive
										// 1 = in window,no keypress
										// 2 = in window, key pressed
	
	MissedNoteObserver(GameScreenController gsc, ArrayList<ArrayDeque<Integer>> timeMap) {
		this.parent = gsc;
	}

	@Override
	public void update(Observable o, Object obj) {
		int railIndex = ((Marker) obj).getRailIndex();
		
		// entering keypress window
		if(((Marker) obj).isNearHitbar && status[railIndex] == 0) {	
			status[railIndex] = 1;
		}
		// inside window, no keypress
		else if(((Marker) obj).isNearHitbar && status[railIndex] == 1 ) {
				// do nothing
		}
		// inside window, yes keypress
		else if(((Marker) obj).isNearHitbar && status[railIndex] == 2) {	
				// do nothing
		}
		// exiting window, no keypress
		else if( !((Marker) obj).isNearHitbar && status[railIndex] == 1) {
			parent.playerStatus.updateStatus(false);
			status[railIndex] = 0;
		}
		// exiting window, yes keypress
		else if( !((Marker) obj).isNearHitbar && status[railIndex] == 2) {
			status[railIndex] = 0;
		}
	}
	
	public void registerKeypress(int railIndex) {
		if(status[railIndex] == 1)
			status[railIndex] = 2;
	}
}
