package Data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import main.GamePanel;

public class SaveLoad 
{
	GamePanel gp;
	
	public SaveLoad(GamePanel gp)
	{
		this.gp = gp;
	}
	
	public void save()
	{
		try {
			
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));
			
			DataStorage ds = new DataStorage();
			
			if(ds.HighScore < gp.ui.PlayTime)
			{
				ds.HighScore = gp.ui.PlayTime;
			}
			
			if(ds.TopKills < gp.ui.kills)
			{
				ds.TopKills = gp.ui.kills;
			}
			
			// Write the DataStorage Object
			oos.writeObject(ds);
		} catch (Exception e) {
			System.out.println("Save Exception");
		}
	}
	
	public void load()
	{
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));
			
			// Read the DataStorage object
			DataStorage ds = (DataStorage)ois.readObject();
			
			gp.ui.HighScore = ds.HighScore;
			gp.ui.TopKills = ds.TopKills;

		} catch (Exception e) {
			System.out.println("Load Exception");
		}
	}
}
