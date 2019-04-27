package project.csc207.slidingtiles;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import project.csc207.Account;
import project.csc207.AccountManager;
import project.csc207.LauncherActivity;
import project.csc207.R;
import project.csc207.ScoreBoardForGame;

/**
 * Score Board for Sliding Tile Game
 */
public class ScoreBoardForSlidingTiles extends AppCompatActivity implements ScoreBoardForGame {

    private AccountManager accountManager;
    private ArrayList<Account> topPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board_for_sliding_tiles);
        loadFromFile();

        setTopPlayers();
        setTextViewForTopPlayers();
        setTextViewForAccount();

    }

    @Override
    public void setTextViewForAccount() {
        TextView titleText = findViewById(R.id.SlidingTileTitle);
        TextView accountScoreText = findViewById(R.id.userScoreSlidingTile);
        String title = "ScoreBoard For Sliding Tiles Game";
        String accountsAndScore = "Your Score is: " +
                accountManager.getCurrentAccount().getSlidingTileScores();

        titleText.setText(title);
        accountScoreText.setText(accountsAndScore);
    }

    @Override
    public void setTextViewForTopPlayers() {
        TextView num1Player = findViewById(R.id.num1SlidingTile);
        TextView num2Player = findViewById(R.id.num2SlidingTile);
        TextView num3Player = findViewById(R.id.num3SlidingTile);
        ArrayList<String> textForView = new ArrayList<>();

        for (Account account : topPlayers) {
            String str = "User: " + account.getUserName() + " Score : " + account.getSlidingTileScores();
            textForView.add(str);
        }
        while (textForView.size() < 3) {
            textForView.add(" ");
        }
        num1Player.setText(textForView.get(0));
        num2Player.setText(textForView.get(1));
        num3Player.setText(textForView.get(2));
    }

    @Override
    public void setTopPlayers() {
        topPlayers = new ArrayList<>();
        HashMap<String, Account> allAccount = accountManager.getAllAccount();
        Collection<Account> accounts = allAccount.values();
        System.out.println(accounts.size());
        for (Account account : accounts) {
            int slidingTileScores = account.getSlidingTileScores();
            topPlayers = checkTopPlayers(account, slidingTileScores, topPlayers);
        }
    }

    @Override
    public ArrayList<Account> checkTopPlayers(Account player, int score,
                                              ArrayList<Account> topPlayers) {

        for (int i = 0; i < 3; i++) {
            if (i == topPlayers.size()) {
                topPlayers.add(i, player);
                break;
            } else {
                Account topPlayer = topPlayers.get(i);
                int recordOfTopPlayer = topPlayer.getSlidingTileScores();
                if (score > recordOfTopPlayer) {
                    topPlayers.add(i, player);
                    break;
                }
            }
        }
        return topPlayers;
    }

    /**
     * load AccountManager from file
     */
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
