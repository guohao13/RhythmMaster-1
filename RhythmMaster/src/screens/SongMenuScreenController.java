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

	private void setupButtons() {
		ImageIcon songsIcon = new ImageIcon(Main.class.getResource("../Images/componentImages/Song-Back.png"));
		JButton songsButton = new JButton(songsIcon);
		songsButton.setBounds(1280 / 32, 720 *7/8 - songsIcon.getIconHeight() / 2,
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
		wiiThemeButton.setBounds(1280 / 32, 720 * 5/8 - wiiThemeIcon.getIconHeight() / 2, wiiThemeIcon.getIconWidth(),
				wiiThemeIcon.getIconHeight());
		wiiThemeButton.setContentAreaFilled(false);
		wiiThemeButton.setBorderPainted(false);

		wiiThemeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				requestScreenChangeTo(Screen.GAME);
			}
		});

		screenCanvas.addButton(wiiThemeButton);

		ImageIcon butterflyIcon = new ImageIcon(Main.class.getResource("../Images/componentImages/Song-Butterfly.png"));
		JButton butterflyButton = new JButton(butterflyIcon);
		butterflyButton.setBounds(1280 / 32, 720 * 6/8 - butterflyIcon.getIconHeight() / 2,
				butterflyIcon.getIconWidth(), butterflyIcon.getIconHeight());
		butterflyButton.setContentAreaFilled(false);
		butterflyButton.setBorderPainted(false);

		butterflyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				requestScreenChangeTo(Screen.GAME);
			}
		});

		screenCanvas.addButton(butterflyButton);

		// ApplicationManager.SELECTION = 0;
		// requestScreenChangeTo(Screen.SETTINGS);
		// }
		// });
		// screenCanvas.add(button);

		// JButton button = new JButton(new
		// ImageIcon(Main.class.getResource("../Images/testButton.png")));
		// button.setBounds(200,400,200,100);
		// button.setContentAreaFilled(false);
		// button.setBorderPainted(false);
		//
		// button.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent event) {
		// ApplicationManager.SELECTION = 1;
		// requestScreenChangeTo(Screen.SETTINGS);
		// }
		// });
		// screenCanvas.addButton(button);
	}
}
