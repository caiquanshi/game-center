package project.csc207;

import java.util.ArrayList;

/**
 * Interface for different Game ScoreBoard,will show the scores of top 3 players and current Account
 */
public interface ScoreBoardForGame {

    /**
     * set a Arraylist contains top 3 player of the game, could be empty or less than 3 if the
     * number of account is less than 3.
     */
    void setTopPlayers();

    /**
     * check whether the player is Top players, add player to Top player lists if the player's score
     * of this game is higher than previous top 3 players
     *
     * @param player     The Account of the player to check
     * @param score      The highest score of checked player for a type of game
     * @param topPlayers An Array list contains top 3 players of the game.
     * @return An Array list contains top 3 players, could be less than 3 if number of Accounts are
     * less than 3
     */
    ArrayList<Account> checkTopPlayers(Account player, int score, ArrayList<Account> topPlayers);

    /**
     * set the TextView for top players by topPlayersList,if the number of top players less than 3,
     * leaving empty place for the rest top players.
     */
    void setTextViewForTopPlayers();

    /**
     * set the text view for Account User's record score of this game, below the top 3 player's
     * score
     */
    void setTextViewForAccount();
}
