package com.somestupidappproject.betago.utils;

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

    public int getWhiteScore() {
        return whiteScore;
    }

    public int getBlackScore() {
        return blackScore;
    }

    public void incrementWhiteScore() {
        updateWhiteScore(1);
    }

    public void incrementBlackScore() {
        updateBlackScore(1);
    }

    public void updateWhiteScore(int score) {
        whiteScore += score;
    }

    public void updateBlackScore(int score) {
        blackScore += score;
    }

}
