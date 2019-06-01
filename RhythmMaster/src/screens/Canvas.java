package screens;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Canvas extends JPanel {
	private static final long serialVersionUID = 1L;
	private Image background;
	private List<JButton> canvasButtons = new ArrayList<JButton>();
	private List<Drawable> dynamicDrawables = new ArrayList<Drawable>(), staticDrawables = new ArrayList<Drawable>();
	
	
	public Canvas() {
		setLayout(null);
		setFocusable(true);
		requestFocusInWindow();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, ApplicationManager.SCREEN_WIDTH, ApplicationManager.SCREEN_HEIGHT, this);
		for(Drawable d: staticDrawables) {
			d.draw(g);
		}
		for (Drawable d : dynamicDrawables) {
			d.draw(g);
		}
	}
   
	public void setBackground(String path) {
		background = new ImageIcon(Main.class.getResource(path)).getImage();
		repaint();
	}
   
	public JButton addButton(JButton button) {
		canvasButtons.add(button);
		add(button);
		repaint();
		return button;
	}
	
	public Component addComponent(Component c) {
		add(c);
		repaint();
		return c;
	}
	
	public Drawable addDynamicDrawable(Drawable d) {
		dynamicDrawables.add(d);
		repaint();
		return d;
	}
	
	public Drawable addStaticDrawable(Drawable d) {
		staticDrawables.add(d);
		repaint();
		return d;
	}
	public void removeButtonListeners() {
		for (JButton button : canvasButtons) {
			for (ActionListener listener : button.getActionListeners()) {
				button.removeActionListener(listener);
			}
		}
	}
}
