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

    // Determines if the move is valid or not
    // invalid moves included are currently
    // 1. Suicide move aka going in a spot where the group you are adding to has no liberties
    // 2. NOT IMPLEMENTED Ko invalid move
    // 3. Occupying a spot that already has a piece
    public static boolean isValidMove(Stone stone, Board board, boolean isBlacksMove) {
        // convert the boolean into an int
        int color = isBlacksMove ? 1 : 2;

        // Check if point is already occupied
        if (board.getStone(stone.getX(), stone.getY()) != null) {
            return false;
        }

//        // check for suicide move,
//        if (BoardUtils.isSuicideMove(stone, board, color)) {
//            return false;
//        }

        return true;
    }


}