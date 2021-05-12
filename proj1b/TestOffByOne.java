import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {

    // You must use this CharacterComparator and not instantiate
    // new ones, or the auto grader might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    @Test
    public void testOff() {
        char a = 'a';
        char b = 'b';
        char c = 'd';
        char d = '&';
        char e = '%';
        assertTrue(offByOne.equalChars(a, b));
        assertFalse(offByOne.equalChars(a, c));
        assertTrue(offByOne.equalChars(d, e));
    }
}
