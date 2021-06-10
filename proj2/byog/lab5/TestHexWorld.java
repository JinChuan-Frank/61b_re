package byog.lab5;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import org.junit.Test;
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
    }*/

    public static void main(String[] args) {
        //TETile[][] world = new TETile[WIDTH][HEIGHT];
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        HexWorld w = new HexWorld();
        w.addHexagon(2,15,15,world);
        ter.renderFrame(world);
    }
}
