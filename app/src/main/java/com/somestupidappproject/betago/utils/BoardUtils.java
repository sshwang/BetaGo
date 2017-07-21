package com.somestupidappproject.betago.utils;

import com.somestupidappproject.betago.board.Board;
import com.somestupidappproject.betago.board.Stone;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sapatel on 7/15/2017.
 */

public class BoardUtils {
    private static final String TAG = "betago.EndGameUtils";

    // Get the neighbors of the given node
    public static Set<Stone> getNeighbors(Stone stone, Board board) {
        int x = stone.getX();
        int y = stone.getY();
        Set<Stone> ret = new HashSet<Stone>() {
        };

        if ( y + 1 < board.getBoardSize() ){
            ret.add(board.getStone(x, y + 1));
        }
        if (y - 1 > -1) {
            ret.add(board.getStone(x, y - 1));
        }
        if (x + 1 < board.getBoardSize()) {
            ret.add(board.getStone(x + 1, y));
        }
        if (x - 1 > -1) {
            ret.add(board.getStone(x - 1, y));
        }

        return ret;
    }


}
