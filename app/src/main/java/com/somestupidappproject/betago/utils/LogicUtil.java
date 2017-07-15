package com.somestupidappproject.betago.utils;

import android.util.Log;

import com.somestupidappproject.betago.board.Board;
import com.somestupidappproject.betago.board.Point;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Tom on 7/12/2017.
 */

public class LogicUtil {
    private static final String TAG = "betago.LogicUtil";

    // Parameters: a point, the board
    // Returns: whether or not the node is dead
    // if there is no node on that point method return false
    public static boolean isAlive( Point point, Board board ){
        return isAliveRecursive(point, board, new HashSet<Point>(){});
    }

    // Function that will return the group of nodes of the same color
    // touching the node given
    public static HashSet<Point> findGroup(Point point, Board board) {
        return findGroupRecursive(point, board, new HashSet<Point>(){});
    }

    private static HashSet<Point> findGroupRecursive(Point point, Board board, HashSet<Point> groupFound) {
        HashSet<Point> nodesToCheck = BoardUtils.getNeighbors(point, board);
        groupFound.add(point);

        for (Point p : nodesToCheck) {
            // means the spot is un occupied
            if (p.getColor() == 0){
                continue;
            }

            // check if we have seen this node already
            boolean skipNode = false;
            for (Point checkedPoint : groupFound) {
                if (checkedPoint.getX() == p.getX() && checkedPoint.getY() == p.getY()){
                    skipNode = true;
                    break;
                }
            }

            if (skipNode) {
                continue;
            }

            // keep walking the tree
            if (p.getColor() == point.getColor()) {
                HashSet<Point> groupies = findGroupRecursive(p, board, groupFound);
                groupFound.addAll(groupies);
            }
        }

        return groupFound;
    }

    // Recursive function that will walk the group and return true if
    // it finds an open liberty, it will skip the nodes we have already seen
    private static boolean isAliveRecursive(Point point, Board board, HashSet<Point> alreadyCheckedPoints){
        int x = point.getX();
        int y = point.getY();
        int color = point.getColor();

        HashSet<Point> nodesToCheck = BoardUtils.getNeighbors(point, board);

        alreadyCheckedPoints.add(point);
        for (Point p : nodesToCheck) {
            // means the spot is un occupied
            if (p.getColor() == 0){
                return  true;
            }

            // check if we have seen this node already
            boolean skipNode = false;
            for (Point checkedPoint : alreadyCheckedPoints) {
                if (checkedPoint.getX() == p.getX() && checkedPoint.getY() == p.getY()){
                    skipNode = true;
                    break;
                }
            }

            if (skipNode) {
                continue;
            }

            if (p.getColor() == color) {
                if (isAliveRecursive(p, board, alreadyCheckedPoints)) {
                    return true;
                }
            }
        }

        return false;
    }

    //given a point and a board, returns the owner of the territory associated with that point
    //returns 0 if the point is already taken by a player
    //returns 0 if the territory is contested
    //returns 1 if black owns the territory
    //returns 2 if white owns the territory
    public static int getTerritoryOwner(Board board,
                                  Point point) {
        if (point.getColor() == 1 || point.getColor() == 2) {
            return 0;
        }

        Map<Integer, Boolean> foundPlayer = new HashMap<>();
        foundPlayer.put(1, false);
        foundPlayer.put(2, false);
        isTerritoryOwnedRecursive(point, board, new HashSet<Point>(){}, foundPlayer);
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

    //given a point, determines if the point is owned by a color.
    //returns false if the point is a color (black or white)
    public static void isTerritoryOwnedRecursive(Point point,
                                                 Board board,
                                                 Set<Point> alreadyCheckedPoints,
                                                 Map<Integer, Boolean> foundPlayer) {
        boolean foundWhitePiece = false;
        boolean foundBlackPiece = false;

        HashSet<Point> nodesToCheck = BoardUtils.getNeighbors(point, board);
        alreadyCheckedPoints.add(point);

        for (Point p : nodesToCheck) {
            if (alreadyCheckedPoints.contains(p)) {
                continue;
            } else if (p.getColor() == 1) {
                foundPlayer.put(1, true);
            } else if (p.getColor() == 2) {
                foundPlayer.put(2, true);
            } else {
                isTerritoryOwnedRecursive(p, board, alreadyCheckedPoints, foundPlayer);
            }
        }
    }




}


