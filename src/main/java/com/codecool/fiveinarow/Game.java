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
        int counterForwardDiagonal = 1;
        int counterBackwardDiagonal = 1;
        int counterVertical = 1;
        int counterHorizontal = 1;
        for(int i = 0; i < board.length; i++) {
            if (board[i][i] == player) {
                counterBackwardDiagonal++;

                if (counterBackwardDiagonal == howMany) {
                    return true;
                }
            if (board[i][(board.length - 1)] == player) {
                counterForwardDiagonal++;
                }
                if (counterForwardDiagonal == howMany){
                    return true;
                }
            for(int j = 0; j < board[0].length; j++) {
                if(board[j][i] == player){
                counterVertical++;
            }
                if (counterVertical == howMany) {
                    return true;
                }

                if(board[i][j] == player)
                    counterHorizontal++;

                if (counterHorizontal == howMany) {
                    return true;
                }
            }
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
