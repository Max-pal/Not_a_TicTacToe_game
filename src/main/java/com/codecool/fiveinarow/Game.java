package com.codecool.fiveinarow;
import java.util.Arrays;

public class Game /* implements GameInterface */ {

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
        return null;
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

	public static int[][] getTestBoard(int row, int col) {
		int[][] board = new int[row][col];
		for (int r = 0; r < row; r++) {
			for (int c = 0; c < col; c++) {
				board[r][c] = 0;
			}
		}
		return board;
	}

    public static void printBoard() {
    	int[][] board = getTestBoard(7, 11);
    	for (int[] row : board)
    		System.out.println(Arrays.toString(row));
    }

    public void printResult(int player) {
    }

    public void enableAi(int player) {
    }

    public void play(int howMany) {
    }
}
