package com.somestupidappproject.betago;

import java.util.HashSet;

/**
 * Created by Tom on 7/12/2017.
 */


public class LogicUtil {

    public boolean isDeadRecursive(Point point, Point[][] board) {

        return true;

    }

    public HashSet<Point> getNeighbors(Point point, Point[][] board) {
        int x = point.X;
        int y = point.Y;
        HashSet<Point> ret = new HashSet<Point>() {
        };

        if (y + 1 < 19) {
            ret.add(board[x][y + 1]);
        }
        if (y - 1 > -1) {
            ret.add(board[x][y - 1]);
        }
        if (x + 1 < 19) {
            ret.add(board[x + 1][y]);
        }
        if (x - 1 > 0) {
            ret.add(board[x - 1][y]);
        }

        return ret;
    }
}


