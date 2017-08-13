package com.somestupidappproject.betago.utils;

import android.util.Log;

import com.somestupidappproject.betago.board.Stone;

import java.util.List;

/**
 * Created by Sagar Patel on 8/7/2017.
 */

public class InfluenceBoard {
    private double[][] board;
    private int boardSize;
    private double startBias;
    private static final String TAG = "InfluenceBoard";
    private static final double DAMPNING = 1.5;
    private static final double INFLUENCE_THRESHOLD = .25;

    public InfluenceBoard(int boardSize) {
        this.board = new double[boardSize][boardSize];
        this.boardSize = boardSize;
        initializeBoard();
        startBias = 1.0;
    }

    public InfluenceBoard(int boardSize, List<Stone> stoneList) {
        this(boardSize);
        stoneList.forEach(this::addStone);
    }

    public void initializeBoard() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = 0;
            }
        }
    }

    public void addStone(Stone stone) {
        //we are going to add influence for black and subtract influence for white
        int multiplier = 1;
        if (stone.getColor() == Stone.WHITE) {
            multiplier = -1;
        }

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                int manhattenDistance = BoardUtils.getManhattenDistance(stone.getX(), stone.getY(), i, j);
                board[i][j] += multiplier * startBias / Math.pow(2, DAMPNING * manhattenDistance);
            }
        }
    }

    public ScoreCount getInfluenceScore() {
        int blackScore = 0;
        int whiteScore = 0;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                double influence = board[j][i];
                if (influence > INFLUENCE_THRESHOLD) {
                    blackScore++;
                } else if (influence < -INFLUENCE_THRESHOLD) {
                    whiteScore++;
                }
            }
        }
        logState();
        return new ScoreCount(whiteScore, blackScore);
    }

    public void logState() {
        Log.d(TAG, "----------------------------------------------------------------");
        for (int i = 0; i < boardSize; i++) {
            String owner = "";
            String inf = "";
            for (int j = 0; j < boardSize; j++) {
                double influence = board[j][i];
                inf += influence + " ";
                if (influence > INFLUENCE_THRESHOLD) {
                    owner += "B";
                } else if (influence < -INFLUENCE_THRESHOLD) {
                    owner += "W";
                } else {
                    owner += ".";
                }

                owner += " ";
            }
            Log.d(TAG, owner);
            owner = "";
        }
        Log.d(TAG, "----------------------------------------------------------------");
    }


}
