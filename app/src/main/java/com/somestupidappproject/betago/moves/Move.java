package com.somestupidappproject.betago.moves;

import android.widget.ImageView;

import com.somestupidappproject.betago.board.Point;

import java.util.ArrayList;

/**
 * Created by shwang on 7/13/17.
 */

public class Move {

    private Point point;
    private ArrayList<Point> capturedPoints;
    private ImageView imageView;

    public Move() {
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public ArrayList<Point> getCapturedPoints() {
        return capturedPoints;
    }

    public void setCapturedPoints(ArrayList<Point> capturedPoints) {
        this.capturedPoints = capturedPoints;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

}
