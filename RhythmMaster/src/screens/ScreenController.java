package screens;

import java.util.Observable;

enum Screen
{
	MAIN_MENU,
	SONG_MENU,
	SETTINGS,
	GAME;
}

public abstract class ScreenController extends Observable {
	Screen screenType;
	Canvas screenCanvas;
	String screenMusicPath;
	String screenBackgroundPath;
	
	public ScreenController() { ; }
	
	public abstract Canvas setupCanvas();
	
	public Screen getScreenType() {
		return screenType;
	}
	
	public Canvas getScreenCanvas() {
		return screenCanvas;
	}

	public String getScreenMusicPath() {
		return screenMusicPath;
	}
	
	public void requestScreenChangeTo(Screen desiredScreen) {
		super.setChanged();
		super.notifyObservers(desiredScreen);
	}
	
	public void exitAction(Screen desiredScreen) {
		screenCanvas.removeButtonListeners();
	}
}
