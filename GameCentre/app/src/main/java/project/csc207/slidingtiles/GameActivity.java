package project.csc207.slidingtiles;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.content.Intent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import project.csc207.Account;
import project.csc207.AccountManager;
import project.csc207.CustomAdapter;
import project.csc207.LauncherActivity;
import project.csc207.R;
import project.csc207.SaveLoad;
import project.csc207.ScoreResult;

/**
 * The game activity.
 */
public class GameActivity extends AppCompatActivity implements Observer,SaveLoad {

    /**
     * The account manager.
     */
    private AccountManager accountManager;
    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    /**
     * Constants for swiping directions. Should be an enum, probably.
     */
    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;

    // Grid View and calculated column height and width based on device size
    private GestureDetectGridView gridView;
    private static int columnWidth, columnHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromFile(StartingActivity.TEMP_SAVE_FILENAME);
        createTileButtons(this);
        setContentView(R.layout.activity_main);

        AddViewToActivity();
        AddUndoListener();

    }

    private void AddUndoListener() {
        accountManager.getCurrentAccount().getBoardManager().getBoard().addObserver(this);
        final Button undoButton = findViewById(R.id.undoButton);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountManager.getCurrentAccount().getBoardManager().getBoard().undo();
            }
        });
    }

    /**
     * Add View To activity and set up Observer and call display method
     */
    private void AddViewToActivity() {
        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(Board.NUM_COLS);
        gridView.setBoardManager(accountManager.getCurrentAccount().getBoardManager());
        accountManager.getCurrentAccount().getBoardManager().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / Board.NUM_COLS;
                        columnHeight = displayHeight / Board.NUM_ROWS;

                        display();
                    }
                });
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        Board board = accountManager.getCurrentAccount().getBoardManager().getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != Board.NUM_ROWS; row++) {
            for (int col = 0; col != Board.NUM_COLS; col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                this.tileButtons.add(tmp);

            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     * And AutoSave for any change
     */
    private void updateTileButtons() {
        Board board = accountManager.getCurrentAccount().getBoardManager().getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / Board.NUM_ROWS;
            int col = nextPos % Board.NUM_COLS;
            b.setBackgroundResource(board.getTile(row, col).getBackground());
            nextPos++;
        }
        saveToFile(LauncherActivity.SAVE_FILENAME);
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        saveToFile(StartingActivity.TEMP_SAVE_FILENAME);
    }

    @Override
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

    @Override
    public void update(Observable o, Object arg) {
        display();
        gameOver();
    }

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }


    /**
     * jump to ScoreResult page if the game is over, and update the score to Account if the score
     * is higher than the record
     */
    void gameOver() {
        if (accountManager.getCurrentAccount().getBoardManager().isGameOver()) {
            Account account = accountManager.getCurrentAccount();
            int score = accountManager.getCurrentAccount().getBoardManager().countScore();
            int record = account.getSlidingTileScores();
            account.setSlidingTileScores(score);
            saveToFile(LauncherActivity.SAVE_FILENAME);
            ArrayList<Integer> scores = new ArrayList<>();
            scores.add(score);
            scores.add(record);
            goToScoreResult(scores);

        }

    }

    /**
     * jump to Score Result page and set Intent extra with given List
     * @param scores Arraylist with score and record of Sliding Tile
     */
    private void goToScoreResult(ArrayList<Integer> scores) {
        Intent gameResultIntent = new Intent(GameActivity.this,
                ScoreResult.class);
        gameResultIntent.putIntegerArrayListExtra("scores", scores);
        startActivity(gameResultIntent);
    }

}
