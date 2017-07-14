package com.somestupidappproject.betago;

import java.util.HashSet;

/**
 * Created by Tom on 7/12/2017.
 */

public class LogicUtil {
    // Get the neighbors of the given node
    public static HashSet<Point> getNeighbors(Point point, Point[][] board) {
        int x = point.X;
        int y = point.Y;
        HashSet<Point> ret = new HashSet<Point>() {
        };

        if ( y + 1 < 19 ){
            ret.add(board[x][y+1]);
        }
        if (y - 1 > -1) {
            ret.add(board[x][y - 1]);
        }
        if (x + 1 < 19) {
            ret.add(board[x + 1][y]);
        }
        if (x - 1 > -1) {
            ret.add(board[x - 1][y]);
        }

        return ret;
    }

    // Parameters: a point, the board
    // Returns: whether or not the node is dead
    // if there is no node on that point method return false
    public static boolean isAlive( Point point, Point[][] board ){
        return isAliveRecursive(point, board, new HashSet<Point>(){});
    }

    // Function that will return the group of nodes of the same color
    // touching the node given
    public static HashSet<Point> findGroup(Point point, Point[][] board) {
        return findGroupRecursive(point, board, new HashSet<Point>(){});
    }

    private static HashSet<Point> findGroupRecursive(Point point, Point[][] board, HashSet<Point> groupFound) {
        HashSet<Point> nodesToCheck = getNeighbors(point, board);
        groupFound.add(point);

        for (Point p : nodesToCheck) {
            // means the spot is un occupied
            if (p.Color == 0){
                continue;
            }

            // check if we have seen this node already
            boolean skipNode = false;
            for (Point checkedPoint : groupFound) {
                if (checkedPoint.X == p.X && checkedPoint.Y == p.Y){
                    skipNode = true;
                    break;
                }
            }

            if (skipNode) {
                continue;
            }

            // keep walking the tree
            if (p.Color == point.Color) {
                HashSet<Point> groupies = findGroupRecursive(p, board, groupFound);
                groupFound.addAll(groupies);
            }
        }

        return groupFound;
    }

    // Recursive function that will walk the group and return true if
    // it finds an open liberty, it will skip the nodes we have already seen
    private static boolean isAliveRecursive(Point point, Point[][] board, HashSet<Point> alreadyCheckedPoints){
        int x = point.X;
        int y = point.Y;
        int color = point.Color;

        HashSet<Point> nodesToCheck = getNeighbors(point, board);

        alreadyCheckedPoints.add(point);
        for (Point p : nodesToCheck) {
            // means the spot is un occupied
            if (p.Color == 0){
                return  true;
            }

            // check if we have seen this node already
            boolean skipNode = false;
            for (Point checkedPoint : alreadyCheckedPoints) {
                if (checkedPoint.X == p.X && checkedPoint.Y == p.Y){
                    skipNode = true;
                    break;
                }
            }

            if (skipNode) {
                continue;
            }

            if (p.Color == color) {
                if (isAliveRecursive(p, board, alreadyCheckedPoints)) {
                    return true;
                }
            }
        }

        return false;
    }
}


