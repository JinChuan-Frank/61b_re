package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapGenerator {

    private static final int WIDTH = 65;
    private static final int HEIGHT = 36;
    private static final long SEED = 34573445;
    private static final Random RANDOM = new Random(SEED);
    private static final RandomUtils randomUtils = new RandomUtils();
    public static ArrayList<Position> exits = new ArrayList<>();
    public static ArrayList<Room> rooms = new ArrayList<>();

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
        public Position exit;

        public Room(Position p, int w, int h, Position e) {
            position = p;
            width = w;
            height = h;
            exit = e;
        }

        private boolean isEligibleRoom() {
            Position position = this.position;
            int width = this.width;
            int height = this.height;
            Position end = calEndingPosition(position, width, height);
            return position.xPos >= 0 && position.xPos < WIDTH && position.yPos >= 0 && position.yPos < HEIGHT &&
                    end.xPos >= 0 && end.xPos < WIDTH && end.yPos >= 0 && end.yPos < HEIGHT;
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

    public static void drawRooms(ArrayList<Room> rooms, TETile[][] world) {
        drawSingleRoom(rooms.get(0), world);
        for (int i = 1; i < rooms.size(); i++) {
            drawRoomAndExit(rooms.get(i), world);
        }
    }

    public static void drawRoomAndExit(Room room, TETile[][] world) {
        drawSingleRoom(room, world);
        drawExit(room.exit, world);
    }

    public static void generateRooms() {
        generateStartRoom();
        generateNewRoom(6);
    }

    public static void generateStartRoom() {
        boolean isEligible = false;
        Room room = new Room(new Position(0,0), 0, 0,new Position(0,0));
        while (isEligible == false) {
            int xPos = randomUtils.uniform(RANDOM, WIDTH / 4);
            int yPos = randomUtils.uniform(RANDOM, HEIGHT / 3);
            Position position = new Position(xPos, yPos);
            int width = randomUtils.uniform(RANDOM, 3, (WIDTH - xPos) / 3);
            int height = randomUtils.uniform(RANDOM, 3, (HEIGHT - yPos) / 2);
            room = new Room(position, width, height, new Position(position.xPos + 1, position.yPos));
            isEligible = room.isEligibleRoom();
        }
        rooms.add(0, room);
    }


    public static void generateNewRoom(int times) {
        for (int i = 0; i < times; i ++) {
            int k = RandomUtils.uniform(RANDOM, rooms.size());
            Room room = rooms.get(k);
            Position exit = generateRandomExit(room);
            Room newRoom = branchOffThisRoom(room, exit);
            if (newRoom.isEligibleRoom() && checkOverlap(newRoom, room) == false){
                rooms.add(newRoom);
            }
        }
    }

    public static Room branchOffThisRoom(Room room, Position exit) {
        Room temp;
        if (room.width > 3 && room.height > 3) {
            temp = generateRandomHallWay(room, exit);
        } else if ((room.width == 3 && exit.xPos == room.position.xPos + 1)
                || (room.height == 3 && exit.yPos == room.position.yPos + 1)) {
            temp = generateRandomNeighborRoom(room, exit);
        } else {
            temp = generateRandomHallWay(room, exit);
        }
        return temp;
    }

    public static Room generateRandomHallWay(Room current, Position exit) {
        int width = 0;
        int height = 0;
        boolean isEligible = false;
        boolean isOverlap = true;
        int times = 0;
        Position start = current.position;
        Position end = calEndingPosition(start, current.width, current.height);
        Room hallWay = new Room(new Position(0,0), 0, 0, new Position(0,0));
        while ( (isEligible == false || isOverlap == true) && times <= 10 ) {
            if (exit.yPos == start.yPos) {
                width = 3;
                height = randomUtils.uniform(RANDOM, 3, start.yPos);
            } else if (exit.yPos == end.yPos) {
                width = 3;
                height = randomUtils.uniform(RANDOM, 3, HEIGHT - end.yPos);
            } else if (exit.xPos == start.xPos) {
                height = 3;
                width = randomUtils.uniform(RANDOM, 3, start.xPos);
            } else if (exit.xPos == end.xPos) {
                height = 3;
                width = randomUtils.uniform(RANDOM, 3, WIDTH - end.xPos);
            }
            Position neighboringRoomPos = calHallwayPosition(current, exit, width, height);
            hallWay = new Room(neighboringRoomPos, width, height, exit);
            isEligible = hallWay.isEligibleRoom();
            isOverlap = checkOverlap(hallWay, current);
            times ++;
        }
        return hallWay;
    }

    public static Room generateRandomNeighborRoom(Room current, Position exit) {
        boolean isEligible = false;
        boolean isOverlap = true;
        int times = 0;
        Room room = new Room(new Position(0,0), 0, 0, new Position(0,0));
        while ( (isEligible == false || isOverlap == true) && times <= 10 ) {
            int width = randomUtils.uniform(RANDOM, 4, WIDTH);
            int height = randomUtils.uniform(RANDOM, 4, HEIGHT);
            int xOff = randomUtils.uniform(RANDOM, 1, width - 2);
            int yOff = randomUtils.uniform(RANDOM, 1, height - 2);
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
            room = new Room(new Position(xPos, yPos), width, height, exit);
            isEligible = room.isEligibleRoom();
            isOverlap = checkOverlap(room, current);
            times ++;
        }
        return room;
    }

    public static boolean checkOverlap(Room newRoom, Room oldRoom) {
        boolean isOverlap = true;
        if (rooms.size() == 1) {
            return false;
        }
        for (Room room : rooms) {
            if (room.equals(oldRoom)) {
                continue;
            }
            if ( newRoom.isOverlap(room)) {
                isOverlap = true;
            } else {
                isOverlap =false;
            }
        }
        return isOverlap;
    }

    /** Generate random exit for the given room.
     *
     * @param current the given room.
     * @return exit generated at this room for creation of a new room.
     */
    public static Position generateRandomExit(Room current) {
        int width = current.width;;
        int height = current.height;
        boolean isValidExit = false;
        Position exit = new Position(0,0);
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
        while (isValidExit == false) {
            int i = RandomUtils.uniform(random, numberOfPositions);
            exit = positions.get(i);
            isValidExit = checkValidExit(exit);
        }
        return exit;
    }

    /**
     * Check if a given exit is eligible.
     * @param position the exit to be checked.
     */
    public static boolean checkValidExit(Position position) {
        boolean isValidExit = false;
        if (exits.contains(position)) {
            isValidExit = false;
        }
        int xPos = position.xPos;
        int yPos = position.yPos;
        if ( xPos >= 4 &&  yPos >= 4 && xPos <= WIDTH - 4 && yPos<= HEIGHT - 4) {
            isValidExit = true;
        }
        return isValidExit;
    }

    /**
     * Calculate hallway position for the given room.
     * @param current the current room where the hallway is branching off.
     * @param exit exit connecting the current room and the new hallway.
     * @param width width of the hallway.
     * @param height height of the hallway.
     * @return position of the hallway.
     */
    public static Position calHallwayPosition(Room current, Position exit, int width, int height) {
        Position end = calEndingPosition(current.position, current.width, current.height);
        int hallwayXPos = 0;
        int hallwayYPos = 0;
        if (exit.xPos == current.position.xPos) {
            hallwayXPos = exit.xPos - (width - 1);
            hallwayYPos = exit.yPos - 1;
        } else if (exit.xPos == end.xPos) {
            hallwayXPos = exit.xPos;
            hallwayYPos = exit.yPos - 1;
        } else if (exit.yPos == current.position.yPos) {
            hallwayXPos = exit.xPos - 1;
            hallwayYPos = exit.yPos - (height - 1);
        } else if(exit.yPos == end.yPos) {
            hallwayXPos = exit.xPos - 1;
            hallwayYPos = exit.yPos;
        }
        return new Position(hallwayXPos,hallwayYPos);
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
