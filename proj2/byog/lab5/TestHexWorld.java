package byog.lab5;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestHexWorld {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    @Test
    public void testHexRwoWidth() {
        assertEquals(2, HexWorld.hexRowWidth(2, 0));
        assertEquals(4, HexWorld.hexRowWidth(2, 1));
        assertEquals(4, HexWorld.hexRowWidth(2, 2));
        assertEquals(2, HexWorld.hexRowWidth(2, 3));
        assertEquals(3, HexWorld.hexRowWidth(3, 0));
        assertEquals(5, HexWorld.hexRowWidth(3, 1));
        assertEquals(7, HexWorld.hexRowWidth(3, 2));
        assertEquals(7, HexWorld.hexRowWidth(3, 3));
        assertEquals(4, HexWorld.hexRowWidth(4, 7));
        assertEquals(11, HexWorld.hexRowWidth(5, 6));
    }

    @Test
    public void testGetXOffset() {
        assertEquals(0, HexWorld.getXOffset(2, 0));
        assertEquals(-1, HexWorld.getXOffset(2, 1));
        assertEquals(-1, HexWorld.getXOffset(2, 2));
        assertEquals(0, HexWorld.getXOffset(2, 3));
        assertEquals(-2, HexWorld.getXOffset(3, 3));
        assertEquals(-3, HexWorld.getXOffset(5, 6));
    }

    @Test
    public void testColumnNumber() {
        assertEquals(3, HexWorld.getColumnHexNumber(0));
        assertEquals(4, HexWorld.getColumnHexNumber(1));
        assertEquals(5, HexWorld.getColumnHexNumber(2));
    }

    @Test
    public void testGetColumnStartingPosition() {
        int[] t = new int[]{0, 0};
        int[] expectedAtZero = new int[] {0, 0};
        int[] expectedAtOne = new int[] {5, -3};
        int[] expectedAtTwo = new int[] {10, -6};
        int[] expectedAtThree = new int[] {15, -3};
        int[] expectedAtFour = new int[]{20, 0};
        int[] columnZeroPos = HexWorld.getColumnStartingPosition(t, 3, 0);
        int[] columnOnePos = HexWorld.getColumnStartingPosition(t, 3, 1);
        int[] columnTwoPos = HexWorld.getColumnStartingPosition(t, 3, 2);
        int[] columnThreePos = HexWorld.getColumnStartingPosition(t, 3,3);
        int[] columnFourPos = HexWorld.getColumnStartingPosition(t, 3, 4);
        assertArrayEquals(expectedAtZero, columnZeroPos);
        assertArrayEquals(expectedAtOne, columnOnePos);
        assertArrayEquals(expectedAtTwo, columnTwoPos);
        assertArrayEquals(expectedAtFour, columnFourPos);
    }

    @Test
    public void testColumnPositions() {
        int[] t = new int[]{0, 0};
        int[][] expectedColumnZero = new int[][]{{0, 0}, {0, 6}, {0, 12}};
        int[][] actualColumnZero = HexWorld.getColumnPositions(t, 3, 0);
        assertArrayEquals(expectedColumnZero, actualColumnZero);
        int[][] expectedColumnOne = new int[][]{{5, -3}, {5, 3}, {5, 9}, {5, 15}};
        int[][] actualColumnOne = HexWorld.getColumnPositions(t, 3, 1);
        assertArrayEquals(expectedColumnOne, actualColumnOne);
    }
}


