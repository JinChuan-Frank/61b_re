package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class MapGenerator {
    public static class Position {
        private int xPos;
        private int yPos;

        public Position(int x, int y) {
            xPos = x;
            yPos = y;
        }
    }
    public static class Room {
        private Position position;
        private int width;
        private int height;

        public Room(Position p, int w, int h) {
            position = p;
            width = w;
            height = h;
        }
    }

    public static void drawNeighboringRoom(Room current, Position exitPoint, int width, int height, TETile[][] world) {
        Position start = current.position;
        int currentWidth = current.width;
        int currentHeight = current.height;
        Position end = calEndingPosition(start, currentWidth, currentHeight);
        if (exitPoint.xPos == end.xPos) {
            drawSingleRoom(exitPoint, width, height, world);

        }
    }


    private static void drawExits(Position[] exits, TETile[][] world) {
        for (Position p : exits) {
            world[p.xPos][p.yPos] = Tileset.FLOOR;
        }
    }

    public static void drawSingleRoom(Position startingPoint, int width, int height, TETile[][] world) {
        Position endPoint = calEndingPosition(startingPoint, width, height);
        drawRoomWalls(startingPoint, endPoint, world);
        drawRoomFloors(startingPoint, endPoint, world);
    }

    private static Position calEndingPosition(Position startingPoint, int width, int height) {
        int xPositionEnd = startingPoint.xPos + width - 1;
        int yPositionEnd = startingPoint.yPos + height - 1;
        return new Position(xPositionEnd, yPositionEnd);
    }

    public static void drawRoomWalls(Position startingPoint, Position endPoint, TETile[][] world) {
        for (int i = startingPoint.xPos; i <= endPoint.xPos; i++) {
            world[i][startingPoint.yPos] = Tileset.WALL;
            world[i][endPoint.yPos] = Tileset.WALL;
        }
        for (int j = startingPoint.yPos; j <= endPoint.yPos; j++) {
            world[startingPoint.xPos][j] = Tileset.WALL;
            world[endPoint.xPos][j] = Tileset.WALL;
        }
    }

    public static void drawRoomFloors(Position startingPoint, Position endPoint, TETile[][] world) {
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
