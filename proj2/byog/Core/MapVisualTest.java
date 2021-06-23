package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;


public class MapVisualTest {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 35;

    public static void testDrawNeighborRoom() {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        initializeWorld(world);
        MapGenerator.Position start = new MapGenerator.Position(2,2);
        MapGenerator.Room current = new MapGenerator.Room(start, 4, 4);
        MapGenerator.Position exit = new MapGenerator.Position(3,5);
        MapGenerator.drawSingleRoom(start, 4, 4, world);
        MapGenerator.drawNeighboringRoom(current, exit, 3, 5, world );
        //MapGenerator.Position p = MapGenerator.calNeighborRoomPosition(current, exit, )
        ter.renderFrame(world);
    }

    public static void testDrawSingleRoom() {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        initializeWorld(world);
        MapGenerator.Position start = new MapGenerator.Position(0,0);
        MapGenerator.drawSingleRoom(start, 10, 4, world);
        ter.renderFrame(world);
    }

    private static void initializeWorld(TETile[][] t) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                t[x][y] = Tileset.NOTHING;
            }
        }
    }

    public static void main(String[] args){

        testDrawNeighborRoom();

    }


}
