package com.codecool.fiveinarow;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;
import java.lang.Character;

public class Game implements GameInterface {

    private int[][] board;
    private View view;
    public int[][][] allWinOptionsInOneMove;

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
        int[] pickDirectWin = this.getAiMoveByWinOptions(player);
        if (pickDirectWin[0] == -1) {
            return pickDirectWin;
        }

        int enemy = player == 1 ? 2 : 1;
        int[] preventDirectLose = this.getAiMoveByWinOptions(enemy);
        if (preventDirectLose[0] == -1) {
            return preventDirectLose;
        }

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
        int counterForwardDiagonal = 1;
        int counterBackwardDiagonal = 1;
        int counterVertical = 1;
        int counterHorizontal = 1;
        for (int i = 0; i < board.length; i++) {
            if (board[i][i] == player) {
                counterBackwardDiagonal++;

                if (counterBackwardDiagonal == howMany) {
                    return true;
                }

                if (board[i][(board.length - 1)] == player) {
                    counterForwardDiagonal++;
                }
                if (counterForwardDiagonal == howMany) {
                    return true;
                }
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
        }
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
    }

    public void play(int howMany) {
        this.allWinOptionsInOneMove = getWinningMoveOptions(howMany);
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
    public int[][][] getWinningMoveOptions(int howMany) {
        int[][][] horizontalOptions;
        int countOfHorizontalOptions = this.board.length * (this.board[0].length - (howMany - 1));
        horizontalOptions = new int[countOfHorizontalOptions][howMany - 1][2];

        int horizontalIndexCounter = 0;
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j <= this.board[0].length - (howMany - 1); j++) {
                for (int k = 0; k < howMany - 1; k++) {
                    horizontalOptions[horizontalIndexCounter][k][0] = i;
                    horizontalOptions[horizontalIndexCounter][k][1] = j + k;
                }
                horizontalIndexCounter++;
            }
        }

        int[][][] verticalOptions;
        int countOfVerticalOptions = this.board[0].length * (this.board.length - (howMany - 1));
        verticalOptions = new int[countOfVerticalOptions][howMany - 1][2];

        int  verticalIndexCounter = 0;
        for (int j = 0; j < this.board[0].length; j++) {
            for (int i = 0; i <= this.board.length - (howMany - 1); j++) {
                for (int k = 0; k < howMany - 1; k++) {
                    verticalOptions[verticalIndexCounter][k][0] = i + k;
                    verticalOptions[verticalIndexCounter][k][1] = j;
                }
                verticalIndexCounter++;
            }
        }

        int[][][] upDownDiagonalOptions;
        int countOfOneWayDiagonalOptions = (this.board.length - (howMany - 1)) * (this.board[0].length - (howMany - 1));
        upDownDiagonalOptions = new int[countOfOneWayDiagonalOptions][howMany - 1][2];

        int upDownDiagonalIndexCounter = 0;
        for (int i = 0;  i <= this.board.length - (howMany - 1); i++) {
            for(int j = 0; j <= this.board.length - (howMany - 1); j++) {
                for (int k = 0; k < howMany - 1; k++) {
                    upDownDiagonalOptions[upDownDiagonalIndexCounter][k][0] = i + k;
                    upDownDiagonalOptions[upDownDiagonalIndexCounter][k][1] = j + k;
                }
                upDownDiagonalIndexCounter++;
            }
        }

        int[][][] downUpDiagonalOptions = new int[countOfOneWayDiagonalOptions][howMany - 1][2];

        int downUpDiagonalIndexCounter = 0;
        for (int i = this.board.length; i >= howMany - 1; i--) {
            for (int j = 0; j <= this.board[0].length - (howMany - 1); j++) {
                for (int k = 0; k < howMany - 1; k++) {
                    downUpDiagonalOptions[downUpDiagonalIndexCounter][k][0] = i - k;
                    downUpDiagonalOptions[downUpDiagonalIndexCounter][k][1] = j + k;
                }
                downUpDiagonalIndexCounter++;
            }
        }
        int[][][] allWinningOptions = new int[horizontalOptions.length + verticalOptions.length + upDownDiagonalOptions.length + downUpDiagonalOptions.length][howMany - 1][2];
        int position = 0;

        for (int[][] element : horizontalOptions) {
            allWinningOptions[position] = element;
            position++;
        }
        for (int[][] element : verticalOptions) {
            allWinningOptions[position] = element;
            position++;
        }
        for (int[][] element : upDownDiagonalOptions) {
            allWinningOptions[position] = element;
            position++;
        }
        for (int[][] element : downUpDiagonalOptions) {
            allWinningOptions[position] = element;
            position++;
        }
        return allWinningOptions;
    }

    public int[] getAiMoveByWinOptions(int player) {
        int[] aiMove = new int[2];
        for (int[][] element : this.allWinOptionsInOneMove) {
            int first = this.board[element[0][0]][element[0][1]];
            boolean flag = true;
            if (first != player) {
                flag = false;
                continue;
            }
            else {
                for (int i = 1; i < element.length && flag; i++) {
                    if (this.board[element[1][0]][element[1][1]] != player) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    int horizontalStep = element[1][0] - element[0][0];
                    int verticalStep = element[0][1] - element[0][0];
                    int nextFieldRowAtStart = element[0][0] - horizontalStep;
                    int nextFieldColAtStart = element[0][1] - verticalStep;
                    int nextFieldRowAtEnd = element[element.length - 1][0] + horizontalStep;
                    int nextFieldColAtEnd = element[element.length - 1][1] + verticalStep;

                    if ((nextFieldRowAtStart >= 0 || nextFieldRowAtStart < this.board.length) && nextFieldColAtStart >= 0 && this.board[nextFieldRowAtStart][nextFieldColAtStart] == 0) {
                        aiMove[0] = nextFieldRowAtStart;
                        aiMove[1] = nextFieldColAtStart;
                        return aiMove;
                    }
                    else if ((nextFieldRowAtEnd >= 0 || nextFieldRowAtEnd < this.board.length) && nextFieldColAtEnd < this.board[0].length && this.board[nextFieldRowAtEnd][nextFieldColAtEnd] == 0) {
                        aiMove[0] = nextFieldRowAtEnd;
                        aiMove[1] = nextFieldColAtEnd;
                        return aiMove;
                    }
                }
            }
        }
        aiMove[0] = -1;
        aiMove[1] = -1;
        return aiMove;
    }
}
