package screens;

import java.awt.Dimension;

import javax.swing.JFrame;

public class DisplayManager extends JFrame {

	private static final long serialVersionUID = 1L;
	private Canvas canvas;

   public DisplayManager() {
      canvas = new Canvas();
      canvas.setPreferredSize(new Dimension(ApplicationManager.SCREEN_WIDTH, ApplicationManager.SCREEN_HEIGHT));
      setContentPane(canvas);
      setDefaultCloseOperation(EXIT_ON_CLOSE);  
      this.setSize(ApplicationManager.SCREEN_WIDTH, ApplicationManager.SCREEN_HEIGHT);
      setTitle("GAME NAME");
      setVisible(true);
   }
   
   public void setCanvas(Canvas desiredCanvas) {
	  if (canvas != null)
		   remove(canvas);
	   
	  canvas = desiredCanvas;
      canvas.setPreferredSize(new Dimension(ApplicationManager.SCREEN_WIDTH, ApplicationManager.SCREEN_HEIGHT));
	  setContentPane(canvas);
	  validate();
   }
}