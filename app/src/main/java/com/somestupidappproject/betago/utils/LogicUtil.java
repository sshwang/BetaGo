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

    // Parameters: a Stone, the board
    // Returns: whether or not the node is dead
    // if there is no node on that Stone method return false
    public static boolean isAlive( Stone Stone, Board board ){
        return isAliveRecursive(Stone, board, new HashSet<Stone>(){});
    }

    public static boolean isAlive(Stone stone, Board board, HashSet<Stone> stonesNotToCheck) {
        // create a new hashset so we don't mess with the callers set
        HashSet<Stone> stonesNotToCheckCopy = new HashSet<Stone>() {{ addAll(stonesNotToCheck); }};
        return isAliveRecursive(stone, board, stonesNotToCheckCopy);
    }

    // Function that will return the group of nodes of the same color
    // touching the node given
    public static Set<Stone> findGroup(Stone Stone, Board board) {
        return findGroupRecursive(Stone, board, new HashSet<Stone>(){});
    }

    private static Set<Stone> findGroupRecursive(Stone Stone, Board board, HashSet<Stone> groupFound) {
        Set<Stone> nodesToCheck = BoardUtils.getNeighbors(Stone, board);
        groupFound.add(Stone);

        for (Stone s : nodesToCheck) {

            // check if we have seen this node already
            boolean skipNode = false;
            for (Stone checkedStone : groupFound) {
                if (checkedStone.getX() == s.getX() && checkedStone.getY() == s.getY()){
                    skipNode = true;
                    break;
                }
            }

            if (skipNode) {
                continue;
            }

            // means the spot is un occupied
            if (s == null){
                continue;
            }

            // keep walking the tree
            if (s.getColor() == Stone.getColor()) {
                Set<Stone> groupies = findGroupRecursive(s, board, groupFound);
                groupFound.addAll(groupies);
            }
        }

        return groupFound;
    }

    // Recursive function that will walk the group and return true if
    // it finds an open liberty, it will skip the nodes we have already seen
    private static boolean isAliveRecursive(Stone Stone, Board board, HashSet<Stone> alreadyCheckedStones){
        int x = Stone.getX();
        int y = Stone.getY();

        Set<Stone> nodesToCheck = BoardUtils.getNeighbors(Stone, board);

        alreadyCheckedStones.add(Stone);
        for (Stone s : nodesToCheck) {
            // means the spot is un occupied
            if (s == null){
                return  true;
            }

            // check if we have seen this node already
            boolean skipNode = false;
            for (Stone checkedStone : alreadyCheckedStones) {
                if (checkedStone.getX() == s.getX() && checkedStone.getY() == s.getY()){
                    skipNode = true;
                    break;
                }
            }

            if (skipNode) {
                continue;
            }

            if (isAliveRecursive(s, board, alreadyCheckedStones)) {
                return true;
            }
        }

        return false;
    }

    //given a Stone and a board, returns the owner of the territory associated with that Stone
    //returns 0 if the Stone is already taken by a player
    //returns 0 if the territory is contested
    //returns 1 if black owns the territory
    //returns 2 if white owns the territory
    public static int getTerritoryOwner(Board board,
                                        Stone Stone) {
        if (Stone.getColor() == Stone.BLACK || Stone.getColor() == Stone.WHITE) {
            return 0;
        }

        Map<Integer, Boolean> foundPlayer = new HashMap<>();
        foundPlayer.put(1, false);
        foundPlayer.put(2, false);
        isTerritoryOwnedRecursive(Stone, board, new HashSet<Stone>(){}, foundPlayer);
        boolean blackContact = foundPlayer.get(1);
        boolean whiteContact = foundPlayer.get(2);
        if (blackContact && whiteContact) {
            return 0;
        } else if (blackContact && !whiteContact) {
            return 1;
        } else if (!blackContact && whiteContact) {
            return 2;
        } else {
            Log.d(TAG, "Territory owner could not be computed");
        }
        return 0;
    }

    //given a Stone, determines if the Stone is owned by a color.
    //returns false if the Stone is a color (black or white)
    private static void isTerritoryOwnedRecursive(Stone Stone,
                                                 Board board,
                                                 Set<Stone> alreadyCheckedStones,
                                                 Map<Integer, Boolean> foundPlayer) {
        boolean foundWhitePiece = false;
        boolean foundBlackPiece = false;

        Set<Stone> nodesToCheck = BoardUtils.getNeighbors(Stone, board);
        alreadyCheckedStones.add(Stone);

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


