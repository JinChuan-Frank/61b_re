import static org.junit.Assert.*;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class TestFlik {
    @Test
    public void testIsSameNumber() {
        int a = 3;
        int b = 3;
        int c = -3;
        assertTrue(Flik.isSameNumber(a,b));
        assertFalse(Flik.isSameNumber(b,c));
        int x = 3;
        int y = 59;
        assertFalse(Flik.isSameNumber(x,y));
    }

}

