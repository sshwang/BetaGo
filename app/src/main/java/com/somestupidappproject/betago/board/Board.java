package com.somestupidappproject.betago.board;

/**
 * Created by sapatel on 7/15/2017.
 */

public class Board {
    private Point[][] board;
    private int boardSize;

    public Board(int boardSize) {
        this.board = new Point[boardSize][boardSize];
        this.boardSize = boardSize;
    }

    public Point getPoint(int i, int j) {
        return board[i][j];
    }

    public void setPoint(int i, int j, Point point) {
        board[i][j] = point;
    }

    public int getBoardSize() {
        return boardSize;
    }
}
