package screens;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import status.Status;


public class GameScreenController extends ScreenController{

	Timer winLossTimer;
	Status playerStatus;
	SoundPlayer gameSongPlayer;
	static final float MINIMUM_HP = 0f;
	static final String[] SONG_OPTIONS = {	"../Sounds/Butterfly.wav",
											"../Sounds/BadApple.wav" };
	
	public GameScreenController() {
		screenType = Screen.GAME;
		screenMusicPath = "";
		screenBackgroundPath = "../Images/testOtherBackground.jpg";
		setupDisplayAndMusic();
		playGameSong(ApplicationManager.SELECTION);
		setupWinLossTimer();
	}
	
	@Override
	public Canvas setupCanvas() {
		screenCanvas = new Canvas();
		screenCanvas.setBackground(screenBackgroundPath);
		setupButtons();
		setupKeys();
		return screenCanvas;
	}
	
	private void setupButtons() {
		JButton button = new JButton(new ImageIcon(Main.class.getResource("../Images/testButton.png")));
		button.setBounds(400,500,200,100);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				gameSongPlayer.stopClip();
				requestScreenChangeTo(Screen.MAIN_MENU);
			}
		});
		
		screenCanvas.addButton(button);
	}
	
	public void setupKeys() {
		ActionMap actionMap = screenCanvas.getActionMap();
		int focusCondition = JComponent.WHEN_IN_FOCUSED_WINDOW;    
		InputMap inputMap = screenCanvas.getInputMap(focusCondition);

		String railZero = "RAIL_ZERO";
		String railOne = "RAIL_ONE";
		String railTwo = "RAIL_TWO";
		String railThree = "RAIL_THREE";
	     
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_1, 0), railZero);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_2, 0), railOne);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_3, 0), railTwo);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_4, 0), railThree);

		actionMap.put(railZero, new KeyAction(railZero));
		actionMap.put(railOne, new KeyAction(railOne));
		actionMap.put(railTwo, new KeyAction(railTwo));
		actionMap.put(railThree, new KeyAction(railThree));
	}
		
	private class KeyAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public KeyAction(String command) {
			putValue(ACTION_COMMAND_KEY, command);
		}

		public void actionPerformed(ActionEvent e) {
			int currBeat = getCurrentBeat();
			switch (e.getActionCommand()) {
				case "RAIL_ZERO":
					handleKeyPressed(0, currBeat);
					break;
				case "RAIL_ONE":
					handleKeyPressed(1, currBeat);
					break;
				case "RAIL_TWO":
					handleKeyPressed(2, currBeat);
					break;
				case "RAIL_THREE":
					handleKeyPressed(3, currBeat);
					break;
			}
		}
	}
	
	public void handleKeyPressed (int railNumber, int beatNumber) {
		System.out.println("Key for rail " + railNumber + " pressed at Beat " + beatNumber);
	}
	
	public int getCurrentBeat() {
		return timeToBeat(gameSongPlayer.getClipTime());
	}
	
	private int timeToBeat(int clipTime) {
		System.out.println(clipTime);
		return ((int)Math.floorDiv((long)clipTime, (60000000 / 120))); // TODO: should multiply by BPM, also this is mils should be micros
	}
	
	private void playGameSong(int selection) {
		gameSongPlayer = new SoundPlayer(SONG_OPTIONS[selection]);
	}

	private void setupWinLossTimer() {
		playerStatus = new Status();
		
		TimerTask checkHPLoss = new TimerTask() {
			@Override
	        public void run() {
				if (playerStatus.getHP() < MINIMUM_HP)
					handleLoss();
	        }
		};
		
		TimerTask endOfSongWin = new TimerTask() {
			@Override
	        public void run() {
				handleWin();
		    }
		};
		
		winLossTimer = new Timer("winLossTimer");
		winLossTimer.scheduleAtFixedRate(checkHPLoss, 0, 100);
		winLossTimer.schedule(endOfSongWin, Math.floorDiv(gameSongPlayer.getClipLength(), 1000) + 1);
	}
	
	private void handleLoss() {
		winLossTimer.cancel();
		int timeSurvived = Math.floorDiv(gameSongPlayer.getClipTime(), 1000000);
		gameSongPlayer.stopClip();
		JOptionPane.showMessageDialog(screenCanvas, "You lost \nYour hit percent was too low \nBut you survived " + timeSurvived + " seconds!", "Sorry", JOptionPane.WARNING_MESSAGE);
		requestScreenChangeTo(Screen.MAIN_MENU);
	}
	
	private void handleWin() {
		winLossTimer.cancel();
		gameSongPlayer.stopClip();
		JOptionPane.showMessageDialog(screenCanvas, "You won! \nYou scored " + playerStatus.getScore(), "Congratulations!", JOptionPane.WARNING_MESSAGE);
		requestScreenChangeTo(Screen.MAIN_MENU);		
	}
}
