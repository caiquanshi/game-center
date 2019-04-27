package project.csc207.lightsoutgame;

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
 * Score Board of Lights out game
 */
public class ScoreBoardForLightsOut extends AppCompatActivity implements ScoreBoardForGame {

    private AccountManager accountManager;
    private ArrayList<Account> topPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board_for_lights_out);
        loadFromFile();

        setTopPlayers();
        setTextViewForTopPlayers();
        setTextViewForAccount();

    }

    /**
     * Text view for the account.
     */
    @Override
    public void setTextViewForAccount() {
        TextView titleText = findViewById(R.id.LightsOutTitle);
        TextView accountScoreText = findViewById(R.id.userScoreLightsOut);
        String title = "ScoreBoard For Lights Out Game";
        String accountsAndScore = "Your Score is: " +
                accountManager.getCurrentAccount().getLightOutScores();

        titleText.setText(title);
        accountScoreText.setText(accountsAndScore);
    }

    /**
     * Text view for the top players.
     */
    @Override
    public void setTextViewForTopPlayers() {
        TextView num1Player = findViewById(R.id.num1LightsOut);
        TextView num2Player = findViewById(R.id.num2LightsOut);
        TextView num3Player = findViewById(R.id.num3LightsOut);
        ArrayList<String> textForView = new ArrayList<>();

        for (Account account : topPlayers) {
            String str = "User: " + account.getUserName() + " Score : " + account.getLightOutScores();
            textForView.add(str);
        }
        while (textForView.size() < 3) {
            textForView.add(" ");
        }
        num1Player.setText(textForView.get(0));
        num2Player.setText(textForView.get(1));
        num3Player.setText(textForView.get(2));
    }

    /**
     * Set the scores of the top players.
     */
    @Override
    public void setTopPlayers() {
        topPlayers = new ArrayList<>();
        HashMap<String, Account> allaccounts = accountManager.getAllAccount();
        Collection<Account> accounts = allaccounts.values();
        System.out.println(accounts.size());
        for (Account account : accounts) {
            int lightsOutScore = account.getLightOutScores();
            topPlayers = checkTopPlayers(account, lightsOutScore, topPlayers);
        }
    }

    /**
     * Check to see who are the top players and return them.
     * @param player     The Account of the player to check
     * @param score      The highest score of checked player for a type of game
     * @param topPlayers An Array list contains top 3 players of the game.
     * @return the top players
     */
    @Override
    public ArrayList<Account> checkTopPlayers(Account player, int score,
                                              ArrayList<Account> topPlayers) {

        for (int i = 0; i < 3; i++) {
            if (i == topPlayers.size()) {
                topPlayers.add(i, player);
                break;
            } else {
                Account topPlayer = topPlayers.get(i);
                int recordOfTopPlayer = topPlayer.getLightOutScores();
                if (score > recordOfTopPlayer) {
                    topPlayers.add(i, player);
                    break;
                }
            }
        }
        if (topPlayers.size() > 3) {
            topPlayers.remove(3);
        }
        return topPlayers;
    }

    /**
     * Load from the save file
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
