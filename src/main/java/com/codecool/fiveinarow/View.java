package com.codecool.fiveinarow;

public class View {

    private final static char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    public final static char[] cellSymbols = {'.', 'X', 'O'};
    private final static byte cellPadding = 4;
    public final static String clearSequence = "\033[H\033[2J";
    private final static String title = "  ____  ___  __  __  ___  _  ___   _" + "\n" +
                                        " / ___|/ _ \\|  \\/  |/ _ \\| |/ | | | |" + "\n" +
                                        "| |  _| | | | |\\/| | | | | ' /| | | |" + "\n" +
                                        "| |_| | |_| | |  | | |_| | . \\| |_| |" + "\n" +
                                        " \\____|\\___/|_|  |_|\\___/|_|\\_\\\\___/" + "\n\n";
    private final static String watermark = " _            ___  ___    _     ___ _____   _      _   ___  ___  ___    _" + "\n" +
                                            "| |__ _  _   / _ \\| _ \\  /_\\   | __|_   _| | |    /_\\ | _ )/ _ \\| _ \\  /_\\" + "\n" +
                                            "| '_ \\ || | | (_) |   / / _ \\  | _|  | |   | |__ / _ \\| _ \\ (_) |   / / _ \\" + "\n" +
                                            "|_.__/\\_, |  \\___/|_|_\\/_/ \\_\\ |___| |_|   |____/_/ \\_\\___/\\___/|_|_\\/_/ \\_\\" + "\n" +
                                            "      |__/" + "\n\n";
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
        StringBuilder printable = new StringBuilder(title + watermark + this.columnHeaders);

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
