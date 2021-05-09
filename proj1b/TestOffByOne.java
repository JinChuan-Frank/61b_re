import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {

    // You must use this CharacterComparator and not instantiate
    // new ones, or the auto grader might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    //Uncomment this class once you've created your CharacterComparator interface and OffByOne class. **/
    @Test
    public void TestOffByOne(){
        char a = 'a';
        char b = 'a';
        char c = 'b';
        char d = 'A';
        assertTrue(offByOne.equalChars(a,b));
        assertFalse(offByOne.equalChars(b,c));
        assertFalse(offByOne.equalChars(a,d));
    }
}
