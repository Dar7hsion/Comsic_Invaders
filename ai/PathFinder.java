package ai;

import java.util.ArrayList;
import java.util.Random;

import background.Tile;
import main.GamePanel;

public class PathFinder 
{
GamePanel gp;
	
	public ArrayList<Node> OpenArea = new ArrayList<>(); 	
	public ArrayList<Node> pathList = new ArrayList<>(); 	
	public ArrayList<Node> openList = new ArrayList<>(); 			
	public Node[][] node;
	//public Tile tileMapLocation[][];
	public Node startNode, goalNode, currentNode; 				
	boolean goalReached = false;							
	int step = 0; 											
	
	public PathFinder(GamePanel gp)
	{
		this.gp = gp;
		//tileMapLocation = new Tile[gp.maxWorldCol][gp.maxWorldRow];
		inataniateNodes();
	}
	
	//INATANIATES THE BASE NODE ARRAY, BUILDS THE AN EMPTY NODE[][] ARRAY
	public void inataniateNodes()
	{
		node = new Node[gp.maxWorldCol][gp.maxWorldRow];
		
		int col = 0;
		int row = 0;
		
		while(col < gp.maxWorldCol && row < gp.maxWorldRow)
		{
			node[col][row] = new Node(col,row);
			
			col++;
			if(col == gp.maxWorldCol)
			{
				col = 0;
				row++;
			}
		}
	}
	
	//RESTS THE NODE ARRAY BETWEEN USES SO NEW PATHS CAN BE FOUD AS THE PLAYER MOVES
	public void resetNodes()
	{
		int col = 0;
		int row = 0;
		
		while(col < gp.maxWorldCol && row < gp.maxWorldRow)
		{
			//RESET OPEN, CHECKED AND SOLID STATE
			node[col][row].open = false; 
			node[col][row].checked = false; 
			
			col++;
			if(col ==gp.maxWorldCol)
			{
				col = 0;
				row++;
			}
		}
		// RESET OTHER SETTINGS
		openList.clear();
		pathList.clear();
		goalReached = false;
		step = 0;
	}
	
	//SETS THE NODES, THE EMPTY NODE[][] IS FILLED WITH COST VALUES AND SOLID VALUES, THESE ARE USED TO SET THE PATHS
	public void setNodes(int startCol, int startRow, int goalCol, int goalRow)
	{
		resetNodes();
		
		//SET START AND GOAL NODE
		startNode = node[startCol][startRow];
		currentNode = startNode;
		goalNode = node[goalCol][goalRow];
		openList.add(currentNode);
		
		int col = 0;
		int row = 0;
		
		while(col < gp.maxWorldCol && row < gp.maxWorldRow)
		{
			getCost(node[col][row]);
			col++;
			if(col == gp.maxWorldCol)
			{
				col = 0;
				row++;
			}
		}
	}
	
	public void getCost(Node node)
	{
		//GET GCOST(The Distance from the start node)
		int xDistance = Math.abs(node.col - startNode.col);
		int yDistance = Math.abs(node.row - startNode.row);
		node.gCost = xDistance + yDistance;
				
		//GET HCOST(The Distance from the start node)
		xDistance = Math.abs(node.col - goalNode.col);
		yDistance = Math.abs(node.row - goalNode.row);
		node.hCost = xDistance + yDistance;
				
		//GET FCOST (The total cost)
		node.fCost = node.gCost + node.hCost;
	}
	
	//TAKES THE COST VALUES AND USES THE OPEN LIST TO FIND THE BEST PATH, EACH ELEMENT IN OPEN LIST IS CHECKED AND REMOVED ONCED COMPARED. 
	public boolean search()
	{
		while(goalReached == false && step < 2500)
		{
			
			int col = currentNode.col;
			int row = currentNode.row;
			
			//CHECK THE CURRENT NODE
			currentNode.checked = true;
			openList.remove(currentNode);
			
			//OPEN THE UP NODE
			if(row -1 >= 0)
			{
				openNode(node[col][row-1]);
			}
			//OPEN THE LEFT NODE
			if(col -1 >= 0)
			{
				openNode(node[col-1][row]);
			}
			//OPEN THE DOWN NODE
			if(row +1 < gp.maxWorldRow)
			{
				openNode(node[col][row+1]);
			}
			//OPEN THE RIGHT NODE
			if(col +1 < gp.maxWorldCol)
			{
				openNode(node[col+1][row]);
			}
			
			//FIND THE BEST NODE 
			int bestNodeIndex = 0;
			int bestNodefCost = 999;
			for(int i = 0; i < openList.size(); i++)
			{
				//CHECK IF THIS NODE'S FCOST IS BETTER
				if(openList.get(i).fCost < bestNodefCost)
				{
					bestNodeIndex = i;
					bestNodefCost = openList.get(i).fCost;
				}
				//IF FCOST IS EQUALS WE CHECK THE GCOST
				else if(openList.get(i).fCost == bestNodefCost)
				{
					if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost)
					{
						bestNodeIndex = i;
					}
				}
			}
			
			//IF THERE IS NO NODE IN THE OPENLIST, END THE LOOP
			if(openList.size() == 0)
			{
				break;
			}
			
			//AFTER THE LOOP, OPENLIST [bestNodeIndex] IS THE NEXT STEP
			currentNode = openList.get(bestNodeIndex);
			
			if(currentNode ==  goalNode)
			{
				goalReached = true;
				trackThePath();
			}
			step++;
		}
		return goalReached;
	}
	
	//OPENS THE NODES
	private void openNode(Node node)
	{
		if(node.open == false && node.checked == false && node.solid == false)
		{
			//IF THE NODE IS NOT OPENED YET ADD IT TO THE OPEN LIST
			node.open = true;
			node.parent = currentNode;
			openList.add(node);
		}
	}
	
	//BACK TRACKS THE PATH 
	private void trackThePath()
	{
		//BACKTRACK AND DRAW THE BEST PATH
		Node current = goalNode;
		
		while(current != startNode)
		{
			pathList.add(0,current);
			current = current.parent;
		}
	}
}
