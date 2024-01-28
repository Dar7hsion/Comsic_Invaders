package ai;

import Entity.SolidArea;

public class Node extends SolidArea
{
	Node parent;
	public int col, row, gCost, hCost, fCost;
	public boolean solid, open, checked;
	
	public Node(int col, int row)
	{
		this.col = col;
		this.row = row;
		x = col*48;
		y = row*48;
		setH(48);
		setR();
	}
	
	public void setCol(int col)
	{
		this.col = col;
	}
	
	public void setRow(int row)
	{
		this.row = row;
	}
	
	public void setGcost(int gCost)
	{
		this.gCost = gCost;
	}
	
	public void setHcost(int hCost)
	{
		this.hCost = hCost;
	}
	
	public void setSolid(boolean solid)
	{
		this.solid = solid;
	}
	
	public void setOpen(boolean open)
	{
		this.open = open;
	}
	
	public void setChecked(boolean checked)
	{
		this.checked = checked;
	}
	
	public int getCol()
	{
		return col;
	}
	
	public int getRow()
	{
		return row;
	}
	
	public int getGcost()
	{
		return gCost;
	}
	
	public int getHcost()
	{
		return hCost;
	}
	
	public boolean getSolid()
	{
		return solid;
	}
	
	public boolean getOpen()
	{
		return open;
	}
	
	public boolean getChecked()
	{
		return checked;
	}

}
