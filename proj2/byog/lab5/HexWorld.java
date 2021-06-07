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

    public static void addHexgagon() {

    }

    /**
     * Computes the width of row i for a size s hexagon.
     * @param s The size of the hex.
     * @param i The row number where i = 0 is the bottom row.
     * @return
     */
    public static int hexRowWidth(int s, int i) {
        int rowWidth = s;
        if ( i < s) {
            rowWidth = s + i * 2;
        } else if (i == s) {
            rowWidth = 3 * s - 2;
        } else {
            rowWidth = 3 * s - 2 - 2 * (i - s);
        }
        return rowWidth;
    }



    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
    }

}
