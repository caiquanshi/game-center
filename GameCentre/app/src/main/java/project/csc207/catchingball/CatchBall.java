package project.csc207.catchingball;

import android.content.Intent;
import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import project.csc207.Account;
import project.csc207.AccountManager;
import project.csc207.LauncherActivity;
import project.csc207.R;
import project.csc207.SaveLoad;
import project.csc207.ScoreResult;


/*
 *  A class that build the basis of the game named catching the ball
 *
 *  This was adapted from a video from codingwithsara on Youtube
 *  link here:
 *   https://www.youtube.com/watch?v=ojD6ZDi2ep8&list=PLRdMAPi4QUfbIg6dRXf56cbMfeYtTdNSA
 */

public class CatchBall extends AppCompatActivity implements SaveLoad {

    /**
     * the score, starting text, and elements of images
     */
    private TextView scoreLabel;
    private TextView startLabel;
    private ImageView box;
    private ImageView orange;
    private ImageView pink;
    private ImageView black;

    /**
     * Size of the screen
     */
    private int frameHeight;
    private int boxSize;
    private int screenWidth;
    private int screenHeight;

    /**
     * Position of the obstacles
     */
    private int boxY;
    private int orangeX;
    private int orangeY;
    private int pinkX;
    private int pinkY;
    private int blackX;
    private int blackY;

    /**
     * Speed of the obstacles
     */
    private int boxSpeed;
    private int orangeSpeed;
    private int pinkSpeed;
    private int blackSpeed;

    /**
     * Score of the game
     */
    private int score;

    /**
     * Initialize Class
     */
    private Handler handler = new Handler();
    private Timer timer = new Timer();


    /**
     * Status Check
     */
    private boolean action_flg = false;
    private boolean start_flg = false;

    private AccountManager accountManager;

    /**
     * set up for game
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromFile(LauncherActivity.SAVE_FILENAME);
        score = accountManager.getCurrentAccount().getCatchballScoreForSave();
        setContentView(R.layout.activity_catchball);
        setGameViews();
        setBallAndBoxSpeedAndPosition();
    }

    /**
     * set the speed and position of ball and box
     */
    private void setBallAndBoxSpeedAndPosition() {
        boxSpeed = Math.round(screenHeight / 60);
        orangeSpeed = Math.round(screenWidth / 60);
        pinkSpeed = Math.round(screenWidth / 36);
        blackSpeed = Math.round(screenWidth / 45);

        orange.setX(-80);
        orange.setY(-80);
        pink.setX(-80);
        pink.setY(-80);
        black.setX(-80);
        black.setY(-80);
        String text ="Score : " + Integer.toString(score);
        scoreLabel.setText(text);
    }

    /**
     * set the View of game materials and the Window of the Game
     */
    private void setGameViews() {
        scoreLabel = findViewById(R.id.scoreLabel);
        startLabel = findViewById(R.id.startLabel);
        box = findViewById(R.id.box);
        orange = findViewById(R.id.orange);
        pink = findViewById(R.id.pink);
        black = findViewById(R.id.black);
        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);

        screenWidth = size.x;
        screenHeight = size.y;
    }

    /**
     * change the position while hitting the obstacles
     */
    public void changePos() {

        hitOrange();
        hitPink();
        hitBlack();
        changingParticlePosition();

        if (action_flg) {
            boxY -= boxSpeed;

        } else {
            boxY += boxSpeed;
        }

        // Check box position.
        if (boxY < 0) { boxY = 0;}
        if (boxY > frameHeight - boxSize){ boxY = frameHeight - boxSize; }

        box.setY(boxY);
        String str = "Score : " + score;
        scoreLabel.setText(str);

    }

    /**
     * changing the position of Particles
     */
    private void changingParticlePosition() {
        orangeX -= orangeSpeed;
        if (orangeX < 0) {
            orangeX = screenWidth + 20;
            orangeY = (int) Math.floor(Math.random() * (frameHeight - orange.getHeight()));
        }
        orange.setX(orangeX);
        orange.setY(orangeY);


        blackX -= blackSpeed;
        if (blackX < 0) {
            blackX = screenWidth + 10;
            blackY = (int) Math.floor(Math.random() * (frameHeight - black.getHeight()));
        }
        black.setX(blackX);
        black.setY(blackY);


        pinkX -= pinkSpeed;
        if (pinkX < 0) {
            pinkX = screenWidth + 5000;
            pinkY = (int) Math.floor(Math.random() * (frameHeight - pink.getHeight()));
        }
        pink.setX(pinkX);
        pink.setY(pinkY);
    }

    /**
     * update the Score of current game to Account and jump to Score Result Page if the box hit
     * the black particle
     */
    private void hitBlack() {
        int blackCenterX = blackX + black.getWidth() / 2;
        int blackCenterY = blackY + black.getHeight() / 2;

        if (0 <= blackCenterX && blackCenterX <= boxSize &&
                boxY <= blackCenterY && blackCenterY <= boxY + boxSize) {
            timer.cancel();
            timer = null;

            Account account = accountManager.getCurrentAccount();
            account.setCatchballScoreForSave(0);
            int record = account.getCatchBallScore();
            account.setCatchBallScore(score);

            accountManager.updateAccount();
            saveToFile(LauncherActivity.SAVE_FILENAME);

            ArrayList<Integer> scores = new ArrayList<>();
            scores.add(score);
            scores.add(record);
            goToScoreResult(scores);
        }
    }

    /**
     * Add 30 points to score if the box hit the pink particle, and save the score of current game
     */
    private void hitPink() {
        int pinkCenterX = pinkX + pink.getWidth() / 2;
        int pinkCenterY = pinkY + pink.getHeight() / 2;

        if (0 <= pinkCenterX && pinkCenterX <= boxSize &&
                boxY <= pinkCenterY && pinkCenterY <= boxY + boxSize) {

            score += 30;
            pinkX = -10;
            accountManager.getCurrentAccount().setCatchballScoreForSave(score);
            accountManager.updateAccount();
            saveToFile(LauncherActivity.SAVE_FILENAME);
        }
    }

    /**
     * add 10 points to score and save the current game score if the box hits the pink particle
     */
    private void hitOrange() {
        int orangeCenterX = orangeX + orange.getWidth() / 2;
        int orangeCenterY = orangeY + orange.getHeight() / 2;

        if (0 <= orangeCenterX && orangeCenterX <= boxSize &&
                boxY <= orangeCenterY && orangeCenterY <= boxY + boxSize) {

            score += 10;
            orangeX = -10;
            accountManager.getCurrentAccount().setCatchballScoreForSave(score);
            accountManager.updateAccount();
            saveToFile(LauncherActivity.SAVE_FILENAME);
        }
    }


    /**
     * adjust the frame and manage the time
     */
    public boolean onTouchEvent(MotionEvent me) {

        if (!start_flg) {
            start_flg = true;
            FrameLayout frame = findViewById(R.id.frame);
            frameHeight = frame.getHeight();
            boxY = (int)box.getY();
            boxSize = box.getHeight();
            startLabel.setVisibility(View.GONE);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePos();
                        }
                    });
                }
            }, 0, 20);
        }
        else {
            if (me.getAction() == MotionEvent.ACTION_DOWN) {
                action_flg = true;
            } else if (me.getAction() == MotionEvent.ACTION_UP) {
                action_flg = false;
            }
        }
        return true;
    }


    /**
     * jump to ScoreResult page with an Array list be given, contains the score of the current game
     * and the highest score of Catching Ball Game of current Account.
     * @param scores the current game score and the record score of Catching Ball Game
     */
    private void goToScoreResult(ArrayList<Integer> scores){
        Intent gameResultIntent = new Intent(CatchBall.this,
                ScoreResult.class);
        gameResultIntent.putIntegerArrayListExtra("scores",scores);
        startActivity(gameResultIntent);
    }

    /**
     * Save the board manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(accountManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Load the board manager from fileName.
     *
     * @param fileName the name of the file
     */
    public void loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
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
