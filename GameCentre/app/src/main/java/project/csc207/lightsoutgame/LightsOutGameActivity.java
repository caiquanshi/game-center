package project.csc207.lightsoutgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import project.csc207.AccountManager;
import project.csc207.LauncherActivity;
import project.csc207.R;
import project.csc207.SaveLoad;
import project.csc207.ScoreResult;
import project.csc207.CustomAdapter;

public class LightsOutGameActivity extends AppCompatActivity implements Observer, SaveLoad {


    /**
     * Buttons of Lights Out.
     */
    private ArrayList<Button> lightsButtons = new ArrayList<>();

    /**
     * the accountManager for current  Account
     */
    private AccountManager accountManager;

    /**
     * GridView of Lights Out.
     */
    private LightsOutDetectGridView lightsGrid;

    /**
     * Dimensions for size of the columns.
     */
    private static int columnWidth, columnHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromFile(LightsOutStartingActivity.TEMP_SAVE_FILENAME);
        accountManager.getCurrentAccount().getLightsOutBoardManager().addObserver(this);
        createLights(this);
        setContentView(R.layout.activity_lights_out_game);

        addViewToActivity();
        addUndoListener();
    }

    /**
     * add undo button listener
     */
    private void addUndoListener() {
        final Button undoButton = findViewById(R.id.undoButton);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountManager.getCurrentAccount().getLightsOutBoardManager().undo();
            }
        });
    }

    /**
     * set the game and lights in 5 x 5 grids
     */
    private void addViewToActivity() {
        lightsGrid = findViewById(R.id.LightsGrid);
        lightsGrid.setNumColumns(5);
        lightsGrid.setLightsOutBoardManager
                (accountManager.getCurrentAccount().getLightsOutBoardManager());

        lightsGrid.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        lightsGrid.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = lightsGrid.getMeasuredWidth();
                        int displayHeight = lightsGrid.getMeasuredHeight();

                        columnWidth = displayWidth / LightsOutBoard.NUM_COLS;
                        columnHeight = displayHeight / LightsOutBoard.NUM_ROWS;

                        display();
                    }
                });
    }


    /**
     * Creates the buttons for the lights
     *
     * @param context the context
     */
    private void createLights(Context context) {
        LightsOutBoard lightsBoard =
                accountManager.getCurrentAccount().getLightsOutBoardManager().getLightsOutBoard();
        lightsButtons = new ArrayList<>();
        for (int row = 0; row != LightsOutBoard.NUM_ROWS; row++) {
            for (int col = 0; col != LightsOutBoard.NUM_COLS; col++) {
                Button light = new Button(context);
                light.setBackgroundResource(lightsBoard.getLight(row, col).getBackground());
                lightsButtons.add(light);
            }
        }
    }

    /**
     * Update light backgrounds.
     */
    private void updateLights() {
        LightsOutBoard board =
                accountManager.getCurrentAccount().getLightsOutBoardManager().getLightsOutBoard();
        int nextPos = 0;
        for (Button b : lightsButtons) {
            int row = nextPos / LightsOutBoard.NUM_ROWS;
            int col = nextPos % LightsOutBoard.NUM_COLS;
            b.setBackgroundResource(board.getLight(row, col).getBackground());
            nextPos++;
        }
        saveToFile(LauncherActivity.SAVE_FILENAME);
    }

    /**
     * Set up background images and use adapter to set the view.
     */
    public void display() {
        updateLights();
        lightsGrid.setAdapter(new CustomAdapter(lightsButtons, columnWidth, columnHeight));
    }

    /**
     * Load from save file
     *
     * @param fileName name of save file
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

    /**
     * Save to the file.
     *
     * @param fileName name of save file
     */
    @Override
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
     * Update the game's display and check for game over.
     *
     * @param o   observer
     * @param arg arguments
     */
    @Override
    public void update(Observable o, Object arg) {
        display();
        gameOver();
    }

    /**
     * set the score of the game to Account and jump to ScoreResult page if the game is over
     */
    public void gameOver() {
        if (accountManager.getCurrentAccount().getLightsOutBoardManager().allLightsOut()) {
            ArrayList<Integer> scores = new ArrayList<>();
            int score = accountManager.getCurrentAccount().getLightsOutBoardManager().countScore();
            int records = accountManager.getCurrentAccount().getLightOutScores();
            accountManager.getCurrentAccount().setLightOutScores(score);
            saveToFile(LauncherActivity.SAVE_FILENAME);
            scores.add(score);
            scores.add(records);
            goToScoreResult(scores);
        }
    }

    /**
     * Go to score result screen.
     *
     * @param scores list of scores
     */
    private void goToScoreResult(ArrayList<Integer> scores) {
        Intent gameResultIntent = new Intent(LightsOutGameActivity.this,
                ScoreResult.class);
        gameResultIntent.putIntegerArrayListExtra("scores", scores);
        startActivity(gameResultIntent);
    }
}
