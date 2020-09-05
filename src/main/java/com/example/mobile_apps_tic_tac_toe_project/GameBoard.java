package com.example.mobile_apps_tic_tac_toe_project;

import android.widget.Button;

class GameBoard {
    static final int MAX_SQUARE_BOARD_SIZE = 3;

    private Button[][] buttons = new Button[MAX_SQUARE_BOARD_SIZE][MAX_SQUARE_BOARD_SIZE];

    private boolean player1Turn = true;

    private int roundCount = 0;

    boolean getPlayer1Turn() {
        return player1Turn;
    }

    void setPlayer1Turn(boolean isFirstPlayerTurn) {
        player1Turn = isFirstPlayerTurn;
    }

    int getRoundCount() {
        return roundCount;
    }

    void setRoundCount(int newRoundCount) {
        if (newRoundCount <= 0) {
            roundCount = 0;
        }

        roundCount = newRoundCount;
    }

    Button getButtonByIndex(int row, int column) {
        return buttons[row][column];
    }

    void setButtonViaIndex(int row, int column, Button newValue) {
        buttons[row][column] = newValue;
    }

    boolean checkForWin() {
        String[][] board = new String[MAX_SQUARE_BOARD_SIZE][MAX_SQUARE_BOARD_SIZE];

        convertButtonsTextToString(board);

        if (checkRowForWin(board)) {
            return true;

        } else if (checkColumnForWin(board)) {
            return true;

        } else if (checkMainDiagonalForWin(board)) {
            return true;

        } else if (checkSecondaryDiagonalForWin(board)) {
            return true;

        } else {
            return false;
        }
    }

    private void convertButtonsTextToString(String[][] board) {

        for (int i = 0; i < MAX_SQUARE_BOARD_SIZE; i++) {
            for (int j = 0; j < MAX_SQUARE_BOARD_SIZE; j++) {
                board[i][j] = buttons[i][j].getText().toString();
            }
        }
    }

    private boolean checkRowForWin(String[][] board) {

        for (int i = 0; i < MAX_SQUARE_BOARD_SIZE; i++) {

            boolean row_i_0_equals_i_1 = board[i][0].equals(board[i][1]);
            boolean row_i_0_equals_i_2 = board[i][0].equals(board[i][2]);
            boolean row_i_0_isNotEmptyString = !board[i][0].equals("");

            if (row_i_0_equals_i_1 && row_i_0_equals_i_2 && row_i_0_isNotEmptyString) {
                return true;
            }
        }

        return false;
    }

    private boolean checkColumnForWin(String[][] board) {

        for (int j = 0; j < MAX_SQUARE_BOARD_SIZE; j++) {

            boolean column_0_j_equals_1_j = board[0][j].equals(board[1][j]);
            boolean column_0_j_equals_2_j = board[0][j].equals(board[2][j]);
            boolean column_0_j_isNotEmptyString = !board[0][j].equals("");

            if (column_0_j_equals_1_j && column_0_j_equals_2_j && column_0_j_isNotEmptyString) {
                return true;
            }
        }

        return false;
    }

    private boolean checkMainDiagonalForWin(String[][] board) {
        return board[0][0].equals(board[1][1]) && board[0][0].equals(board[2][2]) && !board[0][0].equals("");
    }

    private boolean checkSecondaryDiagonalForWin(String[][] board) {
        return board[0][2].equals(board[1][1]) && board[0][2].equals(board[2][0]) && !board[0][2].equals("");
    }

    void resetBoard() {
        for (int i = 0; i < MAX_SQUARE_BOARD_SIZE; i++) {
            for (int j = 0; j < MAX_SQUARE_BOARD_SIZE; j++) {
                buttons[i][j].setText("");
            }
        }

        roundCount = 0;
        player1Turn = true;
    }
}

