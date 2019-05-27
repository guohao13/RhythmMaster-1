package screens;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class SettingsScreenController extends ScreenController {

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
		return screenCanvas;
	}
	
	private void setupVolumeBar() {

		DrawableRectangle volumeBar = new DrawableRectangle(1280/4 - 150, 720*3/4 - 25, 300, 50, Color.RED);
		volumeBar.setFilled(true);
		screenCanvas.addDrawable(volumeBar);
		screenCanvas.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				update(e);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				update(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				update(e);
			}
			
			public void update(MouseEvent e) {
				int mouseX = e.getX();
				int mouseY = e.getY();
				System.out.println("clicked! at " + mouseX + ", " + mouseY);
				if (mouseY > volumeBar.y && mouseY < volumeBar.y + volumeBar.height && mouseX > volumeBar.x && mouseX < volumeBar.x+300) {
					int newWidth = (mouseX > volumeBar.x && mouseX < volumeBar.x + 300) ? mouseX - volumeBar.x : 0;
					volumeBar.setSize(newWidth, volumeBar.height);
					screenCanvas.repaint();
				}
			} 
			
		});
		screenCanvas.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				update(e);
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			public void update(MouseEvent e) {
				int mouseX = e.getX();
				int mouseY = e.getY();
				System.out.println("clicked! at " + mouseX + ", " + mouseY);
				if (mouseY > volumeBar.y && mouseY < volumeBar.y + volumeBar.height && mouseX > volumeBar.x && mouseX < volumeBar.x+300) {
					int newWidth = (mouseX > volumeBar.x && mouseX < volumeBar.x + 300) ? mouseX - volumeBar.x : 0;
					volumeBar.setSize(newWidth, volumeBar.height);
					screenCanvas.repaint();
				}
			} 

		});
	}

	private void setupButtons() {
		ImageIcon settingsIcon = new ImageIcon(Main.class.getResource("../Images/SETTINGS_head.png"));
		JButton settingsButton = new JButton(settingsIcon);
		settingsButton.setBounds(1280 / 2 - settingsIcon.getIconWidth() / 2, 720 / 4 - settingsIcon.getIconHeight() / 2,
				settingsIcon.getIconWidth(), settingsIcon.getIconHeight());
		settingsButton.setContentAreaFilled(false);
		settingsButton.setBorderPainted(false);

		settingsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
			}
		});

		screenCanvas.addButton(settingsButton);


	}
}
