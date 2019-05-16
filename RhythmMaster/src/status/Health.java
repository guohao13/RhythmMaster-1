package status;

import java.util.ArrayDeque;

public class Health {
	
	private static final int MOVING_WINDOW_SIZE = 40;
	
	public ArrayDeque<Boolean> hitHistory;
	public int sumMovingWindow;		// used to avoid processing hitHistory every tick
	
	Health() {
		this.hitHistory = new ArrayDeque<>(MOVING_WINDOW_SIZE);
		this.sumMovingWindow = 0;
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
	
	
	
	
	public static void main(String[] args) {
		Health h = new Health();
		h.updateHistory(true);
		h.updateHistory(true);
		System.out.println("sum = " + h.sumMovingWindow + " % = " + h.getHistoryHitPercent());
		for(int x = 0; x < 39; x++) {
			h.updateHistory(false);
		}
		System.out.println("sum = " + h.sumMovingWindow + " % = " + h.getHistoryHitPercent());
		h.updateHistory(true);
		h.updateHistory(true);
		System.out.println("sum = " + h.sumMovingWindow + " % = " + h.getHistoryHitPercent());
		h.updateHistory(false);
		System.out.println("sum = " + h.sumMovingWindow + " % = " + h.getHistoryHitPercent());
		h.updateHistory(true);
		h.updateHistory(true);
		System.out.println("sum = " + h.sumMovingWindow + " % = " + h.getHistoryHitPercent());
		for(int x = 0; x < 39; x++) {
			h.updateHistory(false);
		}
		System.out.println("sum = " + h.sumMovingWindow + " % = " + h.getHistoryHitPercent());
		h.resetHistory();
		System.out.println("sum = " + h.sumMovingWindow + " % = " + h.getHistoryHitPercent());
	}
}
