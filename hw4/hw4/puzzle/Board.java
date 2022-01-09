package hw4.puzzle;

public class Board implements WorldState {

    private int[][] board;

    /**
     * Constructs a board from an N-by-N array of tiles where
     * tiles[i][j] = tile at row i, column j
     * @param tiles
     */
    public Board(int[][] tiles) {
        board = tiles;
    }

    /**
     * Returns value of tile at row i, column j (or 0 if blank)
     * @param i
     * @param j
     * @return
     */
    public int tileAt(int i, int j) {
        return board[i][j];
    }

    /**
     * Returns the board size N
     * @return
     */
    public int size() {
        return board.length * board.length;
    }

    /**
     * The number of tiles in the wrong position
     * @return
     */
    public int hamming() {
        return 0;
    }

    /**
     * The sum of the Manhattan distances (sum of the vertical and horizontal distance)
     * from the tiles to their goal positions
     * @return
     */
    public int manhattan() {
        return 0;
    }

    public boolean equals(Object y) {
        return false;
    }

    @Override
    public int estimatedDistanceToGoal() {
        return 0;
    }

    @Override
    public Iterable<WorldState> neighbors() {
        return null;
    }

    /** Returns the string representation of the board.
     * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }
}
