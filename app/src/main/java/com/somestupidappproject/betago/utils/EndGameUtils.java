package com.somestupidappproject.betago.utils;

import com.somestupidappproject.betago.board.Board;

/**
 * Created by sapatel on 7/14/2017.
 */

public class EndGameUtils {
    private static final String TAG = "betago.EndGameUtils";


    //returns the player with the highest score.
    // Is not efficient. We can optimize if necessary.
    //returns 1 if black wins
    //returns 2 if white wins
    //returns 0 if tie
    public static int getWinningPlayer(Board board,
                                       int numCapturesBlack,
                                       int numCapturesWhite) {
        int blackScore = numCapturesBlack;
        int whiteScore = numCapturesWhite;

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
            return 1;
        } else if (whiteScore > blackScore) {
            return 2;
        }
        return 0;
    }

}
