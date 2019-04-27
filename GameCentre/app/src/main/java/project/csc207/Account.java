package project.csc207;

import java.io.Serializable;

import project.csc207.lightsoutgame.LightsOutBoardManager;
import project.csc207.slidingtiles.BoardManager;

/**
 * The Account with all Scores and BoardManager of different games, with setters and getters
 */
public class Account implements Serializable {

    /**
     * The account saved boardManager
     */
    private BoardManager bm;

    /**
     * the Account Saved LightsOutBoardManager
     */
    private LightsOutBoardManager lbm;

    private int catchBallScoreForSave;

    /**
     * The score for the current account.
     */
    private int slidingTileScores = 0;

    /**
     * The score for the current account.
     */
    private int catchBallScore = 0;

    /**
     * The highest score for lights out game of the current account.
     */
    private int lightOutScores = 0;

    /**
     * The username of the account currently logged in.
     */
    private String userName;

    /**
     * The password of the account currently logged in.
     */
    private String password;

    /**
     * Create a new account.
     */
    Account(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.bm = null;
    }

    /**
     * Return the current account's username.
     */
    public String getUserName() {
        return userName;
    }


    /**
     * Return the score for the current user.
     */
    String getPassword() {
        return password;
    }

    /**
     * Return the saved boardManager
     */
    public BoardManager getBoardManager() {
        return bm;
    }

    /**
     * Set the saved boardManager
     */
    public void setBoardManager(BoardManager bm) {
        this.bm = bm;
    }

    /**
     * Return the saved LightOutBoardManager
     *
     * @return saved LightOutBoardManager
     */
    public LightsOutBoardManager getLightsOutBoardManager() {
        return lbm;
    }

    public void setCatchballScoreForSave(int catchballScoreForSave) {
        this.catchBallScoreForSave = catchballScoreForSave;
    }

    public int getCatchballScoreForSave() {
        return catchBallScoreForSave;
    }

    /**
     * set the saved LightsOutBoardManager
     *
     * @param lbm the given LightsOutBoardManager
     */
    public void setLightsOutBoardManager(LightsOutBoardManager lbm) {
        this.lbm = lbm;
    }

    /**
     * set the lights out Game score to new score if the new score is higher than the record score.
     *
     * @param lightOutScores the new score of LightsOut game
     */
    public void setLightOutScores(int lightOutScores) {
        if (this.lightOutScores < lightOutScores) {
            this.lightOutScores = lightOutScores;
        }
    }

    /**
     * return the highest score of lights out Game
     *
     * @return the highest score of light out Game
     */
    public int getLightOutScores() {
        return lightOutScores;
    }

    /**
     * set the slidingTile score to new score if the new score is higher than record
     *
     * @param slidingTileScores new score of sliding tile game
     */
    public void setSlidingTileScores(int slidingTileScores) {
        if (this.slidingTileScores < slidingTileScores) {
            this.slidingTileScores = slidingTileScores;
        }
    }

    /**
     * get the sliding tile game record
     *
     * @return the record of sliding tile game
     */
    public int getSlidingTileScores() {
        return slidingTileScores;
    }

    /**
     * get the record of Catching Ball Game
     *
     * @return the record score of Catching Ball
     */
    public int getCatchBallScore() {
        return catchBallScore;
    }

    /**
     * set the record of Catching Ball Game if the new score is higher than the record
     *
     * @param catchBallScore the new score of Catching Ball Game
     */
    public void setCatchBallScore(int catchBallScore) {
        if (catchBallScore > this.catchBallScore) {
            this.catchBallScore = catchBallScore;
        }
    }
}
