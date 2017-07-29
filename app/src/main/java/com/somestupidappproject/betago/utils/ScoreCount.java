package com.somestupidappproject.betago.utils;

import com.somestupidappproject.betago.player.Player;

/**
 * Created by Sagar Patel on 7/24/2017.
 */

public class ScoreCount {
    private int whiteScore;
    private int blackScore;

    public ScoreCount() {
        this(0,0);
    }

    public ScoreCount(int initialWhiteScore, int initialBlackScore) {
        whiteScore = initialWhiteScore;
        blackScore = initialBlackScore;
    }

    public int getScore(String player) {
        int score = 0;
        if (player == Player.BLACK) {
            score = blackScore;
        }
        else if (player == Player.WHITE) {
            score = whiteScore;
        }
        return score;
    }

    public void incrementPlayerScore(String player) {
        if (player == Player.BLACK) {
            blackScore += 1;
        }
        else if (player == Player.WHITE) {
            whiteScore += 1;
        }
    }

    public void updateScore (String player, int score) {
        if (player == Player.BLACK) {
            blackScore += score;
        }
        else if (player == Player.WHITE) {
            whiteScore += score;
        }
    }

}
