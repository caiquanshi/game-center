package project.csc207.lightsoutgame;

import java.io.Serializable;
import java.util.Observable;

import project.csc207.R;

/**
 * A light in LightsOut Board
 */
public class Light extends Observable implements Serializable {

    /**
     * The background numLights to set the light to be white or black
     */
    private int background;

    /**
     * The status of light, if On, then the light is white, otherwise is black.
     */
    private boolean isOn = false;

    /**
     * The unique numLights for each light
     */
    private int id;

    /**
     * Return light's numLights.
     *
     * @return light numLights
     */
    public int getId() {
        return id;
    }

    /**
     * Return light's background
     *
     * @return light background
     */
    public int getBackground() {
        return background;
    }

    /**
     * Return light's on or off state
     *
     * @return light's on or off state
     */
    boolean getState() {
        return isOn;
    }

    /**
     * switch the light, change the background of light and the getState state.
     */
    void switchLight() {
        if (this.isOn) {
            setLight(false);
        } else {
            setLight(true);
        }
    }

    /**
     * A light in the lights out board.
     *
     * @param id light's numLights
     */
    Light(int id) {
        this.id = id;
        setLight(isOn);
    }

    /**
     * set the state and the background of the light based on the given statement of light
     * default statement is true for isOn
     *
     * @param state the statement of light
     */
    void setLight(Boolean state) {
        this.isOn = state;
        if (state) {
            background = R.drawable.lightson;
        } else {
            background = R.drawable.lightsoff;
        }
    }

}
