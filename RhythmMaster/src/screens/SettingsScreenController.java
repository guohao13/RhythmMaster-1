package screens;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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
		double volume = volumeBar.getWidth()/BAR_WIDTH;
		
	}
	private void setDifficulty() {
		double difficulty = difficultyBar.getWidth()/BAR_WIDTH;
	}
	
	private void setupDifficultyBar() {
		difficultyBar.setFilled(true);
		screenCanvas.addDynamicDrawable(difficultyBar);
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
				if (mouseY > difficultyBar.y && mouseY < difficultyBar.y + difficultyBar.height && mouseX > difficultyBar.x && mouseX < difficultyBar.x+300) {
					int newWidth = (mouseX > difficultyBar.x && mouseX < difficultyBar.x + 300) ? mouseX - difficultyBar.x : 0;
					difficultyBar.setSize(newWidth, difficultyBar.height);
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
				if (mouseY > difficultyBar.y && mouseY < difficultyBar.y + difficultyBar.height && mouseX > difficultyBar.x && mouseX < difficultyBar.x+300) {
					int newWidth = (mouseX > difficultyBar.x && mouseX < difficultyBar.x + 300) ? mouseX - difficultyBar.x : 0;
					difficultyBar.setSize(newWidth, difficultyBar.height);
					screenCanvas.repaint();
				}
				
				
			} 

		});
	}
	
	private void setupVolumeBar() {

		
		volumeBar.setFilled(true);
		screenCanvas.addDynamicDrawable(volumeBar);
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
