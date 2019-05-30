package screens;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Observable;

public class Marker extends DrawableRectangle {
	private static final int SIDE_LENGTH = 50;
	private static final int HITBAR_Y_COORD = 670;
	private static final int OBSERVER_WINDOW_SIZE = 50;
	private static final int Y_COORD_STEP_SIZE = 5;
	
	private int railIndex;
	private ObservableYCoord y_coord;
	public boolean isNearHitbar = false;

	Marker(int x, int y, int railIndex) {
		super(x,y,SIDE_LENGTH,SIDE_LENGTH,Color.GRAY);
		this.center = true;
		this.filled = true;
		this.railIndex = railIndex;
		this.y_coord = new ObservableYCoord(this);
		this.y_coord.setValue(y);
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		super.draw(g);
	}
	
	public int getRailIndex() {
		return railIndex;
	}
	
	public ObservableYCoord getYCoord() {
		return y_coord;
	}
	
	public class ObservableYCoord extends Observable {
		
		Marker parent;
		private int y_coord;
		
		ObservableYCoord(Marker p) {
			this.parent = p;
		}

		private void setValue(int val) {
			y_coord = val;
			
			// entering hitbar territory
			if(Math.abs(HITBAR_Y_COORD - val) <= OBSERVER_WINDOW_SIZE) {
				isNearHitbar = true;
				notifyObservers(parent);
			}
			// way before hitbar
			else if(y_coord < HITBAR_Y_COORD - OBSERVER_WINDOW_SIZE - Y_COORD_STEP_SIZE)
				isNearHitbar = false;
			// way past hitbar
			else if(y_coord > HITBAR_Y_COORD + OBSERVER_WINDOW_SIZE + Y_COORD_STEP_SIZE)
				isNearHitbar = false;
			// exiting hitbar
			else {
				isNearHitbar = false;
				notifyObservers(parent);
			}
		}
	}

}
