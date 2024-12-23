package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import object.OBJ_Bone;

public class Player extends Entity{
	GamePanel gp;
	KeyHandler keyH;
	public int move;
	public int health = 3;
	int playerCameraY =0;
	public int itemPickCounter = 0;
	boolean feather = false;
	boolean milk = false;
	
	
	
	public Player(GamePanel gp, KeyHandler keyH) {
		super(gp);
		this.gp = gp;
		this.keyH = keyH;
		
		setDefaultValues();
		getPlayerImage();
		
		solidArea = new Rectangle();
		solidArea.x = 0;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 32;
		solidArea.height = 32;
	}
	
	public void setDefaultValues() {
		worldX = 8 * gp.tileSize;
		worldY = 8 * gp.tileSize;
		move = 4;
		speed = move;
		direction = "down";
		
		maxLife = 6;
		life = maxLife;
		projectile = new OBJ_Bone(gp);
	}
	
	public void setDefaultPositions() {
		worldX = 8 * gp.tileSize;
		worldY = 8 * gp.tileSize;
		direction = "down";
	}
	
	public void restoreLife() {
		life = maxLife;
		invincible = false;
	}
	
	public void getPlayerImage() {
		try {
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/Player_Up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/Player_Up_2.png"));
			
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/Player_Left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/Player_Left_2.png"));
			
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/Player_Down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/Player_Down_2.png"));
			
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/Player_Right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/Player_Right_2.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
	    if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
	        // Save the current position for collision detection
	        int tempX = worldX;
	        int tempY = worldY;

	        if (keyH.upPressed) {
	            tempY -= move;
	            direction = "up";
	        }
	        if (keyH.downPressed) {
	            tempY += move;
	            direction = "down";
	        }
	        if (keyH.leftPressed) {
	            tempX -= move;
	            direction = "left";
	        }
	        if (keyH.rightPressed) {
	            tempX += move;
	            direction = "right";
	        }

	        // Reset collision status before checking for collisions
	        collisionOn = false;

	        // Check for object collision
	        int objIndex = gp.cChecker.checkObject(this, true);
	        pickUpObject(objIndex);
	        
	        int monIndex = gp.cChecker.checkEntity(this, gp.monster);
	        
	        contactMonster(monIndex);
	        
	        // Check for screen edges collision
	        boolean withinBounds = tempX >= gp.tileSize / 2 && tempX + gp.tileSize <= gp.screenWidth - gp.tileSize / 2 &&
	                               tempY >= 0 && tempY + gp.tileSize <= gp.screenHeight;

	                               
	                               
           // int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
	                               
	        // If there's no collision and the movement is within screen bounds, update the position
	        if (withinBounds && !collisionOn) {
	            worldX = tempX;
	            worldY = tempY;
	        }

	        // Sprite animation logic
	        spriteCounter++;
	        if (spriteCounter > 10) {
	            spriteNum = (spriteNum == 1) ? 2 : 1;
	            spriteCounter = 0;
	        }
	    }
	    
	    if(gp.keyH.shotKeyPressed ==  true && projectile.alive == false && shotAvailableCounter == 30) {
	    	projectile.set(worldX, worldY, direction, true, this);
	    	
	    	gp.projectileList.add(projectile);
	    	shotAvailableCounter = 0;
	    }
	    if(milk == true) {
	    	invincible = true;
	    	itemPickCounter++;
	    	if(itemPickCounter > 5 * 60) {
	    		invincible = false;
	    		milk = false;
	    		itemPickCounter = 0;
	    	}
	    }
	    
	    if(feather == true) {
	    	move = 8;
	    	itemPickCounter++;
	    	if(itemPickCounter > 3 * 60) {
	    		feather = false;
	    		itemPickCounter = 0;
	    		move = 4;
	    	}
	    }
	    
	    if(invincible == true) {
	    	invincibleCounter++;
	    	if(invincibleCounter >60) {
	    		invincible = false;
	    		invincibleCounter = 0;
	    	}
	    }
	    if(shotAvailableCounter <30) {
    		shotAvailableCounter++;
    	}
	    
	    if(life <=0) {
	    	gp.gameState = gp.gameOverState;
	    	gp.playSE(5);
	    	gp.ui.commandNum = 0;
	    }
	}

	public void damageMonster(int i) {
		if(i != 999) {
			if(gp.monster[i].invincible == false) {
				gp.playSE(1);
				gp.monster[i].life -=1;
				gp.monster[i].invincible = true;
				
				if(gp.monster[i].life <= 0) {
					gp.monster[i].dying = true;
				}
			}
		}
	}
		
	
	
	public void pickUpObject(int i) {
		if(i != 999) {
			
			String objectName = gp.obj[i].name;
			
			switch(objectName) {
			case "Apple":
				gp.playSE(2);
				life++;
				gp.obj[i] = null;
				break;
			case "Feather":
				gp.playSE(2);
				feather = true;
				gp.obj[i] = null;
				break;
			case "Milk":
				gp.playSE(2);
				milk = true;
				gp.obj[i]=null;
				break;
			}
		}
	}
	
	public void contactMonster(int i) {
		if(i != 999) {
			if(invincible == false) {
				gp.playSE(3);
				life--;
				invincible = true;
			}
		}
	}
	
	public void draw(Graphics2D g2, int cameraY) {
 
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
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		}
		
		//playerCameraY += 1;
		g2.drawImage(image, worldX, worldY + playerCameraY, gp.tileSize, gp.tileSize, null);
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}
}



