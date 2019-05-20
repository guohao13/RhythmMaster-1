package screens;

import java.util.Observable;
import java.util.Observer;

public class ApplicationManager implements Observer {

	public static final int SCREEN_HEIGHT = 720;
	public static final int SCREEN_WIDTH = 1280;
	
	public ScreenController currentScreen;
	public DisplayManager displayManager;
	public MusicManager musicManager;
	
	public boolean screenIsFirst = false;
	
	public ApplicationManager() {
		displayManager = new DisplayManager();
		musicManager = new MusicManager();
		toScreen(Screen.MAIN_MENU);
	}
	
	@Override
	public void update(Observable o, Object arg) { // called when screen wants to change
		toScreen((Screen)arg);		
	}
	
	public void toScreen(Screen screen) {
		if(musicManager.hasClip())
			musicManager.stopClip();
		if(currentScreen != null) {
			currentScreen.exitAction(screen);
			currentScreen.deleteObservers();
		}
		
        switch (screen) 
        { 
        case MAIN_MENU: 
            System.out.println("Loading MAIN_MENU"); 
            currentScreen = new MainMenuScreenController();
            break; 
        case SONG_MENU: 
            System.out.println("Loading SONG_MENU"); 
            currentScreen = new SongMenuScreenController();
            break; 
        case SETTINGS:
        	System.out.println("Loading SETTINGS screen");
        	currentScreen = new SettingsScreenController();
        	break;
        case GAME: 
            System.out.println("Loading GAME screen"); 
            currentScreen = new GameScreenController();
            break; 
        } 
        
        currentScreen.addObserver(this);
        displayManager.setCanvas(currentScreen.getScreenCanvas());
        musicManager.playClip(currentScreen.getScreenMusicPath(), MusicManager.LOOP_CONTINUOUSLY);
	}
}