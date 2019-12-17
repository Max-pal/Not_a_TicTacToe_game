package com.codecool.fiveinarow;
import java.util.Arrays;

public class Game implements GameInterface {

    private int[][] board;
    private final char[] cellSymbols = {'.', 'X', 'O'};
    private char[] columnLetters;

    public Game(int nRows, int nCols) {
		this.board = new int[nRows][nCols];
		initColumnLetters(nCols);
    }

    private void initColumnLetters(int nCols) {
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();
        this.columnLetters = new char[nCols];
        for (int col = 0; col < nCols; col++)
            this.columnLetters[col] = alphabet[col];
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

    public void printBoard() {
        for (int row = 0; row < this.board.length; row++) {

            for (int col = 0; col < this.board[row].length; col++) {
                int cellValue = this.board[row][col];
                System.out.print(String.format("%c ", this.cellSymbols[cellValue]));
            }
            System.out.println();
        }
    }

    public void printResult(int player) {
    }

    public void enableAi(int player) {
    }

    public void play(int howMany) {
        printBoard();
    }
}
