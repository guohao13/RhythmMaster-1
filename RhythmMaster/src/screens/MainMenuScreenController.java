package screens;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MainMenuScreenController extends ScreenController {

	public MainMenuScreenController() {
		screenType = Screen.MAIN_MENU;
		screenMusicPath = "../Sounds/WiiMenu.wav";
		screenBackgroundPath = "../Images/backgroundImages/background-Menu.png";
		setupDisplayAndMusic();
	}
	
	
	@Override
	public Canvas setupCanvas() {
		screenCanvas = new Canvas();
		screenCanvas.setBackground(screenBackgroundPath);
		setupButtons();
		setupText();
		return screenCanvas;
	}
	
	private void setupButtons() {
		ImageIcon menuIcon = new ImageIcon(Main.class.getResource("../Images/MENU.png"));
		JButton button = new JButton(menuIcon);
		button.setBounds(1280/2 - menuIcon.getIconWidth()/2, 720/4 - menuIcon.getIconHeight()/2, menuIcon.getIconWidth(), menuIcon.getIconHeight());
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		
		//screenCanvas.addButton(button);
		
		ImageIcon startGameIcon = new ImageIcon(Main.class.getResource("../Images/componentImages/Menu-Select.png"));
		JButton startGameButton = new JButton(startGameIcon);
		startGameButton.setBounds(1280/32, 720*2/3 - startGameIcon.getIconHeight()/2, startGameIcon.getIconWidth(), startGameIcon.getIconHeight());
		startGameButton.setContentAreaFilled(false);
		startGameButton.setBorderPainted(false);
		
		startGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				requestScreenChangeTo(Screen.SONG_MENU);
			}
		});
		screenCanvas.addButton(startGameButton);
		
		ImageIcon settingsIcon = new ImageIcon(Main.class.getResource("../Images/componentImages/Menu-Settings.png"));
		JButton settingsButton = new JButton(settingsIcon);
		settingsButton.setBounds(1280/32, 720*5/6 - settingsIcon.getIconHeight()/2, settingsIcon.getIconWidth(), settingsIcon.getIconHeight());
		settingsButton.setContentAreaFilled(false);
		settingsButton.setBorderPainted(false);
		
		settingsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				requestScreenChangeTo(Screen.SETTINGS);
			}
		});
		
		screenCanvas.addButton(settingsButton);
		
		
	}
	
	
	
	private void setupText() {
		ImageIcon menuIcon = new ImageIcon(Main.class.getResource("../Images/MENU.png"));
		
		
	}
}
