package com.somestupidappproject.betago.board;

import java.util.ArrayList;

/**
 * Created by evansapienza on 7/20/17.
 */

public class Stone extends Point {

    // 1 is black
    // 2 is white

    public static final int BLACK = 1;
    public static final int WHITE = 2;

    private int color;

    public Stone(int x, int y, int color) {
        super(x, y);
        this.color = color;
    }

    public int getColor() {
        return color;
    }

}
