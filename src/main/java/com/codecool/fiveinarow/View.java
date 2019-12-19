package com.codecool.fiveinarow;

public class View {

    private final static char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    public final static char[] cellSymbols = {'.', 'X', 'O'};
    private final static byte cellPadding = 4;
    public final static String clearSequence = "\033[H\033[2J";
    public final int nRows;
    public final int nCols;
    private final String columnHeaders;

    public View(int nRows, int nCols) {
        this.nRows = nRows;
        this.nCols = nCols;
        this.columnHeaders = renderColumnHeaders();
    }

    private String renderColumnHeaders() {
        StringBuilder columnHeaders = new StringBuilder("\n ");
        for (int col = 0; col < this.nCols; col++)
            columnHeaders.append(String.format("%" + cellPadding + "d", col + 1));
        return columnHeaders.toString();
    }

    public static void clearConsole() {
        System.out.print(clearSequence);
    }

    public String renderBoard(int[][] board) {
        StringBuilder printable = new StringBuilder(this.columnHeaders);

        for (int row = 0; row < this.nRows; row++) {
            printable.append("\n\n").append(alphabet[row]);

            for (int col = 0; col < this.nCols; col++) {
                int cellValue = board[row][col];
                printable.append(String.format("%" + cellPadding + "c", cellSymbols[cellValue]));
            }

        }
        return printable.append("\n").toString();
    }
}
