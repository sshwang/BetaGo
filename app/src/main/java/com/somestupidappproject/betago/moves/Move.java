package com.somestupidappproject.betago.moves;

import android.widget.ImageView;

import com.somestupidappproject.betago.board.Stone;

import java.util.ArrayList;

/**
 * Created by shwang on 7/13/17.
 */

public class Move {

    private Stone stone;
    private ArrayList<Stone> capturedStones = new ArrayList<Stone>();
    private ImageView imageView;

    public Move(Stone stone) {
        this.stone = stone;
    }

    public Move (Stone stone, ArrayList<Stone> capturedStones) {
        this(stone);
        this.capturedStones = capturedStones;
    }

    public Stone getStone() {
        return stone;
    }

    public void setStone(Stone stone) {
        this.stone = stone;
    }

    public ArrayList<Stone> getCapturedStones() {
        return capturedStones;
    }

    public void setCapturedStones(ArrayList<Stone> capturedStones) {
        this.capturedStones = capturedStones;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

}
