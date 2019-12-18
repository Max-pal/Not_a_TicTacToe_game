package com.codecool.fiveinarow;
import java.util.Arrays;

import java.util.Scanner;
import java.lang.Character;

public class Game implements GameInterface {

    private int[][] board;
    private View view;

    public Game(int nRows, int nCols) {
	    this.board = new int[nRows][nCols];
        this.view = new View(nRows, nCols);
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

        int firstRowCharValue = (int) FIRST_ROW_CHAR;
        int lastRowCharValue = firstRowCharValue + rowNumber - 1;
        char lastRowChar = (char) lastRowCharValue;

        String userInput;
        int[] coordinates = new int[2];

        Scanner scan = new Scanner(System.in);
        boolean validInput = false;

	    while (validInput == false) {
	        try {
	    	    System.out.println("Enter coordinates (row letter and column number written together e.g. 'A1')");
	    	    System.out.println("Next player: " + player);
	    	    userInput = scan.next();
                int firstCharInputValue = (int) Character.toUpperCase(userInput.charAt(0));

		        coordinates[0] = firstCharInputValue - firstRowCharValue;
	    	    coordinates[1] = Integer.parseInt(userInput.substring(1)) - 1;

		        if (coordinates[0] < 0 || coordinates[0] > lastRowCharValue - firstRowCharValue || coordinates[1] < FIRST_COL_NUMBER - 1 || coordinates[1] > colNumber - 1)
		            throw new ArrayIndexOutOfBoundsException("Coordinates are out of board");

		        if (this.board[coordinates[0]][coordinates[1]] != 0)
		            throw new ArithmeticException("The field of entered coordinates are occupied");

		        validInput = true;
	        }

	        catch(NumberFormatException e) {
	    	    System.out.println("Invalid column number entered after first character, please try again!");
	        }

	        catch(ArrayIndexOutOfBoundsException e) {
		        System.out.println("Coordinates are out of board size, please try again!");
	        }

	        catch(ArithmeticException e) {
    		    System.out.println("The field of entered coordinates is occupied, please try again!");
	        }
	    }
        return coordinates;
    }

    public int[] getAiMove(int player) {
        return null;
    }

    public void mark(int player, int row, int col) {
        this.board[row][col] = player;
    }

    public boolean hasWon(int player, int howMany) {
        return false;
    }

    public boolean isFull() {
        for(int row = 0; row < view.nRows; row++)
            for(int col = 0; col < view.nCols; col++)
                if (this.board[row][col] == 0) return false;
        return true;
    }

    public void printBoard() {
        String printableBoard = view.renderBoard(this.board);
        System.out.println(printableBoard);
    }

    public void printResult(int player) {
    }

    public void enableAi(int player) {
    }

    public void play(int howMany) {
    }
}
