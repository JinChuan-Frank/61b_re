package byog.lab5;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    /**
     * Add one column of Hexagons.
     * @param p Starting position of the entire HexWorld.
     * @param s Size of Hex.
     * @param i Size of Hex.
     */
    public static void addOneColumn(int[] p, int s, int i, TETile[][] tiles, TETile t) {
        int[][] positions = getColumnPositions(p, s, i);
        for (int j = 0; j < positions.length; j++) {
            addHexagon(s, positions[j], tiles, t);
        }
    }

    /**
     * Calculate starting positions of Hexagons in a certain column.
     * @param p Starting position of the entire HexWorld.
     * @param i Size of Hex.
     * @param s Size of Hex.
     * @return
     */
    public static int[][] getColumnPositions(int[] p, int s, int i) {
        int numberOfHex = getColumnHexNumber(i);
        int[] startPosition = getColumnStartingPosition(p, s, i);
        int[][] positions = new int[numberOfHex][2];
        int xp = startPosition[0];
        int yp = startPosition[1];
        for (int j = 0; j < numberOfHex; j++) {
            positions[j] = new int[] {xp, yp + 2 * s * j};
        }
        return positions;
    }

    /**
     *
     * @param p Starting position of the entire HexWorld.
     * @param s Size of Hex.
     * @param i The ith column in the HexWorld.
     * @return
     */
    public static int[] getColumnStartingPosition(int[] p, int s, int i) {
        if (i <= 2) {
            return new int[]{p[0] + i *(2 * s - 1), p[1] - (i * s) };
        } else {
            return new int[]{p[0] + i *(2 * s - 1), p[1] - ((i -2) * s)};
        }
    }

    public static int getColumnHexNumber(int i) {
        switch (i) {
            case 0 :
            case 4 :
                return  3;
            case 1 :
            case 3 :
                return  4;
            case 2 : return  5;
            default: return 0;
        }
    }
    /**
     *
     * @param size The size of the hex.
     * @param position The 0th number is x coordinates, the 1st is y coordinates.
     * @param tiles
     */
    public static void addHexagon(int size, int[] position, TETile[][] tiles, TETile t) {
        for (int y = position[1]; y < position[1] + size * 2; y++) {
            for (int x = position[0] + HexWorld.getXOffset(size, y - position[1]);
                 x < position[0] + HexWorld.getXOffset(size, y - position[1])
                         + HexWorld.hexRowWidth(size, y - position[1]); x++ )
                tiles[x][y] = t;
        }
    }

    /**
     * Computes the width of row i for a size s hexagon.
     * @param s The size of the hex.
     * @param i The row number where i = 0 is the bottom row.
     * @return
     */
    public static int hexRowWidth(int s, int i) {
        int rowWidth;
        if ( i < s) {
            rowWidth = s + i * 2;
        } else if (i == s) {
            rowWidth = 3 * s - 2;
        } else {
            rowWidth = 3 * s - 2 - 2 * (i - s);
        }
        return rowWidth;
    }

    /**
     * Computes the XOffset of a certain row relative to the leftmost point.
     * @param i The row number where 0 is the bottom row.
     * @param s The size of the hex.
     */
    public static int getXOffset(int s, int i) {
        int xOffset = 0;
        if (i <= s - 1) {
            xOffset = - i;
        } else if (i == s) {
            xOffset = - (i - 1);
        } else {
            xOffset = - (2 * s -1 - i);
        }
        return xOffset;
    }
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        TETile type = Tileset.FLOWER;
        int[] start = new int[] {5,5};
        int size = 3;
        int row = 0;
        HexWorld.addOneColumn(start, size, row, world, type);
        ter.renderFrame(world);

    }

}
