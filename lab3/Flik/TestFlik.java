import static org.junit.Assert.*;

import org.junit.Test;

public class TestFlik {
    @Test
    public void testIsSameNumber() {
        int a = 3;
        int b = 3;
        int c = -3;
        assertTrue(Flik.isSameNumber(a, b));
        assertFalse(Flik.isSameNumber(b, c));
        int x = 3;
        int y = 59;
        assertFalse(Flik.isSameNumber(x, y));
    }

    @Test
    public void testSteve() {
        int a = 128;
        int b = 128;
        System.out.println(Flik.isSameNumber(a, b));
        assertTrue("a = 128", Flik.isSameNumber(a, b));
    }

}

