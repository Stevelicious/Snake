package com.stevenhu;

public class Main {
	
	public static void main(String[] args) throws InterruptedException {
		
		GameLogic game = new GameLogic();
		while (true) {
						
			game.createGame();
			
			while (!game.isGameOver()) {
				game.gamePlay();
			}
			
			System.out.println("GAME OVER");
			Thread.sleep(1000);
		}
	}
}
