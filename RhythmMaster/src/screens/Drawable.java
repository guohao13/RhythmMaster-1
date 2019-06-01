package screens;

import java.awt.Graphics;

public interface Drawable {
	public void draw(Graphics g);
	public void setCenterX(int x);
	public void setCenterY(int y);
	public void setCenter(int x, int y);
	
}
