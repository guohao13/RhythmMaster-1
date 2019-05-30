package status;

import java.util.ArrayDeque;

public class Status {
	
	private static final int MAX_MSGLOG_SIZE = 50;
	
	private int currentScore;		// cumulative point value of notes hit
	private Health currentHealth;	// moving window of last 40 bits
	private HitBar hitBar;
	private ArrayDeque<Message> msgLog;
	
	public Status() {
		currentScore = 0;
		currentHealth = new Health();
		hitBar = new HitBar();
		msgLog = new ArrayDeque<>(MAX_MSGLOG_SIZE);
	}
	
	public void updateStatus(boolean isHit) {
		currentHealth.updateHistory(isHit);
		if(isHit)
			currentScore += 10;
		else
			currentScore -= 5;
	}
	
	public void addMsg(Message m) {
		if(msgLog.size() >= MAX_MSGLOG_SIZE)
			msgLog.remove();
		msgLog.addLast(m);
	}
	
	public float getHP() {
		return(currentHealth.getHistoryHitPercent());
	}
	
	public int getScore() {
		return currentScore;
	}
}