package hw4.puzzle;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestBoard {
    @Test
    public void verifyImmutability() {
        int r = 2;
        int c = 2;
        int[][] x = new int[r][c];
        int cnt = 0;
        for (int i = 0; i < r; i += 1) {
            for (int j = 0; j < c; j += 1) {
                x[i][j] = cnt;
                cnt += 1;
            }
        }
        Board b = new Board(x);
        assertEquals("Your Board class is not being initialized with the right values.", 0, b.tileAt(0, 0));
        assertEquals("Your Board class is not being initialized with the right values.", 1, b.tileAt(0, 1));
        assertEquals("Your Board class is not being initialized with the right values.", 2, b.tileAt(1, 0));
        assertEquals("Your Board class is not being initialized with the right values.", 3, b.tileAt(1, 1));

        x[1][1] = 1000;
        assertEquals("Your Board class is mutable and you should be making a copy of the values in the passed tiles array. Please see the FAQ!", 3, b.tileAt(1, 1));
    }

    @Test
    public void testConstructor() {
        int[][] tiles = {{3, 0}, {1, 2}};
        Board board = new Board(tiles);
        assertEquals(board.goal[0][0], 1);
        assertEquals(board.goal[0][1], 2);
        assertEquals(board.goal[1][0], 3);
        assertEquals(0, board.goal[1][1]);

        int[][] tiles1 = new int[3][3];
        int cnt = 0;
        for (int i = 0; i < 3; i += 1) {
            for (int j = 0; j < 3; j += 1) {
                tiles1[i][j] = cnt;
                cnt += 1;
            }
        }
        Board board1 = new Board(tiles1);
        assertEquals(1, board1.tileAt(0, 1));
        assertEquals(8, board1.tileAt(2, 2));
        assertEquals(8, board1.goal[2][1]);
        assertEquals(0, board1.goal[2][2]);
    }
}
