package Inputs;

import java.awt.event.MouseEvent;

import main.GamePanel;

public class MouseListener implements java.awt.event.MouseListener 
{
	
	GamePanel gp;
	
	public MouseListener (GamePanel gp)
	{
		this.gp = gp;
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		if (e.getButton() == e.BUTTON1) 
		{
			gp.fire = true;
		}
		if (e.getButton() == e.BUTTON3) 
		{
			gp.fire = true;
		}	
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		if (e.getButton() == e.BUTTON1) 
		{
			gp.fire = false;
		}
		if (e.getButton() == e.BUTTON3) 
		{
			gp.fire = false;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

}
