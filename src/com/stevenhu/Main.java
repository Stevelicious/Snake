package com.stevenhu;

public class Main {
	
	public static void main(String[] args) throws InterruptedException {
		GameLogic game = new GameLogic();
		game.createGame();
		
		while (true) {
			game.gamePlay();
		}
	}
}
