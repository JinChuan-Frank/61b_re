package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.net.PortUnreachableException;


public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 60;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        displayMainMenu();
    }

    private void displayMainMenu() {
        int halfWidth = WIDTH / 2;
        int halfHeight = HEIGHT / 2;
        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.white);
        Font bigFont = new Font("Arial", Font.BOLD, 60);
        Font smallFont = new Font("Arial", Font.BOLD, 40);
        StdDraw.setFont(bigFont);
        StdDraw.text(halfWidth, HEIGHT / 3 * 2, "CS 61B : THE GAME");
        StdDraw.setFont(smallFont);
        StdDraw.text(halfWidth, halfHeight, "New Game (N)");
        StdDraw.text(halfWidth, halfHeight - 5, "Load Game (L)");
        StdDraw.text(halfWidth, halfHeight - 10, "Quit (Q)");
        StdDraw.show();
    }

    private void solicitUserInput() {
        char key = StdDraw.nextKeyTyped();
        if (key == 'N' || key == 'n') {
            newGame();
        }
    }

    private void newGame() {

    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        TETile[][] finalWorldFrame = null;
        return finalWorldFrame;
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.displayMainMenu();
    }
}
