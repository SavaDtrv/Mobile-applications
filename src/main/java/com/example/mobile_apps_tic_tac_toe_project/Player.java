package com.example.mobile_apps_tic_tac_toe_project;

class Player {

    private int points;

    void setPlayerPoints(int newPoints) {
        if (newPoints <= 0) {
            points = 0;
            return;
        }

        points = newPoints;
    }

    int getPoints() {
        return points;
    }

    void increasePointsIfWins() {
        points++;
    }
}
