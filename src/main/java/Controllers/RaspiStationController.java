package Controllers;

import Games.TicTacToe;

import java.io.IOException;

public class RaspiStationController {

    public static void main(String[] args) throws InterruptedException, IOException {
        TicTacToe ticTacToe = new TicTacToe();
        ticTacToe.startGame();
    }
}
