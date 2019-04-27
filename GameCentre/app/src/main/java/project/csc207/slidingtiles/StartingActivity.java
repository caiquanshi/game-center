package project.csc207.slidingtiles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import project.csc207.AccountManager;
import project.csc207.LauncherActivity;
import project.csc207.R;
import project.csc207.SaveLoad;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class StartingActivity extends AppCompatActivity implements SaveLoad {


    /**
     * A temporary save file.
     */
    public static final String TEMP_SAVE_FILENAME = "save_file_tmp.ser";

    /**
     * The board size.
     */
    private int BoardSize = 4;

    /**
     * The account manager.
     */
    private AccountManager accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromFile(LauncherActivity.SAVE_FILENAME);
        accountManager.getCurrentAccount().setBoardManager(new BoardManager(BoardSize));
        saveToFile(TEMP_SAVE_FILENAME);

        setContentView(R.layout.activity_starting_);
        setStringTextView();
        addNumRowsListener();
        displayAccountName();
        addStartButtonListener();
        addLoadButtonListener();
        AddScoreBoardButtonListener();
    }

    /**
     * jump to ScoreBoard for SlidingTiles once the button is click
     */
    private void AddScoreBoardButtonListener() {
        final Button rankButton = findViewById(R.id.SlidingTileScoreButton);
        rankButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scoreBoard = new Intent(StartingActivity.this,
                        ScoreBoardForSlidingTiles.class);
                startActivity(scoreBoard);
            }
        });
    }

    /**
     * Change the text to show the account's name.
     */
    private void displayAccountName() {
        TextView accountNameSlidingTextView = findViewById(R.id.AccountNameSliding);
        String username = accountManager.getCurrentAccount().getUserName();
        accountNameSlidingTextView.setText(username);
    }

    /**
     * Activate the board size buttons.
     */
    private void addNumRowsListener() {
        RadioGroup NumOfRows;
        NumOfRows = findViewById(R.id.ChoicesOfBoardSize);
        NumOfRows.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.RowNum3:
                        BoardSize = 3;
                        break;
                    case R.id.RowNum4:
                        BoardSize = 4;
                        break;
                    case R.id.RowNum5:
                        BoardSize = 5;
                        break;
                    default:
                        BoardSize = 4;
                        break;
                }
            }
        });

    }

    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountManager.getCurrentAccount().setBoardManager(new BoardManager(BoardSize));
                switchToGame();
            }
        });
    }

    /**
     * set the String for xml document
     */
    private void setStringTextView() {
        TextView textView = findViewById(R.id.GameText);
        String str = "Welcome To Sliding Tiles  A Puzzle Game where you mu" +
                "st arrange the numbers in the correct order";
        textView.setText(str);

    }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFromFile(LauncherActivity.SAVE_FILENAME);
                if (accountManager.getCurrentAccount().getBoardManager() == null) {
                    makeToastNoLoadedText();
                }else{
                saveToFile(TEMP_SAVE_FILENAME);
                makeToastLoadedText();
                switchToGame();}
            }
        });
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }

    /**
     * Display that no game was saved before.
     */
    private void makeToastNoLoadedText() {
        Toast.makeText(this, "No Saved Game", Toast.LENGTH_SHORT).show();
    }


    /**
     * Read the temporary board from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadFromFile(TEMP_SAVE_FILENAME);
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, GameActivity.class);
        saveToFile(StartingActivity.TEMP_SAVE_FILENAME);
        startActivity(tmp);
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

}

