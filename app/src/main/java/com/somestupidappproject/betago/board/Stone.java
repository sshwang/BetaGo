package com.somestupidappproject.betago.board;

/**
 * Created by evansapienza on 7/20/17.
 */

public class Stone extends Point {

    // 0 is untaken
    // 1 is black
    // 2 is white

    public static final int UNTAKEN = 0;
    public static final int BLACK = 1;
    public static final int WHITE = 2;
    public static final int PASS = 3;

    private int color;

    public Stone(int x, int y, int color) {
        super(x, y);
        this.color = color;
    }

    public int getColor() {
        return color;
    }

}
