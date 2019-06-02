package screens;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Canvas extends JPanel {
	private static final long serialVersionUID = 1L;
	private Image background;
	private List<JButton> canvasButtons = new ArrayList<JButton>();
	private ArrayList<Drawable>staticDrawables = new ArrayList<Drawable>();
	private ArrayDeque<Drawable> dynamicDrawables = new ArrayDeque<Drawable>();
	
	
	public Canvas() {
		setLayout(null);
		setFocusable(true);
		requestFocusInWindow();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, ApplicationManager.SCREEN_WIDTH, ApplicationManager.SCREEN_HEIGHT, this);
		for(Drawable s: staticDrawables) {
			s.draw(g);
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
	
	public ArrayDeque<Marker> getDynamicList(){
		ArrayDeque<Marker> m = new ArrayDeque<Marker>();
		for(Drawable d : dynamicDrawables) {
			if (d instanceof Marker) {
				m.add((Marker)d);
			}
		}
		return m;
	}
	public ArrayList<Drawable> getStaticList(){
		return staticDrawables;
	}
	
	synchronized public Drawable addDynamicDrawable(Drawable m) {
		dynamicDrawables.add(m);
		repaint();
		return m;
	}
	
	synchronized public Drawable addStaticDrawable(Drawable d) {
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
