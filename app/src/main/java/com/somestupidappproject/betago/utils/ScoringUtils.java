package com.somestupidappproject.betago.utils;

import com.somestupidappproject.betago.board.Board;
import com.somestupidappproject.betago.board.Stone;
import com.somestupidappproject.betago.moves.Move;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by sapatel on 7/14/2017.
 */

public class ScoringUtils {
    private static final String TAG = "betago.EndGameUtils";

    /**
     * returns the player with the highest score.
     * This is not efficient but can optimize if necessary
     * @param board
     * @param previousMoves
     * @return
     *  returns 1 if black wins
     *  returns 2 if white wins
     *  returns -1 if tie
     */
    public static int getWinningPlayer(Board board, Stack<Move> previousMoves) {
        ScoreCount captureScores = getCaptureScores(previousMoves);
        int blackScore = captureScores.getBlackScore();
        int whiteScore = captureScores.getWhiteScore();

        for (int i = 0; i < board.getBoardSize(); i++) {
            for (int j = 0; j < board.getBoardSize(); j++) {
                if (board.getStone(i, j).getColor() == 0) {
                    int territoryOwner = LogicUtil.getTerritoryOwner(board, board.getStone(i, j));
                    if (territoryOwner == 1) {
                        blackScore++;
                    } else if (territoryOwner == 2) {
                        whiteScore++;
                    }
                }
            }
        }

        if (blackScore > whiteScore) {
            return Stone.BLACK;
        } else if (whiteScore > blackScore) {
            return Stone.WHITE;
        }
        return -1;
    }

    /**
     * Calculates the stones captured by each player from a list of moves made.
     * @param previousMoves a collection of moves made this game.
     * @return the number of captured pieces for both players
     */
    public static ScoreCount getCaptureScores(Stack<Move> previousMoves) {
        List<Move> prevMoves = new ArrayList<Move>(previousMoves);
        ScoreCount scoreCount = new ScoreCount();
        prevMoves.forEach((move) -> {
            move.getCapturedStones().forEach((capturedStone) -> {
                if (capturedStone.getColor() == Stone.BLACK) {
                    //stone taken was black so white gets the point
                    scoreCount.incrementWhiteScore();
                } else if (capturedStone.getColor() == Stone.WHITE) {
                    //stone taken was white so black gets the point
                    scoreCount.incrementBlackScore();
                }
            });
        });
        return scoreCount;
    }
}
