package com.codecool.fiveinarow;

import java.util.Scanner;
import java.util.Arrays;

public class Config {

    public static int minWidth = 3, maxWidth = 20;
    public static int minHeight = 3, maxHeight = 20;
    private static int minHowMany = 3, maxHowMany = 7;
    private static String[] gameModes = {"Player vs Player", "Player vs AI", "AI vs AI"};
    private static String gameModeMenu = "Choose game mode:\n\n" +
                                         "\t(1) Player vs. Player\n" + "\t(2) Player vs. AI\n" + "\t(3) AI vs. AI\n\n" +
                                         "Your choice: ";

    public int width, height, howMany;
    private String mainMenu;
    private String currentGameMode;
    private boolean[] aiStates;

    public Config(int defaultWidth, int defaultHeight, int defaultHowMany) {
        this.width = defaultWidth;
        this.height = defaultHeight;
        this.howMany = defaultHowMany;
        this.currentGameMode = gameModes[0];
        this.aiStates = new boolean[2];
    }

    private void setMainMenu() {
        this.mainMenu = "Choose an option:\n\n" +
                            "\t(1) Launch game\n" +
                            String.format("\t(2) Set game mode (current: %s)\n", this.currentGameMode) +
                            String.format("\t(3) Configure board dimensions (current: %d x %d)\n", this.width, this.height) +
                            String.format("\t(4) Set length of winning sequence (current: %d)\n", this.howMany) +
                            "\t(0) Exit\n\n" +
                        "Your choice: ";
    }

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

    private static String[] getStringArrayOfRange(int from, int to) {
        String[] arr = new String[to - from];
        for (int i = from; i < to; i++)
            arr[i - from] = Integer.toString(i);
        return arr;
    }

    public void initMainMenu() {
        setMainMenu();
        String[] validOptions = getStringArrayOfRange(0, 5);
        String option = getUserInput(this.mainMenu, validOptions);
        switch (option) {
            case "2":
                setGameMode();
                break;
            case "3":
                setBoardDimensions();
                break;
            case "4":
                setHowMany();
                break;
            case "0":
                System.exit(0);
                break;
        }
    }

    private void setGameMode() {
        String[] validOptions = getStringArrayOfRange(1, 4);
        String option = getUserInput(gameModeMenu, validOptions);
        switch (option) {
            case "1":
                Arrays.fill(this.aiStates, Boolean.valueOf(false));
                break;
            case "2":
                this.aiStates[1] = true;
                break;
            case "3":
                Arrays.fill(this.aiStates, Boolean.valueOf(true));
                break;
        }
        this.currentGameMode = gameModes[Integer.parseInt(option) - 1];
        initMainMenu();
    }

    private void setBoardDimensions() {
        int[] boardDimensions = new int[2];

        String widthPrompt = String.format("Specify board width (%d <= width <= %d): ", minWidth, maxWidth);
        String heightPrompt = String.format("Specify board height (%d <= height <= %d): ", minHeight, maxHeight);

        String[] validWidths = new String[maxWidth - minWidth + 1];
        String[] validHeights = new String[maxHeight - minHeight + 1];

        for (int i = 0; i < validWidths.length; i++)
            validWidths[i] = Integer.toString(i + minWidth);
        for (int i = 0; i < validHeights.length; i++)
            validHeights[i] = Integer.toString(i + minHeight);

        this.width = Integer.parseInt(getUserInput(widthPrompt, validWidths));
        this.height = Integer.parseInt(getUserInput(heightPrompt, validHeights));
        if (this.width < this.howMany && this.height < this.howMany)
            this.howMany = (this.width < this.height) ? this.width : this.height;

        initMainMenu();
    }

    private void setHowMany() {
        int maxHowMany_ = maxHowMany;
        if (this.height < maxHowMany && this.width < maxHowMany)
            maxHowMany_ = (this.width < this.height) ? this.width : this.height;

        String[] validOptions = getStringArrayOfRange(minHowMany, maxHowMany_ + 1);
        String prompt = String.format("Specify length (%d <= length <= %d): ", minHowMany, maxHowMany_);

        String input = getUserInput(prompt, validOptions);
        this.howMany = Integer.parseInt(input);

        initMainMenu();
    }

    public void initGame(Game game) {
        for (int i = 0; i < 2; i++)
            if (this.aiStates[i])
                game.enableAi(i + 1);
    }

}
