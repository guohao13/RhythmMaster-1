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
	private int BAR_WIDTH = 300, BAR_HEIGHT = 50, BORDER_WIDTH = 10;
	DrawableRectangle volumeBar = new DrawableRectangle(1280 / 4 - BAR_WIDTH / 2, 720 * 3 / 4 - BAR_HEIGHT / 2,
			BAR_WIDTH, BAR_HEIGHT, Color.RED);
	DrawableRectangle difficultyBar = new DrawableRectangle(1280 * 3 / 4 - BAR_WIDTH / 2, 720 * 3 / 4 - BAR_HEIGHT / 2,
			BAR_WIDTH, BAR_HEIGHT, Color.RED);
	DrawableRectangle volumeBacker = new DrawableRectangle(1280 / 4 - BAR_WIDTH / 2 - BORDER_WIDTH,
			720 * 3 / 4 - BAR_HEIGHT / 2 - BORDER_WIDTH, BAR_WIDTH + 2 * BORDER_WIDTH, BAR_HEIGHT + 2 * BORDER_WIDTH,
			Color.BLACK);
	DrawableRectangle difficultyBacker = new DrawableRectangle(1280 * 3 / 4 - BAR_WIDTH / 2 - BORDER_WIDTH,
			720 * 3 / 4 - BAR_HEIGHT / 2 - BORDER_WIDTH, BAR_WIDTH + 2 * BORDER_WIDTH, BAR_HEIGHT + 2 * BORDER_WIDTH,
			Color.BLACK);

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

	private void setVolume() {
		double volume = volumeBar.getWidth() / BAR_WIDTH;

	}

	private void setDifficulty() {
		double difficulty = difficultyBar.getWidth() / BAR_WIDTH;
	}

	private void setupDifficultyBar() {

		difficultyBar.setFilled(true);
		difficultyBacker.setFilled(true);
		screenCanvas.addDynamicDrawable(difficultyBar);
		screenCanvas.addStaticDrawable(difficultyBacker);
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
				if (mouseY > difficultyBar.y && mouseY < difficultyBar.y + difficultyBar.height
						&& mouseX > difficultyBar.x && mouseX < difficultyBar.x + 300) {
					int newWidth = (mouseX > difficultyBar.x && mouseX < difficultyBar.x + 300)
							? mouseX - difficultyBar.x
							: 0;
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
				if (mouseY > difficultyBar.y && mouseY < difficultyBar.y + difficultyBar.height
						&& mouseX > difficultyBar.x && mouseX < difficultyBar.x + 300) {
					int newWidth = (mouseX > difficultyBar.x && mouseX < difficultyBar.x + 300)
							? mouseX - difficultyBar.x
							: 0;
					difficultyBar.setSize(newWidth, difficultyBar.height);
					screenCanvas.repaint();
				}

			}

		});
	}

	private void setupVolumeBar() {

		volumeBar.setFilled(true);
		volumeBacker.setFilled(true);
		screenCanvas.addDynamicDrawable(volumeBar);
		screenCanvas.addStaticDrawable(volumeBacker);
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
				if (mouseY > volumeBar.y && mouseY < volumeBar.y + volumeBar.height && mouseX > volumeBar.x
						&& mouseX < volumeBar.x + 300) {
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
				if (mouseY > volumeBar.y && mouseY < volumeBar.y + volumeBar.height && mouseX > volumeBar.x
						&& mouseX < volumeBar.x + 300) {
					int newWidth = (mouseX > volumeBar.x && mouseX < volumeBar.x + 300) ? mouseX - volumeBar.x : 0;
					volumeBar.setSize(newWidth, volumeBar.height);
					screenCanvas.repaint();
				}
			}

		});
	}

	private void setupButtons() {

		ImageIcon volumeIcon = new ImageIcon(Main.class.getResource("../Images/componentImages/Settings-Volume.png"));
		JButton volumeButton = new JButton(volumeIcon);
		volumeButton.setBounds(1280 / 4 - volumeIcon.getIconWidth() / 2, 720 * 5 / 8 - volumeIcon.getIconHeight() / 2,
				volumeIcon.getIconWidth(), volumeIcon.getIconHeight());
		volumeButton.setContentAreaFilled(false);
		volumeButton.setBorderPainted(false);
		screenCanvas.addButton(volumeButton);
		ImageIcon difficultyIcon = new ImageIcon(Main.class.getResource("../Images/componentImages/Settings-Tolerance.png"));
		JButton dificultyButton = new JButton(difficultyIcon);
		dificultyButton.setBounds(1280 *3/ 4 - difficultyIcon.getIconWidth() / 2, 720 * 5 / 8 - difficultyIcon.getIconHeight() / 2,
				difficultyIcon.getIconWidth(), difficultyIcon.getIconHeight());
		dificultyButton.setContentAreaFilled(false);
		dificultyButton.setBorderPainted(false);
		screenCanvas.addButton(dificultyButton);

	}
}
