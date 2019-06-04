package screens;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class SongMenuScreenController extends ScreenController {
	
	public SongMenuScreenController() {
		screenType = Screen.SONG_MENU;
		screenMusicPath = "../Sounds/BadApple.wav";
		screenBackgroundPath = "../Images/testOtherBackground.jpg";
		setupDisplayAndMusic();
	}
	
	@Override
	public Canvas setupCanvas() {
		screenCanvas = new Canvas();
		screenCanvas.setBackground(screenBackgroundPath);
		setupButtons();
		return screenCanvas;
	}
	
	private void setupButtons() {
		ImageIcon songsIcon = new ImageIcon(Main.class.getResource("../Images/SONGS_head.png"));
		JButton songsButton = new JButton(songsIcon);
		songsButton.setBounds(1280 / 2 - songsIcon.getIconWidth() / 2, 720 / 5 - songsIcon.getIconHeight() / 2, songsIcon.getIconWidth(), songsIcon.getIconHeight());
		songsButton.setContentAreaFilled(false);
		songsButton.setBorderPainted(false);

		songsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				requestScreenChangeTo(Screen.SONG_MENU);
			}
		});

		screenCanvas.addButton(songsButton);
		
		ImageIcon wiiThemeIcon = new ImageIcon(Main.class.getResource("../Images/wiitheme.png"));
		JButton wiiThemeButton = new JButton(wiiThemeIcon);
		wiiThemeButton.setBounds(1280 / 3 - wiiThemeIcon.getIconWidth() / 2, 720 *3/ 5 - wiiThemeIcon.getIconHeight() / 2,
				wiiThemeIcon.getIconWidth(), wiiThemeIcon.getIconHeight());
		wiiThemeButton.setContentAreaFilled(false);
		wiiThemeButton.setBorderPainted(false);

		wiiThemeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				ApplicationManager.SELECTION = 1;
				requestScreenChangeTo(Screen.GAME);
			}
		});

		screenCanvas.addButton(wiiThemeButton);
		
		ImageIcon butterflyIcon = new ImageIcon(Main.class.getResource("../Images/butterfly.png"));
		JButton butterflyButton = new JButton(butterflyIcon);
		butterflyButton.setBounds(1280 *2/ 3 - butterflyIcon.getIconWidth() / 2, 720 *3/ 5 - butterflyIcon.getIconHeight() / 2,
				butterflyIcon.getIconWidth(), butterflyIcon.getIconHeight());
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
