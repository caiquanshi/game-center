package project.csc207.slidingtiles;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class BoardManagerTest {
    /**
     * Size of board
     */
    private final int SIZE = 4;

    /**
     * Test board manager
     */
    private BoardManager testBoardManager = new BoardManager(SIZE);

    @Before
    public void setUp() {
        //Create solved board
        List<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < SIZE * SIZE - 1; i++) {
            tiles.add(new Tile(i));
        }
        tiles.add(new Tile(24));
        Board testSolvedBoard = new Board(tiles);
        testBoardManager.setBoard(testSolvedBoard);
    }

    /**
     * Test puzzleSolved method correctly checks for solved and unsolved boards.
     */
    @Test
    public void testPuzzleSolved() {
        //Test solved board
        assertTrue(testBoardManager.puzzleSolved());
        //Test unsolved board
        List<Tile> tiles = new ArrayList<>();
        Integer[] testTileNumbers = {0, 1, 2, 3, 4, 6, 5, 7, 8, 9, 10, 11, 12, 13, 14, 24};
        for (int t : testTileNumbers) {
            tiles.add(new Tile(t));
        }
        Board testUnsolvedBoard = new Board(tiles);
        testBoardManager.setBoard(testUnsolvedBoard);
        assertFalse(testBoardManager.puzzleSolved());
    }

    /**
     * Test isValidTap method correctly returns if the tile at a certain position is valid
     */
    @Test
    public void testIsValidTap() {
        //Test valid tiles
        assertTrue(testBoardManager.isValidTap(14));
        assertTrue(testBoardManager.isValidTap(11));
        //Test random invalid tile
        assertFalse(testBoardManager.isValidTap(0));
        //Test position of blank tile itself
        assertFalse(testBoardManager.isValidTap(15));
    }

    /**
     * Test touchMove method correctly swaps tiles if and only if the tap is valid
     */
    @Test
    public void testTouchMove() {
        //Test that blank tile and to its left are swapped
        testBoardManager.touchMove(14);
        int swappedTileOne = testBoardManager.getBoard().getTile(3, 2).getId();
        int swappedTileTwo = testBoardManager.getBoard().getTile(3, 3).getId();
        assertEquals(25, swappedTileOne);
        assertEquals(15, swappedTileTwo);
        testBoardManager.touchMove(13);
        swappedTileOne = testBoardManager.getBoard().getTile(3, 1).getId();
        swappedTileTwo = testBoardManager.getBoard().getTile(3, 2).getId();
        assertEquals(25, swappedTileOne);
        assertEquals(14, swappedTileTwo);
        //Test that the tiles are not swapped when the tap is not valid
        testBoardManager.touchMove(0);
        swappedTileOne = testBoardManager.getBoard().getTile(3, 1).getId();
        swappedTileTwo = testBoardManager.getBoard().getTile(0, 0).getId();
        assertEquals(25, swappedTileOne);
        assertEquals(1, swappedTileTwo);
    }

    /**
     * Test isGameOver method correctly returns whether or not the game is over
     */
    @Test
    public void testIsGameOver() {
        testBoardManager.touchMove(14);
        assertFalse(testBoardManager.isGameOver());
        testBoardManager.touchMove(15);
        assertTrue(testBoardManager.isGameOver());
    }

    /**
     * Test countScore gives lower score to player if they take more moves
     */
    @Test
    public void testCountScore() {
        testBoardManager.touchMove(14);
        int higherScore = testBoardManager.countScore();
        testBoardManager.touchMove(13);
        int lowerScore = testBoardManager.countScore();
        //The higher score should be higher than the lower score by one since it took one less move
        assertEquals(higherScore - lowerScore, 1);
    }
}
