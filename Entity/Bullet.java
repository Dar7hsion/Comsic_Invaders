package Entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import main.GamePanel;

public class Bullet extends SolidArea
{

	GamePanel gp;
	// constructor
	public Bullet(double screenX, double screenY, double a, int w, int h, GamePanel gp) {

		this.screenX = screenX;
		this.screenY = screenY;
		this.x = screenX + (gp.hero.x - (gp.screenWidth/2));
		this.y = screenY + (gp.hero.y - (gp.screenHeight/2));
		this.a = a;
		this.w = w;
		this.h = h;
		this.gp = gp;
		setR();
	}

	// move toward the angle
	// //forward
	public void moveForward(int speed) {
		x += Math.cos(a)*speed;
		y += Math.sin(a)*speed;
		screenX = gp.screenWidth/2 + (x -gp.hero.x);
		screenY = gp.screenHeight/2 + (y -gp.hero.y);
	}
	
	public void setSolidArea()
	{
		
		this.setCenterX();
		this.setCenterY();
		this.setScreenCenterX();
		this.setScreenCenterY();
	}
	
	public void setScreenCenterX()
	{
		this.screenCenterX = this.screenX + this.r;
	}
	
	public void setScreenCenterY()
	{
		this.screenCenterY = this.screenY + this.r;
	}
	
	public void setScreenX(double screenX)
	{
		this.screenX = screenX;
	}
	
	public void setScreenY(double screenY)
	{
		this.screenY = screenY;
	}
	
	public double getScreenX()
	{
		return this.screenX;
	}
	
	public double getScreenY()
	{
		return this.screenY;
	}
	
	public void update()
	{
		ArrayList tmpBs = gp.hero.getBullets();
		for (int i = 0; i < tmpBs.size(); i++) 
		{
			Bullet tmpB = (Bullet) tmpBs.get(i);
			tmpB.moveForward(6);//3
			tmpB.setSolidArea();
			boolean removed = false;
			ArrayList tmpEs = gp.spawner.getEnemys();
			
			for (int j = 0; j < tmpEs.size(); j++) 
			{
				Enemy tmpE = (Enemy) tmpEs.get(j);
				if(collision(tmpB, tmpE))
				{
					if(removed == false)
					{	
						tmpBs.remove(i);
						gp.ui.kills++;
						removed = true;
					}
					tmpE.alive = false;
				}	
			}
			if (tmpB.getScreenX() > gp.screenWidth ||  tmpB.getScreenX() < 0 || tmpB.getScreenY() > gp.screenHeight || tmpB.getScreenY() < 0) 
			{
				if(tmpBs.get(i) != null )
				{
					tmpBs.remove(i);
				}
			}
		}
		
		ArrayList tmpEs = gp.spawner.getEnemys();
		for (int i = 0; i < tmpEs.size(); i++) 
		{
			Enemy tmpE = (Enemy) tmpEs.get(i);
			ArrayList tmpEBs = tmpE.getBullets();
			for (int j = 0; j < tmpEBs.size(); j++) 
			{
				Bullet tmpEB = (Bullet) tmpEBs.get(j);
				tmpEB.moveForward(6);//3
				tmpEB.setSolidArea();
				if(collision(gp.hero, tmpEB))
				{
					tmpEBs.remove(j);
					gp.hero.alive = false;
				}
				else
				{
					if (tmpEB.getScreenX() > gp.screenWidth ||  tmpEB.getScreenX() < 0 ||  tmpEB.getScreenY() > gp.screenHeight || tmpEB.getScreenY() < 0) 
					{
						tmpEBs.remove(j);
					}
				}
			}
		}
	}

	public void draw(Graphics2D g2)
	{
		ArrayList bullets = gp.hero.getBullets();
		for (int i = 0; i < bullets.size(); i++) 
		{
			Bullet tmpB = (Bullet) bullets.get(i);
		    //playing with bullet colors
			if (i % 2 == 0) 
			{
				g2.setColor(Color.GREEN);
			} 
			else
			{
				g2.setColor(Color.white);
			}
			g2.fillRect((int) tmpB.getScreenX(), (int) tmpB.getScreenY(), tmpB.getW(), tmpB.getH());
		}
		
		ArrayList tmpEs = gp.spawner.getEnemys();
		for (int i = 0; i < tmpEs.size(); i++) 
		{
			Enemy tmpE = (Enemy) tmpEs.get(i);
			ArrayList tmpEBs = tmpE.getBullets();
			for (int j = 0; j < tmpEBs.size(); j++) 
			{

				Bullet tmpEB = (Bullet) tmpEBs.get(j);

				if (i % 2 == 0) 
				{
					g2.setColor(Color.red);
				} 
				else
				{
					g2.setColor(Color.white);
				}
				g2.fillRect((int) tmpEB.getScreenX(), (int) tmpEB.getScreenY(), tmpEB.getW(), tmpEB.getH());
			}
		}
	}
}
