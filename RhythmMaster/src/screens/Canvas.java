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
	private static final long serialVersionUID = 1L;	// needed to prevent warning
	private Image background;
	private List<JButton> canvasButtons = new ArrayList<JButton>();
	private ArrayList<Drawable>staticDrawables = new ArrayList<Drawable>();
	private ArrayList<Drawable> dynamicDrawables = new ArrayList<Drawable>();	
	
	public Canvas() {
		setLayout(null);
		setFocusable(true);
		requestFocusInWindow();
	}
	
	@Override
	synchronized public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, ApplicationManager.SCREEN_WIDTH, ApplicationManager.SCREEN_HEIGHT, this);
		for(Drawable s: staticDrawables) {
			s.draw(g);
		}
		for (Drawable d : dynamicDrawables) {
			d.draw(g);
		}
	}
   
	// sets the background to the background image
	public void setBackground(String path) {
		background = new ImageIcon(Main.class.getResource(path)).getImage();
		repaint();
	}
   
	// adds a button to this panel
	public JButton addButton(JButton button) {
		canvasButtons.add(button);
		add(button);
		repaint();
		return button;
	}
	
	// adds a generic component to this panel
	public Component addComponent(Component c) {
		add(c);
		repaint();
		return c;
	}
	
	synchronized public ArrayList<Drawable> getDynamicList(){
		return dynamicDrawables;
	}
	
	synchronized public ArrayList<Drawable> getStaticList(){
		return staticDrawables;
	}
	
	// adds a drawable, which will be dynamically manipulated to this panel
	synchronized public Drawable addDynamicDrawable(Drawable m) {
		dynamicDrawables.add(m);
		repaint();
		return m;
	}
	
	// adds a drawable, which is not expected to be manipulated to this panel
	synchronized public Drawable addStaticDrawable(Drawable d) {
		staticDrawables.add(d);
		repaint();
		return d;
	}
	
	// removes all action listeners from all buttons in this panel
	public void removeButtonListeners() {
		for (JButton button : canvasButtons) {
			for (ActionListener listener : button.getActionListeners()) {
				button.removeActionListener(listener);
			}
		}
	}
	
	// removes the marker from the dynamic drawable list so it will not be redrawn
	synchronized public void removeMarker(Marker m) {
		dynamicDrawables.remove(m);
	}	
}
