package status;

public class Status {
		
	private int currentScore;		// cumulative point value of notes hit
	private int currentStreak;
	private Health currentHealth;	// moving window of last 40 bits
	
	public Status() {
		currentScore = 0;
		currentStreak = 0;
		currentHealth = new Health();
	}
	
	public void updateStatus(boolean isHit) {
		currentHealth.updateHistory(isHit);
		if(isHit) {
			currentScore += 10;
			currentStreak++;
		}
		else {
			currentScore -= 5;
			currentStreak = 0;
		}
	}

	public float getHP() {
		return(currentHealth.getHistoryHitPercent());
	}
	
	public int getScore() {
		return currentScore;
	}
}