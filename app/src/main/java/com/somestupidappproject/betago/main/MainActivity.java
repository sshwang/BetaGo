package com.somestupidappproject.betago.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.somestupidappproject.betago.R;
import com.somestupidappproject.betago.board.Board;
import com.somestupidappproject.betago.board.BoardView;
import com.somestupidappproject.betago.game.Game;


public class MainActivity extends AppCompatActivity {

    //Logging stuff
    private static final String TAG = MainActivity.class.getName();

    //Game instantiation stuff
    Board board = new Board(9); // change board size here, 9, 13, 19.

    private View undoMoveButton;
    private TextView whoseMoveTextView;
    private int maxSquareSize;

    Game game;
    BoardView boardView;
    RelativeLayout boardContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board);

        whoseMoveTextView = (TextView) findViewById(R.id.whoseMoveTextView);
        whoseMoveTextView.setText("Black's Turn");

        undoMoveButton = findViewById(R.id.undoMoveButton);

        boardContainer = (RelativeLayout) findViewById(R.id.board_container);

        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        maxSquareSize = Math.min(displayMetrics.widthPixels, displayMetrics.heightPixels);

        RelativeLayout.LayoutParams layoutParams =
                new RelativeLayout.LayoutParams(maxSquareSize, maxSquareSize);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        boardContainer.setLayoutParams(layoutParams);

        game = new Game(board);

        boardView = new BoardView(this, game, maxSquareSize);

        boardContainer.addView(boardView);

        undoMoveButton.setOnClickListener(v -> {
            if (game.undoMostRecentMove()) {
                String whoseMoveText = game.isBlacksMove ? "Black's Turn" : "White's Turn";
                whoseMoveTextView.setText(whoseMoveText);
                if (game.previousMoves.size() == 0) {
                    setUndoButton(false);
                }
                boardView.renderBoard();
            }
        });
        setUndoButton(false);
    }

    public void updateMoveText() {
        String whoseMoveText = game.isBlacksMove ? "Black's Turn" : "White's Turn";
        whoseMoveTextView.setText(whoseMoveText);
    }

    public void updateMoveTextInvalid() {
        String errorText = game.isBlacksMove ? "Invalid Move: Black's Turn" : "Invalid Move: White's Turn";
        whoseMoveTextView.setText(errorText);
    }

    public void setUndoButton(boolean enabled) {
        undoMoveButton.setEnabled(enabled);
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

    private void resetGame() {
        setUndoButton(false);
        board.resetBoard();
        game = new Game(board);
        boardView.removeAllViews();
        boardContainer.removeView(boardView);
        boardView = new BoardView(this, game, maxSquareSize);
        boardContainer.addView(boardView);
        whoseMoveTextView.setText("Black's Turn");
    }
}
