package com.codecool.fiveinarow;

public class View {

    private final static char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();
    private final char[] rowLetters;
    private final static char[] cellSymbols = {'.', 'X', 'O'};
    private final static byte cellPadding = 4;
    public final int nRows;
    public final int nCols;
    private final String columnHeaders;

    public View(int nRows, int nCols) {
        this.rowLetters = new char[nRows];
        for (int row = 0; row < nRows; row++)
            this.rowLetters[row] = alphabet[row];

        this.nRows = nRows;
        this.nCols = nCols;

        this.columnHeaders = generateColumnHeaders();
    }

    private String generateColumnHeaders() {
        StringBuilder columnHeaders = new StringBuilder("\n ");
        for (int col = 0; col < this.nCols; col++)
            columnHeaders.append(String.format("%" + cellPadding + "d", col + 1));
        return columnHeaders.toString();
    }

    public String renderBoard(int[][] board) {
        StringBuilder printable = new StringBuilder(this.columnHeaders);

        for (int row = 0; row < this.nRows; row++) {
            printable.append("\n\n").append(this.rowLetters[row]);

            for (int col = 0; col < this.nCols; col++) {
                int cellValue = board[row][col];
                printable.append(String.format("%" + cellPadding + "c", cellSymbols[cellValue]));
            }

        }
        return printable.append("\n").toString();
    }
}
