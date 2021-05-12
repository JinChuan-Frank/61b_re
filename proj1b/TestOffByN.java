import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    CharacterComparator offByFive = new OffByN(5);

    @Test
    public void testOffByN() {
        char a = 'a';
        char b = 'f';
        char c = 'h';
        assertTrue(offByFive.equalChars(a, b));
        assertFalse(offByFive.equalChars(a, c));
    }
}
