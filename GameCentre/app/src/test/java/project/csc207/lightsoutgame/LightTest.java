package project.csc207.lightsoutgame;
import org.junit.*;

import project.csc207.R;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertEquals;

public class LightTest {

    /**
     * Light for testing
     */
    private Light testLight = new Light(0);

    /**
     * Set up light to be on before every test.
     */
    @Before
    public void setUp() {
        testLight.setLight(true);
    }

    /**
     * Test getId method returns the correct id.
     */
    @Test
    public void testGetId() {
        int result = testLight.getId();
        int correctId = 0;
        assertEquals(correctId, result);
    }

    /**
     * Test getState method returns the correct on or off state.
     */
    @Test
    public void testGetState() {
        boolean result = testLight.getState();
        assertTrue(result);
        testLight.setLight(false);
        result = testLight.getState();
        assertFalse(result);
    }

    /**
     * Test getBackground method returns the correct background.
     */
    @Test
    public void testGetBackground() {
        int result = testLight.getBackground();
        assertEquals(R.drawable.lightson, result);
        testLight.setLight(false);
        result = testLight.getBackground();
        assertEquals(R.drawable.lightsoff, result);
    }

    /**
     * Test switchLight method switches the state of light from on to off or off to on.
     */
    @Test
    public void testSwitchLight() {
        testLight.switchLight();
        assertFalse(testLight.getState());
        testLight.switchLight();
        assertTrue(testLight.getState());
    }

    /**
     * Test setLight method successfully sets the state of the light.
     */
    @Test
    public void testSetLight() {
        testLight.setLight(false);
        assertFalse(testLight.getState());
        testLight.setLight(true);
        assertTrue(testLight.getState());
    }

}
