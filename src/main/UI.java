package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import object.OBJ_Heart;
import entity.Entity;

public class UI {
	GamePanel gp;
	Graphics2D g2;
	Font arial_40;
	BufferedImage heart_full, heart_half, heart_blank;
	
	public int point = 0;
	public int subState = 0;
	
	
	public int commandNum = 0;
	public int titleScreenState = 0;
	
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		arial_40 = new Font("Arial", Font.PLAIN, 40);
		Entity heart = new OBJ_Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;
		
	}
	
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		g2.setFont(arial_40);
		g2.setColor(Color.white);
		
		
		if(gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		
		if(gp.gameState == gp.playState) {
			drawPlayerLife();
			drawPoint();
			
		}
		if(gp.gameState == gp.pauseState) {
			drawPlayerLife();
			drawPoint();
			drawPauseScreen();
		}
		
		if(gp.gameState == gp.optionsState) {
			drawOptionsScreen();
		}
		
		if(gp.gameState == gp.gameOverState) {
			drawGameOverScreen();
		}
	}
	
	public void drawGameOverScreen() {
		
		g2.setColor(new Color(0,0,0,150));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		int x;
		int y;
		String text;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));
		
		text = "Game Over";
		g2.setColor(Color.black);
		x = getXforCenteredText(text);
		y = gp.tileSize * 4;
		g2.drawString(text, x, y);
		
		g2.setColor(Color.white);
		x = getXforCenteredText(text);
		y = gp.tileSize * 4;
		g2.drawString(text, x-4, y-4);
		
		
		g2.setFont(g2.getFont().deriveFont(50f));
		text = "Kills: "+ point;
		x = getXforCenteredText(text);
		y+= gp.tileSize * 2;
		g2.drawString(text, x, y);
		
		text = "Retry";
		x = getXforCenteredText(text);
		y += gp.tileSize *2;
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			g2.drawString(">", x -40, y);
		}
		
		text = "Quit";
		x = getXforCenteredText(text);
		y+= 65;
		g2.drawString(text, x, y);
		if(commandNum == 1) {
			g2.drawString(">", x -40, y);
		}
	}
	
	public void drawPlayerLife() {
		int x = gp.tileSize/2;
		int y = gp.tileSize/2;
		
		int i = 0;
		while(i <gp.player.maxLife/2) {
			g2.drawImage(heart_blank, x, y, null);
			i++;
			x+= gp.tileSize;
		}
		
		x = gp.tileSize/2;
		y = gp.tileSize/2;
		i = 0;
		while(i<gp.player.life) {
			g2.drawImage(heart_half, x, y, null);
			i++;
			if(i<gp.player.life) {
				g2.drawImage(heart_full, x, y, null);
			}
			i++;
			x += gp.tileSize;
		}
		
		
	}
	
	void drawPoint() {
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));
		String text = "Kills: " + point;
	    int x = gp.screenWidth - 150; // Adjust this value based on the width of the text
	    int y = gp.tileSize; // Position it slightly below the top edge
	    g2.drawString(text, x, y);
	}
	
	public void drawTitleScreen() {
		g2.setColor(new Color(70, 120, 80));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
		String text = "Pixel Barn Brawl";
		int x = getXforCenteredText(text);
		int y = gp.tileSize * 3;
		
		g2.setColor(Color.gray);
		g2.drawString(text, x+5, y+5);
		
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		
		x = gp.screenWidth/2 - (gp.tileSize*2)/2;
		y += gp.tileSize*2;
		g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);
		
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
		
		text = "New Game";
		x = getXforCenteredText(text);
		y += gp.tileSize * 3.5;
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		
		
		text = "Options";
		x = getXforCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if(commandNum == 1) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		
		text = "Quit";
		x = getXforCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if(commandNum == 2) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		
	}
	
	public void drawOptionsScreen() {
		g2.setColor(new Color(70, 120, 80));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(25F));
		
		int frameX = gp.tileSize * 4;
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize * 8;
		int frameHeight = gp.tileSize * 10;
		
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);	
		
		switch(subState) {
		case 0: options_top(frameX, frameY); break;
		case 1: options_control(frameX, frameY);break;
		}
	}
	
	public void options_top(int frameX, int frameY) {
		int textX;
		int textY;
		
		String text = "Options";
		textX = getXforCenteredText(text);
		textY = frameY + gp.tileSize;
		
		g2.drawString(text, textX, textY);
		
		textX = frameX + gp.tileSize;
		textY += gp.tileSize * 2;
		g2.drawString("Music", textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX - 25, textY);
		}
		
		textY += gp.tileSize;
		g2.drawString("Sound Effect", textX, textY);
		if(commandNum == 1) {
			g2.drawString(">", textX - 25, textY);
		}
		
		textY += gp.tileSize;
		g2.drawString("Control", textX, textY);
		if(commandNum == 2) {
			g2.drawString(">", textX - 25, textY);
		}
		
		
		textY+= 4 * gp.tileSize;
		g2.drawString("Back", textX, textY);
		if(commandNum == 3) {
			g2.drawString(">", textX -25, textY);
		}
		textX = frameX + gp.tileSize * 5;
		textY = frameY + gp.tileSize*2 + 24 ;
		g2.drawRect(textX, textY, 120, 24);
		int volumeWidth = 24 * gp.music.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
		
		
		textY += gp.tileSize;
		g2.drawRect(textX, textY, 120, 24);
		int seWidth = 24 * gp.SE.volumeScale;
		g2.fillRect(textX, textY, seWidth, 24);
		
		
	}
	
	public void options_control(int frameX, int frameY) {
		commandNum = 0;
		int textX;
		int textY;
		
		String text = "Control";
		textX = getXforCenteredText(text);
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);
		
		textX = frameX + gp.tileSize;
		textY += gp.tileSize * 2;
		
		g2.drawString("Move", textX, textY); textY+= gp.tileSize;
		g2.drawString("Shoot", textX, textY); textY+= gp.tileSize;
		g2.drawString("Pause", textX, textY);textY+= gp.tileSize;
		
		textX = frameX + gp.tileSize * 5;
		textY = frameY + gp.tileSize*3;
		
		g2.drawString("WASD", textX, textY); textY += gp.tileSize;
		g2.drawString("SPACE", textX, textY); textY += gp.tileSize;
		g2.drawString("ESCAPE", textX, textY); textY += gp.tileSize;
		
		
		
		textX = frameX + gp.tileSize;
		textY = frameY + gp.tileSize * 9;
		g2.drawString("Back", textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX- 25, textY);
		}
	}
	
	public void drawSubWindow(int x, int y,int width, int height) {
		Color c = new Color(0,0,0,210);
		g2.setColor(c);
		g2.fillRoundRect(x,y,width,height,35,35);
		
		c = new Color(255,255,255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
	}
	
	public void drawPauseScreen() {
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
		String text = "Pause";
		int x = getXforCenteredText(text);
		int y= gp.screenHeight/2;
		
		g2.drawString(text, x, y);
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
		
		text = "Resume";
		x = getXforCenteredText(text);
		y += gp.tileSize * 3.5;
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		
		text = "Quit";
		x = getXforCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if(commandNum == 1) {
			g2.drawString(">", x-gp.tileSize, y);
		}
	}
	
	public int getXforCenteredText(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
	}
}
