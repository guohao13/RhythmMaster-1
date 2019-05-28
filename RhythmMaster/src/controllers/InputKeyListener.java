package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputKeyListener implements KeyListener {
	
	public interface GSCHook {
		public void processInput(int railNum, int tickIndex);
	}
	private GameScreenController gsc;	
	private int numRails;
	
	InputKeyListener(GameScreenController gsc) {
		this.gsc = gsc;
		this.numRails = 4;
	}

	@Override
	public void keyPressed(KeyEvent k) {
		System.out.println("Key: " + k.getKeyChar());
		
		int tickAtKeyPress = this.gsc.getCurrentTick();
		
		// keys: 1, 2, 3, 4, 5, 6, 7
		// converted to rail nums: 0, 1, 2 ...
		int railNum = Character.getNumericValue(k.getKeyChar()-1);
		this.gsc.processInput(railNum, tickAtKeyPress);
		//this.gsc.tickGame();	
	}
	
	@Override
	public void keyReleased(KeyEvent k) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent k) {
		// TODO Auto-generated method stub
		
	}

}
