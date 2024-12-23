package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_Milk extends Entity {
	public OBJ_Milk(GamePanel gp) {
		super(gp);
		name = "Milk";
		try {
			down1 = ImageIO.read(getClass().getResourceAsStream("/object/Milk.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
		collision = false;
	
		solidArea.x = 0;
		solidArea.y = 0;
		solidArea.width = 16;
		solidArea.height = 16;
		solidAreaDefaultX = solidArea.x;
	}
}
