package screens;

import java.awt.Dimension;

import javax.swing.JFrame;

public final class DisplayManager extends JFrame {

	private static final long serialVersionUID = 1L;
	private Canvas canvas;

	// creates the frame and draws the canvas
   public DisplayManager() {
      canvas = new Canvas();
      canvas.setPreferredSize(new Dimension(ApplicationManager.SCREEN_WIDTH, ApplicationManager.SCREEN_HEIGHT));
      setContentPane(canvas);
      setDefaultCloseOperation(EXIT_ON_CLOSE);  
      this.setSize(ApplicationManager.SCREEN_WIDTH, ApplicationManager.SCREEN_HEIGHT);
      setTitle("RHYTHM MASTER");
      setVisible(true);
   }
   
   // sets up the canvas to be the desiredCanvas
   public void setCanvas(Canvas desiredCanvas) {
	  if (canvas != null)
		   remove(canvas);
	   
	  canvas = desiredCanvas;
      canvas.setPreferredSize(new Dimension(ApplicationManager.SCREEN_WIDTH, ApplicationManager.SCREEN_HEIGHT));
	  setContentPane(canvas);
	  validate();
   }
}