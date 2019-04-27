package project.csc207.slidingtiles;

import android.support.annotation.NonNull;

import java.util.Observable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * The sliding tiles board.
 */
public class Board extends Observable implements Serializable, Iterable<Tile> {

    /**
     * The number of rows.
     */
    static int NUM_ROWS = 4;

    /**
     * The number of rows.
     */
    static int NUM_COLS = 4;

    /**
     * All the moves made to the board.
     */
    private Stack<int[]> gameMoves = new Stack<>();

    /**
     * The tiles on the board in row-major order.
     */
    private Tile[][] tiles = new Tile[NUM_ROWS][NUM_COLS];


    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param tiles the tiles for the board
     */
    Board(List<Tile> tiles) {
        Iterator<Tile> iter = tiles.iterator();

        for (int row = 0; row != Board.NUM_ROWS; row++) {
            for (int col = 0; col != Board.NUM_COLS; col++) {
                this.tiles[row][col] = iter.next();
            }
        }
    }

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param tiles     the tiles for the board
     * @param BoardSize the size of the board
     */
    Board(List<Tile> tiles, int BoardSize) {
        this.tiles = new Tile[BoardSize][BoardSize];
        Iterator<Tile> iter = tiles.iterator();
        NUM_COLS = NUM_ROWS = BoardSize;
        for (int row = 0; row != Board.NUM_ROWS; row++) {
            for (int col = 0; col != Board.NUM_COLS; col++) {
                this.tiles[row][col] = iter.next();
            }
        }
    }

    /**
     * Return the number of tiles on the board.
     *
     * @return the number of tiles on the board
     */
    int numTiles() {
        return NUM_COLS * NUM_ROWS;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    /**
     * Swap the tiles at (row1, col1) and (row2, col2)
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     */
    void swapTiles(int row1, int col1, int row2, int col2) {
        Tile first = this.tiles[row1][col1];
        Tile second = this.tiles[row2][col2];
        this.tiles[row1][col1] = second;
        this.tiles[row2][col2] = first;
        gameMoves.push(new int[]{row1, col1, row2, col2});
    }

    /**
     * Undo the previous move.
     */
    void undo() {
        if (!gameMoves.empty()) {
            int[] moves = gameMoves.pop();
            swapTiles(moves[0], moves[1], moves[2], moves[3]);
            gameMoves.pop();

            setChanged();
            notifyObservers();
        }
    }

    /**
     * return the number of Moves done by the Player
     * @return the number of moves to solve the game
     */
    int getGameMoves() {
        return gameMoves.size();
    }

    @NonNull
    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }

    @NonNull
    @Override
    public Iterator<Tile> iterator() {
        return new BoardIterator();
    }

    /**
     * An iterator for a sliding tile board.
     */
    class BoardIterator implements Iterator<Tile> {
        /**
         * The row of the current tile.
         */
        int row = 0;

        /**
         * The column of the current tile.
         */
        int col = 0;

        @Override
        public boolean hasNext() {
            return !(getTile(row, col).getId() == (NUM_COLS * NUM_ROWS));
        }

        @Override
        public Tile next() {
            Tile next = getTile(row, col);
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
            return next;
        }
    }
}

