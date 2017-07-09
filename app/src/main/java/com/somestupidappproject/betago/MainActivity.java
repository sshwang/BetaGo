package com.somestupidappproject.betago;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    protected int[] imageIds = new int[]{ R.id.point1, R.id.point2, R.id.point3, R.id.point4,
            R.id.point5, R.id.point6, R.id.point7, R.id.point8, R.id.point9, R.id.point10,
            R.id.point11, R.id.point12, R.id.point13, R.id.point14, R.id.point15, R.id.point16,
            R.id.point17, R.id.point18, R.id.point19, R.id.point20, R.id.point21, R.id.point22,
            R.id.point23, R.id.point24, R.id.point25, R.id.point26, R.id.point27, R.id.point28,
            R.id.point29, R.id.point30, R.id.point31, R.id.point32, R.id.point33, R.id.point34,
            R.id.point35, R.id.point36};
    protected ImageView[] tileViews = new ImageView[imageIds.length];
    protected Boolean[] isPointTaken = new Boolean[imageIds.length];
    protected boolean isBlacksMove;
    private View clearBoardButton, confirmMoveButton, cancelMoveButton;
    private Integer lastClickedIndex;
    private ImageView lastImageView;
    private TextView whoseMoveTextView;

    private static final String TAG = "betago.MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < imageIds.length; i++) {
            int id = imageIds[i];
            Log.d(TAG, "finding id " + id);
            tileViews[i] = (ImageView) findViewById(id);
            tileViews[i].setOnClickListener(this);
        }
        Arrays.fill(isPointTaken, Boolean.FALSE);
        isBlacksMove = true;

        whoseMoveTextView = (TextView) findViewById(R.id.whoseMoveTextView);
        whoseMoveTextView.setText("Black's Turn");

        clearBoardButton = findViewById(R.id.clearBoardButton);
        clearBoardButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

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
                isPointTaken[lastClickedIndex] = false;
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
        int ix = -1;
        for (int i = 0; i < imageIds.length; i++) {
            if (imageIds[i] == v.getId()) {
                ix = i;
                break;
            }
        }

        // clicked on something other than game board
        if (ix == -1) return;

        lastImageView = thisImageView;
        lastClickedIndex = ix;

        int row = ix % 6;
        int col = ix / 6;

        if (isPointTaken[ix] != true) {
            isPointTaken[ix] = true;
            if (isBlacksMove == true) {
                thisImageView.setImageResource(R.drawable.ic_fiber_manual_record_black_48dp);
            } else {
                thisImageView.setImageResource(R.drawable.ic_panorama_fish_eye_black_48dp);
            }
            whoseMoveTextView.setText("Confirm or Cancel");
            toggleButtons();
        }

    }

    private void resetGame() {
        for (int i = 0; i < isPointTaken.length; i++) {
            if (isPointTaken[i] == true) {
                tileViews[i].setImageResource(R.drawable.ic_add_black_48dp);
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
