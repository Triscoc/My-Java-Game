package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	
	public boolean upPressed, downPressed, leftPressed, rightPressed, shotKeyPressed=false;
	public boolean enterPressed, spacePressed;
	boolean keyHeld;
	
	GamePanel gp;
	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getExtendedKeyCode();
		
		if(gp.gameState == gp.titleState) {
			titleState(code);
		}
		
		if(gp.gameState == gp.playState) {
			playState(code);
		}
		
		if(gp.gameState == gp.pauseState) {
			pauseState(code);
		}
		
		if(gp.gameState == gp.optionsState) {
			optionsState(code);
		}
		
		if(gp.gameState == gp.gameOverState) {
			gameOverState(code);
		}
		
		
		
	}
	public void gameOverState(int code) {
		
		if(code == KeyEvent.VK_W) {
			gp.ui.commandNum++;
			gp.ui.commandNum%=2;
			gp.playSE(4);
		}
		
		if(code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			gp.ui.commandNum%=2;
			gp.playSE(4);
		}
		
		if(code ==  KeyEvent.VK_SPACE || code == KeyEvent.VK_ENTER) {
			if(gp.ui.commandNum == 0) {
				gp.gameState = gp.playState;
				gp.retry();
			}
			
			if(gp.ui.commandNum == 1) {
				gp.gameState = gp.titleState;
			}
		}
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getExtendedKeyCode();
		keyHeld = false;
		
		if(code == KeyEvent.VK_W) {
			upPressed = false;
			
		}
		if(code == KeyEvent.VK_S) {
			downPressed = false;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if(code == KeyEvent.VK_D) {
			rightPressed = false;
		}
		
		if(code == KeyEvent.VK_SPACE) {
			shotKeyPressed = false;
			spacePressed = false;
		}
		
		if(code == KeyEvent.VK_ENTER) {
			enterPressed = false;
		}
		
		
	}

	
	public void titleState(int code) {
		if(code == KeyEvent.VK_W) {
			gp.ui.commandNum += 2;
			gp.ui.commandNum%= 3;
			gp.playSE(4);
		}
		if(code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			gp.ui.commandNum%= 3;
			gp.playSE(4);
		}
		
		if(code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
			if(gp.ui.commandNum == 0) {
				gp.gameState = gp.playState;
				//gp.playMusic(0);
				gp.ui.commandNum =0;
			}
			if(gp.ui.commandNum == 1) {
				gp.gameState = gp.optionsState;
				gp.ui.commandNum = 0;
			}
			if(gp.ui.commandNum == 2) {
				System.exit(0);
			}
		}
	}
	
	public void playState(int code) {
		if(code == KeyEvent.VK_W) {
			upPressed = true;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = true;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = true;
		}
		if(code == KeyEvent.VK_D) {
			rightPressed = true;
		}
		if(code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.pauseState;
			gp.stopMusic();
			gp.ui.commandNum = 0;
			
			//System.out.println("Pressed escape , gamestate = " + gp.gameState);
		}
		
		if(code == KeyEvent.VK_SPACE) {
			shotKeyPressed = true;
		}
	}
	
	public void optionsState(int code) {
		if(code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.titleState;
		}
		
		if(code == KeyEvent.VK_W) {
			if(gp.ui.subState == 0) {
				gp.ui.commandNum += 3;
				gp.ui.commandNum%= 4;
				gp.playSE(4);
			}
			
		}
		if(code == KeyEvent.VK_S) {
			if(gp.ui.subState == 0) {
				gp.ui.commandNum++;
				gp.ui.commandNum%= 4;
				gp.playSE(4);
			}
			
		}
		
		if(code == KeyEvent.VK_A) {
			if(gp.ui.subState == 0) {
				if(gp.ui.commandNum == 0 && gp.music.volumeScale>0) {
					gp.music.volumeScale--;
					gp.music.checkVolume();
					gp.playSE(4);
				}
				if(gp.ui.commandNum == 1 && gp.SE.volumeScale>0) {
					gp.SE.volumeScale--;
					gp.SE.checkVolume();
					gp.playSE(4);
				}
			}
		}
		
		if(code == KeyEvent.VK_D) {
			if(gp.ui.subState == 0) {
				if(gp.ui.commandNum == 0 && gp.music.volumeScale < 5) {
					gp.music.volumeScale++;
					gp.music.checkVolume();
					gp.playSE(4);
				}
				if(gp.ui.commandNum == 1 && gp.SE.volumeScale < 5) {
					gp.SE.volumeScale++;
					gp.SE.checkVolume();
					gp.playSE(4);
				}
			}
		}
		
		if(code == KeyEvent.VK_SPACE || code == KeyEvent.VK_ENTER) {

			if(gp.ui.subState == 0) {
				if(gp.ui.commandNum == 2) {
					gp.ui.subState = 1;
				}

				if(gp.ui.commandNum == 3) {
					gp.gameState = gp.titleState;
					gp.ui.commandNum = 0;
				}
			}
			if(gp.ui.subState == 1) {
				if(gp.ui.commandNum ==0) {
					gp.ui.subState = 0;
				}
			}
		}
		
	}
	
	public void pauseState(int code) {
		if(code == KeyEvent.VK_ESCAPE) {
			//gp.gameState = gp.playState;
		}
		
		if(code == KeyEvent.VK_W) {
			gp.ui.commandNum++;
			gp.ui.commandNum %=2;
			gp.playSE(4);
		}
		if(code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			gp.ui.commandNum %=2;
			gp.playSE(4);
		}
		
		if(code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
			if(gp.ui.commandNum == 0) {
				gp.gameState = gp.playState;
				gp.playMusic(0);
			}
			if(gp.ui.commandNum == 1) {
				gp.gameState = gp.titleState;
				gp.playMusic(0);
			}
		}
	}
}