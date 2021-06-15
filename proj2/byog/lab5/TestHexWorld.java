package byog.lab5;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import org.junit.Test;

import javax.swing.text.Position;

import static org.junit.Assert.*;

public class TestHexWorld {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    /**@Test
    public void testHexRwoWidth() {
        assertEquals(2,HexWorld.hexRowWidth(2,0));
        assertEquals(4,HexWorld.hexRowWidth(2,1));
        assertEquals(4,HexWorld.hexRowWidth(2,2));
        assertEquals(2,HexWorld.hexRowWidth(2,3));
        assertEquals(3,HexWorld.hexRowWidth(3,0));
        assertEquals(5,HexWorld.hexRowWidth(3,1));
        assertEquals(7,HexWorld.hexRowWidth(3,2));
        assertEquals(7,HexWorld.hexRowWidth(3,3));
        assertEquals(4,HexWorld.hexRowWidth(4,7));
        assertEquals(11,HexWorld.hexRowWidth(5,6));
    }

    @Test
    public void testGetXOffset() {
        assertEquals(0, HexWorld.getXOffset(2,0));
        assertEquals(-1, HexWorld.getXOffset(2,1));
        assertEquals(-1, HexWorld.getXOffset(2,2));
        assertEquals(0, HexWorld.getXOffset(2,3));
        assertEquals(-2,HexWorld.getXOffset(3,3));
        assertEquals(-3,HexWorld.getXOffset(5,6));
    }

    @Test
    public void testColumnNumber() {
        assertEquals(3,HexWorld.getColumnHexNumber(0));
        assertEquals(4,HexWorld.getColumnHexNumber(1));
        assertEquals(5,HexWorld.getColumnHexNumber(2));
    }

    @Test
    public void testGetColumnStartingPosition() {
        int[] t = new int[]{0, 0};
        int[] expectedAtZero = new int[] {0, 0};
        int[] expectedAtOne = new int[] {5, -3};
        int[] expectedAtTwo = new int[] {10, -6};
        int[] expectedAtFour = new int[] {20, 0};
        int[] columnZeroPos = HexWorld.getColumnStartingPosition(t,3,0);
        int[] columnOnePos = HexWorld.getColumnStartingPosition(t,3,1);
        int[] columnTwoPos = HexWorld.getColumnStartingPosition(t,3,2);
        int[] columnFourPos = HexWorld.getColumnStartingPosition(t,3,4);
        assertArrayEquals(expectedAtZero, columnZeroPos);
        assertArrayEquals(expectedAtOne, columnOnePos);
        assertArrayEquals(expectedAtTwo,columnTwoPos);
    }

    @Test
    public void testColumnPositions() {
        int[] t = new int[] {0, 0};
        int[][] expectedColumnZero = new int[][] {{0, 0}, {0, 6}, {0, 12}};
        int[][] actualColumnZero = HexWorld.getColumnPositions(t, 3, 0);
        assertArrayEquals(expectedColumnZero, actualColumnZero);
        int[][]  expectedColumnOne = new int[][] {{5, -3}, {5, 3}, {5, 9}, {5, 15}};
        int[][] actualColumnOne = HexWorld.getColumnPositions(t,3,1);
        assertArrayEquals(expectedColumnOne, actualColumnOne);
    }*/

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
