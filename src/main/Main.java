package main;

import java.util.Scanner;

import javax.swing.JFrame;


public class Main {
	public final static String username = "admin";
	public final static String password = "123";
	
	public static void signIn() {
		Scanner scn = new Scanner(System.in);
		System.out.println("Please enter username: ");
		String a = scn.next();
		System.out.println("Please enter password: ");
		String b = scn.next();	
		while(!a.equals(username) || !b.equals(password)) {
			System.out.println("Please reenter: ");
			System.out.println("Please enter username: ");
			a = scn.next();
			System.out.println("Please enter password: ");
			b = scn.next();	
		}
	}
	
	public static void main(String[] args) {
		
		signIn();
		
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Pixel Barn Brawl");
		
		GamePanel gamePanel = new GamePanel();
		
		
		window.add(gamePanel);
		
		window.pack();
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		gamePanel.setupGame();
		gamePanel.startGameThread();
		
		
		
	}
}
