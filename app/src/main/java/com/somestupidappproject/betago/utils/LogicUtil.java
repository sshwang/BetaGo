package com.somestupidappproject.betago.utils;

import android.util.Log;

import com.somestupidappproject.betago.board.Board;
import com.somestupidappproject.betago.board.Stone;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Tom on 7/12/2017.
 */

public class LogicUtil {
    private static final String TAG = "betago.LogicUtil";

    private static final int TERRITORY_CONTESTED = 0;
    private static final int BLACK_TERRITORY = 1;
    private static final int WHITE_TERRITORY = 2;

    // Parameters: a stone, the board
    // Returns: whether or not the node is dead
    // if there is no node on that stone method return false
    public static boolean isAlive( Stone stone, Board board ){
        return isAliveRecursive(stone, board, new HashSet<Stone>(){});
    }

    public static boolean isAlive(Stone stone, Board board, HashSet<Stone> stonesNotToCheck) {
        // create a new hashset so we don't mess with the callers set
        HashSet<Stone> stonesNotToCheckCopy = new HashSet<Stone>() {{ addAll(stonesNotToCheck); }};
        return isAliveRecursive(stone, board, stonesNotToCheckCopy);
    }

    // Function that will return the group of nodes of the same color
    // touching the node given
    public static Set<Stone> findGroup(Stone stone, Board board) {
        return findGroupRecursive(stone, board, new HashSet<Stone>(){});
    }

    private static Set<Stone> findGroupRecursive(Stone stone, Board board, HashSet<Stone> groupFound) {
        Set<Stone> nodesToCheck = BoardUtils.getNeighbors(stone, board);
        groupFound.add(stone);

        for (Stone s : nodesToCheck) {
            // means the spot is un occupied
            if (s.getColor() == Stone.UNTAKEN){
                continue;
            }

            // check if we have seen this node already
            boolean skipNode = false;
            for (Stone checkedPoint : groupFound) {
                if (checkedPoint.getX() == s.getX() && checkedPoint.getY() == s.getY()){
                    skipNode = true;
                    break;
                }
            }

            if (skipNode) {
                continue;
            }

            // keep walking the tree
            if (s.getColor() == stone.getColor()) {
                Set<Stone> groupies = findGroupRecursive(s, board, groupFound);
                groupFound.addAll(groupies);
            }
        }

        return groupFound;
    }

    // Recursive function that will walk the group and return true if
    // it finds an open liberty, it will skip the nodes we have already seen
    private static boolean isAliveRecursive(Stone stone, Board board, HashSet<Stone> alreadyCheckedPoints){
        int x = stone.getX();
        int y = stone.getY();
        int color = stone.getColor();

        Set<Stone> nodesToCheck = BoardUtils.getNeighbors(stone, board);

        alreadyCheckedPoints.add(stone);
        for (Stone s : nodesToCheck) {
            // check if we want to not include this node
            boolean skipNode = false;
            for (Stone checkedPoint : alreadyCheckedPoints) {
                if (checkedPoint.getX() == s.getX() && checkedPoint.getY() == s.getY()){
                    skipNode = true;
                    break;
                }
            }

            if (skipNode) {
                continue;
            }

            // means the spot is un occupied
            if (s.getColor() == Stone.UNTAKEN){
                return  true;
            }

            if (s.getColor() == color) {
                if (isAliveRecursive(s, board, alreadyCheckedPoints)) {
                    return true;
                }
            }
        }

        return false;
    }

    //given a stone and a board, returns the owner of the territory associated with that stone
    //returns 0 if the stone is already taken by a player
    //returns 0 if the territory is contested
    //returns 1 if black owns the territory
    //returns 2 if white owns the territory
    public static int getTerritoryOwner(Board board,
                                        Stone stone) {
        if (stone.getColor() == 1 || stone.getColor() == 2) {
            return TERRITORY_CONTESTED;
        }

        Map<Integer, Boolean> foundPlayer = new HashMap<>();
        foundPlayer.put(1, false);
        foundPlayer.put(2, false);
        isTerritoryOwnedRecursive(stone, board, new HashSet<Stone>(){}, foundPlayer);
        boolean blackContact = foundPlayer.get(1);
        boolean whiteContact = foundPlayer.get(2);
        if (blackContact && whiteContact) {
            return TERRITORY_CONTESTED;
        } else if (blackContact && !whiteContact) {
            return BLACK_TERRITORY;
        } else if (!blackContact && whiteContact) {
            return WHITE_TERRITORY;
        } else {
            Log.d(TAG, "Territory owner could not be computed");
        }
        return TERRITORY_CONTESTED;
    }

    //given a stone, determines if the stone is owned by a color.
    //returns false if the stone is a color (black or white)
    private static void isTerritoryOwnedRecursive(Stone stone,
                                                  Board board,
                                                  Set<Stone> alreadyCheckedStones,
                                                  Map<Integer, Boolean> foundPlayer) {
        boolean foundWhitePiece = false;
        boolean foundBlackPiece = false;

        Set<Stone> nodesToCheck = BoardUtils.getNeighbors(stone, board);
        alreadyCheckedStones.add(stone);

        for (Stone s : nodesToCheck) {
            if (alreadyCheckedStones.contains(s)) {
                continue;
            } else if (s.getColor() == 1) {
                foundPlayer.put(1, true);
            } else if (s.getColor() == 2) {
                foundPlayer.put(2, true);
            } else {
                isTerritoryOwnedRecursive(s, board, alreadyCheckedStones, foundPlayer);
            }
        }
    }




}

