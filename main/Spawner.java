package main;

import java.util.ArrayList;
import java.util.Random;

import Entity.Asteroid;
import Entity.Enemy;

public class Spawner 
{
	GamePanel gp;
	Random rand = new Random();
	private ArrayList enemies;
	private ArrayList astros;
	
	public Spawner(GamePanel gp) 
	{
		this.gp = gp;
		enemies = new ArrayList();
		astros = new ArrayList();
	}
	
	public void setAstros()
	{
		int astroCount = 60;
		for(int i = 0; i < astroCount; i++)
		{
			if(i < astroCount*(1/4))
			{
				spawnA(0 + gp.screenWidth/4 + gp.tileSize, 0 + gp.screenHeight/4 + gp.tileSize);
			}
			if(i < astroCount*(2/4))
			{
				spawnA(gp.worldWidth - gp.screenWidth/4 - gp.tileSize, 0 + gp.screenHeight/4 + gp.tileSize);
			}
			if(i < astroCount*(3/4))
			{
				spawnA(0 + gp.screenWidth/4 + gp.tileSize, gp.worldHeight - gp.screenHeight/4 - gp.tileSize);
			}
			if(i < astroCount)
			{
				spawnA(gp.worldWidth - gp.screenWidth/4 - gp.tileSize, gp.worldHeight - gp.screenHeight/4 - gp.tileSize);
			}
		}
	}
	
	public void setEnemyLocation()
	{
		int tileNum = (rand.nextInt(49)+1)*gp.tileSize;
		int sideNum = rand.nextInt(3) + 1;
		switch (sideNum) 
		{
		
        case 1:
            spawn(gp.tileSize, tileNum);
            break;
            
        case 2:
        	spawn(gp.tileSize*(gp.maxWorldCol-1), tileNum);
            break;
            
        case 3:
        	spawn(tileNum, gp.tileSize);
            break;
            
        case 4:
        	spawn(tileNum, gp.tileSize*(gp.maxWorldRow-1));
            break;
            
		}
	}
	
	public ArrayList getEnemys()
	{
		return enemies;
	}
	
	public ArrayList getAstros()
	{
		return astros;
	}
	
	public void remove(int i)
	{
		enemies.remove(i);
	}
	
	public void spawn(int x, int y)
	{
		for (int i = 0; i < 1; i++) 
		{
			gp.enemy.setX(x);
			gp.enemy.setY(y);
			gp.enemy.setA(0);
			gp.enemy.setW(48);
			gp.enemy.setH(48);
			enemies.add(new Enemy(gp.enemy.getX(), gp.enemy.getY(), gp.enemy.getA(), gp.enemy.getW(), gp.enemy.getH(),gp));
		}
		gp.spawn = false;
	}
	
	public void spawnE()
	{
		for (int i = 0; i < 1; i++) 
		{
			gp.enemy.setX((gp.worldWidth/2));
			gp.enemy.setY((gp.worldHeight/2));
			gp.enemy.setA(0);
			gp.enemy.setW(48);
			gp.enemy.setH(48);
			enemies.add(new Enemy(gp.enemy.getX(), gp.enemy.getY(), gp.enemy.getA(), gp.enemy.getW(), gp.enemy.getH(), gp));
		}
		gp.spawn = false;
	}
	
	public void spawnA(double x, double y)
	{
		double aX = x;
		double aY = y;
		for (int i = 0; i < 1; i++) 
		{
			//System.out.println("b");
			gp.astro.setX(aX);
			gp.astro.setY(aY);
			int pick = rand.nextInt(3);
			double angle = (rand.nextDouble())*360;
			gp.astro.setA(angle);
			switch(pick)
			{
			case 0:
				gp.astro.setW(48);
				gp.astro.setH(48);
				gp.astro.speed = 1.6;
			break;
			
			case 1:
				gp.astro.setW(96);
				gp.astro.setH(96);
				gp.astro.speed = 0.8;
			break;
			
			case 2:
				gp.astro.setW(192);
				gp.astro.setH(192);
				gp.astro.speed = 0.4;
			break;
			}
			gp.astro.setR();
			astros.add(new Asteroid(gp.astro.getX(), gp.astro.getY(), gp.astro.getA(),gp.astro.getS(),gp.astro.getR(), gp.astro.getW(), gp.astro.getH(), gp));
		}
		gp.spawnA = false;
	}
}
