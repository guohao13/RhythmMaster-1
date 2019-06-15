package status;

import screens.GameScreenController;

public class Status {
	
	private int currentScore;		// cumulative point value of notes hit
	private int currentStreak;
	private Health currentHealth;	// moving window of last 40 bits
	private float currentStreakMultiplier = 1f;
	
	public Status() {
		currentScore = 0;
		currentStreak = 0;
		currentHealth = new Health();
	}
	
	public void updateStatus(boolean isHit) {
		currentHealth.updateHistory(isHit);
		
		if(isHit) {
			currentScore += 10*currentStreakMultiplier;
			currentStreak++;
		}
		else {
			currentScore -= 5;
			currentStreak = 0;
		}
		
		if(currentStreak >= 100)
			currentStreakMultiplier = 5f;
		else if(currentStreak >= 50)
			currentStreakMultiplier = 3.5f;
		else if(currentStreak >= 20)
			currentStreakMultiplier = 2f;
		else
			currentStreakMultiplier = 1f;
	}

	public float getHP() {
		return(currentHealth.getHistoryHitPercent());
	}
	
	public int getScore() {
		return currentScore;
	}
	
	public int getCurrentStreak() {
		return currentStreak;
	}
}