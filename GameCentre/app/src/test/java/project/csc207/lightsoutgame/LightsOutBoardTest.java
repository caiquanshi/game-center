package project.csc207.lightsoutgame;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertEquals;

public class LightsOutBoardTest {

    /**
     * Lights out board manager and lights out board for testing.
     */
    private LightsOutBoardManager testBoardManager = new LightsOutBoardManager();
    private LightsOutBoard testBoard = testBoardManager.getLightsOutBoard();

    /**
     * Turn all lights on the lights out board on before tests.
     */
    @Before
    public void setUp(){
        LightsOutTestHelpMethods.getLightsAllOn(testBoard);
    }

    /**
     * Test getLight method returns the correct light.
     */
    @Test
    public void testGetLight() {
        Light result = testBoard.getLight(0, 0);
        assertEquals(0, result.getId());
        assertTrue(result.getState());
        String test = testBoard.toString();
        result = testBoard.getLight(2, 4);
        assertEquals(14, result.getId());
    }

    /**
     * Test lights out board iterator goes to every light.
     */
    @Test
    public void testLightsOutBoardIterator() {
        int numLights = 0;
        for (Light l: testBoard) {
            l.setLight(false);
            numLights++;
        }
        //Check if the number of lights are correct
        assertEquals(25, numLights);
        //Check if the foreach loop turned off all the lights
        boolean allOff = true;
        for (int row = 0; row < LightsOutBoard.NUM_ROWS; row++) {
            for (int col = 0; col < LightsOutBoard.NUM_COLS; col++)
            {
                if (testBoard.getLight(row, col).getState()) {
                    allOff = false;
                }
            }
        }
        assertTrue(allOff);
    }
}
