package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestHexWorld {
    @Test
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
}
