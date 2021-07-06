package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.ArrayList;



public class MapVisualTest {
    private static final int WIDTH = 65;
    private static final int HEIGHT = 36;

    public static void testGenerateRooms() {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        initializeWorld(world);
        MapGenerator.generateRooms(6);
        MapGenerator.drawRooms(MapGenerator.rooms, world);
        MapGenerator.drawExits(MapGenerator.exits, world);
        ter.renderFrame(world);
    }

    public static void testGenerateRandomNeighborRoom() {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        initializeWorld(world);
        MapGenerator.Position position = new MapGenerator.Position(10, 10);
    }

    public static void testGenerateRandomRoom() {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        initializeWorld(world);
        MapGenerator.Room room = MapGenerator.generateRandomRoom();
        System.out.print("Position:" + room.position.xPos + "*" + room.position.yPos + " " + "Width" + room.width + " " + "Height" + room.height);
        MapGenerator.drawSingleRoom(room, world);
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
        testGenerateRooms();
    }

}
