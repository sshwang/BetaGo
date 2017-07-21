package com.somestupidappproject.betago.board;

import android.content.Context;
import android.view.View;

import com.somestupidappproject.betago.R;

/**
 * Created by evansapienza on 7/20/17.
 */

public class StoneView extends View {

    public Point point;

    public StoneView(Context context, int color) {
        super(context);
        if (color == Stone.BLACK)
            setBackgroundResource(R.drawable.ic_fiber_manual_record_black_48dp);
        else
            setBackgroundResource(R.drawable.ic_panorama_fish_eye_black_48dp);
    }
}
