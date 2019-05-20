package status;

import java.awt.Color;

public class HitBar {

	private float tolerance = 0.25f;
	private Color currColor;
	
	HitBar(float f, Color c) {
		this.tolerance = f;
		this.currColor = c;
	}
	
	
	public float getTolerance() {
		return this.tolerance;
	}
	
	public void setTolerance(float f) {
		if(0 < f && f < 1) {
			this.tolerance = f;
		}
	}
	
	public Color getCurrColor() {
		return this.currColor;
	}
	
	public void setCurrColor(Color c) {
		this.currColor = c;
	}
}
