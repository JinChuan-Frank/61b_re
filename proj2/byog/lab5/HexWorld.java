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
     *
     * @param p
     * @param size
     * @param t

    public void tesselateHex(int[] p, int size, TETile[][] t) {

        for (int i = 0; i < pArrays.length; i ++) {
            addHexagon(size,pArrays[i],t);
        }
    } */



    /**
     *
     * @param i The ith column. 0 is the leftmost.
     * @param s The size of hex.
     * @param startPosition The starting position.
     * @return

    public int[][] getColumnsPosition(int i, int s, int[] startPosition) {
        int n = getColumnHexNumber(i);
        int [][] hexPositions = new int[n][2];
        for (int j = 0; j < n; j ++) {
            hexPositions[j] = new int[]{xp, yp + s * j};
        }
        return hexPositions;
    }*/

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
