package screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;

import java.io.IOException;

import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
	static final float MINIMUM_HP = 0.5f;

	static int MARKER_SPAWN_RATE;
	
	final Color railColor = Color.BLACK;
	final int railSpacing = 150; 
	final int railWidth = 10;
	final int railHeight = 600;
	final int hitBarWidth = 700;
	final int hitBarHeight = 50;
	final int railTop = 60; 
	final int screenCenterX = ApplicationManager.SCREEN_WIDTH / 2;
	final int rail1x = screenCenterX - railSpacing * 3 / 2 - railWidth / 2;
	final int rail2x = screenCenterX - railSpacing / 2 - railWidth / 2;
	final int rail3x = screenCenterX + railSpacing / 2 - railWidth / 2;
	final int rail4x = screenCenterX + railSpacing * 3 / 2 - railWidth / 2;
	final int dy = 2;
	
	final int progressBarWidth = hitBarWidth;
	final int progressBarHeight = hitBarHeight/5;
	
	int markerIndex;
	DrawableRectangle hitBar;
	DrawableRectangle progressBar;
	ArrayList<Marker> markers = new ArrayList<Marker>();
	
	SoundPlayer gameSongPlayer;
	Song currentSong;
	Status playerStatus;
	HitDetectionObserver hitDetectionObserver;
	ScheduledExecutorService winLossScheduler;
	ScheduledExecutorService markerScheduler;
	ScheduledExecutorService hitDetect;
	ScheduledExecutorService songPositionScheduler;
	JLabel scoreLabel;
	JLabel hpLabel;
	
	public GameScreenController() {
		screenType = Screen.GAME;
		screenMusicPath = "";
		screenBackgroundPath = "../Images/backgroundImages/background-Game.png";
		setupDisplayAndMusic();
		setupKeys();
		setupSongAndMissedNoteObs();
		playGameSong(ApplicationManager.SELECTION);
		setupMarkerTimer();
		setupWinLossTimer();
		setupSongPosTimer();
	}
	
	private void setupSongPosTimer() {
		// TODO Auto-generated method stub
		

		TimerTask updateSongPosition = new TimerTask() {
			@Override
			public void run() {
				double percent = (double)gameSongPlayer.getClipTime()/(double)gameSongPlayer.getClipLength();
				progressBar.width = (int) (percent * progressBarWidth);
				System.out.println("hi! " + progressBar.width);
			}
		};
		songPositionScheduler = Executors.newScheduledThreadPool(3);
		songPositionScheduler.scheduleAtFixedRate(updateSongPosition, 0, 15, TimeUnit.MILLISECONDS);
		
	}

	private void setupMarkerTimer() {
		markerIndex = 0;
		TimerTask ttMarkerSpawn, ttMarkerPos, ttSongPos;
		
		ttMarkerSpawn = new TimerTask() {
			@Override
			public void run() {
				try {
					spawnMarkers();
					markerIndex++;
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("ttMarkerSpawn crash");
				}
			}
		};

		ttMarkerPos = new TimerTask() {
			@Override
			public void run() {
				try {
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
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println("ttMarkerPos crash");
				}
			}
		};
		

		markerScheduler = Executors.newScheduledThreadPool(2);
		markerScheduler.scheduleAtFixedRate(ttMarkerSpawn, 0, MARKER_SPAWN_RATE, TimeUnit.MICROSECONDS);
		markerScheduler.scheduleAtFixedRate(ttMarkerPos, 0, 10, TimeUnit.MILLISECONDS);
	}
	
	private void spawnMarkers() {
		boolean[] beats = currentSong.getBitsAt(markerIndex);
		Marker m;
				
		for(int index = 0; index < 4; index++) {
			if(beats[index]) {
				switch(index) {
					case 0: m = new Marker(rail1x + 2, railTop, 0);
						markers.add(m);
						screenCanvas.addDynamicDrawable(m);
						m.addObserver(hitDetectionObserver);
						break;
					case 1: m = new Marker(rail2x + 2, railTop, 1);
						markers.add(m);
						screenCanvas.addDynamicDrawable(m);
						m.addObserver(hitDetectionObserver);
						break;
					case 2: m = new Marker(rail3x + 2, railTop, 2);
						markers.add(m);
						screenCanvas.addDynamicDrawable(m);
						m.addObserver(hitDetectionObserver);
						break;
					case 3: m = new Marker(rail4x +2, railTop, 3);
						markers.add(m);
						screenCanvas.addDynamicDrawable(m);
						m.addObserver(hitDetectionObserver);
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
		setupProgressBar();
		setupText();
		setupStatusLabels();
		setupKeyLabels();
		
		return screenCanvas;
	}
	
	private void setupProgressBar() {
		progressBar = new DrawableRectangle(screenCenterX - this.progressBarWidth / 2, 50 - progressBarHeight/2, 0,
				progressBarHeight, Color.WHITE);
		progressBar.setFilled(true);
		screenCanvas.addDynamicDrawable(progressBar);
		DrawableRectangle progressBarBacker = new DrawableRectangle(screenCenterX - this.progressBarWidth / 2, 50 - progressBarHeight/2, progressBarWidth,
				progressBarHeight, Color.BLACK);
		progressBarBacker.setFilled(true);
		screenCanvas.addStaticDrawable(progressBarBacker);
		
		
	}

	private void setupStatusLabels() {
		Font font;
		
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResource("../Fonts/ARDESTINE.TTF").openStream());
			GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
			graphicsEnvironment.registerFont(font);
			font = font.deriveFont(32f);
			scoreLabel = new JLabel("0");			
			hpLabel = new JLabel("1.0");
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
	
	private void setupKeyLabels() {
		Font font;
		
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResource("../Fonts/ARDESTINE.TTF").openStream());
			GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
			graphicsEnvironment.registerFont(font);
			font = font.deriveFont(32f);
			JLabel jlRail0 = new JLabel("1");			
			JLabel jlRail1 = new JLabel("2");
			JLabel jlRail2 = new JLabel("3");
			JLabel jlRail3 = new JLabel("4");
			jlRail0.setFont(font);
			jlRail1.setFont(font);
			jlRail2.setFont(font);
			jlRail3.setFont(font);
			jlRail0.setBounds(rail1x - 3, 560 - hitBarHeight / 2, 50, 50);
			jlRail1.setBounds(rail2x - 5, 560 - hitBarHeight / 2, 50, 50);
			jlRail2.setBounds(rail3x - 5, 560 - hitBarHeight / 2, 50, 50);
			jlRail3.setBounds(rail4x - 5, 560 - hitBarHeight / 2, 50, 50);
			jlRail0.setForeground(Color.WHITE);
			jlRail1.setForeground(Color.WHITE);
			jlRail2.setForeground(Color.WHITE);
			jlRail3.setForeground(Color.WHITE);
			screenCanvas.add(jlRail0);
			screenCanvas.add(jlRail1);
			screenCanvas.add(jlRail2);
			screenCanvas.add(jlRail3);
		} catch (FontFormatException  | IOException e) {
			e.printStackTrace();		
		}
	}
  
	private void setupRails() {	
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
				hitBar.setColor(Color.GREEN);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				hitBar.setColor(Color.BLACK);
			}

		});
		t.start();
	}

	public void setupKeys() {
		ActionMap actionMap = screenCanvas.getActionMap();
		int focusCondition = JComponent.WHEN_IN_FOCUSED_WINDOW;
		InputMap inputMap = screenCanvas.getInputMap(focusCondition);

		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_1, 0), "RAIL_ZERO");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_2, 0), "RAIL_ONE");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_3, 0), "RAIL_TWO");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_4, 0), "RAIL_THREE");

		actionMap.put("RAIL_ZERO", new KeyAction("RAIL_ZERO"));
		actionMap.put("RAIL_ONE", new KeyAction("RAIL_ONE"));
		actionMap.put("RAIL_TWO", new KeyAction("RAIL_TWO"));
		actionMap.put("RAIL_THREE", new KeyAction("RAIL_THREE"));
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
	
	public void handleKeyPressed (int railIndex) {
		hitDetectionObserver.registerKeypress(railIndex);
		flashHitBarColor();
	}

	private void playGameSong(int selection) {
		System.out.println("play game song");
		
		if(currentSong.getDelay() == 0)
			gameSongPlayer = new SoundPlayer(ApplicationManager.SONG_OPTIONS[selection]);
		else
			gameSongPlayer = new SoundPlayer(ApplicationManager.SONG_OPTIONS[selection], currentSong.getDelay());
	}

	private void setupWinLossTimer() {
		playerStatus = new Status();

		TimerTask checkHPLoss = new TimerTask() {
			@Override
			public void run() {
				try {
					int score = playerStatus.getScore();
					float hp = playerStatus.getHP();
					scoreLabel.setText(String.valueOf(score));
					hpLabel.setText(String.valueOf(hp));
					if (playerStatus.getHP() < MINIMUM_HP)
						handleLoss();
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("checkHPLoss exception");
				}
			}
		};

		TimerTask endOfSongWin = new TimerTask() {
			@Override
			public void run() {
				try {
					handleWin();
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("endOfSongWin exception");
				}
			}
		};

		winLossScheduler = Executors.newScheduledThreadPool(3);
		winLossScheduler.scheduleAtFixedRate(checkHPLoss, 0, 100, TimeUnit.MILLISECONDS);
		winLossScheduler.schedule(endOfSongWin, gameSongPlayer.getClipLength() + 
							currentSong.getDelay() * 1000 + 2000000, TimeUnit.MICROSECONDS);
	}

	private void handleLoss() {
		markerScheduler.shutdown();
		winLossScheduler.shutdown();
		hitDetect.shutdown();
		songPositionScheduler.shutdown();
		
		int timeSurvived = Math.floorDiv(gameSongPlayer.getClipTime(), 1000000);
		gameSongPlayer.stopClip();
		JOptionPane.showMessageDialog(screenCanvas,
				"You lost \nYour hit percent was too low \nBut you survived " + timeSurvived + " seconds!", "Sorry",
				JOptionPane.WARNING_MESSAGE);
		for(Marker m : markers)
			m.y_coord.deleteObserver(hitDetectionObserver);
		requestScreenChangeTo(Screen.MAIN_MENU);
	}

	private void handleWin() {
		markerScheduler.shutdown();
		winLossScheduler.shutdown();
		hitDetect.shutdown();
		songPositionScheduler.shutdown();
		
		gameSongPlayer.stopClip();
		JOptionPane.showMessageDialog(screenCanvas, "You won! \nYou scored " + playerStatus.getScore(),
				"Congratulations!", JOptionPane.WARNING_MESSAGE);
		for(Marker m : markers)
			m.y_coord.deleteObserver(hitDetectionObserver);
		requestScreenChangeTo(Screen.MAIN_MENU);
	}

	private void setupSongAndMissedNoteObs() {
		currentSong = new Song(ApplicationManager.SELECTION);
		MARKER_SPAWN_RATE = currentSong.getMicroSecPerBeat();
		
		hitDetect = Executors.newScheduledThreadPool(1);
		hitDetect.schedule(new Runnable() {
			@Override
			public void run() {
				hitDetectionObserver = new HitDetectionObserver(GameScreenController.this, screenCanvas);
			}
		}, 0, TimeUnit.SECONDS);
	}
}
