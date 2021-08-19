package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.net.PortUnreachableException;
import java.util.Locale;


public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 36;
    int halfWidth = WIDTH / 2;
    int halfHeight = HEIGHT / 2;
    boolean isPlayerTurn;


    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        displayMainMenu();
    }

    private void displayMainMenu() {
        setCanvas();
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
        solicitUserInput();
    }

    private void setCanvas() {
        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
    }

    private void solicitUserInput() {
        isPlayerTurn = true;
        while (isPlayerTurn) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            if (Character.toLowerCase(key) == 'n' ) {
                isPlayerTurn = false;
                newGame();
            }
        }
    }

    private void newGame() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.white);
        Font bigFont = new Font("Arial", Font.BOLD, 60);
        StdDraw.setFont(bigFont);
        StdDraw.text(halfWidth, halfHeight, "Please enter seed, press 'S' to end");
        StdDraw.show();
        long seed = Long.parseLong(readSeed());
        MapGenerator mapGenerator = new MapGenerator(seed);
        TETile[][] world = mapGenerator.generateWorld();
        isPlayerTurn = true;
        showMapAndTrackMovement(mapGenerator, world);
    }

    private void showMapAndTrackMovement(MapGenerator mapGenerator, TETile[][] world) {
        TERenderer teRenderer = new TERenderer();
        teRenderer.initialize(WIDTH, HEIGHT);
        teRenderer.renderFrame(world);

        while (isPlayerTurn) {
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            headUpDisplay(x, y, world);
            teRenderer.renderFrame(world);
            if (StdDraw.mouseX() != x || StdDraw.mouseY() != y) {
                continue;
            }
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char input = StdDraw.nextKeyTyped();
            Character.toLowerCase(input);
            mapGenerator.movePlayer(input, world);
            teRenderer.renderFrame(world);
        }

    }

    private void headUpDisplay(double x, double y, TETile[][] world) {
        try {
            String text = "";
            int xOffSet = (int) x;
            int yOffSet = (int) y;
            TETile teTile = world[xOffSet][yOffSet];

            if (teTile == Tileset.WALL) {
                text = "Wall";
            } else if (teTile == Tileset.FLOOR) {
                text = "Floor";
            } else if (teTile == Tileset.PLAYER) {
                text = "Player";
            }

            StdDraw.setPenColor(Color.WHITE);
            StdDraw.textLeft(1, HEIGHT - 1, text);
            StdDraw.show();

        } catch (IndexOutOfBoundsException e) {

        }

    }

    private char readInput() {
        isPlayerTurn = true;
        char input = ' ';
        while (isPlayerTurn) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            input = StdDraw.nextKeyTyped();
            isPlayerTurn = false;
        }
        return input;
    }

    private String readSeed() {
        StdDraw.setPenColor(Color.white);
        isPlayerTurn = true;
        String input = "";
        while (isPlayerTurn) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            StdDraw.clear(Color.BLACK);
            char key = StdDraw.nextKeyTyped();
            Character.toLowerCase(key);
            if (key == 's') {
                break;
            }
            input = input + key;
            drawFrame(input);
        }

        isPlayerTurn = false;
        System.out.println(input);
        return input;

    }

    private void drawFrame(String s) {
        Font bigFont = new Font("Arial", Font.BOLD, 30);
        StdDraw.setFont(bigFont);
        StdDraw.text(halfWidth, halfHeight, s);
        StdDraw.show();
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
        input = input.toLowerCase();
        String substring = input.substring(input.indexOf('n') + 1, input.indexOf('s') - 1);
        MapGenerator mapGenerator = new MapGenerator(Long.parseLong(substring));
        TETile[][] finalWorldFrame = mapGenerator.generateWorld();
        return finalWorldFrame;
    }

}
