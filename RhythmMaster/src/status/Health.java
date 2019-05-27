package status;

import java.util.ArrayDeque;

public class Health {
	
	private static final int MOVING_WINDOW_SIZE = 40;
	private static final int WINDOW_SEED_VALUE = 25;
	
	public ArrayDeque<Boolean> hitHistory;
	public int sumMovingWindow;		// used to avoid processing hitHistory every tick
	
	Health() {
		this.hitHistory = new ArrayDeque<>(MOVING_WINDOW_SIZE);
		// seed hit history so that beginning misses don't cause a lose
		for(int a = 0; a < WINDOW_SEED_VALUE; a++) 
			this.hitHistory.add(true);
		
		this.sumMovingWindow = 0;
	}
	
	public float updateHistoryGetPercent(boolean b) {
		if(hitHistory.size() == MOVING_WINDOW_SIZE) {
			boolean val = hitHistory.removeFirst();
			if(val)		
				sumMovingWindow--;
		}
		
		hitHistory.addLast(new Boolean(b));
		if(b)
			sumMovingWindow++;
		
		return (float) sumMovingWindow / hitHistory.size();
	}
	
	public float getHistoryHitPercent() {
		if(sumMovingWindow == 0)
			return 0;
		else
			return (float)sumMovingWindow / hitHistory.size();
	}
	
	public void resetHistory() {
		hitHistory = new ArrayDeque<>(MOVING_WINDOW_SIZE);
		sumMovingWindow = 0;
	}
	
	/*
	 * METHODS TO CHECK FOR "COMBOS" FOR MESSAGES
	 * eg: last 5 history entries are true
	 * then send a STREAK message
	 */

}
