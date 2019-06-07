package status;

import java.util.ArrayDeque;

public class Health {
	
	private static final int MOVING_WINDOW_SIZE = 40;
	private static final int WINDOW_SEED_VALUE = 40;
	
	public ArrayDeque<Boolean> hitHistory;
	public int sumMovingWindow;		// used to avoid processing hitHistory every tick
	
	Health() {
		hitHistory = new ArrayDeque<>(MOVING_WINDOW_SIZE);
		// seed hit history so that beginning misses don't cause a lose
		for(int a = 0; a < WINDOW_SEED_VALUE; a++) 
			hitHistory.add(true);
		
		sumMovingWindow = WINDOW_SEED_VALUE;
	}
	
	public void updateHistory(boolean b) {
		if(hitHistory.size() == MOVING_WINDOW_SIZE) {
			boolean val = hitHistory.removeFirst();
			if(val)		
				sumMovingWindow--;
		}
		hitHistory.addLast(new Boolean(b));
		if(b)
			sumMovingWindow++;
	}
	
	public float getHistoryHitPercent() {
		if(sumMovingWindow == 0)
			return 0;
		else
			return ((float)sumMovingWindow / hitHistory.size());
	}
}
