package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity{

	
	public OBJ_Heart(GamePanel gp) {
		super(gp);
		
		
		name = "Heart";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/object/heart_full.png"));
			image2 = ImageIO.read(getClass().getResourceAsStream("/object/heart_half.png"));
			image3 = ImageIO.read(getClass().getResourceAsStream("/object/heart_blank.png"));
			image = uTool.scaleImage(image,  gp.tileSize, gp.tileSize);
			image2 = uTool.scaleImage(image2,  gp.tileSize, gp.tileSize);
			image3 = uTool.scaleImage(image3,  gp.tileSize, gp.tileSize);
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
