package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_Apple extends Entity {
	
	
	public OBJ_Apple(GamePanel gp) {
		super(gp);
		name = "Apple";
		try {
			down1 = ImageIO.read(getClass().getResourceAsStream("/object/Apple.png"));
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
