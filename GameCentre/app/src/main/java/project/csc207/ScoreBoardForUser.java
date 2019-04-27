package project.csc207;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class ScoreBoardForUser extends AppCompatActivity {

    /**
     * The account manager.
     */
    private AccountManager accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromFile();
        setContentView(R.layout.activity_scoreboard_for_user);

        displayAccountName();
        setScoreTextView();
        addGameCenterListener();
    }

    /**
     * Change text to account name.
     */
    private void displayAccountName() {
        TextView accountNameTextView = findViewById(R.id.AccountName);
        String username = "Account User: "+ accountManager.getCurrentAccount().getUserName();
        accountNameTextView.setText(username);
    }

    private void setScoreTextView(){

        Account account = accountManager.getCurrentAccount();
        TextView slidingTileText = findViewById(R.id.SlidingtileScore);
        TextView catchingText = findViewById(R.id.CatchingBallScore);
        TextView lightsOutText = findViewById(R.id.LightsOutScore);

        Integer slidingTileScore = account.getSlidingTileScores();
        Integer catchingBallScore = account.getCatchBallScore();
        Integer lightsOutScore = account.getLightOutScores();

        String slidingTile = "The Score of Sliding Tile Game: " + slidingTileScore.toString();
        String catchBall = "The Score of Catching Ball Game: " + catchingBallScore.toString();
        String lightsOut = "The Score of Lights Out Game: " + lightsOutScore.toString();

        slidingTileText.setText(slidingTile);
        catchingText.setText(catchBall);
        lightsOutText.setText(lightsOut);


    }

    private void addGameCenterListener(){
        final Button gameCenterButton = findViewById(R.id.GameCenter);
        gameCenterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameCenterIntent =
                        new Intent(ScoreBoardForUser.this,AccountActivity.class);
                startActivity(gameCenterIntent);
            }
        });
    }

    private void loadFromFile() {

        try {
            InputStream inputStream = this.openFileInput(LauncherActivity.SAVE_FILENAME);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                accountManager = (AccountManager) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

}