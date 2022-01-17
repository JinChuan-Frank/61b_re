package hw4.puzzle;
import edu.princeton.cs.algs4.Queue;



public class Board implements WorldState {

    private final int[][] board;
    private final int[][] goal;
    private final Tile[] T;
    private int distance;

    private class Tile {
        private int[] initialPos;
        private int[] rightPos;
        private int disToRightPos;

        Tile(int[] initial, int[] goal) {
            initialPos = initial;
            rightPos = goal;

        }

        private boolean inRightPosition() {
            return initialPos.equals(rightPos);
        }


        private int disToRightPos() {

            int d = 0;
            d += Math.abs(initialPos[0] - rightPos[0]);
            d += Math.abs(initialPos[1] - rightPos[1]);
            return d;
        }
    }

    /**
     * Constructs a board from an N-by-N array of tiles where
     * tiles[i][j] = tile at row i, column j
     * @param tiles
     */
    public Board(int[][] tiles) {
        int N = tiles.length;
        board = new int[N][N];
        goal = new int[N][N];
        T = new Tile[N * N];

        setGoal(goal, N);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                board[i][j] = tiles[i][j];
                int value = tiles[i][j];
                if (value != 0) {
                    int[] initial = {i, j};
                    int[] goalPos = calRightPos(N, value, goal);
                    Tile tile = new Tile(initial, goalPos);
                    tile.disToRightPos = tile.disToRightPos();
                    T[value] = tile;
                }


            }
        }
        distance = sumUpDistanceOfTiles();

    }

    private int sumUpDistanceOfTiles() {
        int sum = 0;
        for (int i = 1; i < T.length; i++) {
            sum += T[i].disToRightPos;
        }
        return sum;
    }

    public void setGoal(int[][] g, int N) {

        int numberAtPosition = 1;
        for (int row = 0; row < N; row++) {
            for (int column = 0; column < N; column++) {
                goal[row][column] = numberAtPosition;
                numberAtPosition++;
            }
        }
        goal[N - 1][N - 1] = 0;

    }

    private int[] calRightPos(int N, int value, int[][] g) {
        int[] rightPos = new int[2];
        if (value == 0) {
            rightPos[0] = N - 1;
            rightPos[1] = N - 1;
        } else {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (g[i][j] == value) {
                        rightPos[0] = i;
                        rightPos[1] = j;
                    }
                }
            }
        }

        return rightPos;
    }

    /**
     * Returns value of tile at row i, column j (or 0 if blank)
     * @param i
     * @param j
     * @return
     */
    public int tileAt(int i, int j) {
        if ((i < 0 || i > size() - 1) || (j < 0 || j > size() - 1)) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return board[i][j];
    }

    /**
     * Returns the board size N
     * @return
     */
    public int size() {
        return board.length;
    }

    /**
     * The number of tiles in the wrong position
     * @return
     */
    public int hamming() {
        int wrong = 0;
        for (int i = 1; i < T.length; i++) {
            if (!T[i].inRightPosition()) {
                wrong++;
            }
        }
        return wrong;
    }


    /**
     * The sum of the Manhattan distances (sum of the vertical and horizontal distance)
     * from the tiles to their goal positions
     * @return
     */
    public int manhattan() {
        return distance;
    }

    /**
     * Returns true if this board's tile values are the same
     * position as y's
     * @param y
     * @return
     */
    public boolean equals(Object y) {

        if (y == null) {
            return false;
        }
        if (y == this) {
            return true;
        }
        if (y.getClass().isInstance(this)) {
            Board boardY = (Board) y;
            for (int i = 0; i < size(); i++) {
                for (int j = 0; j < size(); j++) {
                    if (board[i][j] != boardY.board[i][j]) {
                        return false;
                    }
                }
            }
        } else {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int N = size();
        int sum = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sum = (sum + board[i][j]) * 31;
            }
        }
        return sum;
    }

    /**
     * Estimated distance to goal. This method should
     * simply return the results of manhattan() when submitted to Gradescope.
     * @return
     */
    @Override
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    /**
     * Returns the neighbors of the current board. Cite from :http://joshh.ug/neighbors.html.
     */
    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == 0) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = 0;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = 0;
                }
            }
        }


        return neighbors;
    }

    /** Returns the string representation of the board.
     * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }
}
