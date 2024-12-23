package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Projectile;
import main.GamePanel;

public class OBJ_Bone extends Projectile{

		GamePanel gp;
	public OBJ_Bone(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "Bone";
		speed = 10;
		maxLife = 60;
		life = maxLife;
		attack = 1;
		alive = false;
		
		solidArea.x = 0;
		solidArea.y = 0;
		solidArea.width = 16;
		solidArea.height = 16;
		getImage();
	}
	
	public void getImage() {
		try {
			left1 = ImageIO.read(getClass().getResourceAsStream("/projectile/Bone.png"));
			left2 = sheet.rotateImage(left1, 5);
			
			right1 = sheet.flipImageHorizontal(left1);
			right2 = sheet.rotateImage(right1, -5);
			
			up1 = sheet.turnImage(left1);
			up2 = sheet.rotateImage(up1, -5);
			
			down1 = sheet.turnImage(right1);
			down2 = sheet.rotateImage(down1, -5);
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		

	}
}
