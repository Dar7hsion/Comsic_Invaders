package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import javax.swing.JPanel;

import Data.SaveLoad;
import Entity.Asteroid;
import Entity.Bullet;
import Entity.Enemy;
import Entity.Hero;
import Entity.Images;
import Inputs.KeyHandler;
import Inputs.MouseListener;
import ai.PathFinder;
import background.TileManager;


public class GamePanel extends JPanel implements Runnable//inheritance, gamepanel has all attributes of parent class jpanel, implements the Runnable interface
{
	// SCREEN SETTING
	final int originaTileSize = 16; // 16x16 tile size
	final int scale = 3; //16 Pixels is super small so we scale the image up 
	public final int tileSize = originaTileSize * scale; //48x48
	public final int maxScreenCol = 16; //number of columns 
	public final int maxScreenRow = 12; //number of Rows
	public final int screenWidth = tileSize * maxScreenCol; // 0-768 pixels from upper left corner
	public final int screenHeight = tileSize * maxScreenRow; //0-576 pixels from upper left corner
	
	// WORLD SETTINGS
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
	// GAME STATE
	public int gameState;
	public final int playState = 1;
	public final int pauseState = 2;
	
	//Keyhandler
	public boolean scan, path;
	
	//FPS
	int FPS = 60;
	
	//Images
	public static Images images = new Images();
	
	//Bullets
	public static Bullet bullet;
	private int reload, numToShoot, spread;
	public boolean fire, special;
	
	//Enemies
	public static Enemy enemy;
	public boolean tPath, spawn;
	
	//Asteroid
	public static Asteroid astro;
	public boolean spawnA;
	
	//Builds class objects
	public Spawner spawner = new Spawner(this);
	public Sound sound = new Sound();
	public SaveLoad saveLoad = new SaveLoad(this);
	public Thread gameThread;
	public MouseListener mouseL = new MouseListener(this);
	public TileManager tileM= new TileManager(this);
	public KeyHandler keyH = new KeyHandler(this); 
	public PathFinder pathF = new PathFinder(this);
	public UI ui = new UI(this);

	
	//set player's default position 
	public Hero hero= new Hero(worldWidth/2, worldHeight/2, 0, 50, 30, this);
	
	//CONSTRUCTOR //builds the object 
	public GamePanel()
	{
		this.setPreferredSize(new Dimension(screenWidth, screenHeight)); //set the size of the window, Dimension takes width and height
		this.setBackground(Color.black); //sets background color to black
		this.setDoubleBuffered(true); // Double-buffering is the process of drawing graphics into an off-screen image buffer and then copying the contents of the buffer to the screen all at once
		this.addKeyListener(keyH); //add the keylistener to game panel
		this.setFocusable(true); //allows the game panel to be focused
		
		saveLoad.load();
		bullet = new Bullet(0, 0, 0, 0, 0, this);
		reload = 20;//30
		numToShoot = 1;
		spread = 0;
		enemy = new Enemy(0, 0, 0, 0, 0, this);
		astro = new Asteroid(0, 0, 0, 0, 0, 0, 0, this);
		spawner.setAstros();
		playMusic(0);
		gameState = playState;
	}
	
	public void startGameThread()
	{
		gameThread = new Thread(this);//instantiates thread
		gameThread.start(); //starts thread
	}
	
	@Override
	public void run() //method is called with thread is started/ Game loop
	{
		double drawInterval = 1000000000/FPS;//0.0166666 seconds
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		int spawnCount = 60;
		
		while(gameThread != null)
		{
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;
			if(delta >= 1)
			{
				spawn = false;	
				spawnCount++;
				if(spawnCount > 120)
				{
					spawnCount = 0;
					spawn = true;
				}
				update();
				repaint();	
				delta--;
			}
		}
	}
	
	public void update() //updates information X:0,Y:0 is upper left corner
	{
		if(gameState == playState)
		{
			hero.update();
			// moving bullets
			
			// check if shooting
			if (fire)
			{
				playSE(1);
				hero.fire(reload, numToShoot, spread);//20, 1, 0
				
			}
			bullet.update();
			if (spawn)
			{
				spawner.setEnemyLocation();
			}
			
			astro.update();
			enemy.update();
			tileM.setBounds();
		}	
	}
	
	public void paint(Graphics g) //draws image 
	{
		super.paint(g);
		Graphics2D g2 = (Graphics2D)g; //add more graphic features 
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		AffineTransform old = g2.getTransform();
		
		//Draws background
		tileM.draw((Graphics2D)g2);
		g2.setTransform(old);
		
		//Draws Hero bound lines 
		g2.setColor(Color.orange);
		g2.drawLine(tileM.upperX, tileM.upperY, tileM.upperX, tileM.lowerY);
		g2.drawLine(tileM.lowerX, tileM.upperY, tileM.lowerX, tileM.lowerY);
		g2.drawLine(tileM.upperX, tileM.upperY, tileM.lowerX, tileM.upperY);
		g2.drawLine(tileM.upperX, tileM.lowerY, tileM.lowerX, tileM.lowerY);
		g2.setTransform(old);
		
		//Draws hero
		hero.draw((Graphics2D)g2);
		g2.setTransform(old);
		
		//Drawing the bullets
		bullet.draw((Graphics2D)g2);
		g2.setTransform(old);
	
		//Drawing the Enemies
		enemy.draw((Graphics2D)g2);
		g2.setTransform(old);
		
		//Draws asteroids
		astro.draw((Graphics2D)g2);
		
		//Draws UI
		ui.draw((Graphics2D)g2);
		g2.setTransform(old);
		g2.dispose();
	}
	
	public void playMusic(int i)
	{
		sound.setFile(i);
		sound.play();
		sound.loop();
	}
	
	public void stopMusic()
	{
		sound.stop();
	}
	
	public void playSE(int i)
	{
		sound.setFile(i);
		sound.play();
	}
}
