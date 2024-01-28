package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JPanel;

public class UI 
{
	GamePanel gp;
	Graphics2D g2;
	Font arial_20, arial_40, arial_80B;
	public double PlayTime;
	public double HighScore;
	public int kills = 0;
	public int TopKills;
	DecimalFormat dFormat = new DecimalFormat("#0000.000");
	public int commandNum = 0;
	
	public UI(GamePanel gp)
	{
		this.gp = gp;
		arial_40 = new Font("Arial", Font.PLAIN, 20);
		arial_40 = new Font("Arial", Font.PLAIN, 40);
		arial_80B = new Font("Arial", Font.BOLD, 80);
	}
	
	public void draw(Graphics2D g2)
	{
		this.g2 = g2;
		
		//Draws timer in upper left corner 
		if(gp.gameState == gp.playState)
		{
			drawSubWindow(gp.tileSize - 30, gp.tileSize/2 -3, gp.tileSize*3, gp.tileSize);
			g2.setFont(arial_20);
			g2.setColor(Color.orange);
			PlayTime +=(double)1/60;
			g2.drawString("Time: "+ dFormat.format(PlayTime), gp.tileSize, gp.tileSize);
		}
		
		//Draws Pause Menu
		if(gp.gameState == gp.pauseState)
		{
			g2.setFont(arial_40);
			g2.setColor(Color.white);
			
			//drawPauseScreen();
			int frameX = gp.tileSize;
			int frameY = gp.tileSize;
			int frameWidth = gp.screenWidth - (gp.tileSize*2);
			int frameHeight = gp.screenHeight - (gp.tileSize*2);
			drawSubWindow(frameX, frameY, frameWidth, frameHeight);
			
			//draw the options 
			String text = "OPTIONS";
			int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			int x = gp.tileSize*2;
			int y = gp.tileSize*4;
			g2.drawString(text, x, y);
			
			//game time 
			g2.drawString("Time: "+ dFormat.format(PlayTime), x + gp.tileSize*6, y);
			
			//restart
			y += gp.tileSize*2;
			g2.drawString("RESTART", x, y);
			
			//Kills
			g2.drawString("Kills: " + kills, x + gp.tileSize*6, y);
			if(commandNum == 0)
			{
				g2.drawString(">", x-25, y);
			}
			
			//Quit
			y += gp.tileSize*2;
			g2.drawString("QUIT", x, y);
			if(commandNum == 1)
			{
				g2.drawString(">", x-25, y);
			}
			
			//Best game time
			g2.setColor(Color.red);
			g2.drawString("Top Kills: " + TopKills, x + gp.tileSize*6, y);
			
			//Best Time
			y += gp.tileSize*2;
			g2.drawString("Top Time: " + dFormat.format(HighScore), x, y);
		}
	}
	
	//Helper method draws sub windows 
	public void drawSubWindow(int x, int y, int width, int height)
	{
		Color c = new Color(0, 0, 0, 210);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c = new Color(255, 255, 255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
		
	}
}
