package com.codecool.fiveinarow;

import java.util.Scanner;
import java.lang.Character;

public class Game implements GameInterface {

    private int[][] board;

    public Game(int nRows, int nCols) {
	this.board = new int[nRows][nCols];
    }

    public int[][] getBoard() {
        return this.board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int[] getMove(int player) {
	char FIRST_ROW_CHAR = 'A';
	int FIRST_COL_NUMBER = 1;

	int rowNumber = this.board.length;
	int colNumber = this.board[0].length;

	int firstRowCharValue = (int) FIRST_RAW_CHAR;
	int lastRowCharValue = firstRowCharValue + rowNumber - 1;
	char lastRowChar = (char) lastRowCharValue;

	String userInput;
	int[] coordinates = new int[2];

	Scanner scan = new Scanner(System.in);
	boolean validInput = false;

	while (validInput == false) {
	    try {
	    	System.out.println("Enter coordinates(row letter and column number written together e.g: A1)");
	    	System.out.println("Next player: " + player);
	    	userInput = scan.next();

		coordinates[0] = (int) Character.toUpperCase(userInput.charAt(0)) - firstRowCharValue;
	    	coordinates[1] = Integer.parseInt(userInput.substring(1)) - 1;

		if (coordinates[0] < firstRowCharValue || coordinates[0] > lastRowCharValue || coordinates[1] < FIRST_COL_NUMBER - 1 || coordinates[1] > colNumber - 1) {
		    throw new NumberFormatException("Coordinates are out of board, please try again!");
		}
		validInput = true;
	    }
	    catch(NumberFormatException e) {
	    	System.out.println("Invalid column number entered after first character, please try again!");
	    }
	}

        return coordinates;
    }

    public int[] getAiMove(int player) {
        return null;
    }

    public void mark(int player, int row, int col) {
    }

    public boolean hasWon(int player, int howMany) {
        return false;
    }

    public boolean isFull() {
        return false;
    }

    public void printBoard() {
    }

    public void printResult(int player) {
    }

    public void enableAi(int player) {
    }

    public void play(int howMany) {
    }
}
