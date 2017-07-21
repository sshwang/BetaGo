package com.somestupidappproject.betago.board;

import android.util.Pair;

import java.util.ArrayList;

/**
 * Created by Tom on 7/12/2017.
 */

public class Point {
    // should we replace these with tuples or pairs?
    // fewer getters/setters
    private int X;
    private int Y;

    // defaults to -1,-1
    public Point() {
        this(-1,-1);
    }

    public Point(int x, int y) {
        this.X = x;
        this.Y = y;
    }

    public void updateCoordinates(int x, int y) {
        this.X = x;
        this.Y = y;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }
}
