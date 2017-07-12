package com.somestupidappproject.betago;

/**
 * Created by Tom on 7/12/2017.
 */

public class Point {
    public int X;
    public int Y;

    // defaults to 0
    public Point() {
        this.X = 0;
        this.Y = 0;
    }

    public Point(int x, int y) {
        this.X = x;
        this.Y = y;
    }

    public void addCoordinates(int x, int y) {
        this.X = x;
        this.Y = y;
    }
}
