package screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;

import java.io.IOException;

import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import song.Song;
import status.Status;

public class GameScreenController extends ScreenController {
	int globalYOffset = -20;
	int screenCenterX = 1280 / 2, 
			screenCenterY = 680 / 2, 
			railSpacing = 150, 
			railWidth = 10, 
			railHeight = 600,
			hitBarWidth = 700, hitBarHeight = 50;
	int railTop = 60, 
			rail1x = screenCenterX - railSpacing * 3 / 2 - railWidth / 2,
			rail2x = screenCenterX - railSpacing / 2 - railWidth / 2,
			rail3x = screenCenterX + railSpacing / 2 - railWidth / 2,
			rail4x = screenCenterX + railSpacing * 3 / 2 - railWidth / 2;
	int dy = 2;
	int markerIndex = 0;
	DrawableRectangle hitBar;
	ArrayList<Marker> markers = new ArrayList<Marker>();
	Timer screenTimer;
	Timer winLossTimer;
	JLabel hpLabel;
	JLabel scoreLabel;
	Status playerStatus;
	SoundPlayer gameSongPlayer;
	Song currentSong;
	HitDetectionObserver hitDetectionObserver;
	static final float MINIMUM_HP = 0.5f;

	static int MARKER_SPAWN_RATE;
	
	public GameScreenController() {
		screenType = Screen.GAME;
		screenMusicPath = "";
		screenBackgroundPath = "../Images/backgroundImages/background-Game.png";
		screenTimer = new Timer();
		setupDisplayAndMusic();
		setupKeys();
		setupSongAndMissedNoteObs();
		playGameSong(ApplicationManager.SELECTION);
		setupMarkerTimer();
		setupWinLossTimer();
		
	}
	
	private void setupMarkerTimer() {
		markerIndex = 0;
		
		TimerTask markerSpawn = new TimerTask() {
			@Override
			public void run() {
				spawnMarkers();
				markerIndex++;
			}
		};
		
		TimerTask markerPos = new TimerTask() {
			@Override
			public void run() {
				Marker m;
				for(int x = 0; x < markers.size(); x++) {
					m = markers.get(x);
					m.setLocation(m.x, m.y + dy);
					if (m.getY() > 720) {
						markers.remove(m);
						screenCanvas.removeMarker(m);
					}
				}
				screenCanvas.repaint();
			}
		};
		screenTimer.scheduleAtFixedRate(markerSpawn, 0, MARKER_SPAWN_RATE);
		screenTimer.scheduleAtFixedRate(markerPos, 0, 10);
	}
	
	private void spawnMarkers() {
		boolean[] beats = currentSong.getBitsAt(markerIndex);
		Marker temp;
				
		for(int index = 0; index < 4; index++) {
			if(beats[index]) {
				switch(index) {
					case 0: temp = new Marker(rail1x, railTop, 0);
						markers.add(temp);
						screenCanvas.addDynamicDrawable(temp);
						temp.addObserver(hitDetectionObserver);
						break;
					case 1: temp = new Marker(rail2x, railTop, 1);
						markers.add(temp);
						screenCanvas.addDynamicDrawable(temp);
						temp.addObserver(hitDetectionObserver);
						break;
					case 2: temp = new Marker(rail3x, railTop, 2);
						markers.add(temp);
						screenCanvas.addDynamicDrawable(temp);
						temp.addObserver(hitDetectionObserver);
						break;
					case 3: temp = new Marker(rail4x, railTop, 3);
						markers.add(temp);
						screenCanvas.addDynamicDrawable(temp);
						temp.addObserver(hitDetectionObserver);
						break;
				}
			}
		}
	}

	@Override
	public Canvas setupCanvas() {
		screenCanvas = new Canvas();
		screenCanvas.setBackground(screenBackgroundPath);
		setupRails();
		setupHitBar();
		setupText();
		setupStatusLabels();
		
		return screenCanvas;
	}
	
	private void setupStatusLabels() {
		Font font;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResource("../Fonts/ARDESTINE.TTF").openStream());
			GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
			graphicsEnvironment.registerFont(font);
			font = font.deriveFont(32f);
			scoreLabel = new JLabel("score label not set");			
			hpLabel = new JLabel("hp label not set");
			scoreLabel.setFont(font);
			hpLabel.setFont(font);
			scoreLabel.setBounds(100, 550, 100, 100);
			hpLabel.setBounds(100, 100, 100, 100);
			screenCanvas.add(scoreLabel);
			screenCanvas.add(hpLabel);
		} catch (FontFormatException  | IOException e) {
			e.printStackTrace();		
		}
		
		
	}

	private void setupText() {			
		ImageIcon healthIcon = new ImageIcon(Main.class.getResource("../Images/componentImages/Game-Health.png"));		
		JButton healthButton = new JButton(healthIcon);		
		healthButton.setBounds(ApplicationManager.SCREEN_WIDTH / 32, ApplicationManager.SCREEN_HEIGHT/8-healthIcon.getIconHeight()/2,		
				healthIcon.getIconWidth(), healthIcon.getIconHeight());		
		healthButton.setContentAreaFilled(false);		
		healthButton.setBorderPainted(false);		
		screenCanvas.addButton(healthButton);		
				
		ImageIcon scoreIcon = new ImageIcon(Main.class.getResource("../Images/componentImages/Game-Score.png"));		
		JButton scoreButton = new JButton(scoreIcon);		
		scoreButton.setBounds(ApplicationManager.SCREEN_WIDTH / 32, ApplicationManager.SCREEN_HEIGHT*6/8-scoreIcon.getIconHeight()/2,		
				scoreIcon.getIconWidth(), scoreIcon.getIconHeight());		
		scoreButton.setContentAreaFilled(false);		
		scoreButton.setBorderPainted(false);		
		screenCanvas.addButton(scoreButton);		
	}
  
	private void setupRails() {	
		Color railColor = Color.BLACK;
		System.out.println("setting up rails!" + screenCenterX + " ");
		DrawableRectangle rail1 = new DrawableRectangle(screenCenterX - railSpacing * 3 / 2 - railWidth / 2,
				60, railWidth, railHeight, railColor);
		rail1.setFilled(true);
		DrawableRectangle rail2 = new DrawableRectangle(screenCenterX - railSpacing / 2 - railWidth / 2,
				60, railWidth, railHeight, railColor);
		rail2.setFilled(true);
		DrawableRectangle rail3 = new DrawableRectangle(screenCenterX + railSpacing / 2 - railWidth / 2,
				60, railWidth, railHeight, railColor);
		rail3.setFilled(true);
		DrawableRectangle rail4 = new DrawableRectangle(screenCenterX + railSpacing * 3 / 2 - railWidth / 2,
				60, railWidth, railHeight, railColor);
		rail4.setFilled(true);

		screenCanvas.addStaticDrawable(rail1);
		screenCanvas.addStaticDrawable(rail2);
		screenCanvas.addStaticDrawable(rail3);
		screenCanvas.addStaticDrawable(rail4);
	}

	private void setupHitBar() {
		hitBar = new DrawableRectangle(screenCenterX - this.hitBarWidth / 2, 560 - hitBarHeight/2, hitBarWidth,
				hitBarHeight, Color.BLACK);
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
				hitBar.c = Color.GREEN;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				hitBar.c = Color.BLACK;

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
			switch (e.getActionCommand()) {
				case "RAIL_ZERO":
					handleKeyPressed(0);
					break;
				case "RAIL_ONE":
					handleKeyPressed(1);
					break;
				case "RAIL_TWO":
					handleKeyPressed(2);
					break;
				case "RAIL_THREE":
					handleKeyPressed(3);
					break;
			}
		}
	}
	
	public void handleKeyPressed (int railNumber) {
		hitDetectionObserver.registerKeypress(railNumber);
		flashHitBarColor();
		System.out.println("Key for rail " + railNumber + " pressed");
	}

	public int getCurrentBeat() {
		if (gameSongPlayer.isPlaying())
			return timeToBeat(gameSongPlayer.getClipTime());
		else
			return -1;
	}

	private int timeToBeat(int clipTime) {
		return ((int)Math.floorDiv((long)clipTime, ( 1000 * currentSong.getMSPerBeat())));
	}

	private int beatToTime(int beatIndex) {
		return beatIndex * currentSong.getMSPerBeat() * 1000;
	}

	private void playGameSong(int selection) {
		System.out.println("play game song");
		gameSongPlayer = new SoundPlayer(ApplicationManager.SONG_OPTIONS[selection]);
	}

	private void setupWinLossTimer() {
		playerStatus = new Status();

		TimerTask checkHPLoss = new TimerTask() {
			@Override
			public void run() {
				int score = playerStatus.getScore();
				float hp = playerStatus.getHP();
				scoreLabel.setText(String.valueOf(score));
				hpLabel.setText(String.valueOf(hp));
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
		winLossTimer.schedule(endOfSongWin, Math.floorDiv(gameSongPlayer.getClipLength(), 1000) + 2500);
		
		TimerTask beat = new TimerTask() {
			int time = 0;
			
			@Override
			public void run() {
				time++;
			}
		};
		screenTimer.scheduleAtFixedRate(beat, 0, MARKER_SPAWN_RATE);
	}

	private void handleLoss() {
		winLossTimer.cancel();
		screenTimer.cancel();
		gameSongPlayer.stopClip();
		for(Marker m : markers)
			m.y_coord.deleteObserver(hitDetectionObserver);
		int timeSurvived = Math.floorDiv(gameSongPlayer.getClipTime(), 1000000);
		JOptionPane.showMessageDialog(screenCanvas,
				"You lost \nYour hit percent was too low \nBut you survived " + timeSurvived + " seconds!", "Sorry",
				JOptionPane.WARNING_MESSAGE);
		requestScreenChangeTo(Screen.MAIN_MENU);
	}

	private void handleWin() {
		winLossTimer.cancel();
		screenTimer.cancel();
		for(Marker m : markers)
			m.y_coord.deleteObserver(hitDetectionObserver);
		gameSongPlayer.stopClip();
		JOptionPane.showMessageDialog(screenCanvas, "You won! \nYou scored " + playerStatus.getScore(),
				"Congratulations!", JOptionPane.WARNING_MESSAGE);
		requestScreenChangeTo(Screen.MAIN_MENU);
	}

	private void setupSongAndMissedNoteObs() {
		currentSong = new Song(ApplicationManager.SELECTION);
		MARKER_SPAWN_RATE = currentSong.getMSPerBeat();
		hitDetectionObserver = new HitDetectionObserver(this);
	}
}
