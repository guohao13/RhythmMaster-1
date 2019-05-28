package status;

import java.util.ArrayDeque;

public class Status {
	
	private static final int MAX_MSGLOG_SIZE = 50;
	
	private int currentScore;		// cumulative point value of notes hit
	private Health currentHealth;	// moving window of last 40 bits
	private ArrayDeque<Message> msgLog;
	
	public Status() {
		this.currentScore = 0;
		this.currentHealth = new Health();
		this.msgLog = new ArrayDeque<>(MAX_MSGLOG_SIZE);
	}
	
	public void updateStatus(int notesHit, int notesMissed) {
		this.currentScore += notesHit * 10;
		this.currentScore -= notesHit * 5;
		
		// interleave processing notesHit and notesMissed to avoid
		// "miss-bombing" the player
		int tempIndex;
		if(notesHit >= notesMissed) {
			tempIndex = notesMissed;
			for(int x = 0; x < notesHit; x++) {
				currentHealth.updateHistory(true);
				if(tempIndex > 0) {
					currentHealth.updateHistory(false);
					tempIndex--;
				}
			}
		}
		else {
			tempIndex = notesHit;
			for(int x = 0; x < notesMissed; x++) {
				currentHealth.updateHistory(false);
				if(tempIndex > 0) {
					currentHealth.updateHistory(true);
					tempIndex--;
				}
			}
		}
	}
	
	public void addMsg(Message m) {
		if(this.msgLog.size() >= MAX_MSGLOG_SIZE)
			this.msgLog.remove();
		this.msgLog.addLast(m);
	}
	
	public float getHP() {
		return(currentHealth.getHistoryHitPercent());
	}
	
	public int getScore() {
		return currentScore;
	}
}