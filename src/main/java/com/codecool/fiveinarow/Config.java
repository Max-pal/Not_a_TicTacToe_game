package com.codecool.fiveinarow;

import java.util.Scanner;

public class Config {

    public static int minWidth = 3, maxWidth = 20;
    public static int minHeight = 3, maxHeight = 20;

    private static String getUserInput(String prompt, String[] validInputs) {
        String choice;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print(prompt);
            choice = scanner.nextLine().trim();
            for (String validChoice : validInputs) {
                if (choice.equals(validChoice)) {
                    View.clearConsole();
                    return choice;
                }
            }
            View.clearConsole();
        }
    }

    public static int[] getBoardDimensions() {
        int[] boardDimensions = new int[2];

        String widthPrompt = String.format("Specify board width (%d <= width <= %d): ", minWidth, maxWidth);
        String heightPrompt = String.format("Specify board height (%d <= height <= %d): ", minHeight, maxHeight);

        String[] validWidths = new String[maxWidth - minWidth + 1];
        String[] validHeights = new String[maxHeight - minHeight + 1];

        for (int i = 0; i < validWidths.length; i++)
            validWidths[i] = Integer.toString(i + minWidth);
        for (int i = 0; i < validHeights.length; i++)
            validHeights[i] = Integer.toString(i + minHeight);

        boardDimensions[1] = Integer.parseInt(getUserInput(widthPrompt, validWidths));
        boardDimensions[0] = Integer.parseInt(getUserInput(heightPrompt, validHeights));
        return boardDimensions;
    }

    private static int chooseGameMode() {
        String prompt = "Choose game mode:\n\n" +
                            "\tPlayer vs. Player (1)\n" +
                            "\tPlayer vs. AI (2)\n" +
                            "\tAI vs. AI (3)\n\n" +
                        "Your choice: ";
        String[] validInputs = {"1", "2", "3"};
        String choice = getUserInput(prompt, validInputs);
        return Integer.parseInt(choice);
    }

    private static int choosePlayer() {
        String prompt = "Choose player number (1/2): ";
        String[] validInputs = {"1", "2"};
        String choice = getUserInput(prompt, validInputs);
        return Integer.parseInt(choice);
    }

    public static void initGame(Game game) {
        int gameMode = chooseGameMode();
        if (gameMode == 2) {
            int player = choosePlayer();
            game.enableAi(player);
        } else if (gameMode == 3) {
            game.enableAi(1);
            game.enableAi(2);
        }
    }
}
