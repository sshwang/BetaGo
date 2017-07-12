package com.somestupidappproject.betago;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    int boardSize = 19;
    protected int[][] imageIds = new int[boardSize][boardSize];

    protected ImageView[][] tileViews = new ImageView[boardSize][boardSize];
    protected Boolean[][] isPointTaken = new Boolean[boardSize][boardSize];
    protected boolean isBlacksMove;
    private View confirmMoveButton, cancelMoveButton;
    private Point lastClickedIndex;
    private ImageView lastImageView;
    private TextView whoseMoveTextView;
    private static final String TAG = "betago.MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                int id = imageIds[i][j];
                Log.d(TAG, "finding id " + id);
                tileViews[i][j] = (ImageView) findViewById(id);
                tileViews[i][j].setOnClickListener(this);
            }
        }
        Arrays.fill(isPointTaken, Boolean.FALSE);
        isBlacksMove = true;

        whoseMoveTextView = (TextView) findViewById(R.id.whoseMoveTextView);
        whoseMoveTextView.setText("Black's Turn");

        confirmMoveButton = findViewById(R.id.confirmMoveButton);
        confirmMoveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                isBlacksMove = !isBlacksMove;
                String whoseMoveText = isBlacksMove ? "Black's Turn" : "White's Turn";
                whoseMoveTextView.setText(whoseMoveText);
                toggleButtons();
            }
        });

        cancelMoveButton = findViewById(R.id.cancelMoveButton);
        cancelMoveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                isPointTaken[lastClickedIndex.X][lastClickedIndex.Y] = false;
                lastImageView.setImageResource(R.drawable.ic_add_black_48dp);
                String whoseMoveText = isBlacksMove ? "Black's Turn" : "White's Turn";
                whoseMoveTextView.setText(whoseMoveText);
                toggleButtons();
            }
        });
        toggleButtons();

    }

    @Override
    public void onClick(View v) {
        if (confirmMoveButton.isEnabled()) return;

        Log.d(TAG, "onClick: " + v);
        ImageView thisImageView = (ImageView) v;

        // which view am i? find index in array
        Point ix = new Point(-1,-1);
        for (int i = 0; i < boardSize; i++) {
            for (int j=0; j < boardSize; j++ ) {
                if (imageIds[i][j] == v.getId()) {
                    ix.addCoordinates(i,j);
                    break;
                }
            }
        }

        // clicked on something other than game board
        if (ix.X == -1 && ix.Y == -1) return;

        lastImageView = thisImageView;
        lastClickedIndex = ix;


        if (isPointTaken[ix.X][ix.Y] != true) {
            isPointTaken[ix.X][ix.Y] = true;
            if (isBlacksMove == true) {
                thisImageView.setImageResource(R.drawable.ic_fiber_manual_record_black_48dp);
            } else {
                thisImageView.setImageResource(R.drawable.ic_panorama_fish_eye_black_48dp);
            }
            whoseMoveTextView.setText("Confirm or Cancel");
            toggleButtons();
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


    private void resetGame() {
        for (int i = 0; i < boardSize; i++) {
            for (int j =0; j < boardSize; j++) {
                if (isPointTaken[i][j] == true) {
                    tileViews[i][j].setImageResource(R.drawable.ic_add_black_48dp);
                }
            }
        }
        Arrays.fill(isPointTaken, Boolean.FALSE);
        isBlacksMove = true;
        whoseMoveTextView.setText("Black's Turn");
    }

    private void toggleButtons() {
        confirmMoveButton.setEnabled(!confirmMoveButton.isEnabled());
        cancelMoveButton.setEnabled(!cancelMoveButton.isEnabled());
    }
}