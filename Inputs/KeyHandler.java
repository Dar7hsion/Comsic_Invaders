package Inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.net.URISyntaxException;

import main.GamePanel;
import main.Main;

public class KeyHandler implements KeyListener // picks up if a listed key is pressed, held down and or released
{
	
    public boolean moveForward, canForward, canBackward, moveBackward, left, right, fire, special, boost, spawn;
	GamePanel gp;
    
    
    public KeyHandler(GamePanel gp)
    {
    	this.gp = gp;
    }
    
	@Override
	public void keyTyped(KeyEvent e){}

	@Override
	public void keyPressed(KeyEvent e) {

		//Used in playstate
		if(gp.gameState == gp.playState)
		{
			if (e.getKeyCode() == e.VK_UP || e.getKeyCode() == e.VK_W) {
			moveForward = true;
			}
			/////////////
			if (e.getKeyCode() == e.VK_DOWN || e.getKeyCode() == e.VK_S) {
			moveBackward = true;
			}
			/////////////
			if (e.getKeyCode() == e.VK_LEFT || e.getKeyCode() == e.VK_A) {
			left = true;
			}
			/////////////
			if (e.getKeyCode() == e.VK_RIGHT || e.getKeyCode() == e.VK_D) {
			right = true;
			}
			/////////////
			if (e.getKeyCode() == e.VK_SHIFT) {
			boost = true;
			}
			/////////////
		}
		//used in pausestate
		else if(gp.gameState == gp.pauseState)
		{
			if (e.getKeyCode() == e.VK_UP || e.getKeyCode() == e.VK_W) {
				gp.ui.commandNum -=1;
				if(gp.ui.commandNum < 0)
				{
					gp.ui.commandNum = 1;
				}
			}
			/////////////
			if (e.getKeyCode() == e.VK_DOWN || e.getKeyCode() == e.VK_S) {
				gp.ui.commandNum +=1;
				if(gp.ui.commandNum > 1)
				{
					gp.ui.commandNum = 0;
				}
			}
			/////////////
			if (e.getKeyCode() == e.VK_ENTER || e.getKeyCode() == e.VK_E) {
				if(gp.ui.commandNum == 0)
				{
					try {
						gp.saveLoad.save();
						Main.restartApplication();
					} catch (URISyntaxException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else if(gp.ui.commandNum == 1)
				{
					gp.saveLoad.save();
					System.exit(0);
				}	
			}
		}
		
		/////////////
		if (e.getKeyCode() == e.VK_ESCAPE) {
			if(gp.gameState == gp.playState)
			{
				gp.gameState = gp.pauseState;
			}
			else if(gp.gameState == gp.pauseState)
			{
				gp.gameState = gp.playState;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

		if (e.getKeyCode() == e.VK_UP || e.getKeyCode() == e.VK_W) {
			moveForward = false;
		}
		/////////////
		if (e.getKeyCode() == e.VK_DOWN || e.getKeyCode() == e.VK_S) {
			moveBackward = false;
		}
		/////////////
		if (e.getKeyCode() == e.VK_LEFT || e.getKeyCode() == e.VK_A) {

			left = false;
		}
		/////////////
		if (e.getKeyCode() == e.VK_RIGHT || e.getKeyCode() == e.VK_D) {
			right = false;
		}
		/////////////
		if (e.getKeyCode() == e.VK_SHIFT) {
			fire = false;
		}
	}
}

