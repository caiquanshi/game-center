package project.csc207.slidingtiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
public class BoardManager extends Observable implements Serializable {

    /**
     * The board being managed.
     */
    private Board board;

    /**
     * the state of the game
     */
    private boolean GAME_OVER = false;

    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */
    BoardManager(Board board) {
        this.board = board;
    }

    /**
     * Return the current board.
     */
    Board getBoard() {
        return board;
    }

    void setBoard(Board newBoard) { this.board = newBoard; }

    /**
     * Manage a new shuffled board.
     *
     * @param boardSize the size of the board.
     */
    BoardManager(int boardSize) {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = boardSize * boardSize;
        for (int tileNum = 0; tileNum != numTiles - 1; tileNum++) {
            tiles.add(new Tile(tileNum));
        }
        tiles.add(new Tile(24));
        Collections.shuffle(tiles);
        while (!(isSolvable(boardSize, tiles))){
            Collections.shuffle(tiles);
        }
        this.board = new Board(tiles, boardSize);
    }

    /**
     * Check to make sure board is solvable
     * Citations: https://www.geeksforgeeks.org/check-instance-15-puzzle-solvable/
     * https://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html?
     * @param boardSize the size of board
     * @return whether the board is solvable
     */
    private boolean isSolvable(int boardSize, List<Tile> tiles) {
        List<Integer> tileNums = new ArrayList<>();
        for (Tile t: tiles) {
            //Add 1 so ids start from 1
            tileNums.add(t.getId());
        }
        int blankTileRow = tileNums.indexOf(25) / Board.NUM_ROWS;
        int inversions = getInversions(tileNums, boardSize);
        if (boardSize % 2 == 1) {
            return inversions % 2 == 0;
        }
        else {
            //Blank row is on odd row starting from bottom
            if (blankTileRow % 2 == 1) {
                return inversions % 2 == 0;
            }
            //Blank row is on even row starting from bottom
            else {
                return inversions % 2 == 1;
            }
        }
    }

    /**
     * Get the number of inversions (times a tile's id precedes a lesser id) for a board
     * Citation: https://www.geeksforgeeks.org/check-instance-15-puzzle-solvable/
     * @param tileNums tile ids in a list
     * @param boardSize size of board
     * @return number of inversions
     */
    private int getInversions(List<Integer> tileNums, int boardSize) {
        int inversions = 0;
        //Remove the blank tile
        tileNums.remove(Integer.valueOf(25));
        for (int i = 0; i < tileNums.size() - 1; i++) {
            for (int j = i + 1; j < tileNums.size(); j++) {
                if (tileNums.get(j) < tileNums.get(i)) {
                    inversions++;
                }
            }
        }
        return inversions;
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    boolean puzzleSolved() {
        boolean solved = true;
        for (int i = 0; i < board.numTiles() - 1; i++) {
            int row = i / Board.NUM_ROWS;
            int col = i % Board.NUM_COLS;
            int id = i + 1;
            Tile tileToCheck = board.getTile(row, col);
            if (tileToCheck.getId() != id) {
                solved = false;
                break;
            }
        }
        return solved;
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    boolean isValidTap(int position) {

        int row = position / Board.NUM_COLS;
        int col = position % Board.NUM_COLS;
        int blankId = 25;
        // Are any of the 4 the blank tile?
        Tile above = row == 0 ? null : board.getTile(row - 1, col);
        Tile below = row == Board.NUM_ROWS - 1 ? null : board.getTile(row + 1, col);
        Tile left = col == 0 ? null : board.getTile(row, col - 1);
        Tile right = col == Board.NUM_COLS - 1 ? null : board.getTile(row, col + 1);
        return (below != null && below.getId() == blankId)
                || (above != null && above.getId() == blankId)
                || (left != null && left.getId() == blankId)
                || (right != null && right.getId() == blankId);
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     * and update the statement of game
     *
     * @param position the position
     */
    void touchMove(int position) {

        int row = position / Board.NUM_ROWS;
        int col = position % Board.NUM_COLS;
        int blankId = 25;

        Tile above = row == 0 ? null : board.getTile(row - 1, col);
        Tile below = row == Board.NUM_ROWS - 1 ? null : board.getTile(row + 1, col);
        Tile left = col == 0 ? null : board.getTile(row, col - 1);
        Tile right = col == Board.NUM_COLS - 1 ? null : board.getTile(row, col + 1);
        if ((above != null) && blankId == above.getId()) {
            board.swapTiles(row - 1, col, row, col);
        } else if ((below != null) && blankId == below.getId()) {
            board.swapTiles(row + 1, col, row, col);
        } else if ((left != null) && blankId == left.getId()) {
            board.swapTiles(row, col - 1, row, col);
        } else if ((right != null) && blankId == right.getId()) {
            board.swapTiles(row, col + 1, row, col);
        }

        GAME_OVER = puzzleSolved();

        setChanged();
        notifyObservers();
    }

    /**
     * return true if puzzle is solved, else return false
     * @return whether the puzzle is solved or not
     */
    boolean isGameOver() {
        return GAME_OVER;
    }

    /**
     * count the score of sliding tile games and return it, the larger the board, the greater the
     * score player will get once they finished the game
     * @return the score of the sliding tile game
     */
    int countScore(){
        return 40*board.numTiles() - board.getGameMoves();

    }
}


