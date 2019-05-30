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
		setupCanvas();
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
		songsButton.setBounds(1280 / 2 - songsIcon.getIconWidth() / 2, 720 / 5 - songsIcon.getIconHeight() / 2,
				songsIcon.getIconWidth(), songsIcon.getIconHeight());
		songsButton.setContentAreaFilled(false);
		songsButton.setBorderPainted(false);

		songsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
			}
		});

		screenCanvas.addButton(songsButton);
		
		ImageIcon badAppleIcon = new ImageIcon(Main.class.getResource("../Images/badapple.png"));
		JButton badAppleButton = new JButton(badAppleIcon);
		badAppleButton.setBounds(1280 / 3 - badAppleIcon.getIconWidth() / 2, 720 *3/ 5 - badAppleIcon.getIconHeight() / 2,
				badAppleIcon.getIconWidth(), badAppleIcon.getIconHeight());
		badAppleButton.setContentAreaFilled(false);
		badAppleButton.setBorderPainted(false);

		badAppleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				requestScreenChangeTo(Screen.GAME);
			}
		});

		screenCanvas.addButton(badAppleButton);
		
		ImageIcon butterflyIcon = new ImageIcon(Main.class.getResource("../Images/butterfly.png"));
		JButton butterflyButton = new JButton(butterflyIcon);
		butterflyButton.setBounds(1280 *2/ 3 - butterflyIcon.getIconWidth() / 2, 720 *3/ 5 - butterflyIcon.getIconHeight() / 2,
				butterflyIcon.getIconWidth(), butterflyIcon.getIconHeight());
		butterflyButton.setContentAreaFilled(false);
		butterflyButton.setBorderPainted(false);

		butterflyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				requestScreenChangeTo(Screen.GAME);
			}
		});

		screenCanvas.addButton(butterflyButton);
	}
}
