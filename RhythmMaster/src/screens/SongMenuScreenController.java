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
		JButton button = new JButton(new ImageIcon(Main.class.getResource("../Images/testButton.png")));
		button.setBounds(200,500,200,100);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				requestScreenChangeTo(Screen.SETTINGS);
			}
		});
		
		screenCanvas.addButton(button);
	}
}
