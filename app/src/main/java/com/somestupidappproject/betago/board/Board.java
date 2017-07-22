package com.somestupidappproject.betago.board;

/**
 * Created by sapatel on 7/15/2017.
 */

public class Board {
    private Stone[][] board;
    private int boardSize;

    public Board(int boardSize) {
        this.board = new Stone[boardSize][boardSize];
        this.boardSize = boardSize;
        initializeBoard();
    }

    public Stone getStone(int i, int j) {
        return board[i][j];
    }

    public void setStone(Stone stone) {
        board[stone.getX()][stone.getY()] = stone;
    }

    public void removeStone(int i, int j) { board[i][j] = new Stone(i, j, Stone.UNTAKEN); }

    public void removeStone(Stone s) {
        if (s.getColor() == 3) {
            return;
        }
        board[s.getX()][s.getY()] = new Stone(s.getX(), s.getY(), Stone.UNTAKEN);
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void initializeBoard() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = new Stone(i, j, Stone.UNTAKEN);
            }
        }
    }

    public void resetBoard() {
        initializeBoard();
    }
}
