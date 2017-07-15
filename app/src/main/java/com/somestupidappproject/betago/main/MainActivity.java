package com.somestupidappproject.betago.main;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.somestupidappproject.betago.R;
import com.somestupidappproject.betago.board.Board;
import com.somestupidappproject.betago.board.LargeBoard;
import com.somestupidappproject.betago.board.Point;
import com.somestupidappproject.betago.moves.Move;
import com.somestupidappproject.betago.utils.BoardUtils;
import com.somestupidappproject.betago.utils.LogicUtil;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Logging stuff
    private static final String TAG = "betago.MainActivity";

    //Game instantiation stuff
    //TODO create different sized boards and create a factory to instantiate them based on user input
    protected Board board = new LargeBoard();
    int boardSize = board.getBoardSize();

    //Image rendering stuff
    protected ImageView[][] tileViews = new ImageView[boardSize][boardSize];
    private View undoMoveButton;
    private TextView whoseMoveTextView;

    //gameplay stuff
    private Move lastMove;
    private ArrayList<Move> previousMoves;
    protected boolean isBlacksMove;

    //game statistic stuff
    private int whiteCaptures = 0;
    private int blackCaptures = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(board.getBoardLayout());

        int[] imageIdsFlat = board.getImageIds();

        // initializing the board view with click listeners
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                int id = i * boardSize + j;
                Log.d(TAG, "finding id " + id);

                // Creating a point object to store image id, and state
                Point point = new Point(i, j);
                point.setImageId(imageIdsFlat[id]);
                point.setTakeState(0);
                board.setPoint(i,j,point);

                // setting up click listeners
                tileViews[i][j] = (ImageView) findViewById(imageIdsFlat[id]);
                tileViews[i][j].setOnClickListener(this);
            }
        }

        previousMoves = new ArrayList<>();

        isBlacksMove = true;

        whoseMoveTextView = (TextView) findViewById(R.id.whoseMoveTextView);
        whoseMoveTextView.setText("Black's Turn");

        undoMoveButton = findViewById(R.id.undoMoveButton);
        undoMoveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (previousMoves.size() > 0) {

                    Move m = previousMoves.get(previousMoves.size() - 1);
                    m.getPoint().revertState();
                    m.getImageView().setImageResource(R.drawable.ic_add_black_48dp);

                    revertPoint(m.getPoint());
                    for (Point p :m.getCapturedPoints()) {
                        revertPoint(p);
                    }

                    previousMoves.remove(previousMoves.size() - 1);
                    isBlacksMove = !isBlacksMove;
                    String whoseMoveText = isBlacksMove ? "Black's Turn" : "White's Turn";
                    whoseMoveTextView.setText(whoseMoveText);
                    if (previousMoves.size() == 0) {
                        undoMoveButton.setEnabled(false);
                    }
                }
            }
        });
        undoMoveButton.setEnabled(false);
    }

    @Override
    public void onClick(View v) {

        Log.d(TAG, "onClick: " + v);
        ImageView thisImageView = (ImageView) v;

        // which view am i? find index in array
        Point ix = new Point();
        int x = -1;
        int y = -1;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {

                if (board.getPoint(i,j).getImageId() == v.getId()) {
                    ix.addCoordinates(i, j);
                    x = i;
                    y = j;
                    break;
                }
            }
        }

        // clicked on something other than game board
        if (x == -1 && y == -1) return;

        Move lm = new Move();
        lm.setImageView(thisImageView);

        if (board.getPoint(x,y).getColor() == 0) {
            if (isBlacksMove == true) {
                tileViews[x][y].setImageResource(R.drawable.ic_fiber_manual_record_black_48dp);
                board.getPoint(x,y).setTakeState(1);
            } else {
                tileViews[x][y].setImageResource(R.drawable.ic_panorama_fish_eye_black_48dp);
                board.getPoint(x,y).setTakeState(2);
            }

            lm.setPoint(board.getPoint(x,y));
            lastMove = lm;
            DidPointCauseCaptureAsyncTask didPointCauseCaptureAsyncTask = new DidPointCauseCaptureAsyncTask(board.getPoint(x, y), board);
            didPointCauseCaptureAsyncTask.execute();

            isBlacksMove = !isBlacksMove;
            String whoseMoveText = isBlacksMove ? "Black's Turn" : "White's Turn";
            whoseMoveTextView.setText(whoseMoveText);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.clearBoard) {
            resetGame();
        }

        return super.onOptionsItemSelected(item);
    }

    private void revertPoint(Point p) {
        int x = p.getX();
        int y = p.getY();
        board.getPoint(x,y).revertState();
        if (board.getPoint(x,y).getColor() == 1) {
            tileViews[x][y].setImageResource(R.drawable.ic_fiber_manual_record_black_48dp);
        } else if (board.getPoint(x,y).getColor() == 2) {
            tileViews[x][y].setImageResource(R.drawable.ic_panorama_fish_eye_black_48dp);
        } else {
            tileViews[x][y].setImageResource(R.drawable.ic_add_black_48dp);
        }
    }

    private void resetGame() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board.getPoint(i,j).getColor() != 0) {
                    tileViews[i][j].setImageResource(R.drawable.ic_add_black_48dp);
                }
            }
        }
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Point point = new Point(i, j);
                board.setPoint(i,j,point);
            }
        }

        isBlacksMove = true;
        whoseMoveTextView.setText("Black's Turn");
    }

    class DidPointCauseCaptureAsyncTask extends AsyncTask<Void, Void, ArrayList<Point>> {

        private Board board;
        private Point point;

        public DidPointCauseCaptureAsyncTask(Point point, Board board) {
            this.point = point;
            this.board = board;
        }

        @Override
        protected ArrayList<Point> doInBackground(Void... params) {

            ArrayList<Point> deadPoints = new ArrayList<>();
            if (point == null) {
                return deadPoints;
            }

            // Get all neighbors and also check if the placed point is surrounded
            Set<Point> neighbors = BoardUtils.getNeighbors(point, board);

            for (Point neighbor : neighbors) {
                if (!LogicUtil.isAlive(neighbor, board)){
                    // Point is dead which means it's group is also dead
                    // Find the group here
                    Set<Point> group = LogicUtil.findGroup(neighbor, board);
                    deadPoints.addAll(group);
                }
            }

            return deadPoints;
        }

        @Override
        protected void onPostExecute(ArrayList<Point> deadPoints) {
            lastMove.setCapturedPoints(deadPoints);
            previousMoves.add(lastMove);
            undoMoveButton.setEnabled(true);
            for (Point deadPoint : deadPoints) {
                int x = deadPoint.getX();
                int y = deadPoint.getY();
                tileViews[x][y].setImageResource(R.drawable.ic_add_black_48dp);
                board.getPoint(x,y).setTakeState(0);
                Log.d(TAG, "DEAD");
            }
        }
    }
}
