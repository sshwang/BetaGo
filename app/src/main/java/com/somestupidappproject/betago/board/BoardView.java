package com.somestupidappproject.betago.board;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.somestupidappproject.betago.R;
import com.somestupidappproject.betago.game.Game;
import com.somestupidappproject.betago.main.MainActivity;

import java.util.ArrayList;

/**
 * Created by evansapienza on 7/19/17.
 */

public class BoardView extends RelativeLayout implements View.OnTouchListener {

    private static final String TAG = BoardView.class.getName();
    private static final int stonePixelWidth = 96;

    Paint paint = new Paint();

    public int boardSize;
    public int maxSquareSize;
    public int padding = 100;
    public int interval;
    public Point[][] boardCoords;
    private ArrayList<Pair<View,Point>> renderedStones = new ArrayList<Pair<View,Point>>();
    private Game game;
    private Board board;
    private MainActivity main;

    public float strokeWidth = 5f;

    private void init(Board board, int maxSquareSize) {
        paint.setColor(Color.BLACK);

        paint.setStrokeWidth(strokeWidth);

        this.setOnTouchListener(this);

        this.boardSize = board.getBoardSize();
        this.maxSquareSize = maxSquareSize;
//        this.interval = Math.round((maxSquareSize - (this.strokeWidth * (this.boardSize - 1))) / (this.boardSize - 1));
        this.interval = (maxSquareSize - (padding * 2)) / (this.boardSize - 1);

        // Setup coordinate system
        this.boardCoords = new Point[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                boardCoords[i][j] = new Point(i * interval + padding, j * interval + padding);
            }
        }

        this.setId(R.id.board);

        //Setup board layout parameters.
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        this.setLayoutParams(layoutParams);
        this.setBackgroundColor(Color.WHITE);

        this.main = (MainActivity) this.getContext();

    }

    public BoardView(Context context, Game game, int maxSquareSize) {
        super(context);
        this.game = game;
        this.board = game.board;
        this.init(board, maxSquareSize);
    }

    private void renderStone(Point point, int color) {

        StoneView stoneView = new StoneView(this.getContext(), color);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(stonePixelWidth, stonePixelWidth);
        layoutParams.leftMargin = point.getX() - stonePixelWidth / 2;
        layoutParams.topMargin = point.getY() - stonePixelWidth /2;
        this.renderedStones.add(new Pair(stoneView, point));
        this.addView(stoneView, layoutParams);
    }

    public void renderBoard() {
        this.removeAllViews();
        for (int i = 0; i < board.getBoardSize(); i++) {
            for (int j = 0; j < board.getBoardSize(); j++) {
                Stone s = board.getStone(i, j);
                if (s != null) {
                    renderStone(boardIndexToCoords(s), s.getColor());
                }
            }
        }
    }

    private void drawBoard(Canvas canvas) {
        for (int i = 0; i < boardSize; i++) {

            // Horizontal lines.
            canvas.drawLine(boardCoords[0][i].getX(), boardCoords[0][i].getY(), boardCoords[boardSize - 1][0].getX(), boardCoords[0][i].getY(), paint);

            // Vertical lines.
            canvas.drawLine(boardCoords[i][0].getX(), boardCoords[i][0].getY(), boardCoords[i][0].getX(), boardCoords[0][boardSize - 1].getY(), paint);
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        drawBoard(canvas);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        Log.d(TAG, "CLICK: x: " + event.getX() + ", y: " + event.getY());
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            Point closestPoint = getClosestClickedPoint((int) event.getX(), (int) event.getY());
            Log.d(TAG, "CLOSEST POINT: " + closestPoint.getX() + "," + closestPoint.getY());

            Point boardIndex = coordsToBoardIndex(closestPoint);

            Stone newStone = new Stone(boardIndex.getX(), boardIndex.getY(), game.getCurrentStoneColor());
            if (game.playStone(newStone)) {
                renderBoard();
                main.updateMoveText();
                if (game.previousMoves.size() > 0)
                    main.setUndoButton(true);
            }
        }
        return true;
    }

    private Point getClosestClickedPoint(int x, int y) {
        int closestX = -1;
        int closestY = -1;

        int smallestDiff = Integer.MAX_VALUE;

        for (int i = 0; i < boardSize; i++) {
            int diff = Math.abs(boardCoords[i][0].getX() - x);
            if (diff < smallestDiff) {
                closestX = boardCoords[i][0].getX();
                smallestDiff = diff;
            }
        }

        smallestDiff = Integer.MAX_VALUE;
        for (int i = 0; i < boardSize; i++) {
            int diff = Math.abs(boardCoords[0][i].getY() - y);
            if (diff < smallestDiff) {
                closestY = boardCoords[0][i].getY();
                smallestDiff = diff;
            }
        }

        return new Point(closestX, closestY);
    }

    private Point coordsToBoardIndex(Point coords) {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Point currentCoords = boardCoords[i][j];
                if (currentCoords.getX() == coords.getX() && currentCoords.getY() == coords.getY())
                    return new Point(i,j);
            }
        }

        return new Point(-1,-1);
    }


    private Point boardIndexToCoords(Point boardIndex) {
        return new Point(boardIndex.getX() * interval + padding, boardIndex.getY() * interval + padding);
    }
}
