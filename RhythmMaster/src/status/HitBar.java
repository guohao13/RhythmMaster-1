package status;

import java.awt.Color;

public class HitBar {

	private float tolerance = 0.25f;
	
	HitBar() {
		
	}
	
	HitBar(float tol) {
		this.tolerance = tol;
	}
	
	
	public float getTolerance() {
		return this.tolerance;
	}
	
	public void setTolerance(float f) {
		if(0 < f && f < 1) {
			this.tolerance = f;
		}
	}
	
	public void setColor(Color c) {
		// GUI hook to set hit bar color
	}
}