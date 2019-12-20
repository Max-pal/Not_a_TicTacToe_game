package com.codecool.fiveinarow;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.lang.Character;

public class Game implements GameInterface {

    private int[][] board;
    private View view;
    public int[][][] allWinOptionsInOneMove;
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

        boolean validInput = false;

        while (validInput == false) {
            try {
                System.out.println("Enter coordinates(row letter and column number written together e.g: A1)");
                System.out.println("Next player: " + player);
                String exitCommand = FiveInARow.scanner.findInLine("quit");

                if (exitCommand != null) {
                    coordinates[0] = -1;
                    coordinates[1] = -1;
                    FiveInARow.scanner.close();
                    System.exit(0);
                    return coordinates;
                }

                userInput = FiveInARow.scanner.next();
                FiveInARow.scanner.nextLine();
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
        int[] pickDirectWin = this.getAiMoveByWinOptions(player);
        if (pickDirectWin[0] != -1) {
            try {TimeUnit.SECONDS.sleep(1);}
            catch (InterruptedException e) {}
            return pickDirectWin;
        }

        int enemy = player == 1 ? 2 : 1;
        int[] preventDirectLose = this.getAiMoveByWinOptions(enemy);
        if (preventDirectLose[0] != -1) {
            try {TimeUnit.SECONDS.sleep(1);}
            catch (InterruptedException e) {}
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

        try {TimeUnit.SECONDS.sleep(1);}
        catch (InterruptedException e) {}
        return aiMove;
    }

    public void mark(int player, int row, int col) {
        this.board[row][col] = player;
    }

    public boolean hasWon(int player, int howMany) {
        int rowCount = board.length;
        int columnCount = board[0].length;

        int counterForwardDiagonal = 0;
        int counterBackwardDiagonal = 0;
        int counterVertical = 0;
        int counterHorizontal = 0;
        for (int topRow = rowCount - howMany; topRow > 0; topRow--) {
            for (
                    int row = topRow, backwardCol = 0, forwardCol = columnCount - 1;
                    row < rowCount && backwardCol < columnCount;
                    row++, backwardCol++, forwardCol--
            ) {
                counterForwardDiagonal = (board[row][forwardCol] == player) ? counterForwardDiagonal + 1 : 0;
                counterBackwardDiagonal = (board[row][backwardCol] == player) ? counterBackwardDiagonal + 1 : 0;
                if (counterForwardDiagonal == howMany || counterBackwardDiagonal == howMany) return true;
            }
            counterForwardDiagonal = counterBackwardDiagonal = 0;
        }

        for (
                int topBackwardCol = 0, topForwardCol = columnCount - 1;
                topBackwardCol <= columnCount - howMany;
                topBackwardCol++, topForwardCol--
        ) {
            for (
                    int row = 0, backwardCol = topBackwardCol, forwardCol = topForwardCol;
                    row < rowCount && backwardCol < columnCount;
                    row++, backwardCol++, forwardCol--
            ) {
                counterForwardDiagonal = (board[row][forwardCol] == player) ? counterForwardDiagonal + 1 : 0;
                counterBackwardDiagonal = (board[row][backwardCol] == player) ? counterBackwardDiagonal + 1 : 0;
                if (counterForwardDiagonal == howMany || counterBackwardDiagonal == howMany) return true;
            }
            counterForwardDiagonal = counterBackwardDiagonal = 0;
        }

        for (int i = 0; i < columnCount; i++) {
            for (int j = 0; j < rowCount; j++) {
                counterVertical = (board[j][i] == player) ? counterVertical + 1 : 0;
                if (counterVertical == howMany) {
                    return true;
                }
            }
            counterVertical = 0;
        }

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                counterHorizontal = (board[i][j] == player) ? counterHorizontal + 1 : 0;
                if (counterHorizontal == howMany) {
                    return true;
                }
            }
            counterHorizontal = 0;
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
        printBoard();
        System.out.println("Game Over");
        if (winnerSymbol == tieSymbol)
            System.out.println("It's a tie!");
        else
            System.out.println(winnerSymbol + " won!");
    }

    public void enableAi(int player) {
        this.aiStates[player - 1] = true;
    }

    private void waitForEnter() {
        System.out.println("\n(Enter) Main menu");
        FiveInARow.scanner.nextLine();
    }

    public void play(int howMany) {
        this.allWinOptionsInOneMove = getWinningMoveOptions(howMany);
        int player = 1;
        int[] move = new int[2];

        while (true) {
            printBoard();
            if (this.aiStates[player - 1])
                move = getAiMove(player);
            else
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
        waitForEnter();
    }
    public int[][][] getWinningMoveOptions(int howMany) {
        int[][][] horizontalOptions;
        int countOfHorizontalOptions = this.board.length * (this.board[0].length - (howMany - 1));
        horizontalOptions = new int[countOfHorizontalOptions][howMany][2];

        int horizontalIndexCounter = 0;
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j <= this.board[0].length - howMany; j++) {
                for (int k = 0; k < howMany; k++) {
                    horizontalOptions[horizontalIndexCounter][k][0] = i;
                    horizontalOptions[horizontalIndexCounter][k][1] = j + k;
                }
                horizontalIndexCounter++;
            }
        }

        int[][][] verticalOptions;
        int countOfVerticalOptions = this.board[0].length * (this.board.length - (howMany - 1));
        verticalOptions = new int[countOfVerticalOptions][howMany][2];

        int  verticalIndexCounter = 0;
        for (int j = 0; j < this.board[0].length; j++) {
            for (int i = 0; i <= this.board.length - howMany; i++) {
                for (int k = 0; k < howMany; k++) {
                    verticalOptions[verticalIndexCounter][k][0] = i + k;
                    verticalOptions[verticalIndexCounter][k][1] = j;
                }
                verticalIndexCounter++;
            }
        }

        int[][][] upDownDiagonalOptions;
        int countOfOneWayDiagonalOptions = (this.board.length - (howMany - 1)) * (this.board[0].length - (howMany - 1));
        upDownDiagonalOptions = new int[countOfOneWayDiagonalOptions][howMany][2];

        int upDownDiagonalIndexCounter = 0;
        for (int i = 0;  i <= this.board.length - howMany; i++) {
            for(int j = 0; j <= this.board.length - howMany; j++) {
                for (int k = 0; k < howMany; k++) {
                    upDownDiagonalOptions[upDownDiagonalIndexCounter][k][0] = i + k;
                    upDownDiagonalOptions[upDownDiagonalIndexCounter][k][1] = j + k;
                }
                upDownDiagonalIndexCounter++;
            }
        }

        int[][][] downUpDiagonalOptions = new int[countOfOneWayDiagonalOptions][howMany - 1][2];

        int downUpDiagonalIndexCounter = 0;
        for (int i = this.board.length - 1; i >= howMany - 1; i--) {
            for (int j = 0; j <= this.board[0].length - howMany; j++) {
                for (int k = 0; k < howMany - 1; k++) {
                    downUpDiagonalOptions[downUpDiagonalIndexCounter][k][0] = i - k;
                    downUpDiagonalOptions[downUpDiagonalIndexCounter][k][1] = j + k;
                }
                downUpDiagonalIndexCounter++;
            }
        }
        int[][][] allWinningOptions = new int[horizontalOptions.length + verticalOptions.length + upDownDiagonalOptions.length + downUpDiagonalOptions.length][howMany][2];
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
            int playerCount = 0;
            int emptyCount = 0;
            for (int i = 0; i < element.length; i++) {
                if (this.board[element[i][0]][element[i][1]] == player)
                    playerCount++;
                else if(this.board[element[i][0]][element[i][1]] == 0)
                    emptyCount++;
            }
            if (playerCount == element.length - 1 && emptyCount == 1) {
                for (int j = 0; j < element.length; j++) {
                    if (this.board[element[j][0]][element[j][1]] == 0) {
                        aiMove[0] = element[j][0];
                        aiMove[1] = element[j][1];
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
