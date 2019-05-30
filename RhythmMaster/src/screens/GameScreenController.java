package screens;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class GameScreenController extends ScreenController {
	int globalYOffset = -20;
	int screenCenterX = 1280 / 2, screenCenterY =  680/ 2, railSpacing = 150, railWidth = 10, railHeight = 600, hitBarWidth = 700, hitBarHeight = 50;
	int railTop = 60, rail1x = screenCenterX - railSpacing*3/2 - railWidth/2, rail2x = screenCenterX - railSpacing/2 - railWidth/2, rail3x = screenCenterX + railSpacing/2 - railWidth/2, rail4x = screenCenterX + railSpacing*3/2 - railWidth/2;
	int dy = 5;
	DrawableRectangle hitBar;
	List<Marker> markers = new ArrayList<Marker>();
	Timer screenTimer;
	Timer updateTimer;
	
	public GameScreenController() {
		screenType = Screen.GAME;
		screenMusicPath = "../Sounds/Butterfly.wav";
		screenBackgroundPath = "../Images/testOtherBackground.jpg";
		screenTimer = new Timer();
		setupCanvas();
		setupRails();
		setupDemoMarkers();
	}
	
	private void setupDemoMarkers() {
		
		markers.add(new Marker(rail1x, railTop,1));
		markers.add(new Marker(rail2x, railTop,2));
		markers.add(new Marker(rail3x, railTop,3));
		markers.add(new Marker(rail4x, railTop,4));
		
		for(Marker m : markers) {
			screenCanvas.addDynamicDrawable(m);
		}
		
		Timer t = new Timer();
		TimerTask tt = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				for(Marker m : markers) {
					m.setLocation(m.x, m.y + dy);
					if (m.getY() > 610) {
						t.cancel();
					}
				}
				screenCanvas.repaint();
			}
			
		};
		t.scheduleAtFixedRate(tt, 0, 10);
		
	}

	@Override
	public Canvas setupCanvas() {
		screenCanvas = new Canvas();
		screenCanvas.setBackground(screenBackgroundPath);
		setupButtons();
		setupHitBar();
		return screenCanvas;
	}

	private void setupButtons() {
//		JButton button = new JButton(new ImageIcon(Main.class.getResource("../Images/testButton.png")));
//		button.setBounds(400, 500, 200, 100);
//		button.setContentAreaFilled(false);
//		button.setBorderPainted(false);
//
//		button.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent event) {
//				requestScreenChangeTo(Screen.MAIN_MENU);
//			}
//		});
//
//		screenCanvas.addButton(button);
	}

	private void setupRails() {
		
		

		System.out.println("setting up rails!" + screenCenterX + " ");
		DrawableRectangle rail1 = new DrawableRectangle(screenCenterX - railSpacing * 3 / 2 - railWidth / 2,
				60, railWidth, railHeight, Color.RED);
		rail1.setFilled(true);
		DrawableRectangle rail2 = new DrawableRectangle(screenCenterX - railSpacing / 2 - railWidth / 2,
				60, railWidth, railHeight, Color.RED);
		rail2.setFilled(true);
		DrawableRectangle rail3 = new DrawableRectangle(screenCenterX + railSpacing / 2 - railWidth / 2,
				60, railWidth, railHeight, Color.RED);
		rail3.setFilled(true);
		DrawableRectangle rail4 = new DrawableRectangle(screenCenterX + railSpacing * 3 / 2 - railWidth / 2,
				60, railWidth, railHeight, Color.RED);
		rail4.setFilled(true);

		screenCanvas.addStaticDrawable(rail1);
		screenCanvas.addStaticDrawable(rail2);
		screenCanvas.addStaticDrawable(rail3);
		screenCanvas.addStaticDrawable(rail4);
	}

	private void setupHitBar() {
		hitBar = new DrawableRectangle(screenCenterX - this.hitBarWidth / 2, 560 - hitBarHeight/2, hitBarWidth,
				hitBarHeight, Color.RED);
		hitBar.setFilled(true);
		screenCanvas.addStaticDrawable(hitBar);
	}
	
	
	
	public void changeHitBarColor(Color c) {
		hitBar.setColor(c);
	}
}
