package com.example.mobile_apps_tic_tac_toe_project;

import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.autofill.AutofillValue;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

class PlayerVSPlayerSameDeviceOrVsCPU extends AppCompatActivity implements View.OnClickListener {

    private static final int PLAYER_ONE_NUMBER = 1;
    private static final int PLAYER_TWO_NUMBER = 2;
    private static final int MAX_GAME_ROUNDS = 9;
    private static final int MIN_ROUNDS_ELAPSED_FOR_WIN = 5;

    private GameBoard board = new GameBoard();

    private Player player1 = new Player();
    private Player player2 = new Player();

    private TextView textViewPointsPlayer1;
    private TextView textViewPointsPlayer2;

    private String playingAgainst = "";

    private ArrayList<Pair<Integer, Integer>> AIIndexesMovedOver = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_vsplayer_same_device_or_vs_cpu);

        textViewPointsPlayer1 = findViewById(R.id.text_view_p1);
        textViewPointsPlayer2 = findViewById(R.id.text_view_p2);

        for (int i = 0; i < GameBoard.MAX_SQUARE_BOARD_SIZE; i++) {
            for (int j = 0; j < GameBoard.MAX_SQUARE_BOARD_SIZE; j++) {

                String buttonID = "button_" + i + j;
                int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
                Button storedViewById = findViewById(resourceID);

                board.setButtonViaIndex(i, j, storedViewById);
                board.getButtonByIndex(i, j).setOnClickListener(this);
            }
        }

        Bundle b = getIntent().getExtras();

        if (b != null) {
            playingAgainst = b.getString("key");
        } else {
            playingAgainst = "Player";
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        switch (playingAgainst) {
            case "Player":
                if (board.getPlayer1Turn()) {
                    ((Button) v).setText("X");
                } else {
                    ((Button) v).setText("O");
                } break;

            case "Super easy mode":
                ((Button) v).setText("X");
                superEasyAILogic();
                break;

            case "Easy mode":
                ((Button) v).setText("X");
                easyAILogic();
                break;
        }

        int changeRound = board.getRoundCount() + 1;
        board.setRoundCount(changeRound);

        if (board.getRoundCount() >= MIN_ROUNDS_ELAPSED_FOR_WIN) {
            announceWinnerOrChangeTurn();

        } else {
            board.setPlayer1Turn(!board.getPlayer1Turn());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void announceWinnerOrChangeTurn() {

        if (board.checkForWin()) {
            if (board.getPlayer1Turn()) {
                playerWon(PLAYER_ONE_NUMBER);

            } else {
                playerWon(PLAYER_TWO_NUMBER);
            }
        } else if (board.getRoundCount() == MAX_GAME_ROUNDS) {
            draw();

        } else {
            board.setPlayer1Turn(!board.getPlayer1Turn());
        }
    }

    private void playerWon(int winningPlayerNumber) {
        if (winningPlayerNumber == PLAYER_ONE_NUMBER) {
            player1.increasePointsIfWins();

        } else {
            player2.increasePointsIfWins();
        }

        String toastText = "Player " + winningPlayerNumber + " wins!";
        Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();

        updatePointsText();
        board.resetBoard();

        AIIndexesMovedOver = new ArrayList<>();
    }

    private void draw() {
        Toast.makeText(this,"Draw!", Toast.LENGTH_SHORT).show();
        board.resetBoard();

        AIIndexesMovedOver = new ArrayList<>();
    }

    private void resetGame() {
        player1.setPlayerPoints(0);
        player2.setPlayerPoints(0);
        updatePointsText();
        board.resetBoard();

        AIIndexesMovedOver = new ArrayList<>();
    }

    private void updatePointsText() {
        String updatePlayer1Points = "Player 1: " + player1.getPoints();
        textViewPointsPlayer1.setText(updatePlayer1Points);
        String updatePlayer2Points = "Player 2: " + player2.getPoints();
        textViewPointsPlayer2.setText(updatePlayer2Points);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", board.getRoundCount());
        outState.putInt("player1Points", player1.getPoints());
        outState.putInt("player2Points", player2.getPoints());
        outState.putBoolean("player1Turn", board.getPlayer1Turn());
        outState.putString("playingAgainst", playingAgainst);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        board.setRoundCount(savedInstanceState.getInt("roundCount"));
        player1.setPlayerPoints(savedInstanceState.getInt("player1Points"));
        player2.setPlayerPoints(savedInstanceState.getInt("player2Points"));
        board.setPlayer1Turn(savedInstanceState.getBoolean("player1Turn"));
        playingAgainst = savedInstanceState.getString("playingAgainst");
    }

    private void superEasyAILogic() {
        Pair<Integer, Integer> indexPair = getFirstAvailableCell();

        String buttonID = "button_" + indexPair.first + indexPair.second;
        int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
        Button storedViewById = findViewById(resourceID);

        storedViewById.setText("O");
    }

    private void easyAILogic() {
        Random rand = new Random();

        int row = rand.nextInt(2);
        int col = rand.nextInt(2);

        Pair<Integer, Integer> indexPair = new Pair<>(row, col);
        AIIndexesMovedOver.add(indexPair);

        String buttonID = "button_" + indexPair.first + indexPair.second;
        int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
        Button storedViewById = findViewById(resourceID);

        while (AIIndexesMovedOver.size() != 8) {

            if (storedViewById.getText().toString().equals("")) {

                storedViewById.setText("O");
                return;
            } else {
                if (getNumberOfTakenCells() > 5 || AIIndexesMovedOver.size() > 6) { //

                    indexPair = getFirstAvailableCell();
                    AIIndexesMovedOver.add(indexPair);

                    buttonID = "button_" + indexPair.first + indexPair.second;
                    resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
                    storedViewById = findViewById(resourceID);

                    storedViewById.setText("O");
                    return;
                } else {
                    int controlNumberForIterations = 0;

                    while (AIIndexesMovedOver.contains(indexPair) && controlNumberForIterations < 30) {

                        row = rand.nextInt(2);
                        col = rand.nextInt(2);

                        indexPair = new Pair<>(row, col);

                        controlNumberForIterations++;
                    }

                    if (controlNumberForIterations == 30) {
                        indexPair = getFirstAvailableCell();
                        AIIndexesMovedOver.add(indexPair);
                    } else {
                        AIIndexesMovedOver.add(indexPair);
                    }

                    buttonID = "button_" + indexPair.first + indexPair.second;
                    resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
                    storedViewById = findViewById(resourceID);
                }
            }
        }
    }

    private int getNumberOfTakenCells() {
        int numberOfTakenCells = 0;

        for (int i = 0; i < GameBoard.MAX_SQUARE_BOARD_SIZE; i++) {
            for (int j = 0; j < GameBoard.MAX_SQUARE_BOARD_SIZE; j++) {
                String buttonID = "button_" + i + j;
                int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
                Button storedViewById = findViewById(resourceID);

                if (!storedViewById.getText().toString().equals("")) {
                    numberOfTakenCells++;
                }
            }
        }

        return numberOfTakenCells;
    }

    private Pair<Integer, Integer> getFirstAvailableCell() {
        for (int i = 0; i < GameBoard.MAX_SQUARE_BOARD_SIZE; i++) {
            for (int j = 0; j < GameBoard.MAX_SQUARE_BOARD_SIZE; j++) {

                String buttonID = "button_" + i + j;
                int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
                Button storedViewById = findViewById(resourceID);

                if (storedViewById.getText().toString().equals("")) {
                    return new Pair<>(i, j);
                }
            }
        }

        return new Pair<>(0,0);
    }

}
