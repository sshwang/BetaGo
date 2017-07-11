package com.somestupidappproject.betago;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    protected int[] imageIds = new int[]{
            R.id.point1_1, R.id.point1_2, R.id.point1_3, R.id.point1_4,
            R.id.point1_5, R.id.point1_6, R.id.point1_7, R.id.point1_8,
            R.id.point1_9, R.id.point1_10, R.id.point1_11, R.id.point1_12,
            R.id.point1_13, R.id.point1_14, R.id.point1_15, R.id.point1_16,
            R.id.point1_17, R.id.point1_18, R.id.point1_19,

            R.id.point2_1, R.id.point2_2, R.id.point2_3, R.id.point2_4,
            R.id.point2_5, R.id.point2_6, R.id.point2_7, R.id.point2_8,
            R.id.point2_9, R.id.point2_10, R.id.point2_11, R.id.point2_12,
            R.id.point2_13, R.id.point2_14, R.id.point2_15, R.id.point2_16,
            R.id.point2_17, R.id.point2_18, R.id.point2_19,

            R.id.point3_1, R.id.point3_2, R.id.point3_3, R.id.point3_4,
            R.id.point3_5, R.id.point3_6, R.id.point3_7, R.id.point3_8,
            R.id.point3_9, R.id.point3_10, R.id.point3_11, R.id.point3_12,
            R.id.point3_13, R.id.point3_14, R.id.point3_15, R.id.point3_16,
            R.id.point3_17, R.id.point3_18, R.id.point3_19,

            R.id.point4_1, R.id.point4_2, R.id.point4_3, R.id.point4_4,
            R.id.point4_5, R.id.point4_6, R.id.point4_7, R.id.point4_8,
            R.id.point4_9, R.id.point4_10, R.id.point4_11, R.id.point4_12,
            R.id.point4_13, R.id.point4_14, R.id.point4_15, R.id.point4_16,
            R.id.point4_17, R.id.point4_18, R.id.point4_19,

            R.id.point5_1, R.id.point5_2, R.id.point5_3, R.id.point5_4,
            R.id.point5_5, R.id.point5_6, R.id.point5_7, R.id.point5_8,
            R.id.point5_9, R.id.point5_10, R.id.point5_11, R.id.point5_12,
            R.id.point5_13, R.id.point5_14, R.id.point5_15, R.id.point5_16,
            R.id.point5_17, R.id.point5_18, R.id.point5_19,

            R.id.point6_1, R.id.point6_2, R.id.point6_3, R.id.point6_4,
            R.id.point6_5, R.id.point6_6, R.id.point6_7, R.id.point6_8,
            R.id.point6_9, R.id.point6_10, R.id.point6_11, R.id.point6_12,
            R.id.point6_13, R.id.point6_14, R.id.point6_15, R.id.point6_16,
            R.id.point6_17, R.id.point6_18, R.id.point6_19,

            R.id.point7_1, R.id.point7_2, R.id.point7_3, R.id.point7_4,
            R.id.point7_5, R.id.point7_6, R.id.point7_7, R.id.point7_8,
            R.id.point7_9, R.id.point7_10, R.id.point7_11, R.id.point7_12,
            R.id.point7_13, R.id.point7_14, R.id.point7_15, R.id.point7_16,
            R.id.point7_17, R.id.point7_18, R.id.point7_19,

            R.id.point8_1, R.id.point8_2, R.id.point8_3, R.id.point8_4,
            R.id.point8_5, R.id.point8_6, R.id.point8_7, R.id.point8_8,
            R.id.point8_9, R.id.point8_10, R.id.point8_11, R.id.point8_12,
            R.id.point8_13, R.id.point8_14, R.id.point8_15, R.id.point8_16,
            R.id.point8_17, R.id.point8_18, R.id.point8_19,

            R.id.point9_1, R.id.point9_2, R.id.point9_3, R.id.point9_4,
            R.id.point9_5, R.id.point9_6, R.id.point9_7, R.id.point9_8,
            R.id.point9_9, R.id.point9_10, R.id.point9_11, R.id.point9_12,
            R.id.point9_13, R.id.point9_14, R.id.point9_15, R.id.point9_16,
            R.id.point9_17, R.id.point9_18, R.id.point9_19,

            R.id.point10_1, R.id.point10_2, R.id.point10_3, R.id.point10_4,
            R.id.point10_5, R.id.point10_6, R.id.point10_7, R.id.point10_8,
            R.id.point10_9, R.id.point10_10, R.id.point10_11, R.id.point10_12,
            R.id.point10_13, R.id.point10_14, R.id.point10_15, R.id.point10_16,
            R.id.point10_17, R.id.point10_18, R.id.point10_19,

            R.id.point11_1, R.id.point11_2, R.id.point11_3, R.id.point11_4,
            R.id.point11_5, R.id.point11_6, R.id.point11_7, R.id.point11_8,
            R.id.point11_9, R.id.point11_10, R.id.point11_11, R.id.point11_12,
            R.id.point11_13, R.id.point11_14, R.id.point11_15, R.id.point11_16,
            R.id.point11_17, R.id.point11_18, R.id.point11_19,

            R.id.point12_1, R.id.point12_2, R.id.point12_3, R.id.point12_4,
            R.id.point12_5, R.id.point12_6, R.id.point12_7, R.id.point12_8,
            R.id.point12_9, R.id.point12_10, R.id.point12_11, R.id.point12_12,
            R.id.point12_13, R.id.point12_14, R.id.point12_15, R.id.point12_16,
            R.id.point12_17, R.id.point12_18, R.id.point12_19,

            R.id.point13_1, R.id.point13_2, R.id.point13_3, R.id.point13_4,
            R.id.point13_5, R.id.point13_6, R.id.point13_7, R.id.point13_8,
            R.id.point13_9, R.id.point13_10, R.id.point13_11, R.id.point13_12,
            R.id.point13_13, R.id.point13_14, R.id.point13_15, R.id.point13_16,
            R.id.point13_17, R.id.point13_18, R.id.point13_19,

            R.id.point14_1, R.id.point14_2, R.id.point14_3, R.id.point14_4,
            R.id.point14_5, R.id.point14_6, R.id.point14_7, R.id.point14_8,
            R.id.point14_9, R.id.point14_10, R.id.point14_11, R.id.point14_12,
            R.id.point14_13, R.id.point14_14, R.id.point14_15, R.id.point14_16,
            R.id.point14_17, R.id.point14_18, R.id.point14_19,

            R.id.point15_1, R.id.point15_2, R.id.point15_3, R.id.point15_4,
            R.id.point15_5, R.id.point15_6, R.id.point15_7, R.id.point15_8,
            R.id.point15_9, R.id.point15_10, R.id.point15_11, R.id.point15_12,
            R.id.point15_13, R.id.point15_14, R.id.point15_15, R.id.point15_16,
            R.id.point15_17, R.id.point15_18, R.id.point15_19,

            R.id.point16_1, R.id.point16_2, R.id.point16_3, R.id.point16_4,
            R.id.point16_5, R.id.point16_6, R.id.point16_7, R.id.point16_8,
            R.id.point16_9, R.id.point16_10, R.id.point16_11, R.id.point16_12,
            R.id.point16_13, R.id.point16_14, R.id.point16_15, R.id.point16_16,
            R.id.point16_17, R.id.point16_18, R.id.point16_19,

            R.id.point17_1, R.id.point17_2, R.id.point17_3, R.id.point17_4,
            R.id.point17_5, R.id.point17_6, R.id.point17_7, R.id.point17_8,
            R.id.point17_9, R.id.point17_10, R.id.point17_11, R.id.point17_12,
            R.id.point17_13, R.id.point17_14, R.id.point17_15, R.id.point17_16,
            R.id.point17_17, R.id.point17_18, R.id.point17_19,

            R.id.point18_1, R.id.point18_2, R.id.point18_3, R.id.point18_4,
            R.id.point18_5, R.id.point18_6, R.id.point18_7, R.id.point18_8,
            R.id.point18_9, R.id.point18_10, R.id.point18_11, R.id.point18_12,
            R.id.point18_13, R.id.point18_14, R.id.point18_15, R.id.point18_16,
            R.id.point18_17, R.id.point18_18, R.id.point18_19,

            R.id.point19_1, R.id.point19_2, R.id.point19_3, R.id.point19_4,
            R.id.point19_5, R.id.point19_6, R.id.point19_7, R.id.point19_8,
            R.id.point19_9, R.id.point19_10, R.id.point19_11, R.id.point19_12,
            R.id.point19_13, R.id.point19_14, R.id.point19_15, R.id.point19_16,
            R.id.point19_17, R.id.point19_18, R.id.point19_19};

    protected ImageView[] tileViews = new ImageView[imageIds.length];
    protected Boolean[] isPointTaken = new Boolean[imageIds.length];
    protected boolean isBlacksMove;
    private View clearBoardButton;
    private TextView whoseMoveTextView;
    private static final String TAG = "betago.MainActivity";
    int layoutResId = R.layout.nineteen_by_ninteen_board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutResId);

        for (int i = 0; i < imageIds.length; i++) {
            int id = imageIds[i];
            Log.d(TAG, "finding id " + id);
            tileViews[i] = (ImageView) findViewById(id);
            tileViews[i].setOnClickListener(this);
        }
        Arrays.fill(isPointTaken, Boolean.FALSE);
        isBlacksMove = true;

        whoseMoveTextView = (TextView) findViewById(R.id.whoseMoveTextView);
        whoseMoveTextView.setText("Black's turn");

        clearBoardButton = findViewById(R.id.clearBoardButton);
        clearBoardButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View v) {
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

        int row = ix % 6;
        int col = ix / 6;

        if (isPointTaken[ix] != true) {
            isPointTaken[ix] = true;
            if (isBlacksMove == true) {
                thisImageView.setImageResource(R.drawable.ic_fiber_manual_record_black_48dp);
                whoseMoveTextView.setText("White's turn");
            } else {
                thisImageView.setImageResource(R.drawable.ic_panorama_fish_eye_black_48dp);
                whoseMoveTextView.setText("Black's turn");
            }
            isBlacksMove = !isBlacksMove;
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
        whoseMoveTextView.setText("Black's turn");
    }
}
