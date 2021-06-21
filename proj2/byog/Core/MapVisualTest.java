package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;


public class MapVisualTest {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 35;


    public static void testDrawSingleRoom() {
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        initializeWorld(world);
        MapGenerator.Position start = new MapGenerator.Position(2,2);
        MapGenerator.Position end = new MapGenerator.Position(7,6);
        MapGenerator.drawRoomFloors(start, end, world);
    }

    private static void initializeWorld(TETile[][] t) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                t[x][y] = Tileset.NOTHING;
            }
        }
    }

    public static void main(String[] args){
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        testDrawSingleRoom();
    }


}
