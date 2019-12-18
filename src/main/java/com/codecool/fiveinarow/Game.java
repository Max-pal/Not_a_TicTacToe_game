package com.codecool.fiveinarow;
import java.util.Arrays;

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
        return null;
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
