package com.codecool.fiveinarow;

public class FiveInARow {

    public static void main(String[] args) {
        View.clearConsole();

        Config config = new Config(3, 8, 5);
        config.initMainMenu();

        Game game = new Game(config.width, config.height);
        game.play(config.howMany);
    }

}
