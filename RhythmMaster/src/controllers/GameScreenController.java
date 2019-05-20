package controllers;

import status.*;
import song.*;

public class GameScreenController {
	
	private GameDisplayManager display;
	private KeyListener keyEvents;
	
	private Song currSong;
	private Status currStatus;
	private HitBar hitBar;
	
	private int currentTick = 0;
	private int maxSongTick = 3000;
	
	GameScreenController() {
		
	}
	
	public void loadDataForSong(String fileName) {
		currSong = new Song(fileName);
	}
	
	public void stepGame() {
		currentTick++;
	}
	
	
	
	public void pauseGame() {
		
	}
	
	public void unpauseGame() {
		
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
