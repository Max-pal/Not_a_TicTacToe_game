package com.codecool.fiveinarow;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;
import java.lang.Character;

public class Game implements GameInterface {

    private int[][] board;
    private View view;
    private boolean[] aiStates = {false, false};

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
                System.out.println("Enter coordinates(row letter and column number written together e.g: A1)");
                System.out.println("Next player: " + player);
                String exitCommand = scan.findInLine("quit");

                if (exitCommand != null) {
                    coordinates[0] = -1;
                    coordinates[1] = -1;
                    System.exit(0);
                    return coordinates;
                }

                userInput = scan.next();
                scan.nextLine();
                int firstCharInputValue = (int) Character.toUpperCase(userInput.charAt(0));

                coordinates[0] = firstCharInputValue - firstRowCharValue;
                coordinates[1] = Integer.parseInt(userInput.substring(1)) - 1;

                if (coordinates[0] < 0 || coordinates[0] > lastRowCharValue - firstRowCharValue || coordinates[1] < FIRST_COL_NUMBER - 1 || coordinates[1] > colNumber - 1)
                    throw new ArrayIndexOutOfBoundsException("Coordinates are out of board");

                if (this.board[coordinates[0]][coordinates[1]] != 0)
                    throw new ArithmeticException("The field of entered coordinates are occupied");

                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid column number entered after first character, please try again!");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Coordinates are out of board size, please try again!");
            } catch (ArithmeticException e) {
                System.out.println("The field of entered coordinates is occupied, please try again!");
            }
        }
        return coordinates;
    }

    public int[] getAiMove(int player) {
        int[][] allValidMoves;
        int emptyCellCounter = 0;

        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {
                if (this.board[i][j] == 0) {
                    emptyCellCounter++;
                }
            }
        }
        allValidMoves = new int[emptyCellCounter][2];

        int indexCounter = 0;
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {
                if (this.board[i][j] == 0) {
                    allValidMoves[indexCounter][0] = i;
                    allValidMoves[indexCounter][1] = j;
                    indexCounter++;
                }
            }
        }
        Random shuffler = new Random();
        int[] aiMove = allValidMoves[shuffler.nextInt(emptyCellCounter)];

        return aiMove;
    }

    public void mark(int player, int row, int col) {
        this.board[row][col] = player;
    }

    public boolean hasWon(int player, int howMany) {
        int rowCount = board.length;
        int columnCount = board[0].length;

        int counterForwardDiagonal = 1;
        int counterBackwardDiagonal = 1;
        int counterVertical = 1;
        int counterHorizontal = 1;

        for (int r = 0; r < rowCount; r++) {
            for (int row = r, col = 0; row >= 0 && col < columnCount; row--, col++) {
                if ((board[row][col] != player) || (board[row][col] != player)) {
                    counterForwardDiagonal = 0;
                } else {
                    counterForwardDiagonal++;
                }
                if (counterForwardDiagonal == howMany) {
                    return true;
                }
            }
        }

        for (int c = 1; c < columnCount; c++) {
            for (int row = rowCount - 1, col = c; row >= 0 && col < columnCount; row--, col++) {
                if ((board[row][col] != player) || (board[row][col] != player)) {
                    counterBackwardDiagonal = 0;
                } else {
                    counterBackwardDiagonal++;
                }
                if (counterBackwardDiagonal == howMany) {
                    return true;
                }
            }
        }

        for (int i = 0; i < board.length; i++) {
            // if (board[i][i] == player) {
            //     counterBackwardDiagonal++;
            //
            //     if (counterBackwardDiagonal == howMany) {
            //         return true;
            //     }
            //
            //     if (board[i][(board.length - 1)] == player) {
            //         counterForwardDiagonal++;
            //     }
            //     if (counterForwardDiagonal == howMany) {
            //         return true;
            //     }
            for (int j = 0; j < board[0].length; j++) {
                if ((board[i][(board.length - 1)] != player) || (board[i][(board.length - 1)] != 0)) {
                    counterVertical = 0;
                } else {
                    counterVertical++;
                }
                if (counterVertical == howMany) {
                    return true;
                }

                if ((board[i][(board.length - 1)] != player) || (board[i][(board.length - 1)] != 0)) {
                    counterHorizontal = 0;
                } else {
                    counterHorizontal++;
                }

                if (counterHorizontal == howMany) {
                    return true;
                }
            }
        }
        return false;
    }


    public boolean isFull() {
        for (int row = 0; row < view.nRows; row++)
            for (int col = 0; col < view.nCols; col++)
                if (this.board[row][col] == 0) return false;
        return true;
    }

    public void printBoard() {
        String printableBoard = view.renderBoard(this.board);
        System.out.println(View.clearSequence + printableBoard);
    }

    public void printResult(int player) {
        char winnerSymbol = View.cellSymbols[player];
        char tieSymbol = '.';
        System.out.println("Game Over");
        if (winnerSymbol == tieSymbol)
            System.out.println("It's a tie!");
        else
            System.out.println(winnerSymbol + " won!");
    }

    public void enableAi(int player) {
        this.aiStates[player - 1] = true;
    }

    public void play(int howMany) {
        int player = 1;
        int[] move = new int[2];

        while (true) {
            printBoard();
            move = getMove(player);
            mark(player, move[0], move[1]);
            if (hasWon(player, howMany)) {
                printResult(player);
                break;
            } else if (isFull()) {
                printResult(0);
                break;
            } else player = player == 1 ? 2 : 1;
        }
    }
}
