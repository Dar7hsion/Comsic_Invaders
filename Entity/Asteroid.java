package Entity;



import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Random;
import main.GamePanel;

public class Asteroid extends SolidArea
{
	GamePanel gp;
	public double speed, screenX, screenY;
	Random rand = new Random();
	
	
	public Asteroid(double x, double y, double a, double s, int r, int w, int h, GamePanel gp)
	{
		this.x = x;
		this.y = y;
		this.a = a;
		this.r = r;
		this.speed = s;
		this.w = w;
		this.h = h;
		this.gp = gp;
		current = GamePanel.images.A;
	}

	
	public void setScreenX(double x) {
		this.screenX = (int)(this.x - (gp.hero.x - (gp.screenWidth/2)));
	}
	
	
	public void setScreenY(double y) {
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
	
	public double getS()
	{
		return this.speed;
	}
	
	public double getScreenX() {
		return this.screenX;
	}

	public double getScreenY() {
		return this.screenY;
	}
	
	
	public void moveForward(double speed) 
	{
		this.x += Math.cos(this.a)*this.speed;
		this.y += Math.sin(this.a)*this.speed;
	}

	public void update() 
	{
		//clears the solid area of the map
		int col = 0;
		int row = 0;
		while(col < gp.maxWorldCol && row < gp.maxWorldRow)
		{
			gp.pathF.node[col][row].solid = false;
			col++;
			if(col == gp.maxWorldCol)
			{
				col = 0;
				row++;
			}
		}
		
		int b = 4;
		ArrayList tmpAs = gp.spawner.getAstros();
		for (int i = 0; i < tmpAs.size(); i++) 
		{
			Asteroid tmpA = (Asteroid) tmpAs.get(i);
			if (tmpA.getX() > gp.worldWidth - gp.screenWidth/b) 
			{
				tmpA.setX(0 + gp.screenWidth/b);
			} 
			else if (tmpA.getX() < 0 + gp.screenWidth/b) 
			{
				tmpA.setX(gp.worldWidth - gp.screenWidth/b);
			}
			if (tmpA.getY() > gp.worldHeight - gp.screenHeight/b) 
			{
				tmpA.setY(0 + gp.screenHeight/b);
			} 
			else if (tmpA.getY() < 0 + gp.screenHeight/b) 
			{
				tmpA.setY(gp.worldHeight - gp.screenHeight/b);
			}
			tmpA.moveForward(tmpA.speed);
			
			setSoildArea(tmpA);
			
			tmpA.setScreenX(tmpA.getX());
			tmpA.setScreenY(tmpA.getY());
			tmpA.setCenterX();
			tmpA.setCenterY();
			tmpA.setScreenCenterX();
			tmpA.setScreenCenterY();
			
			/////hero vs astro
			if(collision(gp.hero, tmpA))
			{
				gp.hero.alive = false;;
			}
			
			/////astro vs enemy
			ArrayList tmpEs = gp.spawner.getEnemys();
			for (int j = 0; j < tmpEs.size(); j++) 
			{
				Enemy tmpE = (Enemy) tmpEs.get(j);
				if(collision(tmpA, tmpE))
				{
					tmpE.alive = false;
				}	
			}
			
			/////astro vs hero_bullet
			ArrayList tmpBs = gp.hero.getBullets();
			for (int j = 0; j < tmpBs.size(); j++) 
			{
				Bullet tmpB = (Bullet) tmpBs.get(j);
				if(collision(tmpA, tmpB))
				{
					tmpBs.remove(j);
				}	
			}
			
			/////astro vs enemy_bullet
			for (int j = 0; j < tmpEs.size(); j++) 
			{
				Enemy tmpE = (Enemy) tmpEs.get(j);
				ArrayList tmpEBs = tmpE.getBullets();
				for (int k = 0; k < tmpEBs.size(); k++) 
				{
					Bullet tmpEB = (Bullet) tmpEBs.get(k);
					if(collision(tmpA, tmpEB))
					{
						tmpEBs.remove(k);
					}
				}
			}
		}
	}
	
	public void setSoildArea(Asteroid tmpA)
	{
		int xIndex = (int)(tmpA.x/gp.tileSize);
		int yIndex = (int)(tmpA.y/gp.tileSize);
		int wIndex = (int)(tmpA.w/gp.tileSize);
		int leftXIndex = xIndex - 1; 
		int rightXIndex = 0;
		int topYIndex = yIndex - 1; 
		int bottomYIndex = 0;
		
		switch(wIndex)
		{
		case 1:
			rightXIndex = xIndex + 1;
			bottomYIndex = yIndex + 1;
		break;
		
		case 2:
			rightXIndex = xIndex + 3;
			bottomYIndex = yIndex + 3;
		break;
		
		case 4:
			rightXIndex = xIndex + 5;
			bottomYIndex = yIndex + 5;
		break;
		}
		
		if(rightXIndex > 49)
		{
			rightXIndex = 49;
		}
		if(bottomYIndex > 49)
		{
			bottomYIndex = 49;
		}
		
		while(leftXIndex <= rightXIndex && topYIndex <= bottomYIndex)
		{
//			System.out.println("X: " + leftXIndex);
//			System.out.println("Y: " + topYIndex);
			
			gp.pathF.node[leftXIndex][topYIndex].screenX = gp.pathF.node[leftXIndex][topYIndex].x - (gp.hero.x - (gp.screenWidth/2));
			gp.pathF.node[leftXIndex][topYIndex].screenY = gp.pathF.node[leftXIndex][topYIndex].y - (gp.hero.y - (gp.screenHeight/2));
					
			gp.pathF.node[leftXIndex][topYIndex].screenCenterX = (int)(gp.pathF.node[leftXIndex][topYIndex].screenX) + (gp.tileSize/2);
			gp.pathF.node[leftXIndex][topYIndex].screenCenterY = (int)(gp.pathF.node[leftXIndex][topYIndex].screenY)+ (gp.tileSize/2);
			
			if(collision(tmpA, gp.pathF.node[leftXIndex][topYIndex]))
			{
				gp.pathF.node[leftXIndex][topYIndex].solid = true;
			}
			leftXIndex++;
			if(leftXIndex > rightXIndex)
			{
				leftXIndex = xIndex - 1;
				topYIndex++;
			}
		}
		
	}
	
	public void draw(Graphics2D g2)
	{
		// draw the image
		AffineTransform old = g2.getTransform();
		ArrayList tmpAs = gp.spawner.getAstros();
		for (int i = 0; i < tmpAs.size(); i++) 
		{
			Asteroid tmpA = (Asteroid) tmpAs.get(i);
			g2.setColor(Color.green);
			if(gp.scan)
			{
				g2.drawOval((int)tmpA.screenX, (int)tmpA.screenY, tmpA.getH(), tmpA.getH());
			}
			
			g2.drawImage(tmpA.getI(), (int)tmpA.getScreenX(), (int) tmpA.getScreenY(), tmpA.getW(), tmpA.getH(), gp);
			
			g2.setTransform(old);
			
			if(gp.scan)
			{
				g2.drawLine((int)tmpA.getScreenCenterX(), (int)tmpA.getScreenCenterY(), (int)gp.hero.getScreenCenterX(), (int)gp.hero.getScreenCenterY());
			}
		}	
	}
	
}
