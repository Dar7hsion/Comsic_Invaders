package Entity;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import ai.Node;
import main.GamePanel;

public class Enemy extends SolidArea
{

	private int screenX, screenY, speed, tmpLoad; // x,y and angle
	private GamePanel gp;
	public String direction;
	public int solidX = 4;										
	public int solidY = 4;
	public int width = 40;	
	private ArrayList bullets; // this will hold our bullets 
	
	public ArrayList<Node> drawPath = new ArrayList<>();

	public Enemy(double x, double y, double a, int w, int h, GamePanel gp) 
	{
		this.alive = true;
		this.x = x;
		this.y = y;
		this.a = a;
		this.w = w;
		this.h = h;
		this.gp = gp;
		bullets = new ArrayList();
		tmpLoad = 0;
		speed = 1;
		current = GamePanel.images.E;
		direction = "down";
		setR();
	}
	
	public int getScreenX() {
		return screenX;
	}

	public int getScreenY() {
		return screenY;
	}
	
	public double getScreenCenterX() {
		return this.screenCenterX;
	}
	
	
	public double getScreenCenterY() {
		return this.screenCenterY;
	}
	
	public ArrayList getBullets() {
		return bullets;
	}
	
	public void setScreenX(double x) {
		this.screenX = (int)(this.x - (gp.hero.x - (gp.screenWidth/2)));
	}
	
	
	public void setScreenY(double sy) {
		this.screenY = (int)(this.y - (gp.hero.y - (gp.screenHeight/2)));
	}
	
	public void setScreenCenterX()
	{
		this.screenCenterX = this.screenX + this.r;
	}
	
	public void setScreenCenterY()
	{
		this.screenCenterY = this.screenY + this.r;
	}
	
	public void setSolidArea()
	{
		this.setCenterX();
		this.setCenterY();	
	}

	public void update() 
	{
		ArrayList tmpEs = gp.spawner.getEnemys();
		for (int i = 0; i < tmpEs.size(); i++) 
		{
			Enemy tmpE = (Enemy) tmpEs.get(i);
			if(tmpE.alive)
			{
				//////////////////////////
				tmpE.a += .01;
				if(tmpE.getA()> 360.0)
				{
					tmpE.setA(0.0);
				}
				//////////////////////////
				if (tmpE.getX() >= gp.worldWidth) 
				{
					tmpE.setX(1);
				} 
				else if (tmpE.getX() <= 0) 
				{
					tmpE.setX(gp.worldWidth-1);
				}

				if (tmpE.getY() >= gp.worldHeight) 
				{
					tmpE.setY(1);
				} 
				else if (tmpE.getY() <= 0) 
				{
					tmpE.setY(gp.worldHeight-1);
				}
				//////////////////////////
				int goalCol = ((int)((gp.hero.getX()-35)/gp.tileSize));
				int goalRow = ((int)((gp.hero.getY()-gp.tileSize)/gp.tileSize));
				int startCol = (int)(tmpE.getX()/gp.tileSize);
				int startRow = (int)(tmpE.getY()/gp.tileSize);
				
				gp.pathF.setNodes(startCol, startRow, goalCol, goalRow);
	
				if(gp.pathF.search() == true)
				{
					tmpE.drawPath.addAll(gp.pathF.pathList);
					
					//NEXT WORLDX AND WORLDY
					int nextX = gp.pathF.pathList.get(0).col * gp.tileSize + gp.tileSize;
					int nextY = gp.pathF.pathList.get(0).row * gp.tileSize + gp.tileSize;
					int enLeftX = (int)tmpE.getX() + solidX;
					int enRightX = (int)tmpE.getX() + solidX + width;
					int enTopY = (int)tmpE.getY() + solidY;
					int enBottomY = (int)tmpE.getY() + solidY + width;
					
					if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) 
					{
						tmpE.direction = "up";
					}
					else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize)
					{
						tmpE.direction = "down";
					}
					else if(enTopY >= nextY &&  enBottomY < nextY + gp.tileSize)
					{
						//LEFT OR RIGHT
						if(enLeftX > nextX)
						{
							tmpE.direction = "left";
						}
						if(enLeftX < nextX)
						{
							tmpE.direction = "right";
						}
					}
				}
				//////////////////////////
				if(collision(gp.hero, tmpE))
				{
					gp.hero.alive = false;
				}
				//////////////////////////
				
				switch(tmpE.direction)
				{
				case "up": 
					tmpE.y -= speed; 
					break;
				case "down": 
					tmpE.y += speed;
					break;
				case "left": 
					tmpE.x -= speed;
					break;
				case "right": 
					tmpE.x += speed;
					break;
				case "hold": 
					break;	
				}
				//////////////////////////
				tmpE.setSolidArea();
				tmpE.setScreenX((int)tmpE.getX());
				tmpE.setScreenY((int)tmpE.getY());
				tmpE.setScreenCenterX();
				tmpE.setScreenCenterY();
				tmpE.fire(40, 1, 0);//80??
			}
			else
			{
				int m = 5;
				tmpE.deathCount++;
				if(tmpE.deathCount < m)
				{
					tmpE.current = GamePanel.images.E0;
				}
				if(tmpE.deathCount < m*2 && tmpE.deathCount >= m)
				{
					tmpE.current = GamePanel.images.E1;
				}
				if(tmpE.deathCount < m*3 && tmpE.deathCount >= m*2)
				{
					tmpE.current = GamePanel.images.E2;
				}
				if(tmpE.deathCount < m*4 && tmpE.deathCount >= m*3)
				{
					tmpE.current = GamePanel.images.E3;
				}
				if(tmpE.deathCount < m*5 && tmpE.deathCount >= m*4)
				{
					tmpE.current = GamePanel.images.E4;
				}
				if(tmpE.deathCount < m*6 && tmpE.deathCount >= m*5)
				{
					tmpE.current = GamePanel.images.E5;	
				}
				if(tmpE.deathCount > m*6)
				{
					tmpE.current = GamePanel.images.D;
					gp.spawner.remove(i);
					tmpE.deathCount = 0;
					tmpE.current = GamePanel.images.E;
					
				}
				tmpE.setScreenX((int)tmpE.getX());
				tmpE.setScreenY((int)tmpE.getY());
			}	
		}
	}
	
	public void fire(int load, int number, int spread) 
	{
		// if reloading time is done
		if (tmpLoad == 0) 
		{ 
			for (int i = 0; i < number; i++) 
			{
				// setting the bullet
				GamePanel.bullet.setX(screenX + Math.cos(this.a)*24);
				GamePanel.bullet.setY(screenY + Math.sin(this.a)*24);
				GamePanel.bullet.setA(a + ((spread * (i - 1)) / 2));
				GamePanel.bullet.setW(5);
				GamePanel.bullet.setH(5);
				// adding the bullet to the array list
				bullets.add(new Bullet(GamePanel.bullet.getX(),GamePanel.bullet.getY(), GamePanel.bullet.getA(), GamePanel.bullet.getW(), GamePanel.bullet.getH(),gp));
			}
			//reset the reload time 
			tmpLoad = load;
		} 
		else 
		{
			tmpLoad -= 1;
		}
	}
	
	public void draw(Graphics2D g2)
	{
		AffineTransform old = g2.getTransform();
		ArrayList enemy = gp.spawner.getEnemys();
		for (int i = 0; i < enemy.size(); i++) 
		{
			Enemy tmpE = (Enemy) enemy.get(i);
			g2.rotate(tmpE.getA(), (int)tmpE.getScreenX() + tmpE.solidX + (width/2), (int)tmpE.getScreenY() + tmpE.solidY + (width/2));
			
			g2.drawImage(tmpE.getI(), (int)tmpE.getScreenX(), (int) tmpE.getScreenY(), tmpE.getW(), tmpE.getH(), gp);
			
			g2.setTransform(old);
		}
	}
}
