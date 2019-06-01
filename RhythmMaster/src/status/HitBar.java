package status;

import java.awt.Color;

public class HitBar {

	private float tolerance = 0.25f;
	
	HitBar() {
		
	}
	
	HitBar(float tol) {
		tolerance = tol;
	}
	
	
	public float getTolerance() {
		return tolerance;
	}
	
	public void setTolerance(float f) {
		if(0 < f && f < 1) {
			tolerance = f;
		}
	}
	
	public void setColor(Color c) {
		// GUI hook to set hit bar color
	}
}