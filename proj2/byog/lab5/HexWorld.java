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

    public void tesselateHex(int[][] pArrays, ) {

    }

    public int[][] getColumnsPosition(int[] s) {
        int [][] rowOnePisition = new int[][] {s}
    }

    /**
     *
     * @param size The size of the hex.
     * @param position The 0th number is x coordinates, the 1st is y coordinates.
     * @param tiles
     */
    public void addHexagon(int size, int[] position, TETile[][] tiles) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }

        for (int y = position[1]; y < position[1] + size * 2; y++) {
            for (int x = position[0] + HexWorld.getXOffset(size, y - position[1]);
                 x < position[0] + HexWorld.getXOffset(size, y - position[1])
                         + HexWorld.hexRowWidth(size, y - position[1]); x++ )
                tiles[x][y] = Tileset.WALL;

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





}
