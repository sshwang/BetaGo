package com.somestupidappproject.betago;

import java.util.HashSet;

/**
 * Created by Tom on 7/12/2017.
 */

public class LogicUtil {
    // Parameters: a point, the board
    // Returns: whether or not the node is dead
    // if there is no node on that point method return false
    public boolean isDead( Point point, Point[][] board ){

        if (point == null){
            return false;
        }

        int x = point.X;
        int y = point.Y;
        int color = point.Color;

        // This is probably an error case
        // the point should be untaken
        if (board[x][y].Color == 0){
            return false;
        }

        // We now know that there is an object here, we have to check
        // if this new object is dead or not.
        Point north = board[x][y+1];
        Point south = board[x][y-1];
        Point east = board[x+1][y];
        Point west = board[x-1][y];

        // means the spot is un occupied
        if (north.Color == 0 || south.Color == 0 || east.Color == 0 || west.Color == 0) {
            return false;
        }

        return true;
    }

    public boolean isDeadRecursive(Point point, Point[][] board){

        return true;
    }

    public HashSet<Point> getNeighbors(Point point, Point[][] board) {
        int x = point.X;
        int y = point.Y;
        HashSet<Point> ret = new HashSet<Point>(){};

        if ( y+1 < 19){
            ret.add(board[x][y+1]);
        }
        if (y - 1 > -1) {
            ret.add(board[x][y-1]);
        }
        if (x + 1 < 19){
            ret.add(board[x+1][y]);
        }
        if (x-1 > 0 ){
            ret.add(board[x-1][y]);
        }

        return ret;
    }
}

