package screens;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class DrawableRectangle extends Rectangle implements Drawable {

	protected Color c;
	protected boolean filled = true;
	protected boolean center = false;

	DrawableRectangle(int x, int y, int height, int width) {
		super(x, y, height, width);
		c = Color.BLACK;
	}

	DrawableRectangle(int x, int y, int height, int width, Color c) {
		super(x, y, height, width);
		this.c = c;
	}

	public void setFilled(boolean b) {
		filled = b;
	}

	public void drawCentered(boolean b) {
		center = b;
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(c);
		//if we want the rectangle centered, set the x and y coordinates for drawing
		int drawX = (center) ? x - width / 2 : x;
		int drawY = (center) ? y - height / 2 : y;
		
		//then draw using those conditionally-derived coordinates
		if (filled) {
			g.fillRect(drawX, drawY, width, height);
		} else {
			g.drawRect(drawX, drawY, width, height);
		}
	}

}
