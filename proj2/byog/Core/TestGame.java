package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

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

    private static void randomTest() {
        Game game = new Game();
        Random random = new Random();
        TERenderer teRenderer = new TERenderer();
        for (int i = 0; i < 1000; i++) {
            long seedNumber = Math.abs(random.nextInt());
            String input = "n" + Long.toString(seedNumber) + "s";
            TETile[][] tiles = game.playWithInputString(input);

            //teRenderer.initialize(WIDTH, HEIGHT);
            //teRenderer.renderFrame(tiles);

        }
    }

    public static void main(String [] args) {
        /**Game game = new Game();
        //game.playWithKeyboard();

        TETile[][] tiles0 = game.playWithInputString("n999999987777899887s");
        //TETile[][] tiles1 = game.playWithInputString("n5197880843569031643s");

        game.playWithInputString("N999SDDD:Q");
        game.playWithInputString("L:Q");
        game.playWithInputString("L:Q");
        TETile[][] tiles2 = game.playWithInputString("LWWWDDD");
        TERenderer teRenderer = new TERenderer();
        teRenderer.initialize(WIDTH, HEIGHT);
        teRenderer.renderFrame(tiles0);*/
        randomTest();

    }



}
