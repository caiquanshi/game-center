package project.csc207.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

public class BoardTest {

    private final int EVEN_SIZE = 4;


    @Test
    public void numTiles() {

        List<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < EVEN_SIZE * EVEN_SIZE; i++) {
            tiles.add(new Tile(i));
        }
        Board testSolvedBoard = new Board(tiles);
        assertEquals(16, testSolvedBoard.numTiles());

    }

    @Test
    public void getTile() {

        List<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < EVEN_SIZE * EVEN_SIZE; i++) {
            tiles.add(new Tile(i));
        }
        Board testSolvedBoard = new Board(tiles);
        Tile testTile = new Tile(0);
        Tile testTile2 = new Tile(1);

        assertEquals(testTile.getId(), testSolvedBoard.getTile(0, 0).getId());
        assertEquals(testTile2.getId(), testSolvedBoard.getTile(0, 1).getId());

    }

    @Test
    public void swapTiles() {

        List<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < EVEN_SIZE * EVEN_SIZE; i++) {
            tiles.add(new Tile(i));
        }
        Board testSolvedBoard = new Board(tiles);
        testSolvedBoard.swapTiles(0, 0, 0, 1);
        assertEquals(2, testSolvedBoard.getTile(0, 0).getId());
        assertEquals(1, testSolvedBoard.getTile(0, 1).getId());
    }


    @Test
    public void getGameMoves() {

        List<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < EVEN_SIZE * EVEN_SIZE; i++) {
            tiles.add(new Tile(i));
        }
        Board testSolvedBoard = new Board(tiles);

        assertEquals(0, testSolvedBoard.getGameMoves());
    }


    @Test
    public void iterator() {
        int EVEN_SIZE = 4;

        List<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < EVEN_SIZE * EVEN_SIZE; i++) {
            tiles.add(new Tile(i));
        }
        Board testSolvedBoard = new Board(tiles);

        assertTrue(testSolvedBoard.iterator() instanceof Board.BoardIterator);
    }
}