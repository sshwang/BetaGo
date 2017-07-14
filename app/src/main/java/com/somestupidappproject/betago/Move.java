package com.somestupidappproject.betago;

import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by shwang on 7/13/17.
 */

public class Move {
    public Point point;
    public ArrayList<Point> capturedPoints;
    public ImageView imageView;

    public Move() {
    }

    public Move(Point point) {
        this.point = point;
    }

    public Move(Point point, ArrayList<Point> capturedPoints) {
        this.point = point;
        this.capturedPoints = capturedPoints;
    }
}
