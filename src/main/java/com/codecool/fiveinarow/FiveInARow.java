package com.codecool.fiveinarow;

public class FiveInARow {

    public static void main(String[] args) {
        View.clearConsole();
        int[] boardDimensions = Config.getBoardDimensions();
        Game game = new Game(boardDimensions[0], boardDimensions[1]);
        Config.initGame(game);
        game.play(5);
    }

}
