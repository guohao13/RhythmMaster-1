package screens;

import java.util.List;
import java.util.ArrayList;
import java.util.Observable;

// enum for easier and more readable screen management
enum Screen	
{
	MAIN_MENU,
	SONG_MENU,
	SETTINGS,
	GAME;
}

public abstract class ScreenController extends Observable {
	Screen screenType;				// type of screen
	Canvas screenCanvas;			// canvas to be drawn
	String screenMusicPath = "";	// path for background music
	String screenBackgroundPath;	// path for background image
	// list of soundplayers that may be needed for bgm, hit sounds, win music
	List<SoundPlayer> soundPlayers = new ArrayList<SoundPlayer>();
	
	public ScreenController() { ; }
	
	protected void setupDisplayAndMusic() {
		setupBGM(); 
		setupCanvas();
	}
	
	// sets up a soundPlayer for the background music
	private void setupBGM() {
		if(screenMusicPath != "") {
			SoundPlayer BGM = new SoundPlayer();
			BGM.playClip(screenMusicPath, SoundPlayer.LOOP_CONTINUOUSLY);
			soundPlayers.add(BGM);
		}
	}
	
	// set up the canvas for display use
	// each screen may have a completely different display so this is abstract
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
	
	// notifies the application manager to change the screen to desired screen
	public void requestScreenChangeTo(Screen desiredScreen) {
		super.setChanged();
		super.notifyObservers(desiredScreen);
	}
	
	// any action that needs to be executed before leaving the screen
	public void exitAction(Screen desiredScreen) {
		stopSounds();
		screenCanvas.removeButtonListeners();
	}
	
	// stops all sounds on all soundplayers
	private void stopSounds() {
		for (SoundPlayer s : soundPlayers) {
			if (s.hasClip())
				s.stopClip();
		}
	}
}
