package com.stevenhu;

import javax.swing.*;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) throws InterruptedException {
		String[] choices = {"Lätt", "Medel", "Svår"};
		String s = (String) JOptionPane.showInputDialog(null,"Svårighetsgrad:", "Välj Svårighetsgrad", JOptionPane.QUESTION_MESSAGE,null,choices,choices[0]);
		System.out.println(s);
		
		
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
