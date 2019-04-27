package project.csc207.lightsoutgame;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * the Board for Lights Out Game
 */

public class LightsOutBoard implements Serializable, Iterable<Light> {

    /**
     * Length of rows.
     */
    static int NUM_ROWS = 5;

    /**
     * Length of columns.
     */
    static int NUM_COLS = 5;

    /**
     * Board of lights.
     */
    private Light[][] lights = new Light[NUM_COLS][NUM_ROWS];


    /**
     * A new board of lights in row-major order.
     * Precondition: len(lights) == NUM_ROWS * NUM_COLS
     *
     * @param lights the lights for the board
     */
    LightsOutBoard(List<Light> lights) {
        Iterator<Light> iter = lights.iterator();

        for (int row = 0; row != LightsOutBoard.NUM_ROWS; row++) {
            for (int col = 0; col != LightsOutBoard.NUM_COLS; col++) {
                this.lights[row][col] = iter.next();
            }
        }
    }

    /**
     * Return the light at the given row and column.
     *
     * @param row row of the board
     * @param col column of the board
     * @return light at the given row and column
     */
    Light getLight(int row, int col) {
        return lights[row][col];
    }

    /**
     * Return lights out board iterator.
     *
     * @return lights out board iterator
     */
    @NonNull
    public Iterator<Light> iterator() {
        return new LightsOutBoardIterator();
    }

    /**
     * Iterator for lights out board.
     */
    class LightsOutBoardIterator implements Iterator<Light> {
        /**
         * The row of the current light.
         */
        int row = 0;

        /**
         * The column of the current light.
         */
        int col = 0;

        /**
         * Number of lights iterated through.
         */
        int numLights = 0;

        /**
         * Check if there is a next light.
         *
         * @return whether or not there's a next element
         */
        @Override
        public boolean hasNext() {
            return numLights < (NUM_COLS * NUM_ROWS);
        }

        /**
         * Go to the next light.
         *
         * @return next light
         */
        @Override
        public Light next() {
            Light nextLight = getLight(row, col);
            if (col < NUM_COLS - 1) {
                col++;
            } else {
                if (row == NUM_ROWS - 1) {
                    col = NUM_COLS - 1;
                    row = NUM_ROWS - 1;
                } else {
                    row++;
                    col = 0;
                }

            }
            numLights++;
            return nextLight;
        }
    }
}

