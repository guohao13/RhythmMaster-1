package screens;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class HitDetectionObserver implements Observer {
	
	private GameScreenController parent;
	private int[] railStatus = {0,0,0,0};	// index = rail #
										// 0 = inactive
										// 1 = in window,no keypress
										// 2 = in window, key pressed
	private ArrayList<Marker>[] markersInHitbar = new ArrayList[4];
	
	HitDetectionObserver(GameScreenController gsc) {
		this.parent = gsc;
		for(int x = 0; x < 4; x++) {
			markersInHitbar[x] = new ArrayList<>();
		}
	}

	@Override
	synchronized public void update(Observable o, Object obj) {
		Marker m = (Marker) obj;
		int railIndex = m.getRailIndex();
		ArrayList<Marker> rail = markersInHitbar[railIndex];
		
		// marker is transitioning INTO hitbar
		if(m.isInHitbar) {
			rail.add(m);
		}
		else {
			if(rail.remove(m)) {
				m.y_coord.deleteObservers();
				parent.playerStatus.updateStatus(false);
				System.out.println("      MISS-no keypress on rail " + railIndex);
			}
		}
	}
	
	synchronized public boolean registerKeypress(int railIndex) {
		ArrayList<Marker> rail = markersInHitbar[railIndex];
		
		if(rail.size() > 0) {
			rail.remove(0);
			System.out.println("HIT on rail " + railIndex);
			parent.playerStatus.updateStatus(true);
			return true;
		}
		else {
			System.out.println("MISS on rail " + railIndex);
			parent.playerStatus.updateStatus(false);
			return false;
		}
	}

}
