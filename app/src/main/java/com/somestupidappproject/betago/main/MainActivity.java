package com.somestupidappproject.betago.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.os.ResultReceiver;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.drive.Drive;
import com.somestupidappproject.betago.R;
import com.somestupidappproject.betago.board.Board;
import com.somestupidappproject.betago.board.BoardView;
import com.somestupidappproject.betago.board.Stone;
import com.somestupidappproject.betago.game.Game;
import com.somestupidappproject.betago.player.Player;
import com.somestupidappproject.betago.utils.ScoreCount;
import com.somestupidappproject.betago.utils.ScoringUtils;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.Multiplayer;
import com.google.android.gms.games.multiplayer.OnInvitationReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.turnbased.OnTurnBasedMatchUpdateReceivedListener;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatchConfig;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer;
import com.google.example.games.basegameutils.BaseGameUtils;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    //Logging stuff
    private static final String TAG = MainActivity.class.getName();

    // the client to chat with google
    private GoogleApiClient mGoogleApiClient;

    // some connection variables
    private static int RC_SIGN_IN = 9001;

    private boolean mResolvingConnectionFailure = false;
    private boolean mAutoStartSignInFlow = true;
    private boolean mSignInClicked = false;

    //Game instantiation stuff
    Board board = new Board(19); // change board size here, 9, 13, 19.

    private View undoMoveButton, passTurnButton;
    private TextView whoseMoveTextView;
    private TextView blackScoreText;
    private TextView whiteScoreText;
    private int maxSquareSize;

    Game game;
    BoardView boardView;
    RelativeLayout boardContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create the Google Api Client with access to the Play Game services
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .build();

        setContentView(R.layout.board);

        whoseMoveTextView = (TextView) findViewById(R.id.whoseMoveTextView);
        blackScoreText = (TextView) findViewById(R.id.blackScoreText);
        whiteScoreText = (TextView) findViewById(R.id.whiteScoreText);
        undoMoveButton = findViewById(R.id.undoMoveButton);

        passTurnButton = findViewById(R.id.passTurnButton);

        boardContainer = (RelativeLayout) findViewById(R.id.board_container);

        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        maxSquareSize = Math.min(displayMetrics.widthPixels, displayMetrics.heightPixels);

        RelativeLayout.LayoutParams layoutParams =
                new RelativeLayout.LayoutParams(maxSquareSize, maxSquareSize);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        boardContainer.setLayoutParams(layoutParams);

        game = new Game(board);

        updateMoveText();

        boardView = new BoardView(this, game, maxSquareSize);

        boardContainer.addView(boardView);

        undoMoveButton.setOnClickListener(v -> {
            if (game.undoMostRecentMove()) {
                updateMoveText();
                if (game.previousMoves.size() == 0) {
                    setUndoButton(false);
                }
                boardView.renderBoard();
            }
        });
        setUndoButton(false);

        passTurnButton.setOnClickListener(v -> {
            Stone stone = new Stone(-1, -1, Stone.UNTAKEN); // Create Pass Stone
            game.playStone(stone);
            updateMoveText();
            setUndoButton(true);
            if (game.isGameOver()) {
                transitionToEndGameScreen();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public  void onConnected(Bundle connectionHint) {
        // The player is signed in. Hide the sign-in button and allow the
        // player to proceed.
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (mResolvingConnectionFailure) {
            // already resolving
            return;
        }

        // if the sign-in button was clicked or if auto sign-in is enabled,
        // launch the sign-in flow
        if (mSignInClicked || mAutoStartSignInFlow) {
            mAutoStartSignInFlow = false;
            mSignInClicked = false;
            mResolvingConnectionFailure = true;

            // Attempt to resolve the connection failure using BaseGameUtils.
            // The R.string.signin_other_error value should reference a generic
            // error string in your strings.xml file, such as "There was
            // an issue with sign-in, please try again later."
            //if (!BaseGameUtils.resolveConnectionFailure(this,
            //        mGoogleApiClient, connectionResult,
            //        RC_SIGN_IN, R.string.signin_other_error)) {
            //    mResolvingConnectionFailure = false;
            //}
        }

        // Put code here to display the sign-in button
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            mSignInClicked = false;
            mResolvingConnectionFailure = false;
            if (resultCode == RESULT_OK) {
                mGoogleApiClient.connect();
            } else {
                // Bring up an error dialog to alert the user that sign-in
                // failed. The R.string.signin_failure should reference an error
                // string in your strings.xml file that tells the user they
                // could not be signed in, such as "Unable to sign in."
                //BaseGameUtils.showActivityResultError(this,
                //        requestCode, resultCode, R.string.signin_failure);
            }
        }
    }

    // Call when the sign-in button is clicked
    private void signInClicked() {
        mSignInClicked = true;
        mGoogleApiClient.connect();
    }

    // Call when the sign-out button is clicked
    private void signOutclicked() {
        mSignInClicked = false;
        Games.signOut(mGoogleApiClient);
    }

    @Override
    public void onConnectionSuspended(int i) {
        // Attempt to reconnect
        mGoogleApiClient.connect();
    }

    public void updateMoveText() {
        whoseMoveTextView.setText(getTurnText());
    }

    public void updateMoveTextInvalid() {
        String errorText = "Invalid Move: ".concat(getTurnText());
        whoseMoveTextView.setText(errorText);
    }

    public void updateCapturedPiecesText() {
        ScoreCount capturesCount = ScoringUtils.getCaptureScores(game.previousMoves);
        String blackScore = Player.BLACK + ": " + capturesCount.getScore(Player.BLACK);
        String whiteScore = Player.WHITE + ": " + capturesCount.getScore(Player.WHITE);
        blackScoreText.setText(blackScore);
        whiteScoreText.setText(whiteScore);
    }

    private String getTurnText() {
        return game.isBlacksMove ? Player.BLACK + "'s Turn" : Player.WHITE + "'s Turn";
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
        } else if (id == R.id.resizeBoard) {
            AlertDialog.Builder b = new AlertDialog.Builder(this);
            b.setTitle("Example");
            String[] types = {"4", "9", "13", "19"};
            b.setItems(types, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    switch(which){
                        case 0:
                            board = new Board(4);
                            break;
                        case 1:
                            board = new Board(9);
                            break;
                        case 2:
                            board = new Board(13);
                            break;
                        case 3:
                            board = new Board(19);
                            break;
                    }
                    resetGame();
                }
            });
            b.show();
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
        whoseMoveTextView.setText(Player.BLACK + "'s Turn");
    }

    private void transitionToEndGameScreen() {
        Intent nextActivity = new Intent(this, EndGameActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("winnerText", ScoringUtils.getWinningPlayer(board, game.previousMoves));
        nextActivity.putExtras(bundle);
        nextActivity.putExtra("endLastGame", new ResultReceiver(null) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                MainActivity.this.finish();
            }
        });
        startActivityForResult(nextActivity,1);
    }
}
