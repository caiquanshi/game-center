package project.csc207.catchingball;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import project.csc207.R;


/*
 *  A class that build the basis of the game named catching the ball
 *
 *  This was adapted from a video from codingwithsara on Youtube
 *  link here:
 *   https://www.youtube.com/watch?v=ojD6ZDi2ep8&list=PLRdMAPi4QUfbIg6dRXf56cbMfeYtTdNSA
 */

public class CatchBallStart extends AppCompatActivity {

    /**
     * build the outline of the background
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catchballstart);
        addScoreBoardListener();
    }

    /**
     * start the game
     */
    public void startGame(View view) {
        startActivity(new Intent(getApplicationContext(), CatchBall.class));
    }


    /**
     * Activate ScoreBoard button
     */
    private void addScoreBoardListener() {
        Button scoreBoardButton = findViewById(R.id.catchBallScoreBoard);
        scoreBoardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scoreBoardIntent = new Intent(CatchBallStart.this,
                        ScoreBoardForCatchingBall.class);
                startActivity(scoreBoardIntent);
            }
        });
    }
}
