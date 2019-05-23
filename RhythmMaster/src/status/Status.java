package status;

import java.awt.Color;
import java.util.ArrayDeque;

public class Status {
	
	private static final int MAX_MSGLOG_SIZE = 50;
	
	public int currentScore;	// cum. point value of notes hit
	private Health currentHealth;	// moving window of last 40 bits
	private HitBar hitBar;
	private ArrayDeque<Message> msgLog;
	
	public Status() {
		this.currentScore = 0;
		this.currentHealth = new Health();
		this.hitBar = new HitBar();
		this.msgLog = new ArrayDeque<>(MAX_MSGLOG_SIZE);
	}
	
	public int getScore() {
		return this.currentScore;
	}
	
	public Health getHealth() {
		return this.currentHealth;
	}
	
	public ArrayDeque<Message> getMsgLog() {
		return this.msgLog;
	}
	
	public void updateStatus(boolean input) {
		System.out.println("UPDATES STATUS");
		// update score
		if(input)
			this.currentScore += 10;
		else
			this.currentScore -= 5;
		
		// update health + set hitbar color
		float hitPercent = this.currentHealth.updateHistoryGetPercent(input);
		if(hitPercent > 0.7)
			this.hitBar.setColor(Color.blue);
		else if(hitPercent > 0.6) 
			this.hitBar.setColor(Color.green);
		else if(hitPercent > 0.5)
			this.hitBar.setColor(Color.yellow);
		else if(hitPercent > 0.4)
			this.hitBar.setColor(Color.orange);
		else if(hitPercent > 0.3)
			this.hitBar.setColor(Color.red);
		
		// handle messages
		
		
				
	
		/*
		this.currentScore += notesHit * 10;
		this.currentScore -= notesHit * 5;
		
		// interleave processing notesHit and notesMissed to avoid
		// "miss-bombing" the player
		int tempIndex;
		if(notesHit >= notesMissed) {
			tempIndex = notesMissed;
			for(int x = 0; x < notesHit; x++) {
				currentHealth.updateHistoryGetPercent(true);
				if(tempIndex > 0) {
					currentHealth.updateHistoryGetPercent(false);
					tempIndex--;
				}
			}
		}
		else {
			tempIndex = notesHit;
			for(int x = 0; x < notesMissed; x++) {
				currentHealth.updateHistoryGetPercent(false);
				if(tempIndex > 0) {
					currentHealth.updateHistoryGetPercent(true);
					tempIndex--;
				}
			}
		}
		*/
	}
	
	public void addMsg(Message m) {
		if(this.msgLog.size() >= MAX_MSGLOG_SIZE)
			this.msgLog.remove();
		this.msgLog.addLast(m);
	}
	
}