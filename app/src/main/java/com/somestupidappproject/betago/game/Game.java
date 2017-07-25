package com.somestupidappproject.betago.game;

import android.util.Log;

import com.somestupidappproject.betago.board.Board;
import com.somestupidappproject.betago.board.Stone;
import com.somestupidappproject.betago.moves.Move;
import com.somestupidappproject.betago.utils.BoardUtils;
import com.somestupidappproject.betago.utils.LogicUtil;

import java.util.ArrayList;
import java.util.Set;
import java.util.Stack;

/**
 * Created by evansapienza on 7/21/17.
 */

public class Game {

    private static final String TAG = Game.class.getName();

    //gameplay stuff
    public Board board;
    public Move lastMove;
    public Stack<Move> previousMoves;
    public boolean isBlacksMove;

    //game statistic stuff
    private int whiteCaptures = 0;
    private int blackCaptures = 0;

    public Game(Board board) {
        this.board = board;
        this.previousMoves = new Stack<>();
        this.isBlacksMove = true;
    }

    public boolean undoMostRecentMove() {
        if (previousMoves.size() > 0) {

            // Remove the just placed stones.
            board.removeStone(lastMove.getStone());

            // Did that last stone results in captures? Put those pieces back.
            for (Stone s : lastMove.getCapturedStones()) {
                board.setStone(s);
            }

            // Go back a move.
            previousMoves.pop();
            if (previousMoves.size() != 0)
                lastMove = previousMoves.lastElement();

            // Switch players.
            isBlacksMove = !isBlacksMove;
            return true;
        } else {
            return false;
        }
    }

    public boolean playStone(Stone stone) {
        if (BoardUtils.isValidMove(stone, board, isBlacksMove, !previousMoves.isEmpty() ? previousMoves.lastElement() : null)) {
            isBlacksMove = !isBlacksMove;
            previousMoves.push(new Move(stone));
            lastMove = previousMoves.lastElement();
            if (stone.getColor() == Stone.UNTAKEN) { // This was a pass. Skip setting the stone and checking for capture
                // TODO End Game logic. If last move was also a pass end the game here
                return true;
            }
            board.setStone(stone);
            didStoneCauseCapture(stone);
            return true;
        } else {
            return false;
        }
    }

    public int getCurrentStoneColor() {
        if (isBlacksMove) {
            return Stone.BLACK;
        } else {
            return Stone.WHITE;
        }
    }

    private void didStoneCauseCapture(Stone stone) {
        ArrayList<Stone> deadStones = BoardUtils.getDeadStones(stone, board);
        lastMove.setCapturedStones(deadStones);
        for (Stone deadStone : deadStones) {
            board.removeStone(deadStone);
            Log.d(TAG, "DEAD");
        }
    }
}
