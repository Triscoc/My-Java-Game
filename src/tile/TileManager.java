package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
	
	private int cameraY = 0;
	public int getCameraY() {
		return cameraY;
	}
	
	public void setCameraY(int cameraY) {
		this.cameraY = cameraY;
	}
	
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[30];
		getTileImage();
		
		mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];
		loadMap("/maps/map01.txt");
	}
	
	public void getTileImage() {
		try {
			
			
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Grass_Up_Left.png"));
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Grass_Up.png"));
			
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Grass_Up_Right.png"));
			
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Grass_Left.png"));
			tile[3].collision = true;
			
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Grass_Middle.png"));
			
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Grass_Right.png"));
			tile[5].collision = true;
			
			tile[6] = new Tile();
			tile[6].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Grass_Down_Left.png"));
			
			tile[7] = new Tile();
			tile[7].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Grass_Down.png"));
			
			tile[8] = new Tile();
			tile[8].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Grass_Down_Right.png"));
			
			tile[9] = new Tile();
			tile[9].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Grass_Middle_2.png"));
			
			tile[10] = new Tile();
			tile[10].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Grass_Middle_3.png"));
			
			tile[11] = new Tile();
			tile[11].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Water.png"));
					
			tile[12] = new Tile();
			tile[12].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Fence_Up.png"));
			
			tile[13] = new Tile();
			tile[13].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Fence_Mid.png"));
			tile[13].collision = true;
			
			tile[14] = new Tile();
			tile[14].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Fence_Down.png"));
			
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadMap(String filePath) {
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while(col < gp.maxScreenCol && row < gp.maxScreenRow) {
				String line = br.readLine();
				
				while(col < gp.maxScreenCol) {
					String numbers[] = line.split(" ");
					
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[col][row] = num;
					col++;
				}
				
				if(col == gp.maxScreenCol) {
					col = 0;
					row++;
				}
			}
			br.close();
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getTile(int col, int row) {
		return mapTileNum[col % gp.maxScreenCol][row % gp.maxScreenRow];
	}
	
	
	public void draw(Graphics2D g2) {
	    int col = 0;
	    int row = 0;
	    int x = 0;
	    int y = 0;

	    // First pass: Draw the map starting from cameraY
	    while(row < gp.maxScreenRow) {
	        col = 0;
	        x = 0;  // Reset x for each new row

	        while(col < gp.maxScreenCol) {
	            int tileNum = getTile(col, row);  // Get the wrapped tile number
	            g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);

	            col++;
	            x += gp.tileSize;
	        }

	        row++;
	        y += gp.tileSize;

	        // If the camera has moved past the screen height, draw the next "row" of the map on top
	        /*if (y >= gp.screenHeight) {
	            y = cameraY;  // Reset y to the top (cameraY)
	        }
	        */
	    }

	    // Second pass: Draw the map from cameraY - screenHeight to ensure continuous scrolling
	    /*col = 0;
	    row = 0;
	    x = 0;
	    y = cameraY - gp.screenHeight;  // Start drawing from above the screen

	    while(row < gp.maxScreenRow) {
	        col = 0;
	        x = 0;

	        while(col < gp.maxScreenCol) {
	            int tileNum = getTile(col, row); 
	            g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);

	            col++;
	            x += gp.tileSize;
	        }

	        row++;
	        y += gp.tileSize;
	    }
*/

	    col = 0;
	    row = 0;
	    x = 0;
	    y = 0;

	    while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
	        if (col == 0 || col == gp.maxScreenCol - 1) {
	            g2.drawImage(tile[13].image, x, y, gp.tileSize, gp.tileSize, null);  // Fence
	        }
	        col++;
	        x += gp.tileSize;

	        if (col == gp.maxScreenCol) {
	            col = 0;
	            x = 0;
	            row++;
	            y += gp.tileSize;
	        }
	    }
/*
	 	col = 0;
	    row = 0;
	    x = 0;
	    y = cameraY - gp.screenHeight;

	    while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
	        if (col == 0 || col == gp.maxScreenCol - 1) {
	            g2.drawImage(tile[13].image, x, y, gp.tileSize, gp.tileSize, null);  // Fence
	        }
	        col++;
	        x += gp.tileSize;

	        if (col == gp.maxScreenCol) {
	            col = 0;
	            x = 0;
	            row++;
	            y += gp.tileSize;
	        }
	    }
	}
*/
	}
}

