package com.codecool.fiveinarow;

public class Game implements GameInterface {

    private int[][] board;

    public Game(int nRows, int nCols) {
    }

    public int[][] getBoard() {
        return board;
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
        String currentPlayerMark;
        currentPlayerMark = (player == 1) ? "X" : "O";
        int counterDiagonal = 1;
        int counterVertical = 1;
        int counterHorizontal = 1;
        for(int i = 0; i < board.lenght; i++) {
            for(int j = 0; j < board.lenght; j++)
                if(board[j][i].equals(currentPlayerMark){
                    counterVertical++;

                if (counterVertical == howMany) {
                    return true;
                }

                if(board[i][j].equals(currentPlayerMark))
                    counterHorizontal++

                if (counterHorizontal == howMany) {
                    return true;
                }
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
