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
    private Pair<Integer, Integer> coordinates;
    private int ImageId;

    // 0 is untaken
    // 1 is black
    // 2 is white
    private int Color = 0;

    private ArrayList<Integer> previousStates;

    // defaults to -1,-1
    public Point() {
        this(-1,-1);
    }

    public Point(int x, int y) {
        this.X = x;
        this.Y = y;
        this.previousStates = new ArrayList<>();
    }

    public void addCoordinates(int x, int y) {
        this.X = x;
        this.Y = y;
    }

    public void setImageId(int id){
        this.ImageId = id;
    }

    public void setTakeState(int color){
        // only update the color if they are different
        if (this.Color != color) {
            this.previousStates.add(this.Color);
            this.Color = color;
        }
    }

    public void revertState(){
        int size = previousStates.size();
        if (size > 0) {
            this.Color = this.previousStates.get(size-1);
            this.previousStates.remove(size-1);
        }
    }

    public int getColor() {
        return Color;
    }

    public int getImageId() {
        return ImageId;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }
}
