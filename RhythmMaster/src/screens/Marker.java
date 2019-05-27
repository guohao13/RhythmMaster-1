package screens;

import java.awt.Color;
import java.awt.Graphics;

public class Marker extends DrawableRectangle {
	private static final int SIDE_LENGTH = 50;

	Marker(int x, int y) {
		super(x,y,SIDE_LENGTH,SIDE_LENGTH,Color.GRAY);
		this.center = true;
		this.filled = true;
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		super.draw(g);
	}

}
