package project.csc207;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

/**
 * the page show up when the game is over, user can choose go back to GameCenter and select
 * new games
 */
public class ScoreResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_score_result);

        TextView scoreLabel = findViewById(R.id.newscore);
        TextView highScoreLabel = findViewById(R.id.highScore);

        setScoresToText(scoreLabel, highScoreLabel);
        addGoBackToGameCenterButton();
    }

    /**
     * Set the Score Based on the Array list of scores on Intent Extra and set the TextView to
     * corresponding label.
     *
     * @param scoreLabel     The label used to set text view of Score
     * @param highScoreLabel the label used to set text view of Highest score
     */
    private void setScoresToText(TextView scoreLabel, TextView highScoreLabel) {
        int indexOfScore = 0;
        int indexOfRecord = 1;

        ArrayList<Integer> scores = getIntent().getIntegerArrayListExtra("scores");
        String score = scores.get(indexOfScore).toString();
        String highScore = scores.get(indexOfRecord).toString();
        String strForScore = "Your Score : " + score;
        String strForHighScore = "Your Record Score : " + highScore;
        if (scores.get(indexOfScore) > scores.get(indexOfRecord)) {
            strForScore = "Congratulation!!! New Records: " + score;
            strForHighScore = "Your New Record Score : " + score;
            scoreLabel.setText(strForScore);
            highScoreLabel.setText(strForHighScore);

        } else {
            scoreLabel.setText(strForScore);
            highScoreLabel.setText(strForHighScore);
        }
    }

    private void addGoBackToGameCenterButton() {
        final Button playAgainButton = findViewById(R.id.BackToGameCenter);
        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGameCenter();
            }
        });
    }

    /**
     * Go back to GameCenter.
     */
    void goToGameCenter() {
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }
}

