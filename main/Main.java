package main;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.swing.JFrame;

import Inputs.MouseListener;

public class Main {
	
	static JFrame window = new JFrame();
	public static void main(String[] args)
	{
		//JFrame window = new JFrame(); //instantiate a JFame window object named window
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //sets the window object to close when closed with the ("X") button
		window.setResizable(false); //window size can't be changed
		window.setTitle("Cosmic Invaders"); //tile that shows up at top of window, NOTE:NEW TITLE NEEDED
		
		GamePanel gamePanel = new GamePanel(); //instantiate a gamePanel object
		window.getContentPane().addMouseListener(new MouseListener(gamePanel));
		
		window.add(gamePanel); // places the gamePanel into the window 
		
		
		window.pack(); //sets the size of the window to the set size of the subcomponent in this case gamepanel 
		
		window.setLocationRelativeTo(null); //where the window will be located, null means center the window 
		window.setVisible(true); //lets the window be seen, can' be seen if not set to true
		gamePanel.startGameThread();
	}
	
	public static void close()
	{
		window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
	}
	
	public static void restartApplication() throws URISyntaxException
	{
		final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
		final File currentJar = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());

		/* is it a jar file? */
		if (!currentJar.getName().endsWith(".jar"))
		    return;

		/* Build command: java -jar application.jar */
		final ArrayList<String> command = new ArrayList<String>();
		command.add(javaBin);
		command.add("-jar");
		command.add(currentJar.getPath());

		final ProcessBuilder builder = new ProcessBuilder(command);
		try {
			builder.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
	}

}
