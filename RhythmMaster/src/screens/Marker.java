package screens;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Observable;

public class Marker extends DrawableRectangle {
	private static final int SIDE_LENGTH = 40;
    private static final int HITBAR_TOP_Y = 560;
    private static final int HITBAR_BOTTOM_Y = 610;
	private static final int OBSERVER_WINDOW_SIZE = 50;
	private static final int Y_COORD_STEP_SIZE = 2;
	
    private int railIndex;
	public ObservableYCoord y_coord;
	public boolean isInHitbar = false;

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
		super.draw(g);
	}
	
	public int getRailIndex() {
		return railIndex;
	}
	
	public void setLocation(int x, int y) {
		super.setLocation(x, y);
		y_coord.setValue(y);
	}
	
	public void addObserver(HitDetectionObserver obs) {
		y_coord.addObserver(obs);
	}
	
	public class ObservableYCoord extends Observable {
		
		Marker parent;
		private int y_coord;
		private final int TOLERANCE_IN_PIXELS = (int) (ApplicationManager.TOLERANCE * (float)50);
		
		ObservableYCoord(Marker p) {
			this.parent = p;
		}

		private void setValue(int val) {
			y_coord = val;
			
			// way before hitbar
			if(y_coord + SIDE_LENGTH < HITBAR_TOP_Y - TOLERANCE_IN_PIXELS)
				isInHitbar = false;
			
			// entering hitbar
			else if(y_coord + SIDE_LENGTH < HITBAR_BOTTOM_Y + TOLERANCE_IN_PIXELS) {
				if(!isInHitbar) {
					isInHitbar = true;
					notifyObservers(parent);
				}
			}
			// exiting hitbar
			else if(y_coord > HITBAR_BOTTOM_Y + TOLERANCE_IN_PIXELS) {
				if(isInHitbar) {
					isInHitbar = false;
					notifyObservers(parent);
				}
			}
		}
		
		@Override
	    public void notifyObservers(Object obj) {
	        setChanged();
	        super.notifyObservers(obj);
	    }
	}
}
