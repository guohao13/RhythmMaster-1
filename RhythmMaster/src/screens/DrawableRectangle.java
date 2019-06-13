package screens;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class DrawableRectangle extends Rectangle implements Drawable {
	private static final long serialVersionUID = 1L;

	private Color c;
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

	// sets if the drawable rectangle should be filled
	public void setFilled(boolean b) {
		filled = b;
	}

	@Override
	public void draw(Graphics g) {
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

	@Override
	public void setCenterX(int x) {
		this.x = x - this.width/2;
	}

	@Override
	public void setCenterY(int y) {
		this.y = y - this.height/2;
	}

	@Override
	public void setCenter(int x, int y) {
		setCenterX(x);
		setCenterY(y);
	}
	
	public void setColor(Color c) {
		this.c = c;
	}
}
