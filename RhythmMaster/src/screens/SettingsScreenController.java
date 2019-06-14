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
	// setup needed to display the sliding bars for display and control
	private int BAR_WIDTH = 300, BAR_HEIGHT = 50, BORDER_WIDTH = 10;
	DrawableRectangle volumeBar = new DrawableRectangle(ApplicationManager.SCREEN_WIDTH / 4 - BAR_WIDTH / 2, 
			ApplicationManager.SCREEN_HEIGHT * 3 / 4 - BAR_HEIGHT / 2,
			BAR_WIDTH, BAR_HEIGHT, new Color(0xeb, 0x69, 0x6e));
	DrawableRectangle difficultyBar = new DrawableRectangle(ApplicationManager.SCREEN_WIDTH * 3 / 4 - BAR_WIDTH / 2, 
			ApplicationManager.SCREEN_HEIGHT * 3 / 4 - BAR_HEIGHT / 2,
			BAR_WIDTH, BAR_HEIGHT, new Color(0xeb, 0x69, 0x6e));
	DrawableRectangle volumeBacker = new DrawableRectangle(ApplicationManager.SCREEN_WIDTH / 4 - BAR_WIDTH / 2 - BORDER_WIDTH,
			ApplicationManager.SCREEN_HEIGHT * 3 / 4 - BAR_HEIGHT / 2 - BORDER_WIDTH, BAR_WIDTH + 2 * BORDER_WIDTH, 
			BAR_HEIGHT + 2 * BORDER_WIDTH, Color.BLACK);
	DrawableRectangle difficultyBacker = new DrawableRectangle(ApplicationManager.SCREEN_WIDTH * 3 / 4 - BAR_WIDTH / 2 - BORDER_WIDTH,
			ApplicationManager.SCREEN_HEIGHT * 3 / 4 - BAR_HEIGHT / 2 - BORDER_WIDTH, BAR_WIDTH + 2 * BORDER_WIDTH, 
			BAR_HEIGHT + 2 * BORDER_WIDTH, Color.BLACK);
	private int barYCoord = difficultyBar.y;
	
	public SettingsScreenController() {
		screenType = Screen.SETTINGS;
		screenMusicPath = "../Sounds/BadApple.wav";
		screenBackgroundPath = "../Images/backgroundImages/background-Settings.png";
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

	// sets the volume in application manager's static variable
	private void setVolume() {
		ApplicationManager.VOLUME = (float)volumeBar.getHeight()/BAR_HEIGHT;
	}
  
	// sets the tolerance in the application manager's static variable
	private void setTolerance() {
		ApplicationManager.TOLERANCE = (float)difficultyBar.getHeight()/BAR_HEIGHT;
	}

	// sets up the difficulty bar in the filled state with mouse click and drag listeners
	private void setupDifficultyBar() {
		difficultyBar.setFilled(true);
		difficultyBacker.setFilled(true);
		screenCanvas.addDynamicDrawable(difficultyBar);
		screenCanvas.addStaticDrawable(difficultyBacker);
		screenCanvas.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int mouseX = e.getX();
				int mouseY = e.getY();
				
				if(difficultyBar.x < mouseX && mouseX < difficultyBar.x + BAR_WIDTH 
					&& barYCoord < mouseY && mouseY < barYCoord + BAR_HEIGHT) {
					int newHeight = barYCoord + BAR_HEIGHT - mouseY;
					difficultyBar.setLocation(difficultyBar.x, mouseY);
					difficultyBar.setSize(300, newHeight);
					screenCanvas.repaint();
					setTolerance();
				}
			}
		});
	
		screenCanvas.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				int mouseX = e.getX();
				int mouseY = e.getY();

				if (mouseY > barYCoord && mouseY < barYCoord + difficultyBar.height
						&& mouseX > difficultyBar.x && mouseX < difficultyBar.x + 300) {
					int newHeight = barYCoord + BAR_HEIGHT - mouseY;
					difficultyBar.setLocation(difficultyBar.x, mouseY);
					difficultyBar.setSize(300, newHeight);
					screenCanvas.repaint();
					setTolerance();
				}	
			} 
		});
	}

	// sets up the volume bar in the filled state with mouse click and drag listeners
	private void setupVolumeBar() {
		volumeBar.setFilled(true);
		volumeBacker.setFilled(true);
		screenCanvas.addDynamicDrawable(volumeBar);
		screenCanvas.addStaticDrawable(volumeBacker);
		screenCanvas.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int mouseX = e.getX();
				int mouseY = e.getY();
				//System.out.println("clicked! at " + mouseX + ", " + mouseY);
				if (mouseY > barYCoord && mouseY < barYCoord + volumeBar.height && mouseX > volumeBar.x
						&& mouseX < volumeBar.x + 300) {
					//int newWidth = (mouseX > volumeBar.x && mouseX < volumeBar.x + 300) ? mouseX - volumeBar.x : 0;
					int newHeight = barYCoord + BAR_HEIGHT - mouseY;
					volumeBar.setLocation(volumeBar.x, mouseY);
					volumeBar.setSize(300, newHeight);
					screenCanvas.repaint();
					setVolume();
				}
			}
		});
		screenCanvas.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				int mouseX = e.getX();
				int mouseY = e.getY();
				
				if (mouseY > barYCoord && mouseY < barYCoord + volumeBar.height && mouseX > volumeBar.x
						&& mouseX < volumeBar.x + 300) {
					//System.out.println("vol - clicked! at " + mouseX + ", " + mouseY);
					//int newWidth = (mouseX > volumeBar.x && mouseX < volumeBar.x + 300) ? mouseX - volumeBar.x : 0;
					int newHeight = barYCoord + BAR_HEIGHT - mouseY;
					volumeBar.setLocation(volumeBar.x, mouseY);
					volumeBar.setSize(300, newHeight);
					screenCanvas.repaint();
					setVolume();
				}
			}
		});
	}

	// sets up the apply button
	private void setupButtons() {
		ImageIcon applyIcon = new ImageIcon(Main.class.getResource("../Images/componentImages/Settings-Apply.png"));
		JButton applyButton = new JButton(applyIcon);
		applyButton.setBounds(ApplicationManager.SCREEN_WIDTH / 32, ApplicationManager.SCREEN_HEIGHT * 7 / 8 
				- applyIcon.getIconHeight() / 2, applyIcon.getIconWidth(), applyIcon.getIconHeight());
		applyButton.setContentAreaFilled(false);
		applyButton.setBorderPainted(false);
		applyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				requestScreenChangeTo(Screen.MAIN_MENU);
			}
		});
		screenCanvas.add(applyButton);

		ImageIcon volumeIcon = new ImageIcon(Main.class.getResource("../Images/componentImages/Settings-Volume.png"));
		JButton volumeButton = new JButton(volumeIcon);
		volumeButton.setBounds(ApplicationManager.SCREEN_WIDTH / 4 - volumeIcon.getIconWidth() / 2, 
				ApplicationManager.SCREEN_HEIGHT * 5 / 8 - volumeIcon.getIconHeight() / 2, volumeIcon.getIconWidth(), volumeIcon.getIconHeight());
		volumeButton.setContentAreaFilled(false);
		volumeButton.setBorderPainted(false);
		screenCanvas.addButton(volumeButton);
		ImageIcon difficultyIcon = new ImageIcon(Main.class.getResource("../Images/componentImages/Settings-Tolerance.png"));
		JButton dificultyButton = new JButton(difficultyIcon);
		dificultyButton.setBounds(ApplicationManager.SCREEN_WIDTH * 3 / 4 - difficultyIcon.getIconWidth() / 2, 
				ApplicationManager.SCREEN_HEIGHT * 5 / 8 - difficultyIcon.getIconHeight() / 2,
				difficultyIcon.getIconWidth(), difficultyIcon.getIconHeight());
		dificultyButton.setContentAreaFilled(false);
		dificultyButton.setBorderPainted(false);
		screenCanvas.addButton(dificultyButton);
	}
}