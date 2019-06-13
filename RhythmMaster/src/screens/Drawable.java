package screens;

import java.awt.Graphics;

public interface Drawable {
	// draws the drawable
	public void draw(Graphics g);
	
	// sets position
	public void setCenterX(int x);
	public void setCenterY(int y);
	public void setCenter(int x, int y);
}
