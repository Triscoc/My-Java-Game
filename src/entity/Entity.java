package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.SpriteSheet;
import main.UtilityTool;

public class Entity {
	//public int x, y;
	public int worldX, worldY;
	public boolean dying = false;
	public int dyingCounter = 0;
	
	public int speed;
	
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public String direction = "down";
	
	public BufferedImage image, image2, image3;
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	public int solidAreaDefaultX, solidAreaDefaultY;
	
	public Rectangle solidArea = new Rectangle();
	public boolean collisionOn = false;
	
	public int shotAvailableCounter = 0;
	
	public int maxLife;
	public int life;
	public boolean alive;
	
	public boolean invincible = false;
	public int invincibleCounter = 0;
	public int type;
	public boolean collision = false;
	
	public int attack;
	public Projectile projectile;
	public SpriteSheet sheet = new SpriteSheet("/projectile/Bone.png", 16, 16);
	public UtilityTool uTool = new UtilityTool();
	
	public GamePanel gp;
	public String name;
	public int actionLockCounter = 0;
	
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setAction() {}
	
	public void checkDrop() {}
	
	public void dropItem(Entity droppedItem) {
		for(int i = 0; i<gp.obj.length; i++) {
			if(gp.obj[i] == null) {
				gp.obj[i] = droppedItem;
				gp.obj[i].worldX = worldX;
				gp.obj[i].worldY = worldY;
				break;
			}
		}
	}
	
	public void update() {
		setAction();
		
		 int tempX = worldX;
	     int tempY = worldY;
		
	     switch(direction){
	     case "up" :
            tempY -= speed;
            break;
	     case "down":
	    	 tempY += speed;
	    	 break;
	     case "left":
            tempX -= speed;
            break;
	     case "right":
            tempX += speed;
            break;
	        }
	     
		collisionOn = false;
		boolean withinBounds = tempX >= gp.tileSize / 2 && tempX + gp.tileSize <= gp.screenWidth - gp.tileSize / 2 &&
		        tempY >= 0 && tempY + gp.tileSize <= gp.screenHeight;
		
		        
		        
		// int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
		    
		// If there's no collision and the movement is within screen bounds, update the position
		
        gp.cChecker.checkObject(this, false);
		boolean contactPlayer = gp.cChecker.checkPlayer(this);        
	    if (withinBounds && !collisionOn) {
			worldX = tempX;
			worldY = tempY;
		}
		
	    if(this.type == 2 && contactPlayer == true) {
	    	if(gp.player.invincible == false) {
	    		gp.player.life -=1;
	    		gp.player.invincible = true;
	    	}
	    }
		
		
		// Sprite animation logic
		spriteCounter++;
		if (spriteCounter > 10) {
		spriteNum = (spriteNum == 1) ? 2 : 1;
		spriteCounter = 0;
		}
		if(invincible == true) {
	    	invincibleCounter++;
	    	if(invincibleCounter >40) {
	    		invincible = false;
	    		invincibleCounter = 0;
	    	}
	    }
	}
	
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		switch(direction) {
		case "up":
			if(spriteNum == 1) {
				image = up1;
			}
			if(spriteNum == 2) {
				image = up2;
			}
			break;
		case"down":
			if(spriteNum == 1) {
				image = down1;
			}
			if(spriteNum == 2) {
				image = down2;
			}
			break;
		case "left":
			if(spriteNum == 1) {
				image = left1;
			}
			if(spriteNum == 2) {
				image = left2;
			}
			break;
		case "right":
			if(spriteNum == 1) {
				image = right1;
			}
			if(spriteNum == 2) {
				image = right2;
			}
			break;
		}
		
		
		if(invincible == true) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
		}
		
		if(dying == true) {
			dyingAnimation(g2);
		}
		g2.drawImage(image,  worldX, worldY,gp.tileSize, gp.tileSize, null);
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
	}
	
	public void dyingAnimation(Graphics2D g2) {
		dyingCounter++;
		
		if(dyingCounter <= 5) {changeAlpha(g2, 0f);}
		if(dyingCounter >5 && dyingCounter <= 10) {changeAlpha(g2, 1f);}
		if(dyingCounter >10 && dyingCounter <= 15) {changeAlpha(g2, 0f);}
		if(dyingCounter >15 && dyingCounter <= 20) {changeAlpha(g2,1f);}
		if(dyingCounter >20 && dyingCounter <= 25) {changeAlpha(g2, 0f);}
		if(dyingCounter >25 && dyingCounter <= 30) {changeAlpha(g2, 1f);}
		if(dyingCounter >30 && dyingCounter <= 35) {changeAlpha(g2, 0f);}
		if(dyingCounter >35 && dyingCounter <= 40) {changeAlpha(g2, 1f);}
		if(dyingCounter > 40) {
			dying = false;
			alive = false;
		}
		
	}
	
	public void changeAlpha(Graphics2D g2, float alphaValue) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
	}
}