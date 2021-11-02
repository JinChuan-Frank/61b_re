package hw3.hash;

import org.junit.Test;


import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.*;


public class TestSimpleOomage {

    @Test
    public void testHashCodeDeterministic() {
        SimpleOomage so = SimpleOomage.randomSimpleOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    @Test
    public void testHashCodePerfect() {
        /*
          no two SimpleOomages should EVER have the same
          hashCode UNLESS they have the same red, blue, and green values!
         */
        SimpleOomage ooA = new SimpleOomage(5, 10, 20);
        SimpleOomage ooB = new SimpleOomage(10, 5, 20);
        SimpleOomage ooC = new SimpleOomage(20, 10, 5);
        SimpleOomage ooD = new SimpleOomage(5, 20, 10);
        SimpleOomage ooE = new SimpleOomage(10, 20, 5);
        SimpleOomage ooF = new SimpleOomage(20, 5, 10);
        SimpleOomage ooA1 = new SimpleOomage(5, 10, 20);

        ArrayList arrayList = new ArrayList();
        arrayList.add(ooA);
        arrayList.add(ooB);
        arrayList.add(ooC);
        arrayList.add(ooD);
        arrayList.add(ooE);
        arrayList.add(ooF);
        for (int i = 0; i < arrayList.size(); i++){
            for (int j = i + 1; j < arrayList.size(); j++) {
                assertNotEquals(arrayList.get(i).hashCode(), arrayList.get(j).hashCode());
            }
        }

        assertEquals(ooA, ooA1);

    }

    @Test
    public void testEquals() {
        SimpleOomage ooA = new SimpleOomage(5, 10, 20);
        SimpleOomage ooA2 = new SimpleOomage(5, 10, 20);
        SimpleOomage ooB = new SimpleOomage(50, 50, 50);
        assertEquals(ooA, ooA2);
        assertNotEquals(ooA, ooB);
        assertNotEquals(ooA2, ooB);
        assertNotEquals(ooA, "ketchup");
    }

    @Test
    public void testHashCodeAndEqualsConsistency() {
        SimpleOomage ooA = new SimpleOomage(5, 10, 20);
        SimpleOomage ooA2 = new SimpleOomage(5, 10, 20);
        HashSet<SimpleOomage> hashSet = new HashSet<>();
        hashSet.add(ooA);
        assertTrue(hashSet.contains(ooA2));
    }

    /* TODO: Uncomment this test after you finish haveNiceHashCode Spread in OomageTestUtility */
    /*@Test
    public void testRandomOomagesHashCodeSpread() {
        List<Oomage> oomages = new ArrayList<>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(SimpleOomage.randomSimpleOomage());
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10));
    }*/

    /** Calls tests for SimpleOomage. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestSimpleOomage.class);
    }
}
