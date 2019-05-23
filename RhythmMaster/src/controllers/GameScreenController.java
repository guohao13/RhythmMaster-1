package controllers;

import javax.swing.JFrame;
import javax.swing.JPanel;

import controllers.InputKeyListener.GSCHook;
import status.*;
import song.*;

public class GameScreenController implements GSCHook {
	
	//private GameDisplayManager display;
	private InputKeyListener keyEvents;
	
	private Song mySong;
	private Status myStatus;
	
	private int currentTick = 0;
	private int maxSongTick = 3000;
	
	GameScreenController(String songFileName) {
		// setup display
		
		// setup keylistener
		this.keyEvents = new InputKeyListener(this);
		this.mySong = new Song(songFileName);
		this.myStatus = new Status();
		
		JFrame jf = new JFrame("1111");
		jf.addKeyListener(this.keyEvents);
		jf.setSize(300,300);
		jf.setVisible(true);
	}
	
	private void gscProcessInput(int railNum, int tickIndex) {
		// frontend process input
			// hit bar flash
		// backend process input
			// get marker positions
		
		// check buttonpress against beatmap
		boolean[] bitsAtThisTick = this.mySong.getBitsAt(tickIndex);		
		int marker_x = 0, marker_y = 0;	// bit exists & input is within hitbar tolerance
		if(bitsAtThisTick[railNum] ) {// && inputIsHit(marker_x, marker_y)) {
			this.myStatus.updateStatus(true);
		}
	
	}
	
	public boolean inputIsHit(int marker_x, int marker_y) {
		// use hitbar tolerance to calculate hit / miss
		
		return true;
	}
	
	public void tickGame() {
		currentTick++;
	}
	
	public int getCurrentTick() {
		return this.currentTick;
	}
	
	public void pauseGame() {
		
	}
	
	public void unpauseGame() {
		
	}
	
	@Override
	public void processInput(int railNum, int tickIndex) {
		gscProcessInput(railNum, tickIndex);
		
	}
	
	
	
	public static void main(String[] args) {
		GameScreenController gsc = new GameScreenController("testSong");
	}
}
