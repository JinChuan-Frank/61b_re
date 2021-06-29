package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import javax.swing.*;
import java.util.Random;


public class MapVisualTest {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 35;

    public static void testIsOverlap() {
        MapGenerator.Position position = new MapGenerator.Position(4,4);
        MapGenerator.Position newRoomPosition = new MapGenerator.Position(7,3);
        MapGenerator.Room oldRoom = new MapGenerator.Room(position, 5, 5);
        MapGenerator.Room newRoom = new MapGenerator.Room(newRoomPosition, 2, 2);
        boolean isOverlap = newRoom.isOverlap(oldRoom);
        System.out.print(isOverlap);
    }

    public static void testGenerateRandomNeighborRoom() {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        initializeWorld(world);
        MapGenerator.Position position = new MapGenerator.Position(10, 10);
        MapGenerator.Room room = new MapGenerator.Room(position, 3, 8);
        MapGenerator.drawSingleRoom(room, world);
        MapGenerator.Position exit = new MapGenerator.Position(11, 17);
        MapGenerator.Room neighborRoom = MapGenerator.generateRandomNeighborRoom(room, exit);
        MapGenerator.drawNeighborRoom(neighborRoom, exit, world);
        ter.renderFrame(world);
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

    public static void testGenerateRandomExit() {
        MapGenerator.Position position = new MapGenerator.Position(2,2);
        MapGenerator.Room room = new MapGenerator.Room(position, 4, 4);
        MapGenerator.Position exit = MapGenerator.generateRandomExit(room);
        System.out.print("Exit:" + exit.xPos + "*" + exit.yPos) ;
    }

    public static void testGenerateRandomHallWay() {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        initializeWorld(world);
        MapGenerator.Position start = new MapGenerator.Position(2,2);
        MapGenerator.Room current = new MapGenerator.Room(start, 4, 4);
        MapGenerator.Position exit = new MapGenerator.Position(5,4);
        MapGenerator.drawSingleRoom(current, world);
        MapGenerator.generateRandomHallWay(current, exit, 3, 3);
        //MapGenerator.Position p = MapGenerator.calNeighborRoomPosition(current, exit, )
        ter.renderFrame(world);
    }

    public static void testDrawSingleRoom() {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        initializeWorld(world);
        MapGenerator.Position start = new MapGenerator.Position(8, 19);
        MapGenerator.Room room = new MapGenerator.Room(start, 38, 16);
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
        testIsOverlap();
    }


}
