package screens;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class SongMenuScreenController extends ScreenController {

	public SongMenuScreenController() {
		screenType = Screen.SONG_MENU;
		screenMusicPath = "../Sounds/BadApple.wav";
		screenBackgroundPath = "../Images/backgroundImages/background-Song.png";
		setupDisplayAndMusic();
	}

	@Override
	public Canvas setupCanvas() {
		screenCanvas = new Canvas();
		screenCanvas.setBackground(screenBackgroundPath);
		setupButtons();
		return screenCanvas;
	}

	// sets up song selection buttons (one per song)
	private void setupButtons() {
		ImageIcon songsIcon = new ImageIcon(Main.class.getResource("../Images/componentImages/Song-Back.png"));
		JButton songsButton = new JButton(songsIcon);
		songsButton.setBounds(ApplicationManager.SCREEN_WIDTH / 32, ApplicationManager.SCREEN_HEIGHT * 7 / 8 - songsIcon.getIconHeight() / 2,
				songsIcon.getIconWidth(), songsIcon.getIconHeight());
		songsButton.setContentAreaFilled(false);
		songsButton.setBorderPainted(false);
		songsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				requestScreenChangeTo(Screen.MAIN_MENU);
			}
		});

		screenCanvas.addButton(songsButton);
    
		ImageIcon wiiThemeIcon = new ImageIcon(Main.class.getResource("../Images/componentImages/Song-Wii.png"));
		JButton wiiThemeButton = new JButton(wiiThemeIcon);
		wiiThemeButton.setBounds(ApplicationManager.SCREEN_WIDTH / 32, ApplicationManager.SCREEN_HEIGHT * 6/8 
				- wiiThemeIcon.getIconHeight() / 2, wiiThemeIcon.getIconWidth(), wiiThemeIcon.getIconHeight());
		wiiThemeButton.setContentAreaFilled(false);
		wiiThemeButton.setBorderPainted(false);
		wiiThemeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				ApplicationManager.SELECTION = 1;
				requestScreenChangeTo(Screen.GAME);
			}
		});

		screenCanvas.addButton(wiiThemeButton);

		ImageIcon butterflyIcon = new ImageIcon(Main.class.getResource("../Images/componentImages/Song-Butterfly.png"));
		JButton butterflyButton = new JButton(butterflyIcon);
		butterflyButton.setBounds(ApplicationManager.SCREEN_WIDTH / 32, ApplicationManager.SCREEN_HEIGHT * 5/8 
				- butterflyIcon.getIconHeight() / 2, butterflyIcon.getIconWidth(), butterflyIcon.getIconHeight());
		butterflyButton.setContentAreaFilled(false);
		butterflyButton.setBorderPainted(false);
		butterflyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				ApplicationManager.SELECTION = 0;
				requestScreenChangeTo(Screen.GAME);
			}
		});

		screenCanvas.addButton(butterflyButton);
	}
}
