package project.csc207.catchingball;

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
 * ScoreBoard For CatchingBall game
 */
public class ScoreBoardForCatchingBall extends AppCompatActivity implements ScoreBoardForGame {

    private AccountManager accountManager;
    private ArrayList<Account> topPlayers;

    /**
     * set up for the scoreboard page
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board_for_catching_ball);
        loadFromFile();

        setTopPlayers();
        setTextViewForTopPlayers();
        setTextViewForAccount();

    }

    /**
     * set up the score for the current account
     */
    @Override
    public void setTextViewForAccount() {
        TextView titleText = findViewById(R.id.Title);
        TextView accountScoreText = findViewById(R.id.UserScore);
        String title = "ScoreBoard For Catching Ball Game";
        String accountsAndScore = "Your Score is: " +
                accountManager.getCurrentAccount().getCatchBallScore();

        titleText.setText(title);
        accountScoreText.setText(accountsAndScore);
    }

    /**
     * set up the top 3 scores for the played account with their username
     */
    @Override
    public void setTextViewForTopPlayers() {
        TextView num1Player = findViewById(R.id.num1Player);
        TextView num2Player = findViewById(R.id.num2Player);
        TextView num3Player = findViewById(R.id.num3Player);
        ArrayList<String> textForView = new ArrayList<>();

        for (Account account : topPlayers) {
            String str = "User: " + account.getUserName() + " Score : " + account.getCatchBallScore();
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
     * set up the multiple scores from different games for the top players
     */
    @Override
    public void setTopPlayers() {
        topPlayers = new ArrayList<>();
        HashMap<String, Account> allaccounts = accountManager.getAllAccount();
        Collection<Account> accounts = allaccounts.values();
        System.out.println(accounts.size());
        for (Account account : accounts) {
            int catchBallScore = account.getCatchBallScore();
            System.out.println("current Account" + account.getUserName());
            System.out.println("Score" + catchBallScore);
            topPlayers = checkTopPlayers(account, catchBallScore, topPlayers);
            System.out.println(topPlayers.size());
        }
    }

    /**
     * double check info of top 3 players
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
                int recordOfTopPlayer = topPlayer.getCatchBallScore();
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
     * load the saved file
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
