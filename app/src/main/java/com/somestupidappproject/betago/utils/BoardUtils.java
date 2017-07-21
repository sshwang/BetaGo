package com.somestupidappproject.betago.utils;

import com.somestupidappproject.betago.board.Board;
import com.somestupidappproject.betago.board.Point;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sapatel on 7/15/2017.
 */

public class BoardUtils {
    private static final String TAG = "betago.EndGameUtils";

    // Get the neighbors of the given node
    public static Set<Point> getNeighbors(Point point, Board board) {
        int x = point.getX();
        int y = point.getY();
        Set<Point> ret = new HashSet<Point>() {
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

    // Determines if the move is valid or not
    // invalid moves included are currently
    // 1. Suicide move aka going in a spot where the group you are adding to has no liberties
    // 2. NOT IMPLEMENTED Ko invalid move
    // 3. Occupying a spot that already has a piece
    public static boolean isValidMove(Point point, Board board, boolean isBlacksMove) {
        // convert the boolean into an int
        int color = isBlacksMove ? 1 : 2;

        // Check if point is already occupied
        if (board.getPoint(point.getX(), point.getY()).getColor() != 0) {
            return false;
        }

        // check for suicide move,
        if (BoardUtils.isSuicideMove(point, board, color)) {
            return false;
        }

        return true;
    }

    private static boolean isSuicideMove(Point point, Board board, int color) {
        // if any neighbors are going to die then it is a valid move
        Set<Point> neighbors = BoardUtils.getNeighbors(point, board);

        // Because we haven't already gone we need to exclude the current point from our search
        HashSet<Point> pointsNotChecked = new HashSet<Point>() {{ add(point); }};

        for (Point neighbor : neighbors) {
            // if your neighbor is your color and is alive, then it is not a suicide move
            if (neighbor.getColor() == color && LogicUtil.isAlive(neighbor, board, pointsNotChecked)){
                return false;
            }
            // if your neighbor is not your color and dead then it is not a suicide move
            else if (neighbor.getColor() != color && !LogicUtil.isAlive(neighbor, board, pointsNotChecked )){
                return false;
            }
        }

        //now check if adding the stone will kill the group it is in
        if (!LogicUtil.isAlive(point, board)) {
            return true;
        }

        return false;
    }
}
