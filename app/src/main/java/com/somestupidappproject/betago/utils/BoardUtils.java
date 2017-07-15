package com.somestupidappproject.betago.utils;

import com.somestupidappproject.betago.board.Board;
import com.somestupidappproject.betago.board.Point;

import java.util.HashSet;

/**
 * Created by sapatel on 7/15/2017.
 */

public class BoardUtils {
    private static final String TAG = "betago.EndGameUtils";

    // Get the neighbors of the given node
    public static HashSet<Point> getNeighbors(Point point, Board board) {
        int x = point.getX();
        int y = point.getY();
        HashSet<Point> ret = new HashSet<Point>() {
        };

        if ( y + 1 < board.getBoardSize() ){
            ret.add(board.getPoint(x, y + 1));
        }
        if (y - 1 > -1) {
            ret.add(board.getPoint(x, y - 1));
        }
        if (x + 1 < board.getBoardSize()) {
            ret.add(board.getPoint(x + 1, y));
        }
        if (x - 1 > -1) {
            ret.add(board.getPoint(x - 1, y));
        }

        return ret;
    }


}
