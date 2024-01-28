package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound 
{
	Clip clip;
	URL soundURL[] = new URL[10];
	
	public Sound()
	{
		soundURL[0] = getClass().getResource("/Sound/spaceBackground.wav");
		soundURL[1] = getClass().getResource("/Sound/lazerSound.wav");
		soundURL[2] = getClass().getResource("/Sound/explosionSound.wav");
	}
	
	public void setFile(int i)
	{
		try
		{
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
		}
		catch(Exception e)
		{}
	}
	
	public void play()
	{
		clip.start();
	}
	
	public void loop()
	{
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop()
	{
		clip.stop();
	}
	
}