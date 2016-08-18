package com.stevenhu;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Steven Hu on 2016-08-18.
 */
public class GameLogic {
	private Terminal terminal;
	private List<Snake> snake = new ArrayList<>();
	private Dot dot;
	private Random rand = new Random();
	
	public GameLogic(){
		terminal = TerminalFacade.createTerminal(System.in, System.out, Charset.forName("UTF8"));
		terminal.enterPrivateMode();
		terminal.setCursorVisible(false);
		
		applyBackgroundColor(Terminal.Color.GREEN);
	}
	
	public void createGame(){
		drawBoarder(Terminal.Color.RED);
		
		snake.add(new Snake(rand.nextInt(terminal.getTerminalSize().getColumns()),rand.nextInt(terminal.getTerminalSize().getRows())));
		dot = new Dot(rand.nextInt(terminal.getTerminalSize().getColumns()),rand.nextInt(terminal.getTerminalSize().getRows()));
		
	}
	
	private void updateScreen(){
		terminal.clearScreen();
		applyBackgroundColor(Terminal.Color.GREEN);
		drawBoarder(Terminal.Color.RED);

//		Draw dot
		terminal.moveCursor(dot.x, dot.y);
		terminal.applyForegroundColor(Terminal.Color.BLACK);
		terminal.putCharacter('\u25cf');

//		Draw player info


//		Draw enemies
		for (int i = 0; i < snake.size(); i++) {
			terminal.moveCursor(snake.get(i).x, snake.get(i).y);
			terminal.applyForegroundColor(102, 51, 0);
			terminal.putCharacter('\u2588');
		}
	}
	
	public void gamePlay() throws InterruptedException {
		updateScreen();
		
		Key key;
		do {
			Thread.sleep(500);
			key = terminal.readInput();
			
		}
		while (key == null);
	}
	
	private void applyBackgroundColor(Terminal.Color color){
		for (int i = 0; i < terminal.getTerminalSize().getColumns(); i++) {
			for (int j = 0; j < terminal.getTerminalSize().getRows(); j++) {
				terminal.moveCursor(i,j);
				terminal.applyBackgroundColor(color);
				terminal.putCharacter(' ');
			}
		}
		
	}
	
	private void drawBoarder(Terminal.Color color){
		for (int i = 0; i < terminal.getTerminalSize().getColumns(); i++) {
			terminal.moveCursor(i,0);
			terminal.applyForegroundColor(color);
			terminal.putCharacter('\u2588');
			terminal.moveCursor(i,terminal.getTerminalSize().getRows()-1);
			terminal.applyForegroundColor(color);
			terminal.putCharacter('\u2588');
		}
		
		for (int i = 0; i < terminal.getTerminalSize().getRows(); i++) {
			terminal.moveCursor(0,i);
			terminal.applyForegroundColor(color);
			terminal.putCharacter('\u2588');
			terminal.moveCursor(terminal.getTerminalSize().getColumns()-1, i);
			terminal.applyForegroundColor(color);
			terminal.putCharacter('\u2588');
		}
	}
}