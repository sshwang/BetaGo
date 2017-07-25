package com.somestupidappproject.betago.utils;
import com.somestupidappproject.betago.board.Board;
import com.somestupidappproject.betago.board.Stone;
import com.somestupidappproject.betago.moves.Move;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sapatel on 7/15/2017.
 */

public class BoardUtils {
    private static final String TAG = "betago.ScoringUtils";

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

    public static ArrayList<Stone> getDeadStones(Stone stone, Board board) {
        ArrayList<Stone> deadStones = new ArrayList<>();
        if (stone.getColor() == Stone.UNTAKEN) {
            return deadStones;
        }

        // Get all neighbors and also check if the placed point is surrounded
        Set<Stone> neighbors = BoardUtils.getNeighbors(stone, board);

        for (Stone neighbor : neighbors) {
            if (!LogicUtil.isAlive(neighbor, board)) {
                // Point is dead which means it's group is also dead
                // Find the group here
                Set<Stone> group = LogicUtil.findGroup(neighbor, board);
                deadStones.addAll(group);
            }
        }

        return deadStones;
    }

    // Determines if the move is valid or not
    // invalid moves included are currently
    // 1. Suicide move aka going in a spot where the group you are adding to has no liberties
    // 2. Ko invalid move
    // 3. Occupying a spot that already has a piece
    public static boolean isValidMove(
            Stone stone,
            Board board,
            boolean isBlacksMove,
            Move previousMove ) {
        // convert the boolean into an int
        int color = isBlacksMove ? 1 : 2;

        // Always return true for Pass
        if (stone.getColor() == Stone.UNTAKEN) {
            return true;
        }

        // Check if stone is already occupied
        if (board.getStone(stone.getX(), stone.getY()).getColor() != Stone.UNTAKEN) {
            return false;
        }

        // Check if it's the first move. Stone must be unoccupied and can't be suicide
        if (previousMove == null) {
            return true;
        }

        // check for suicide move,
        if (BoardUtils.isSuicideMove(stone, board, color)) {
            return false;
        }

        // check for ko
        if (BoardUtils.isKo(stone, board, previousMove)) {
            return false;
        }

        return true;
    }

    // Ko happens when the following conditions are met
    // 1. The last move (stone 1) captured exactly one stone (stone 2)
    // 2. The current played move (stone 3) is the same position as the previous captured stone (stone 2)
    // 3. The current played stone (stone 3) captures the last played stone (stone 1)
    private static boolean isKo(Stone currentStone, Board board, Move previousMove) {
        ArrayList<Stone> previousCapturedStones = previousMove.getCapturedStones();
        Stone previousStone = previousMove.getStone();

        if (previousCapturedStones.size() == 1) {
            Stone capturedStone = previousCapturedStones.get(0);
            if (BoardUtils.isStoneEqual(currentStone, capturedStone)) {
                // Set the dead stone list
                ArrayList<Stone> deadStones = new ArrayList<Stone>();

                // here is where we find the neighbors that would die from playing the stone
                Set<Stone> neighbors = BoardUtils.getNeighbors(currentStone, board);
                HashSet<Stone> stonesNotChecked = new HashSet<Stone>() {{ add(currentStone); }};
                for (Stone neighbor : neighbors) {
                    if (!LogicUtil.isAlive(neighbor, board, stonesNotChecked)){
                        deadStones.add(neighbor);
                    }
                }

                // now verify that the dead stone is equal the previous played stone
                if (deadStones.size() == 1) {
                    Stone deadStone = deadStones.get(0);
                    if (BoardUtils.isStoneEqual(deadStone, previousStone)) {
                        return true;
                    }
                }

            }
        }
        return false;
    }

    private static boolean isStoneEqual(Stone stone1, Stone stone2) {
        if (stone1.getX() == stone2.getX() && stone1.getY() == stone2.getY() && stone1.getColor() == stone2.getColor()) {
            return true;
        }

        return false;
    }

    private static boolean isSuicideMove(Stone stone, Board board, int color) {
        // if any neighbors are going to die then it is a valid move
        Set<Stone> neighbors = BoardUtils.getNeighbors(stone, board);

        // Because we haven't already gone we need to exclude the current stone from our search
        HashSet<Stone> stonesNotChecked = new HashSet<Stone>() {{ add(stone); }};

        for (Stone neighbor : neighbors) {
            // if your neighbor is your color and is alive, then it is not a suicide move
            if (neighbor.getColor() == color && LogicUtil.isAlive(neighbor, board, stonesNotChecked)){
                return false;
            }
            // if your neighbor is not your color and dead then it is not a suicide move
            else if (neighbor.getColor() != color && !LogicUtil.isAlive(neighbor, board, stonesNotChecked )){
                return false;
            }
        }

        //now check if adding the stone will kill the group it is in
        if (!LogicUtil.isAlive(stone, board)) {
            return true;
        }

        return false;
    }
}
