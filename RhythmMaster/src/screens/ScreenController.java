package screens;

import java.util.List;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

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
	String screenMusicPath = "";
	String screenBackgroundPath;
	List<SoundPlayer> soundPlayers = new ArrayList<SoundPlayer>();
	
	public ScreenController() { ; }
	
	protected void setupDisplayAndMusic() {
		setupBGM(); 
		setupCanvas();
	}
	
	private void setupBGM() {
		if(screenMusicPath != "") {
			SoundPlayer BGM = new SoundPlayer();
			BGM.playClip(screenMusicPath, SoundPlayer.LOOP_CONTINUOUSLY);
			soundPlayers.add(BGM);
		}
	}
	
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
		stopSounds();
		screenCanvas.removeButtonListeners();
	}
	
	private void stopSounds() {
		for (SoundPlayer s : soundPlayers) {
			if (s.hasClip())
				s.stopClip();
		}
	}
}
