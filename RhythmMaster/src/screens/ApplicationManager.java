package screens;

import java.util.Observable;
import java.util.Observer;

public final class ApplicationManager implements Observer {

	// statics for use across multiple classes
	public static final String[] SONG_OPTIONS = { 	
			"../Sounds/Butterfly.wav",
			"../Sounds/WiiMenu.wav" };
	
	public static final int SCREEN_HEIGHT = 720;
	public static final int SCREEN_WIDTH = 1280;
	public static float VOLUME = 1f;
	public static float TOLERANCE = 1f;
	public static int SELECTION = 0; 
	
	public ScreenController currentScreen;
	public DisplayManager displayManager;
	
	public boolean screenIsFirst = false;
	
	// starts the user at the main menu screen
	public ApplicationManager() {
		displayManager = new DisplayManager();
		toScreen(Screen.MAIN_MENU);
	}
	
	@Override
	public void update(Observable o, Object arg) { // called when screen wants to change
		toScreen((Screen)arg);		
	}
	
	// changes the current screen to the desired screen
	public void toScreen(Screen screen) {
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
	}
}