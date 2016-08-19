package com.stevenhu;

public class Main {
	
	public static void main(String[] args) throws InterruptedException {
		
		GameLogic game = new GameLogic();
		while (true) {
						
			game.createGame();
			
			while (!game.isGameOver()) {
				game.gamePlay();
			}
			
			Thread.sleep(5000);
		}
	}
}
