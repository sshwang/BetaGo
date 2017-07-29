package com.somestupidappproject.betago.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.somestupidappproject.betago.R;

public class EndGameActivity extends AppCompatActivity {

    TextView winnerText;
    View newGameButton;
    View backToGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
        winnerText = (TextView) findViewById(R.id.winnerIs);
        newGameButton = (View) findViewById(R.id.newGameButton);
        backToGameButton = (View) findViewById(R.id.backToGameButton);

        setNewGameButton();
        setBackToGameButton();
        setWinnerText();
    }

    private void setNewGameButton() {
        newGameButton.setOnClickListener(v -> {
            ResultReceiver resultReceiver = getIntent().getParcelableExtra("endLastGame");
            resultReceiver.send(1, new Bundle());

            //start a new game
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void setBackToGameButton() {
        backToGameButton.setOnClickListener(v -> {
            //end this activity and return to previous activity
            finish();
        });
    }

    private void setWinnerText() {
        Bundle bundle = getIntent().getExtras();
        String winner = "";
        if(bundle != null) {
            winner = bundle.getString("winnerText");
            if (winner.equals("TIE")) {
                winner = "Tie Game!";
            } else {
                winner = winner + " Wins!";
            }
            winnerText.setText(winner);
        }
    }

}
