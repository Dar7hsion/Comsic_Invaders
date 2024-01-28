package Entity;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Images 
{
	public BufferedImage E0, E1, E2, E3, E4, E5, D,R1, R2, R3, B0, B1, B2, B3, B4, B5, E, A;
	
	public Images()
	{
		setImages();
	}
	
	public void setImages()
	{
		try 
		{
			//rocket
			R1 = ImageIO.read(getClass().getResourceAsStream("/rocket1.png"));
			R2 = ImageIO.read(getClass().getResourceAsStream("/rocket2-1.png.png"));
			R3 = ImageIO.read(getClass().getResourceAsStream("/rocket3-1.png.png"));
			//boost
			B0 = ImageIO.read(getClass().getResourceAsStream("/B0.png"));
			B1 = ImageIO.read(getClass().getResourceAsStream("/B1.png"));
			B2 = ImageIO.read(getClass().getResourceAsStream("/B2.png"));
			B3 = ImageIO.read(getClass().getResourceAsStream("/B3.png"));
			B4 = ImageIO.read(getClass().getResourceAsStream("/B4.png"));
			B5 = ImageIO.read(getClass().getResourceAsStream("/B5.png"));
			//enemy
			E = ImageIO.read(getClass().getResourceAsStream("/Enemy.png"));
			//death
			E0 = ImageIO.read(getClass().getResourceAsStream("/explod1.png"));
			E1 = ImageIO.read(getClass().getResourceAsStream("/explod2.png"));
			E2 = ImageIO.read(getClass().getResourceAsStream("/explod3.png"));
			E3 = ImageIO.read(getClass().getResourceAsStream("/explod4.png"));
			E4 = ImageIO.read(getClass().getResourceAsStream("/explod5.png"));
			E5 = ImageIO.read(getClass().getResourceAsStream("/explod6.png"));
			D = ImageIO.read(getClass().getResourceAsStream("/death.png"));
			//asteroid
			A= ImageIO.read(getClass().getResourceAsStream("/astro.png"));
			//Border 

			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

}
