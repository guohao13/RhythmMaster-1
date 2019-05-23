package screens;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JSlider;

public class SettingsScreenController extends ScreenController {
	
	public SettingsScreenController () {	
		screenType = Screen.SETTINGS;
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
		ImageIcon settingsIcon = new ImageIcon(Main.class.getResource("../Images/SETTINGS_head.png"));
		JButton settingsButton = new JButton(settingsIcon);
		settingsButton.setBounds(1280/2 - settingsIcon.getIconWidth()/2, 720/4 - settingsIcon.getIconHeight()/2, settingsIcon.getIconWidth(), settingsIcon.getIconHeight());
		settingsButton.setContentAreaFilled(false);
		settingsButton.setBorderPainted(false);
		
		settingsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
			}
		});
		
		screenCanvas.addButton(settingsButton);
		
		JSlider volumeSlider = new JSlider();
		
		
	}
}
