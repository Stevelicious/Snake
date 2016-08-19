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
	private int score;
	private int snakeDirection = 1;
	
	public GameLogic() {
		terminal = TerminalFacade.createTerminal(System.in, System.out, Charset.forName("UTF8"));
		terminal.enterPrivateMode();
		terminal.setCursorVisible(false);
		
		applyBackgroundColor(Terminal.Color.GREEN);
	}
	
	public void createGame() {
		drawBoarder(Terminal.Color.RED);
		
		snake.add(new Snake(49, 14));
		dot = new Dot(rand.nextInt(terminal.getTerminalSize().getColumns()), rand.nextInt(terminal.getTerminalSize().getRows()));
		updateDot();
	}
	
	private void updateDot() {
		terminal.moveCursor(dot.x, dot.y);
		terminal.applyBackgroundColor(Terminal.Color.GREEN);
		terminal.applyForegroundColor(Terminal.Color.BLACK);
		terminal.putCharacter('\u25cf');
	}
	
	private void updateSnake() {

//		Draw player
		if (snake.size() > 1) {
			
			terminal.moveCursor(snake.get(0).x, snake.get(0).y);
			terminal.applyBackgroundColor(Terminal.Color.GREEN);
			terminal.putCharacter(' ');
			snake.remove(0);
		}
				
		for (int i = 0; i < snake.size(); i++) {
			terminal.moveCursor(snake.get(i).x, snake.get(i).y);
			terminal.applyForegroundColor(102, 51, 0);
			terminal.putCharacter('\u2588');
		}

//		Draw info
		terminal.moveCursor(0, 0);
		String s = "Score: " + score;
		for (int i = 0; i < s.length(); i++) {
			terminal.applyBackgroundColor(Terminal.Color.RED);
			terminal.applyForegroundColor(Terminal.Color.WHITE);
			terminal.putCharacter(s.charAt(i));
		}
		
	}
	
	public void gamePlay() throws InterruptedException {
		
		
		Key key = null;
		do {
			
			moveSnake();
			if (isGameOver()) {
				break;
			}
			if (ateDot()) {
				newDot();
				updateDot();
			}
			updateSnake();
			Thread.sleep(250);
			key = terminal.readInput();
		}
		while (key == null);
		
		if (key !=null) {
			changeDirection(key);
		}
	}
	
	private void newDot() {
		dot = new Dot(rand.nextInt(terminal.getTerminalSize().getColumns() - 2) + 1, rand.nextInt(terminal.getTerminalSize().getRows() - 2) + 1);
		while (dotNotValid()) {
			dot = new Dot(rand.nextInt(terminal.getTerminalSize().getColumns() - 2) + 1, rand.nextInt(terminal.getTerminalSize().getRows() - 2) + 1);
		}
		
		
	}
	
	private boolean dotNotValid() {
		for (Snake part : snake) {
			if (dot.x == part.x && dot.y == part.y) {
				return true;
			}
		}
		if (dot.x == 0 || dot.x ==terminal.getTerminalSize().getColumns()-1
				|| dot.y == 0 || dot.y == terminal.getTerminalSize().getRows()-1){
			return true;
		}
		return false;
	}
	
	
	private void applyBackgroundColor(Terminal.Color color) {
		for (int i = 0; i < terminal.getTerminalSize().getColumns(); i++) {
			for (int j = 0; j < terminal.getTerminalSize().getRows(); j++) {
				terminal.moveCursor(i, j);
				terminal.applyBackgroundColor(color);
				terminal.putCharacter(' ');
			}
		}
		
	}
	
	private void drawBoarder(Terminal.Color color) {
		for (int i = 0; i < terminal.getTerminalSize().getColumns(); i++) {
			terminal.moveCursor(i, 0);
			terminal.applyForegroundColor(color);
			terminal.putCharacter('\u2588');
			terminal.moveCursor(i, terminal.getTerminalSize().getRows() - 1);
			terminal.applyForegroundColor(color);
			terminal.putCharacter('\u2588');
		}
		
		for (int i = 0; i < terminal.getTerminalSize().getRows(); i++) {
			terminal.moveCursor(0, i);
			terminal.applyForegroundColor(color);
			terminal.putCharacter('\u2588');
			terminal.moveCursor(terminal.getTerminalSize().getColumns() - 1, i);
			terminal.applyForegroundColor(color);
			terminal.putCharacter('\u2588');
		}
	}
	
	private void changeDirection(Key key) {
		
		switch (key.getCharacter() + " " + key.getKind()) {
			case "R ArrowRight":
			case "d NormalKey":
				snakeDirection -= 1;
				snakeDirection %= 4;
				
				break;
			case "L ArrowLeft":
			case "a NormalKey":
				snakeDirection += 1;
				snakeDirection %= 4;
				
				break;
		}
	}
	
	
	private void moveSnake() {
		switch (snakeDirection) {
			case 0:
				snake.add(new Snake(snake.get(snake.size() - 1).x, snake.get(snake.size() - 1).y + 1)); //DOWN
				break;
			case 1:
			case -3:
				snake.add(new Snake(snake.get(snake.size() - 1).x + 1, snake.get(snake.size() - 1).y)); //RIGHT
				break;
			case 2:
			case -2:
				snake.add(new Snake(snake.get(snake.size() - 1).x, snake.get(snake.size() - 1).y - 1)); //UP
				break;
			case 3:
			case -1:
				snake.add(new Snake(snake.get(snake.size() - 1).x - 1, snake.get(snake.size() - 1).y)); //LEFT
				break;
		}
		
		
		
		
	}
	
	public boolean isGameOver() {
		for (int i = 1; i < snake.size() - 1; i++) {
			if (snake.get(snake.size() - 1).x == snake.get(i).x && snake.get(snake.size() - 1).y == snake.get(i).y) {
				return true;
				
			}
		}
		if (snake.get(snake.size() - 1).x == 0 || snake.get(snake.size() - 1).x == terminal.getTerminalSize().getColumns() - 1) {
			return true;
		}
		
		if (snake.get(snake.size() - 1).y == 0 || snake.get(snake.size() - 1).y == terminal.getTerminalSize().getRows() - 1) {
			return true;
		}
		return false;
	}
	
	private boolean ateDot() {
		if (snake.get(snake.size() - 1).x == dot.x && snake.get(snake.size() - 1).y == dot.y) {
			snake.add(0, new Snake(snake.get(0).x, snake.get(0).y));
			
			score++;
			return true;
		}
		return false;
	}
	
}

