package com.somestupidappproject.betago.board;

import java.util.ArrayList;
import java.util.List;

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
        if (stone.getColor() == Stone.UNTAKEN) {
            return;
        }
        board[stone.getX()][stone.getY()] = stone;
    }

    public void removeStone(int i, int j) { board[i][j] = new Stone(i, j, Stone.UNTAKEN); }

    public void removeStone(Stone s) {
        if (s.getColor() == Stone.UNTAKEN) {
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

    public List<Stone> getStones() {
        List<Stone> stoneList = new ArrayList<>();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j].getColor() != Stone.UNTAKEN) {
                    stoneList.add(board[i][j]);
                }
            }
        }
        return stoneList;
    }
}
