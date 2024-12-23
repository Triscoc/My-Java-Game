package monster;

import java.awt.Rectangle;
import java.util.Random;

import entity.Entity;
import main.GamePanel;
import main.SpriteSheet;
import object.OBJ_Apple;
import object.OBJ_Feather;
import object.OBJ_Milk;

public class MON_Chicken extends Entity{
	
	SpriteSheet sheet = new SpriteSheet("/monster/Chicken.png", 16, 16);
	
	public MON_Chicken(GamePanel gp) {
		super(gp);
		
		type = 2;
		name = "Chicken";
		speed = 2;
		maxLife = 2;
		life = maxLife;
		alive = true;
		
		direction = "down";
		
		int solidWidth = 11;
        int solidHeight = 11;
        int offsetX = 0; 
        int offsetY = 5; 
        solidArea = new Rectangle(offsetX, offsetY, solidWidth, solidHeight);
        
        getImage();
	}
	
	public void getImage() {
		up1 = sheet.getFrame(1,0);
		up2 = sheet.getFrame(1, 1);
		down1 = sheet.getFrame(1, 0);
		down2 = sheet.getFrame(1, 1);
		right1 = sheet.getFrame(1,0);
		right2 = sheet.getFrame(1, 1);
		left1 = sheet.flipImageHorizontal(right1);
		left2 = sheet.flipImageHorizontal(right2);
	}
	
	public void checkDrop() {
		int i = new Random().nextInt(3)+1;
		if(i == 1) {
			int j = new Random().nextInt(3) +1;
			if(j == 1) {
				dropItem(new OBJ_Apple(gp));
			}
			if(j == 2) {
				dropItem(new OBJ_Feather(gp));
			}
			if(j == 3) {
				dropItem(new OBJ_Milk(gp));
			}
		}
	}
	
	public void setAction() {
		actionLockCounter++;
		
		if(actionLockCounter == 120) {
			Random random = new Random();
			int i = random.nextInt(100)+1;
			
			if(i <= 25) {
				direction = "up";
			}
			if(i >25 && i <= 50) {
				direction = "down";
			}
			if(i > 50 && i <= 75) {
				direction = "left";
			}
			if(i>75 && i<= 100) {
				direction = "right";
			}
			actionLockCounter = 0;;
		}
	}
}
