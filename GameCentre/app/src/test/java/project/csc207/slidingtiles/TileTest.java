package project.csc207.slidingtiles;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import project.csc207.R;

public class TileTest {

    /**
     * Test whether the compareTo check the id and background of the Tiles correctly
     */
    @Test
    public void compareTo() {
        Tile t1 = new Tile(1,1);
        Tile t2 = new Tile(2,1);
        assertEquals(1, t1.compareTo(t2));
    }

    /**
     * Test whether the getter of the background of the Tile is getting the correct info
     */
    @Test
    public void getBackground() {
        Tile t1 = new Tile(2,1);
        Tile t2 = new Tile(1);

        assertEquals(1, t1.getBackground());
        assertEquals(R.drawable.tile_2, t2.getBackground());
    }

    /**
     * Test whether the getter of the ID of the Tile is getting the correct info
     */
    @Test
    public void getId() {
        Tile t1 = new Tile(2,1);
        assertEquals(2, t1.getId());
    }
}