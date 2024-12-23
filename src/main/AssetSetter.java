package main;


import java.util.Random;

import monster.MON_Chicken;
import object.OBJ_Apple;
import object.OBJ_Feather;
import object.OBJ_Milk;

public class AssetSetter {
	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
		//gp.obj[0] = new OBJ_Apple(gp);
		//gp.obj[0].worldX = (int)(Math.random() * gp.maxScreenCol * gp.tileSize);
		//gp.obj[0].worldY = (int) (2 * gp.tileSize);
		
		//gp.obj[1] = new OBJ_Feather(gp);
		//gp.obj[1].worldX = (int)(7 * gp.tileSize);
		//gp.obj[1].worldY = (int) (9 * gp.tileSize);
		
		//gp.obj[2] = new OBJ_Milk(gp);
		//gp.obj[2].worldX = 10 * gp.tileSize;
		//gp.obj[2].worldY = 10 * gp.tileSize;
	}
	
	public void setMonster() {
		gp.monster[0] = new MON_Chicken(gp);
		gp.monster[0].worldX = gp.tileSize * 5;
		gp.monster[0].worldY = gp.tileSize * 5;
		
		spawnChicken(4 * 16, 3 * 16);
	}
	
	public void spawnChicken(int x, int y) {
		for(int i = 0 ; i< gp.monster.length; i++) {
			if(gp.monster[i] ==null) {
				gp.monster[i] = new MON_Chicken(gp);
				gp.monster[i].worldX = x;
				gp.monster[i].worldY = y;
				break;
			}
		}
	}
}
