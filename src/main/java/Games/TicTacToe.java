package Games;

import Controllers.ButtonController;
import Controllers.JoystickController;
import Interfaces.RaspiStationControllersInterface;

import java.io.IOException;

public class TicTacToe implements RaspiStationControllersInterface {
    private final String[][] board;
    private static final int ROWS = 3;
    private static final int COLUMNS = 3;
    private final String regex = "\\s{3}";

    private int selectedRow = 0;
    private int selectedColumn = 0;
    private String player = "X";

    public void startGame() throws InterruptedException, IOException {
        initializeControls();
        System.out.println(printBoard(board));
        while (!isGameOver())
            Thread.sleep(1000);
        System.out.println("Game over!");
    }

    // <editor-fold> Overridden methods
    @Override
    public void initializeControls() throws IOException {
        ButtonController buttonController = new ButtonController(this);
        buttonController.startButton();
        JoystickController joystickController = new JoystickController(this);
        joystickController.startJoystick();
    }

    @Override
    public void leftButtonClicked() {
        setPlay(selectedRow, selectedColumn, player);
        selectedRow = 0;
        selectedColumn = 0;
        System.out.println(printBoard(board));
        if (player.equals("X")) {
            player = "O";
        } else {
            player = "X";
        }
    }

    @Override
    public void leftButtonReleased() {
    }

    @Override
    public void rightButtonClicked() {

    }

    @Override
    public void rightButtonReleased() {

    }

    @Override
    public void joystickWentDown() {
        if (selectedRow < 2)
            selectedRow++;
    }

    @Override
    public void joystickWentUp() {
        if (selectedRow > 0)
            selectedRow--;
    }

    @Override
    public void joystickWentLeft() {
        if (selectedColumn > 0)
            selectedColumn--;
    }

    @Override
    public void joystickWentRight() {
        if (selectedColumn < 2)
            selectedColumn++;
    }
    // </editor-fold>

    /**
     * TicTacToe constructor
     */
    public TicTacToe() {
        board = new String[ROWS][COLUMNS];
        this.initializeBoard();
    }

    /**
     * initializing board and fill with empty spaces
     */
    private void initializeBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                board[i][j] = "   ";
            }
        }
    }

    /**
     * setPlay method will set player's move
     *
     * @param i
     * @param j
     * @param player
     */
    private void setPlay(int i, int j, String player) {
        if (board[i][j].matches(regex))
            board[i][j] = " " + player + " ";
    }

    /**
     * finding winners
     */
    private boolean isGameOver() {
        //checking rows
        for (int i = 0; i < ROWS; i++) {
            if (!board[i][0].matches(regex) && board[i][0].equals(board[i][1]) && board[i][1].equals(board[i][2])) {
                return true;
            }
        }
        //checking columns

        for (int j = 0; j < COLUMNS; j++) {
            if (!board[0][j].matches(regex) && board[0][j].equals(board[1][j]) && board[1][j].equals(board[2][j]))
                return true;
        }
        //checking diagonals
        if (!board[0][0].matches(regex) && board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2]))
            return true;
        if (!board[0][2].matches(regex) && board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0]))
            return true;
        //no body won
        return false;

    }

    /**
     * Print board to screen
     *
     * @return strBoard
     */
    private String printBoard(String[][] board) {
        StringBuilder strBoard = new StringBuilder();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (j == COLUMNS - 1)
                    strBoard.append(board[i][j]);
                else
                    strBoard.append(board[i][j]).append("|");
            }
            if (i != ROWS - 1)
                strBoard.append("\n---+---+---\n");
        }
        return strBoard.toString();
    }
}