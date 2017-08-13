package com.somestupidappproject.betago.utils;

import com.somestupidappproject.betago.board.Board;
import com.somestupidappproject.betago.board.Stone;
import com.somestupidappproject.betago.moves.Move;
import com.somestupidappproject.betago.player.Player;

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
     *  returns Player.BLACK if black wins
     *  returns Player.WHITE if WHITE wins
     *  returns TIE if tie
     */
    public static String getWinningPlayer(Board board, Stack<Move> previousMoves) {
        ScoreCount captureScores = getCaptureScores(previousMoves);
        ScoreCount territoryScores = getTerritoryScores(board);
        ScoreCount influenceScores = getInfluenceScore(board);

        int blackScore = captureScores.getScore(Player.BLACK) +
                territoryScores.getScore(Player.BLACK) +
                influenceScores.getScore(Player.BLACK);
        int whiteScore = captureScores.getScore(Player.WHITE) +
                territoryScores.getScore(Player.WHITE) +
                influenceScores.getScore(Player.WHITE);
        if (blackScore > whiteScore) {
            return Player.BLACK;
        } else if (whiteScore > blackScore) {
            return Player.WHITE;
        }
        return "TIE";
    }

    /**
     * given the current board returns points for territories that are uncontested for both players
     * @param board the current board
     * @return the scores awarded to each player for capturing territories
     */
    public static ScoreCount getTerritoryScores(Board board) {
        ScoreCount territoryScores = new ScoreCount();
        for (int i = 0; i < board.getBoardSize(); i++) {
            for (int j = 0; j < board.getBoardSize(); j++) {
                if (board.getStone(i, j).getColor() == 0) {
                    int territoryOwner = LogicUtil.getTerritoryOwner(board, board.getStone(i, j));
                    if (territoryOwner == 1) {
                        territoryScores.incrementPlayerScore(Player.BLACK);
                    } else if (territoryOwner == 2) {
                        territoryScores.incrementPlayerScore(Player.WHITE);
                    }
                }
            }
        }
        return territoryScores;
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
                    scoreCount.incrementPlayerScore(Player.WHITE);
                } else if (capturedStone.getColor() == Stone.WHITE) {
                    //stone taken was white so black gets the point
                    scoreCount.incrementPlayerScore(Player.BLACK);
                }
            });
        });
        return scoreCount;
    }

    public static ScoreCount getInfluenceScore(Board board) {
        InfluenceBoard influenceBoard = new InfluenceBoard(board.getBoardSize(), board.getStones());
        return influenceBoard.getInfluenceScore();
    }
}
