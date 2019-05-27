package screens;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class GameScreenController extends ScreenController{

	SoundPlayer gameSongPlayer;
	static final String[] SONG_OPTIONS = {	"../Sounds/Butterfly.wav",
											"../Sounds/BadApple.wav" };
	
	public GameScreenController() {
		screenType = Screen.GAME;
		screenMusicPath = "";
		screenBackgroundPath = "../Images/testOtherBackground.jpg";
		setupDisplayAndMusic();
		playGameSong(0);
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
		return (Math.floorDiv(clipTime, 60000)); // TODO: should multiply by BPM
	}
	
	public void playGameSong(int selection) {
		gameSongPlayer = new SoundPlayer(SONG_OPTIONS[selection]);
	}
}
