package com.somestupidappproject.betago;

/**
 * Created by Tom on 7/12/2017.
 */

public class Point {
    public int X;
    public int Y;
    public int ImageId;

    // 0 is untaken
    // 1 is black
    // 2 is white
    public int Color = 0;

    public int PreviousColor = 0;

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

    public void setImageId(int id){
        this.ImageId = id;
    }

    public void setTakeState(int color){
        this.PreviousColor = this.Color;
        this.Color = color;
    }

    public void revertState(){
        this.Color = this.PreviousColor;
        this.PreviousColor = 0;
    }
}
