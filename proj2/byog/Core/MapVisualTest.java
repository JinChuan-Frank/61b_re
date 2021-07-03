package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;


public class MapVisualTest {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 35;

    public static void testGenerateRooms() {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        initializeWorld(world);
        ArrayList<MapGenerator.RoomWithExits> rooms = MapGenerator.generateRooms(5);
        MapGenerator.drawRooms(rooms, world);
        ter.renderFrame(world);
    }

    public static void testCheckOverlap() {
        MapGenerator.Room room1 = new MapGenerator.Room(new MapGenerator.Position(2,2),3,3);
        MapGenerator.Room room2 = new MapGenerator.Room(new MapGenerator.Position(5,2),3,3);
        MapGenerator.Room room3 = new MapGenerator.Room(new MapGenerator.Position(5,5),3,3);
        MapGenerator.Room room4 = new MapGenerator.Room(new MapGenerator.Position(2,3),6,8);
        ArrayList<MapGenerator.Room> rooms = new ArrayList<>();
        rooms.add(0,room1);
        rooms.add(1,room2);
        rooms.add(2,room3);
        boolean isOverlap = MapGenerator.checkOverlap(room4,rooms);
        System.out.print(isOverlap);
    }

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
        MapGenerator.Room room1 = new MapGenerator.Room(position, 3, 8);
        ArrayList<MapGenerator.Room> rooms = new ArrayList<>();
        rooms.add(0, room1);
        MapGenerator.Position exit = MapGenerator.generateRandomExit(room1);
        MapGenerator.Room room2 = MapGenerator.generateRandomHallWay(room1, exit, rooms);
        MapGenerator.drawSingleRoom(room1, world);
        MapGenerator.drawSingleRoom(room2, world);
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
        MapGenerator.Position start = new MapGenerator.Position(10,10);
        MapGenerator.Room current = new MapGenerator.Room(start, 7, 8);
        MapGenerator.Position exit = MapGenerator.generateRandomExit(current);
        //MapGenerator.Room room = MapGenerator.generateRandomHallWay(current, exit);
        MapGenerator.drawSingleRoom(current, world);
        //MapGenerator.drawSingleRoom(room, world);
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
        testGenerateRooms();
    }


}
