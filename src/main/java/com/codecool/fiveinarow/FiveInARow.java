package com.codecool.fiveinarow;

import java.util.Scanner;

public class FiveInARow {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Config config = new Config(8, 3, 3);

        while (true) {
            View.clearConsole();
            config.initMainMenu();
            Game game = new Game(config.height, config.width);
            config.initGame(game);
            game.play(config.howMany);
        }
    }

}
