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
		if(soundPlayers.size() > 0)
			soundPlayers.remove(0);
		if(screenMusicPath != "") {
			SoundPlayer BGM = new SoundPlayer();
			if(ApplicationManager.SELECTION == 0) {
				BGM.playClip(screenMusicPath, SoundPlayer.LOOP_CONTINUOUSLY);
			}
			else {
				TimerTask delayed = new TimerTask() {

					@Override
					public void run() {
						BGM.playClip(screenMusicPath, SoundPlayer.LOOP_CONTINUOUSLY);
					}
					
				};
				Timer t = new Timer();
				t.schedule(delayed, 2500);
			}
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
		System.out.println("exitaction");
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
