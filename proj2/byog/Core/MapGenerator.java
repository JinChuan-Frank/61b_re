package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapGenerator {

    private static final int WIDTH = 50;
    private static final int HEIGHT = 35;
    private static Random random = new Random();
    private static RandomUtils randomUtils = new RandomUtils();

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

        private boolean isEligibleRoom() {
            Position position = this.position;
            int width = this.width;
            int height = this.height;
            Position end = calEndingPosition(position, width, height);
            if (position.xPos >= 0 && position.xPos < width && position.yPos >= 0 && position.yPos < height &&
                    end.xPos >= 0 && end.xPos < WIDTH && end.yPos >= 0 && end.yPos < HEIGHT) {
                return true;
            } else {
                return false;
            }
        }

        public boolean isOverlap(Room oldRoom) {
            Position thisRoomPosition = this.position;
            Position oldRoomPosition = oldRoom.position;
            Position thisRoomEnd = calEndingPosition(position, this.width, this.height);
            Position oldRoomEnd = calEndingPosition(oldRoomPosition, oldRoom.width, oldRoom.height);
            if (thisRoomEnd.xPos < oldRoomPosition.xPos || thisRoomPosition.xPos > oldRoomEnd.xPos
                    || thisRoomPosition.yPos > oldRoomEnd.yPos || thisRoomEnd.yPos < oldRoomPosition.yPos) {
                return false;
            } else {
                return true;
            }
        }
    }

    public static Room[] generateRooms() {
        return null;
    }

    public static Room generateRandomRoom() {
        boolean isEligible = false;
        Room room = new Room(new Position(0,0), 0, 0);
        while (isEligible == false) {
            int xPos = randomUtils.uniform(random, WIDTH);
            int yPos = randomUtils.uniform(random, HEIGHT);
            Position position = new Position(xPos, yPos);
            int width = randomUtils.uniform(random, 3, WIDTH);
            int height = randomUtils.uniform(random, 3, HEIGHT);
            room = new Room(position, width, height);
            isEligible = room.isEligibleRoom();
        }
        return room;
    }

    public static void drawNeighborRoom(Room room, Position exit, TETile[][] world) {
        drawSingleRoom(room, world);
        drawExit(exit, world);
    }

    public static Room generateRandomHallWay(Room current, Position exit, int width, int height) {
        Position neighboringRoomPos = calNeighborRoomPosition(current, exit, width, height);
        Room neighborRoom = new Room(neighboringRoomPos, width, height);
        return neighborRoom;
    }

    public static Room generateRandomNeighborRoom(Room current, Position exit) {
        boolean isEligible = false;
        Room room = new Room(new Position(0,0), 0, 0);
        while (isEligible == false) {
            int width = randomUtils.uniform(random, 3, WIDTH);
            int height = randomUtils.uniform(random, 3, HEIGHT);
            int xOff = randomUtils.uniform(random, 0, width - 2);
            int yOff = randomUtils.uniform(random, 0, height - 2);
            int xPos = 0;
            int yPos = 0;
            Position currentEnd = calEndingPosition(current.position, current.width, current.height);
            if (exit.xPos == current.position.xPos + 1 && exit.yPos == current.position.yPos) {
                xPos = current.position.xPos - xOff;
                yPos = current.position.yPos - height + 1;
            } else if (exit.xPos == current.position.xPos + 1 && exit.yPos == currentEnd.yPos) {
                xPos = current.position.xPos - xOff;
                yPos = currentEnd.yPos;
            } else if (exit.yPos == current.position.yPos + 1 && exit.xPos == current.position.xPos) {
                xPos = current.position.xPos - (width - 1);
                yPos = current.position.yPos - yOff;
            } else if (exit.yPos == current.position.yPos + 1 && exit.xPos == currentEnd.xPos) {
                xPos = currentEnd.xPos;
                yPos = current.position.yPos - yOff;
            }
            room = new Room(new Position(xPos, yPos), width, height);
            isEligible = room.isEligibleRoom();
        }
        return room;
    }

    public static Position GenerateRandomExit(Room current) {
        int width = current.width;;
        int height = current.height;
        Position start = current.position;
        Position end = calEndingPosition(start, current.width, current.height);
        int numberOfPositions = 2 * (width + height - 4);
        List<Position> positions= new ArrayList<>();
        for (int j = start.xPos + 1; j < end.xPos; j ++) {
            Position lower = new Position(j, start.yPos);
            Position upper = new Position(j, end.yPos);
            positions.add(lower);
            positions.add(upper);
        }
        for (int k = start.yPos + 1; k < end.yPos; k++) {
            Position left = new Position(start.xPos, k);
            Position right = new Position(end.xPos, k);
            positions.add(left);
            positions.add(right);
        }
        Random random = new Random();
        int i = RandomUtils.uniform(random, numberOfPositions);
        return positions.get(i);
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
