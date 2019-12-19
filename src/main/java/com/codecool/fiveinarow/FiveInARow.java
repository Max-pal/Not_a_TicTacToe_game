package com.codecool.fiveinarow;

public class FiveInARow {

    public static void main(String[] args) {
        Config config = new Config(3, 8, 5);

        while (true) {
            View.clearConsole();
            config.initMainMenu();
            Game game = new Game(config.width, config.height);
            config.initGame(game);
            game.play(config.howMany);
        }
    }

}
