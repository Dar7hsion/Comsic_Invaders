package background;

import java.awt.Color;
import java.awt.Graphics2D;

import main.GamePanel;

public class Border 
{
	/*
	int b = 4;
	g2.setColor(Color.red);
	g2.drawLine(tileM.upperX - screenWidth/b, tileM.upperY - screenHeight/b, tileM.upperX - screenWidth/b, tileM.lowerY + screenHeight/b);
	g2.drawLine(tileM.lowerX + screenWidth/b, tileM.upperY - screenHeight/b, tileM.lowerX + screenWidth/b, tileM.lowerY + screenHeight/b);
	g2.drawLine(tileM.upperX - screenWidth/b, tileM.upperY - screenHeight/b, tileM.lowerX + screenWidth/b, tileM.upperY - screenHeight/b);
	g2.drawLine(tileM.upperX - screenWidth/b, tileM.lowerY + screenHeight/b, tileM.lowerX + screenWidth/b, tileM.lowerY + screenHeight/b);
	g2.setTransform(old);
	*/
	
	GamePanel gp;
	int xConner;
	int yConner;
	public int upperX, lowerX, upperY, lowerY;
	Graphics2D g2;
	
	
	public Border(GamePanel gp)
	{
		this.gp = gp;
	}
	
	public void setX()
	{
		xConner = (int)(0 - (gp.hero.x - (gp.screenWidth/2)));
	}
	public void setY()
	{
		yConner = (int)(0 - (gp.hero.y - (gp.screenHeight/2)));
	}
	
	public void setBounds()
	{
		upperX = (int)((0 + gp.screenWidth) - (gp.hero.x));
		lowerX = (int)(gp.worldWidth - (gp.hero.x));
		upperY = (int)((0 + gp.screenHeight) - (gp.hero.y));
		lowerY = (int)(gp.worldHeight - (gp.hero.y));
	}
	
	public void drawTop()
	{
		
	}
	
	public void drawBottom()
	{
		
	}
	
	public void drawLeft()
	{
		
	}
	
	public void drawRight()
	{
		
	}
	public void draw()
	{
		
	}

}
