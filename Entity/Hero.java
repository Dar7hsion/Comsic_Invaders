package Entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import main.GamePanel;

public class Hero extends SolidArea
{
	private int deathCount,tmpAngle, speedX, speedY, spriteN, tmpLoad, boostCoolDown, boostTime, timer;
	private ArrayList bullets; // this will hold our bullets 
	private GamePanel gp;

	// constructor
	public Hero(double x, double y, double a, int w, int h, GamePanel gp) 
	{
		alive = true;
		deathCount = 0;
		this.x = x;
		this.y = y;
		this.a = a;
		this.w = w;
		this.h = h;
		this.gp = gp;
		//centers on point of rotation
		screenX = gp.screenWidth/2 - 35;
		screenY = gp.screenHeight/2 - getH()/2;
		bullets = new ArrayList();
		tmpLoad = 0;
		tmpAngle = 270;
		spriteN = 0;
		boostTime = 1000;
		timer = 0;
		gp.keyH.special = gp.keyH.fire = gp.keyH.left = gp.keyH.right = gp.keyH.moveForward = gp.keyH.moveBackward = false;
		gp.keyH.canForward = gp.keyH.canBackward = true;
		speedX = speedY = 2;
		current=GamePanel.images.R1;
		setR();
		setSolidArea();
		this.setScreenCenterX();
		this.setScreenCenterY();
	}

	// returning all the necessary value of this class

	public ArrayList getBullets() {
		return bullets;
		
	}
	
	// setting the values
	public void setA(int aa) 
	{
		a = Math.toRadians(aa);
	}
	
	public void setCenterX()
	{
		this.centerX = this.x + 20 + this.r;
	}
	
	public void setScreenCenterX()
	{
		this.screenCenterX = gp.screenWidth/2;
	}
	
	public void setScreenCenterY()
	{
		this.screenCenterY = gp.screenHeight/2 ;
	}
	
	public void setSolidArea()
	{
		this.setCenterX();
		this.setCenterY();
	}

	public void moveForward(int sx, int sy) 
	{
		x += Math.cos(a) * sx;
		y += Math.sin(a) * sy;
	}

	// //backward
	public void moveBackword(int sx, int sy) 
	{
		x -= Math.cos(a) * sx;
		y -= Math.sin(a) * sy;
	}
	
	public void update()
	{
		// if the hero get off the screen
		// we make it appear from the opposite side of the screen
		if (getX() > gp.worldWidth - gp.screenWidth/2) 
		{
			setX(0 + gp.screenWidth/2);
		} 
		else if (getX() < 0 + gp.screenWidth/2) 
		{
			setX(gp.worldWidth - gp.screenWidth/2);
		}

		if (getY() > gp.worldHeight - gp.screenHeight/2) 
		{
			setY(0 + gp.screenHeight/2);
		} 
		else if (getY() < 0 + gp.screenHeight/2) 
		{
			setY(gp.worldHeight - gp.screenHeight/2);
		}
		// moving the hero
		if(alive)
		{
			// changing the hero angle
			if (gp.keyH.left) 
			{
				tmpAngle -= 2;
			}
			if (gp.keyH.right) 
			{
				tmpAngle += 2;
			}
			// setting the hero angle
			this.setA(tmpAngle);
			// this is just to keep the angle between 0 and 360
			if (tmpAngle > 360) 
			{
				tmpAngle = 0;
			} 
			else if (tmpAngle < 0) 
			{
				tmpAngle = 360;
			}
			//If Boosting, boost time is 1000, cool down has to be greater than 1000 to use 
			if(gp.keyH.boost == true && boostCoolDown > boostTime)
			{
				if (gp.keyH.moveForward) 
				{
					if (gp.keyH.canForward) 
					{
						moveForward(speedX*2, speedY*2);
					}
				}
				if (gp.keyH.moveBackward) 
				{
					if (gp.keyH.canBackward) 
					{
						moveBackword(speedX, speedY);
					}
				}
				//time is how long we can boost, when over 500 start cool down and stop boosting
				timer++;	
				if(timer > 500)
				{
					gp.keyH.boost = false;
					timer = 0;
					boostCoolDown = 0;
				}
			}
			else//if not boosting, normal movement and add to boost cool down 
			{
				boostCoolDown++;
				gp.keyH.boost = false;
				if (gp.keyH.moveForward) 
				{
					if (gp.keyH.canForward) 
					{
						moveForward(speedX, speedY);
					}
				}
				
				if (gp.keyH.moveBackward) 
				{
					if (gp.keyH.canBackward) 
					{
						moveBackword(speedX, speedY);
					}
				}
			}
			setSolidArea();//sets the center point of the solid area used in collision method 
			//sprite image control
			if(gp.keyH.boost == true)//boost images 
			{
				switch(spriteN) 
				{
				case 0:
					current = GamePanel.images.B0;
					spriteN++;
				break;
				
				case 1:
					current = GamePanel.images.B1;
					spriteN++;
				break;
				
				case 2:
					current = GamePanel.images.B2;
					spriteN++;
				break;
				
				case 3:
					current = GamePanel.images.B3;
					spriteN++;
				break;
				
				case 4:
					current = GamePanel.images.B4;
					spriteN++;
				break;
				
				case 5:
					current = GamePanel.images.B5;
					spriteN = 0;
				break;
				}
			}
			else
			{
				if(spriteN > 2)//normal movement images 
				{
					spriteN = 0;
				}
				
				switch(spriteN) {
				
				case 0:
					current = GamePanel.images.R2;
					spriteN++;
				break;
				
				case 1:
					current = GamePanel.images.R3;
					spriteN++;
				break;
				
				case 2:
					current = GamePanel.images.R1;
					spriteN = 0;
				break;
				}
			}
		}
		else//If not alive... 
		{	//reformat image size 48*48
			gp.hero.setW(gp.tileSize);
			gp.hero.setH(gp.tileSize);
			//reformat on screen x aand y location to make the new images appear in the correct location. 
			gp.hero.screenX = (gp.screenWidth/2) - (gp.hero.getW()/2);
			gp.hero.screenY = (gp.screenHeight/2) - (gp.hero.getH()/2);
			int m = 5;
			deathCount++;
			if(deathCount < m)//death images
			{
				current = GamePanel.images.E0;
			}
			if(deathCount < m*2 && deathCount >= m)
			{
				current = GamePanel.images.E1;
			}
			if(deathCount < m*3 && deathCount >= m*2)
			{
				current = GamePanel.images.E2;
			}
			if(deathCount < m*4 && deathCount >= m*3)
			{
				current = GamePanel.images.E3;
			}
			if(deathCount < m*5 && deathCount >= m*4)
			{
				current = GamePanel.images.E4;
			}
			if(deathCount < m*6 && deathCount >= m*5)
			{
				current = GamePanel.images.E5;	
			}
			if(deathCount > m*6)
			{
				current = GamePanel.images.D;
				gp.saveLoad.save();
				//gp.gameThread = null;// kills the gamethread
				gp.gameState = gp.pauseState;
			}
		}						
	}
	
	/*
	 * firing load = how much time you wait between 2 bullets number = how many
	 * bullet you shot in a single shot spread = distance between 2 bullets
	 */
	public void fire(int load, int number, int spread) 
	{
		// if reloading time is done
		if (tmpLoad <= 0) 
		{ 
			
			for (int i = 0; i < number; i++) 
			{
				// setting the bullet
				GamePanel.bullet.setScreenX(screenX + 33 + Math.cos(this.a)*11);
				GamePanel.bullet.setScreenY(screenY + 12 + Math.sin(this.a)*11);
				GamePanel.bullet.setA(a + ((spread * (i - 1)) / 2));
				GamePanel.bullet.setW(5);
				GamePanel.bullet.setH(5);
				// adding the bullet to the array list
				bullets.add(new Bullet(GamePanel.bullet.getScreenX(),GamePanel.bullet.getScreenY(), GamePanel.bullet.getA(), GamePanel.bullet.getW(), GamePanel.bullet.getH(),gp));	
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
		// rotating the hero, rotation point is the middle of the square
		g2.rotate(getA(), (int)screenX + getW() - getH()/2, (int)screenY + getH() / 2);
				
		// draw the image
		g2.drawImage(getI(), (int)screenX, (int)screenY, getW(), getH(), null); 
		
	}
}
