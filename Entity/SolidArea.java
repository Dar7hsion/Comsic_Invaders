package Entity;

import java.awt.Image;
import java.awt.image.BufferedImage;

public class SolidArea 
{
	public double x, y, a, screenX, screenY, centerX, centerY, screenCenterX, screenCenterY; 
	public BufferedImage current;
	public int w, h, r, deathCount;
	public boolean alive;
	
	public Image getI() 
	{
		return current;
	}
	
	public double getCenterX() 
	{
		return centerX;
	}

	public double getCenterY() 
	{
		return centerY;
	}
	public double getX() 
	{
		return x;
	}

	public double getY() 
	{
		return y;
	}

	public double getA() 
	{
		return a;
	}
	
	public int getR() 
	{
		return r;
	}

	public int getW() 
	{
		return w;
	}

	public int getH() 
	{
		return h;
	}
	
	public double getScreenCenterX()
	{
		return this.screenCenterX;
	}
	
	public double getScreenCenterY()
	{
		return this.screenCenterY;
	}
	
	public void setCenterX()
	{
		centerX = x + r;
	}
	
	public void setCenterY()
	{
		centerY = y + r;
	}
	
	public void setR()
	{
		this.r = h/2;
	}
	
	// setting the values
	public void setA(double aa)
	{
		this.a = aa;
	}

	public void setX(double x) 
	{
		this.x = x;
	}

	public void setY(double y) 
	{
		this.y = y;
	}
	
	public void setW(int w) {
		this.w = w;
	}

	public void setH(int h) {
		this.h = h;
	}
	
	public boolean collision(SolidArea sa1, SolidArea sa2)
	{
		double sumR = sa1.r + sa2.r;
		double distance = Math.sqrt(Math.abs((sa2.getScreenCenterX() - sa1.getScreenCenterX())*(sa2.getScreenCenterX() - sa1.getScreenCenterX())) + 
				Math.abs((sa2.getScreenCenterY() - sa1.getScreenCenterY())*(sa2.getScreenCenterY() - sa1.getScreenCenterY())));
		return distance <= sumR;
	}
	
	
	
	

}
