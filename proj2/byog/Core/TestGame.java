package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class TestGame {
    public static final int WIDTH = 80;
    public static final int HEIGHT = 36;

    private static void initializeWorld(TETile[][] t) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                t[x][y] = Tileset.NOTHING;
            }
        }
    }

    public static void main(String [] args) {
        Game game = new Game();
        //game.playWithKeyboard();

        TETile[][] tiles0 = game.playWithInputString("N999SDDDWWWDDD");

        TETile[][] tiles1 = game.playWithInputString("N999SDDD:Q");
        TETile[][] tiles2 = game.playWithInputString("L:Q");
        TERenderer teRenderer = new TERenderer();
        teRenderer.initialize(WIDTH, HEIGHT);
        teRenderer.renderFrame(tiles2);
    }



}
