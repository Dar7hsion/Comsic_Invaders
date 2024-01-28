package background;

import java.awt.image.BufferedImage;

import Entity.SolidArea;

public class Tile
{
	
	public BufferedImage image;
	public int col;
	public int row;
	boolean solid = false;
	
	
	public Tile()
	{

	}
	
	public int getCol()
	{
		return col;
	}
	
	public int getRow()
	{
		return row;
	}
	
	public void setCol(int col)
	{
		this.col = col;
	}
	
	public void setRow(int row)
	{
		this.row = row;
	}
	

}
