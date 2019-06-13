package screens;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundPlayer {
	
	public static int LOOP_CONTINUOUSLY = Clip.LOOP_CONTINUOUSLY; 
	private AudioInputStream audioIn;
	private Clip currClip;
	private String currPath;
	
	public SoundPlayer() {
		audioIn = null;
		currClip = null;		
	}
	
	public SoundPlayer(String path) {
		audioIn = null;
		currClip = null;
		playClip(path);
	}
	
	// starts playing the song after a delay
	public SoundPlayer(String path, int delay) {
		stopClip();
		audioIn = null;
		currClip = null;
		
		
		playClip(path, 0);
		stopClip();
		currClip.setMicrosecondPosition(0);
		
		Timer t = new Timer();
		TimerTask delayPlay = new TimerTask() {
			@Override
			public void run() {
				playClip(path, 0);
			}
		};
		t.schedule(delayPlay, delay);		
	}
	
	// plays the clip without looping
	public void playClip(String path) {
		playClip(path, 0);
	}
	
	// plays the clip looping loops amount of times
	public void playClip(String path, int loops) {
		try {
			stopClip();
			audioIn = AudioSystem.getAudioInputStream(Main.class.getResource(path));
			currClip = AudioSystem.getClip();
			currClip.open(audioIn);
			currClip.start();
			currClip.loop(loops);
			currPath = path;
			setVolume(ApplicationManager.VOLUME);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			System.out.println("Unable to play song at: " + path);
			e.printStackTrace();
		}		
	}
	
	// stops the clip
	public int stopClip() {
		int ret = 0;
		if (hasClip()) {
			ret = getClipTime();
			currClip.close();
		}
		
		return ret;
	}
	
	// checks if there is a registered clip
	public boolean hasClip() {
		return currClip != null;
	}
	
	// gets the current time of the clip in microseconds
	public int getClipTime() {
		return (int)currClip.getMicrosecondPosition();	// casting to an int should still provide sufficient capacity (~35 minutes)
	}

	// gets the length of the clip in microseconds
	public int getClipLength() {
		return (int)currClip.getMicrosecondLength();
	}
	
	// gets the path of the current clip
	public String getClipPath() {
		return currPath;
	}
	
	// sets volume on a non-linear scale in terms of intensity
	public void setVolume(float desired) {
		desired = desired > 1.0f ? 1.0f : desired < 0f ? 0f : desired; // clamp volume from 0 to 1
		FloatControl volume = (FloatControl) currClip.getControl(FloatControl.Type.MASTER_GAIN);
		float range = volume.getMaximum() - volume.getMinimum();
		volume.setValue((range * desired) + volume.getMinimum());
		ApplicationManager.VOLUME = desired;
	}
	
	// returns true if the clip is playing
	public boolean isPlaying() {
		return currClip.isActive();
	}
}
