package main;

import entity.Entity;

public class CollisionChecker {
	GamePanel gp;
	
	public CollisionChecker(GamePanel gp) {
		this.gp =gp;
	}
	
	
	public int checkObject(Entity entity, boolean player) {
	    int index = 999;
	    for (int i = 0; i < gp.obj.length; i++) {
	        if (gp.obj[i] != null) {
	            // Calculate the player's and object hitboxes
	            entity.solidArea.x = entity.worldX + entity.solidArea.x;
	            entity.solidArea.y = entity.worldY + entity.solidArea.y;
	            gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
	            gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

	            // Check for collision based on the direction
	            switch (entity.direction) {
	                case "up":
	                    entity.solidArea.y -= entity.speed;
	                    if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
	                    	if(gp.obj[i].collision == true) {
		                    	   entity.collisionOn = true;
		                       }
		                       if(player == true) {
		                    	   index =i;
		                       }
	                    }
	                    break;
	                case "down":
	                    entity.solidArea.y += entity.speed;
	                    if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
	                    	if(gp.obj[i].collision == true) {
		                    	   entity.collisionOn = true;
		                       }
		                       if(player == true) {
		                    	   index =i;
		                       }
	                    }
	                    break;
	                case "left":
	                    entity.solidArea.x -= entity.speed;
	                    if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
	                    	if(gp.obj[i].collision == true) {
		                    	   entity.collisionOn = true;
		                       }
		                       if(player == true) {
		                    	   index =i;
		                       }
	                    }
	                    break;
	                case "right":
	                    entity.solidArea.x += entity.speed;
	                    if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
	                    	if(gp.obj[i].collision == true) {
		                    	   entity.collisionOn = true;
		                       }
		                       if(player == true) {
		                    	   index =i;
		                       }
	                    }
	                    break;
	            }

	            // Reset hitboxes to their default positions
	            entity.solidArea.x = entity.solidAreaDefaultX;
	            entity.solidArea.y = entity.solidAreaDefaultY;
	            gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
	            gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
	        }
	    }

	    return index;
	}

	public void checkTile(Entity entity) {
		
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX =entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
		
		int entityLeftCol = entityLeftWorldX/gp.tileSize;
		int entityRightCol = entityRightWorldX/gp.tileSize;
		int entityTopRow = entityTopWorldY/gp.tileSize;
		int entityBottomRow = entityBottomWorldY/gp.tileSize;
		
		int tileNum1, tileNum2;
		
		 switch (entity.direction) {
	        case "up":
	            entityTopRow = Math.max((entityTopWorldY - entity.speed) / gp.tileSize, 0);
	            tileNum1 = gp.tileM.mapTileNum[Math.min(entityLeftCol, gp.maxScreenCol - 1)][entityTopRow];
	            tileNum2 = gp.tileM.mapTileNum[Math.min(entityRightCol, gp.maxScreenCol - 1)][entityTopRow];
	            
	            System.out.println("Direction: " + entity.direction);
	            System.out.println("TileNum1: " + tileNum1 + ", TileNum2: " + tileNum2);
	            System.out.println("Collision1: " + gp.tileM.tile[tileNum1].collision + ", Collision2: " + gp.tileM.tile[tileNum2].collision);

	            
	            if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
	                entity.collisionOn = true;
	            }else {
	            	entity.collisionOn = false;
	            }
	            break;

	        case "down":
	            entityBottomRow = Math.min((entityBottomWorldY + entity.speed) / gp.tileSize, gp.maxScreenRow - 1);
	            tileNum1 = gp.tileM.mapTileNum[Math.min(entityLeftCol, gp.maxScreenCol - 1)][entityBottomRow];
	            tileNum2 = gp.tileM.mapTileNum[Math.min(entityRightCol, gp.maxScreenCol - 1)][entityBottomRow];
	            
	            System.out.println("Direction: " + entity.direction);
	            System.out.println("TileNum1: " + tileNum1 + ", TileNum2: " + tileNum2);
	            System.out.println("Collision1: " + gp.tileM.tile[tileNum1].collision + ", Collision2: " + gp.tileM.tile[tileNum2].collision);

	            
	            if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
	                entity.collisionOn = true;
	            }else {
	            	entity.collisionOn = false;
	            }
	            break;

	        case "left":
	            entityLeftCol = Math.max((entityLeftWorldX - entity.speed) / gp.tileSize, 0);
	            tileNum1 = gp.tileM.mapTileNum[entityLeftCol][Math.max(entityTopRow, 0)];
	            tileNum2 = gp.tileM.mapTileNum[entityLeftCol][Math.min(entityBottomRow, gp.maxScreenRow - 1)];
	            
	            System.out.println("Direction: " + entity.direction);
	            System.out.println("TileNum1: " + tileNum1 + ", TileNum2: " + tileNum2);
	            System.out.println("Collision1: " + gp.tileM.tile[tileNum1].collision + ", Collision2: " + gp.tileM.tile[tileNum2].collision);

	            
	            if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
	                entity.collisionOn = true;
	            }else {
	            	entity.collisionOn = false;
	            }
	            break;

	        case "right":
	            entityRightCol = Math.min((entityRightWorldX + entity.speed) / gp.tileSize, gp.maxScreenCol - 1);
	            tileNum1 = gp.tileM.mapTileNum[entityRightCol][Math.max(entityTopRow, 0)];
	            tileNum2 = gp.tileM.mapTileNum[entityRightCol][Math.min(entityBottomRow, gp.maxScreenRow - 1)];
	            
	            System.out.println("Direction: " + entity.direction);
	            System.out.println("TileNum1: " + tileNum1 + ", TileNum2: " + tileNum2);
	            System.out.println("Collision1: " + gp.tileM.tile[tileNum1].collision + ", Collision2: " + gp.tileM.tile[tileNum2].collision);

	            
	            if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
	                entity.collisionOn = true;
	            }else {
	            	entity.collisionOn = false;
	            }
	            break;
	    }
	}
	
	
	public int checkEntity(Entity entity, Entity[] target) {
		int index = 999;
		
		for(int i = 0; i<target.length; i++) {
			if(target[i]!= null) {
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;
				
				target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
				target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;
				
				switch(entity.direction) {
				case "up":
					entity.solidArea.y -= entity.speed;
					if(entity.solidArea.intersects(target[i].solidArea)) {
						entity.collisionOn=true;
						index = i;
					}
					break;
				case "down":
					entity.solidArea.y += entity.speed;
					if(entity.solidArea.intersects(target[i].solidArea)) {
						entity.collisionOn = true;
						index =i;
					}
					break;
				case "left":
					entity.solidArea.x -= entity.speed;
					if(entity.solidArea.intersects(target[i].solidArea)) {
						entity.collisionOn=true;
						index = i;
					}
					break;
				case "right":
					entity.solidArea.x += entity.speed;
					if(entity.solidArea.intersects(target[i].solidArea)) {
						entity.collisionOn = true;
						index =i;
					}
					break;
				}
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				target[i].solidArea.x = target[i].solidAreaDefaultX;
				target[i].solidArea.y = target[i].solidAreaDefaultY;
			}
			
		}
	return index;
	}


public boolean checkPlayer(Entity entity) {
	
	boolean contactPlayer = false;
	
	entity.solidArea.x = entity.worldX + entity.solidArea.x;
	entity.solidArea.y = entity.worldY + entity.solidArea.y;
	
	gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
	gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
	
	switch(entity.direction) {
	case "up": entity.solidArea.y -= entity.speed; break;
	case "down": entity.solidArea.y += entity.speed; break;
	case "left": entity.solidArea.x -= entity.speed; break;
	case "right": entity.solidArea.x += entity.speed; break;
	}
	
	if(entity.solidArea.intersects(gp.player.solidArea)) {
		entity.collisionOn=true;
		contactPlayer =true;
	}
	entity.solidArea.x = entity.solidAreaDefaultX;
	entity.solidArea.y = entity.solidAreaDefaultY;
	gp.player.solidArea.x = gp.player.solidAreaDefaultX;
	gp.player.solidArea.y = gp.player.solidAreaDefaultY;

	return contactPlayer;
}
}
