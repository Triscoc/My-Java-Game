package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
	Clip clip;
	URL soundURL[] = new URL[30];
	FloatControl fc;
	int volumeScale = 3;
	float volume;
	
	public Sound() {
		soundURL[0] = getClass().getResource("/sound/A_Journey_Awaits.wav");
		soundURL[1] = getClass().getResource("/sound/hitmonster.wav");
		soundURL[2] = getClass().getResource("/sound/powerup.wav");
		soundURL[3] = getClass().getResource("/sound/receivedamage.wav");
		soundURL[4] = getClass().getResource("/sound/cursor.wav");
		soundURL[5] = getClass().getResource("/sound/gameover.wav");
	}
	
	public void setFile(int i) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
			fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
			checkVolume();
		} catch(Exception e) {
			
		}
	}
	
	public void play() {
		clip.start();
	}
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop() {
		clip.stop();
	}
	
	public void checkVolume() {
	    if (fc != null) {  // Only attempt to set volume if FloatControl is available
	        switch (volumeScale) {
	            case 0: volume = -80f; break;
	            case 1: volume = -20f; break;
	            case 2: volume = -12f; break;
	            case 3: volume = -5f; break;
	            case 4: volume = 1f; break;
	            case 5: volume = 6f; break;
	        }
	        fc.setValue(volume);  // Set the volume if fc is available
	    } else {
	        System.out.println("Volume control not available for this clip.");
	        // You could implement a fallback here if you want to control volume through other means
	    }
	}

	
}
