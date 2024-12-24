package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	
	final int originalTileSize = 16;
	final int scale = 3;
	public final int tileSize = scale * originalTileSize;
	
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	
	public final int screenWidth = maxScreenCol * tileSize;
	public final int screenHeight = maxScreenRow * tileSize;
	
	int FPS = 60;
	
	TileManager tileM = new TileManager(this);
	Thread gameThread;
	public CollisionChecker cChecker = new CollisionChecker(this);
	public KeyHandler keyH = new KeyHandler(this);
	public Player player = new Player(this, keyH);
	public AssetSetter aSetter = new AssetSetter(this);
	
	Sound music = new Sound();
	Sound SE = new Sound();
	
	public UI ui = new UI(this);
	
	public Entity obj[] = new Entity[20];
	public Entity monster[] = new Entity[20];
	ArrayList<Entity> entityList = new ArrayList<>();
	public ArrayList<Entity> projectileList = new ArrayList<>();
	
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int optionsState = 3;
	public final int gameOverState = 4;
	
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;
	
	public int spawnCounter =0;
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		
	}
	
	public void setupGame() {
		aSetter.setObject();
		aSetter.setMonster();
		playMusic(0);
		//stopMusic();
		gameState = titleState;
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS;
		double nextDrawTime = System.nanoTime() + drawInterval;
		
		while(gameThread != null) {
			
			update();
			
			repaint();
			
			
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime /= 1000000;
				
				if(remainingTime<0) {
					remainingTime = 0;
				}
				
				Thread.sleep((long) remainingTime);
				
				nextDrawTime += drawInterval;
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void retry() {
		player.setDefaultPositions();
		ui.newGame();
		player.restoreLife();
		aSetter.setMonster();
		aSetter.setObject();
	}

	public void update() {
		//System.out.println("Gamestate: "+ gameState);
		if(gameState == playState) {
			player.update();
			spawnCounter++;
			
			if(spawnCounter > 60) {
				int x = (new Random().nextInt(12)+3) * tileSize;
				int y = (new Random().nextInt(3) + 1) * tileSize;
				aSetter.spawnChicken(x,y);
				spawnCounter = 0;
			}
			
			for(int i = 0; i< monster.length; i++) {
				if(monster[i] != null) {
					if(monster[i].alive == true && monster[i].dying == false) {
						monster[i].update();
					}
					if(monster[i].alive == false) {
						monster[i].checkDrop();
						monster[i] = null;
						ui.point++;
					}
				}
			}
			
			for(int i =0; i<projectileList.size(); i++) {
				if(projectileList.get(i)!= null) {
					if(projectileList.get(i).alive == true) {
						projectileList.get(i).update();
					}
					if(projectileList.get(i).alive == false) {
						projectileList.remove(i);
					}
				}
			}
		}
		
		if(gameState == pauseState) {
			
		}
		
	}
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		
		if(gameState == titleState) {
			ui.draw(g2);
		}else if(gameState == optionsState) {
			ui.draw(g2);
		}
		else{
			tileM.draw(g2);
			
			//entityList.add(player);
			
			for(int i = 0; i< obj.length; i++) {
				if(obj[i]!=null) {
					entityList.add(obj[i]);
				}
			}
			
			for(int i =0; i<monster.length; i++) {
				if(monster[i]!=null) {
					entityList.add(monster[i]);
				}
			}
			
			for(int i =0; i<projectileList.size(); i++) {
				if(projectileList.get(i)!=null) {
					entityList.add(projectileList.get(i));
				}
			}
			
			Collections.sort(entityList, new Comparator<Entity>() {

				@Override
				public int compare(Entity e1, Entity e2){
					int result = Integer.compare(e1.worldY, e2.worldY);
					return result;
					// TODO Auto-generated method stub
				}
			
			});
			
			for(int i = 0; i< entityList.size(); i++) {
				entityList.get(i).draw(g2);
			}
			
			entityList.clear();
			
			
			
			ui.draw(g2);
			
			player.draw(g2, tileM.getCameraY());
			g2.dispose();
		}
		
	}
	
	
	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}
	
	public void stopMusic() {
		music.stop();
	}
	
	public void playSE(int i) {
		SE.setFile(i);
		SE.play();
	}
}

