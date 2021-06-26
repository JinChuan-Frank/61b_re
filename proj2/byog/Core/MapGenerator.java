package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class MapGenerator {

    private static final int WIDTH = 50;
    private static final int HEIGHT = 35;

    public static class Position {
        public int xPos;
        public int yPos;

        public Position(int x, int y) {
            xPos = x;
            yPos = y;
        }
    }
    public static class Room {
        public Position position;
        public int width;
        public int height;

        public Room(Position p, int w, int h) {
            position = p;
            width = w;
            height = h;
        }
    }

    public static Room generateRandomRoom() {
        RandomUtils randomUtils = new RandomUtils();
        Random random = new Random();
        boolean isEligible = false;
        Room room = new Room(new Position(0,0), 0, 0);
        while (isEligible == false) {
            int xPos = randomUtils.uniform(random, WIDTH);
            int yPos = randomUtils.uniform(random, HEIGHT);
            Position position = new Position(xPos, yPos);
            int width = randomUtils.uniform(random, 3, WIDTH);
            int height = randomUtils.uniform(random, 3, HEIGHT);
            room = new Room(position, width, height);
            isEligible = isEligibleRoom(room);

        }
        return room;
    }

    private static boolean isEligibleRoom(Room room) {
        Position position = room.position;
        int width = room.width;
        int height = room.height;
        Position end = calEndingPosition(position, width, height);
        if (end.xPos < WIDTH && end.yPos < HEIGHT) {
            return true;
        } else {
            return false;
        }
    }

    public static void drawNeighboringRoom(Room current, Position exitPoint, int width, int height, TETile[][] world) {
        Position neighboringRoomPos = calNeighborRoomPosition(current, exitPoint, width, height);
        Room neighborRoom = new Room(neighboringRoomPos, width, height);
        drawSingleRoom(neighborRoom, world);
        drawExit(exitPoint, world);
    }

    public static Position calNeighborRoomPosition(Room current, Position exit, int width, int height) {
        Position end = calEndingPosition(current.position, current.width, current.height);
        int neighborRoomXPos = 0;
        int neighborRoomYPos = 0;
        if (exit.xPos == current.position.xPos) {
             neighborRoomXPos = exit.xPos - (width - 1);
             neighborRoomYPos = exit.yPos - 1;
        } else if (exit.xPos == end.xPos) {
             neighborRoomXPos = exit.xPos;
             neighborRoomYPos = exit.yPos - 1;
        } else if (exit.yPos == current.position.yPos) {
            neighborRoomXPos = exit.xPos - 1;
            neighborRoomYPos = exit.yPos - (height - 1);
        } else if(exit.yPos == end.yPos) {
            neighborRoomXPos = exit.xPos - 1;
            neighborRoomYPos =exit.yPos;
        }
        return new Position(neighborRoomXPos,neighborRoomYPos);
    }

    private static void drawExit(Position exit, TETile[][] world) {
        world[exit.xPos][exit.yPos] = Tileset.FLOOR;
    }

    public static void drawSingleRoom(Room room, TETile[][] world) {
        Position startingPoint = room.position;
        int width = room.width;
        int height = room.height;
        Position endPoint = calEndingPosition(startingPoint, width, height);
        drawRoomWalls(startingPoint, endPoint, world);
        drawRoomFloors(startingPoint, endPoint, world);
    }

    private static Position calEndingPosition(Position startingPoint, int width, int height) {
        int xPositionEnd = startingPoint.xPos + width - 1;
        int yPositionEnd = startingPoint.yPos + height - 1;
        return new Position(xPositionEnd, yPositionEnd);
    }

    private static void drawRoomWalls(Position startingPoint, Position endPoint, TETile[][] world) {
        for (int i = startingPoint.xPos; i <= endPoint.xPos; i++) {
            world[i][startingPoint.yPos] = Tileset.WALL;
            world[i][endPoint.yPos] = Tileset.WALL;
        }
        for (int j = startingPoint.yPos; j <= endPoint.yPos; j++) {
            world[startingPoint.xPos][j] = Tileset.WALL;
            world[endPoint.xPos][j] = Tileset.WALL;
        }
    }

    private static void drawRoomFloors(Position startingPoint, Position endPoint, TETile[][] world) {
        int floorStartX = startingPoint.xPos + 1;
        int floorStartY = startingPoint.yPos + 1;
        int floorEndX = endPoint.xPos - 1;
        int floorEndY = endPoint.yPos - 1;
        for (int i = floorStartX; i <= floorEndX; i++) {
            for (int j = floorStartY; j <= floorEndY; j++) {
                world[i][j] = Tileset.FLOOR;
            }
        }
    }
}
