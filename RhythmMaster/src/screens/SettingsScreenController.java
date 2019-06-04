package screens;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class SettingsScreenController extends ScreenController {
	private int BAR_WIDTH=300;
	DrawableRectangle volumeBar = new DrawableRectangle(1280/4 - 150, 720*3/4 - 25, BAR_WIDTH, 50, Color.RED), difficultyBar = new DrawableRectangle(1280*3/4 - 150, 720*3/4 - 25, BAR_WIDTH, 50, Color.RED);
	public SettingsScreenController() {
		screenType = Screen.SETTINGS;
		screenMusicPath = "../Sounds/BadApple.wav";
		screenBackgroundPath = "../Images/testOtherBackground.jpg";
		setupDisplayAndMusic();
	}

	@Override
	public Canvas setupCanvas() {
		screenCanvas = new Canvas();
		screenCanvas.setBackground(screenBackgroundPath);
		setupButtons();
		setupVolumeBar();
		setupDifficultyBar();
		return screenCanvas;
	}
	
	private void setVolume() {
		ApplicationManager.VOLUME = (float)volumeBar.getWidth()/BAR_WIDTH;
		
	}
	private void setTolerance() {
		ApplicationManager.TOLERANCE = 1; // TODO: figure out what type this should be difficultyBar.getWidth()/BAR_WIDTH;
	}
	
	private void setupDifficultyBar() {
		difficultyBar.setFilled(true);
		screenCanvas.addStaticDrawable(difficultyBar);
		screenCanvas.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int mouseX = e.getX();
				int mouseY = e.getY();
				System.out.println("clicked! at " + mouseX + ", " + mouseY);
				if (mouseY > difficultyBar.y && mouseY < difficultyBar.y + difficultyBar.height && mouseX > difficultyBar.x && mouseX < difficultyBar.x+300) {
					int newWidth = (mouseX > difficultyBar.x && mouseX < difficultyBar.x + 300) ? mouseX - difficultyBar.x : 0;
					difficultyBar.setSize(newWidth, difficultyBar.height);
					screenCanvas.repaint();
					setTolerance();
				}		
			}
		});
	
		screenCanvas.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				int mouseX = e.getX();
				int mouseY = e.getY();
				System.out.println("clicked! at " + mouseX + ", " + mouseY);
				if (mouseY > difficultyBar.y && mouseY < difficultyBar.y + difficultyBar.height && mouseX > difficultyBar.x && mouseX < difficultyBar.x+300) {
					int newWidth = (mouseX > difficultyBar.x && mouseX < difficultyBar.x + 300) ? mouseX - difficultyBar.x : 0;
					difficultyBar.setSize(newWidth, difficultyBar.height);
					screenCanvas.repaint();
					setTolerance();
				}				
			} 
		});
	}
	
	private void setupVolumeBar() {
		volumeBar.setFilled(true);
		screenCanvas.addDynamicDrawable(volumeBar);
		screenCanvas.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int mouseX = e.getX();
				int mouseY = e.getY();
				System.out.println("clicked! at " + mouseX + ", " + mouseY);
				if (mouseY > volumeBar.y && mouseY < volumeBar.y + volumeBar.height && mouseX > volumeBar.x && mouseX < volumeBar.x+300) {
					int newWidth = (mouseX > volumeBar.x && mouseX < volumeBar.x + 300) ? mouseX - volumeBar.x : 0;
					volumeBar.setSize(newWidth, volumeBar.height);
					screenCanvas.repaint();
					setVolume();
				}
			} 
			
		});
		screenCanvas.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				int mouseX = e.getX();
				int mouseY = e.getY();
				System.out.println("clicked! at " + mouseX + ", " + mouseY);
				if (mouseY > volumeBar.y && mouseY < volumeBar.y + volumeBar.height && mouseX > volumeBar.x && mouseX < volumeBar.x+300) {
					int newWidth = (mouseX > volumeBar.x && mouseX < volumeBar.x + 300) ? mouseX - volumeBar.x : 0;
					volumeBar.setSize(newWidth, volumeBar.height);
					screenCanvas.repaint();
					setVolume();
				}
			} 
		});
	}

	private void setupButtons() {
		ImageIcon settingsIcon = new ImageIcon(Main.class.getResource("../Images/SETTINGS_head.png"));
		JButton settingsButton = new JButton(settingsIcon);
		settingsButton.setBounds(ApplicationManager.SCREEN_WIDTH / 2 - settingsIcon.getIconWidth() / 2, ApplicationManager.SCREEN_HEIGHT / 4 - settingsIcon.getIconHeight() / 2,settingsIcon.getIconWidth(), settingsIcon.getIconHeight());
		settingsButton.setContentAreaFilled(false);
		settingsButton.setBorderPainted(false);

		settingsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				requestScreenChangeTo(Screen.MAIN_MENU);				
			}
		});

		screenCanvas.addButton(settingsButton);
	}
}
