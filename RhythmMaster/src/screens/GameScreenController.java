package screens;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.event.KeyEvent;
import java.util.ArrayDeque;
import java.util.ArrayList;
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

import song.Song;
import status.Status;

public class GameScreenController extends ScreenController {
	static int timescalled = 0;
	int globalYOffset = -20;
	int screenCenterX = 1280 / 2, screenCenterY = 680 / 2, railSpacing = 150, railWidth = 10, railHeight = 600,
			hitBarWidth = 700, hitBarHeight = 50;
	int railTop = 60, rail1x = screenCenterX - railSpacing * 3 / 2 - railWidth / 2,
			rail2x = screenCenterX - railSpacing / 2 - railWidth / 2,
			rail3x = screenCenterX + railSpacing / 2 - railWidth / 2,
			rail4x = screenCenterX + railSpacing * 3 / 2 - railWidth / 2;
	int dy = 5;
	DrawableRectangle hitBar;
	ArrayDeque<Marker> markers = new ArrayDeque<Marker>();
	Timer screenTimer;
	Timer updateTimer;
	Timer winLossTimer;
	Status playerStatus;
	SoundPlayer gameSongPlayer;
	Song currentSong;
	MissedNoteObserver missedNoteObs;

	double period = 100, amplitude = 255;
	int t = 0;

	static final float MINIMUM_HP = 0f;
	static final String[] SONG_OPTIONS = { "../Sounds/Butterfly.wav", "../Sounds/WiiMenu.wav" };
	static final int TIME_OFFSET = 5000000; // 5 seconds for marker to reach hitbar
											// TODO: calc offset using y_velocity of marker

	public GameScreenController() {
		screenType = Screen.GAME;
		screenMusicPath = "";
		screenBackgroundPath = "../Images/testOtherBackground.jpg";

		System.out.println("times called = " + (++timescalled));
		screenCanvas = new Canvas();
		screenCanvas.setBackground(new Color(0x96, 0x94, 0xAB));

		setupCanvas();
		setupSongAndMissedNoteObs();
		setupDisplayAndMusic();
		playGameSong(ApplicationManager.SELECTION);
		setupWinLossTimer();
		setupScreenTimer();
	}

	private void addRandomMarkers() {
		Random r = new Random();
		if (r.nextInt(500) < 3) {
			Marker m = new Marker(rail1x, railTop, 1);
			screenCanvas.addDynamicDrawable(m);
			markers.add(m);
		}
	}

	private void setupScreenTimer() {
		screenTimer = new Timer();
		TimerTask tt = new TimerTask() {

			@Override
			public void run() {
				advanceTime();
			}

		};
		screenTimer.scheduleAtFixedRate(tt, 0, 16);
	}
	
	synchronized void updateMarkers() {
		for (int i = 0; i < markers.size(); i++) {
			if (markers.peekFirst().getCenterY() > 561) {
				markers.pop();
				System.out.println("pop!");
			}
			else {
				markers.peekFirst().setLocation(markers.peekFirst().x, markers.peekFirst().y + dy);
				markers.add(markers.pop());
				System.out.println("rotate!");
			}
		}
		
		ArrayDeque<Marker> markerArray = screenCanvas.getDynamicList();
		for (int j = 0; j < screenCanvas.getDynamicList().size(); j++){
			if(markerArray.peekFirst().getCenterY() > 561) {
				markerArray.pop();
			}
			else {
				markerArray.peekFirst().setLocation(markers.peekFirst().x, markers.peekFirst().y + dy);
				markers.add(markers.pop());
			}
		}
	}
	synchronized private void advanceTime() {
		for (Marker m : markers) {
			m.setLocation(m.x, m.y + dy);
			// System.out.println(m);
		}
		
		updateMarkers();
		//int c1 = (int) ((Math.sin(Math.PI * 2 * t / period) + 1) * amplitude / 2);
		//int c2 = (int) ((Math.cos(Math.PI * 2 * t / period) + 1) * amplitude / 2);
		//System.out.println("c1 = " + c1 + " c2 = " + c2);
//		t++;
		//screenCanvas.setBackground(new Color(255, 255, c1));
		addRandomMarkers();
		screenCanvas.repaint();
	}

	private void setupDemoMarkers() {

		// System.out.println("markers size before = " + markers.size());
		markers.add(new Marker(rail1x, railTop, 1));
		markers.add(new Marker(rail2x, railTop, 2));

		for (Marker m : markers) {
			screenCanvas.addDynamicDrawable(m);
			// System.out.println(m);
		}
		// System.out.println("markers size after = " + markers.size());
	}

	@Override
	public Canvas setupCanvas() {
		setupButtons();
		setupHitBar();
		setupKeys();
		setupRails();
		setupDemoMarkers();
		return screenCanvas;
	}

	private void setupButtons() {
		JButton button = new JButton(new ImageIcon(Main.class.getResource("../Images/testButton.png")));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				gameSongPlayer.stopClip();
				requestScreenChangeTo(Screen.MAIN_MENU);
			}
		});

		screenCanvas.addButton(button);
	}

	private void setupRails() {

		System.out.println("setting up rails!" + screenCenterX + " ");
		DrawableRectangle rail1 = new DrawableRectangle(screenCenterX - railSpacing * 3 / 2 - railWidth / 2, 60,
				railWidth, railHeight, Color.RED);
		rail1.setFilled(true);
		DrawableRectangle rail2 = new DrawableRectangle(screenCenterX - railSpacing / 2 - railWidth / 2, 60, railWidth,
				railHeight, Color.RED);
		rail2.setFilled(true);
		DrawableRectangle rail3 = new DrawableRectangle(screenCenterX + railSpacing / 2 - railWidth / 2, 60, railWidth,
				railHeight, Color.RED);
		rail3.setFilled(true);
		DrawableRectangle rail4 = new DrawableRectangle(screenCenterX + railSpacing * 3 / 2 - railWidth / 2, 60,
				railWidth, railHeight, Color.RED);
		rail4.setFilled(true);

		screenCanvas.addStaticDrawable(rail1);
		screenCanvas.addStaticDrawable(rail2);
		screenCanvas.addStaticDrawable(rail3);
		screenCanvas.addStaticDrawable(rail4);
	}

	private void setupHitBar() {
		hitBar = new DrawableRectangle(screenCenterX - this.hitBarWidth / 2, 560 - hitBarHeight / 2, hitBarWidth,
				hitBarHeight, Color.RED);
		hitBar.setFilled(true);
		screenCanvas.addStaticDrawable(hitBar);
	}

	public void changeHitBarColor(Color c) {
		hitBar.setColor(c);
	}

	public void flashHitBarColor() {

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				hitBar.c = Color.GREEN;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				hitBar.c = Color.RED;

			}

		});
		t.start();
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
			int clipTimeOfInput = gameSongPlayer.getClipTime();
			switch (e.getActionCommand()) {
			case "RAIL_ZERO":
				handleKeyPressed(0, clipTimeOfInput);
				break;
			case "RAIL_ONE":
				handleKeyPressed(1, clipTimeOfInput);
				break;
			case "RAIL_TWO":
				handleKeyPressed(2, clipTimeOfInput);
				break;
			case "RAIL_THREE":
				handleKeyPressed(3, clipTimeOfInput);
				break;
			}
		}
	}

	public void handleKeyPressed(int railNumber, int clipTimeOfInput) {
		int beatIndex = -1;
		if (clipTimeOfInput < 0)
			playerStatus.updateStatus(false); // key input before song clip has started playing
		else {
			beatIndex = timeToBeat(clipTimeOfInput);
			int timeOfBeat = beatToTime(beatIndex);
			int tolerance = 1000000; // TODO: get tolerance from settings screen
			if (Math.abs(clipTimeOfInput - timeOfBeat) <= tolerance)
				playerStatus.updateStatus(true);
			else
				playerStatus.updateStatus(false);
		}
		flashHitBarColor();
		System.out.println("Key for rail " + railNumber + " pressed at Beat " + beatIndex);
	}

	public int getCurrentBeat() {
		if (gameSongPlayer.isPlaying())
			return timeToBeat(gameSongPlayer.getClipTime());
		else
			return -1;
	}

	private int timeToBeat(int clipTime) {
		System.out.println(clipTime);
		return ((int) Math.floorDiv((long) clipTime, (60000000 / 120))); // TODO: should multiply by BPM, also this is
																			// mils should be micros
	}

	private int beatToTime(int beatIndex) {
		return beatIndex * 60000000;
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
		JOptionPane.showMessageDialog(screenCanvas,
				"You lost \nYour hit percent was too low \nBut you survived " + timeSurvived + " seconds!", "Sorry",
				JOptionPane.WARNING_MESSAGE);
		requestScreenChangeTo(Screen.MAIN_MENU);
	}

	private void handleWin() {
		winLossTimer.cancel();
		gameSongPlayer.stopClip();
		JOptionPane.showMessageDialog(screenCanvas, "You won! \nYou scored " + playerStatus.getScore(),
				"Congratulations!", JOptionPane.WARNING_MESSAGE);
		requestScreenChangeTo(Screen.MAIN_MENU);
	}

	private void setupSongAndMissedNoteObs() {
		currentSong = new Song(ApplicationManager.SELECTION);
		missedNoteObs = new MissedNoteObserver(this);
	}

}
