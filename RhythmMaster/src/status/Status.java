package status;

import java.util.ArrayDeque;

public class Status {
	
	private static final int MAX_MSGLOG_SIZE = 50;
	
	private int currentScore;		// cumulative point value of notes hit
	private Health currentHealth;	// moving window of last 40 bits

	public Status() {
		currentScore = 0;
		currentHealth = new Health();
	}
	
	public void updateStatus(boolean isHit) {
		currentHealth.updateHistory(isHit);
		if(isHit)
			currentScore += 10;
		else
			currentScore -= 5;
	}

	public float getHP() {
		return(currentHealth.getHistoryHitPercent());
	}
	
	public int getScore() {
		return currentScore;
	}
}