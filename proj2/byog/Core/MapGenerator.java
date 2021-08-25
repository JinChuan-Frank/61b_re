package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapGenerator implements Serializable {

    private static final int WIDTH = 80;
    private static final int HEIGHT = 36;
    private static Room sentinel = new Room(new Position(0,0), 0, 0,new Position(0,0));;
    private long SEED;
    private Random RANDOM;
    private ArrayList<Position> EXITS;
    private ArrayList<Room> ROOMS;
    private Position playerPosition;
    public TETile[][] WORLD;

    MapGenerator(long l) {
        SEED = l;
        RANDOM = new Random(SEED);
        EXITS = new ArrayList<>();
        ROOMS = new ArrayList<>();
    }

    private static class Position implements Serializable {
        int xPos;
        int yPos;

        Position(int x, int y) {
            xPos = x;
            yPos = y;
        }
    }

    private static class Room implements Serializable {
        private Position position;
        private int width;
        private int height;
        private Position exit;

        private Room(Position p, int w, int h, Position e) {
            position = p;
            width = w;
            height = h;
            exit = e;
        }

        private boolean isEligibleRoom() {
            if (this.equals(sentinel)) {
                return false;
            }
            Position end = calEndingPosition(position, width, height);
            return position.xPos >= 0 && position.xPos < WIDTH && position.yPos >= 0
                    && position.yPos < HEIGHT && end.xPos >= 0 && end.xPos < WIDTH - 1
                    && end.yPos >= 0 && end.yPos < HEIGHT - 1;
        }

        private boolean isOverlap(Room oldRoom) {
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

    public void movePlayer(char c, TETile[][] world) {
        Position p1 = playerPosition;
        Position p2 = calPlayerPosition(c, world);
        world[p1.xPos][p1.yPos] = Tileset.FLOOR;
        world[p2.xPos][p2.yPos] = Tileset.PLAYER;
        playerPosition = p2;
    }

    private Position calPlayerPosition(char c, TETile[][] world) {
        Position newPlayerPosition = new Position(0,0);
        if (c == 'w') {
            newPlayerPosition.yPos = playerPosition.yPos + 1;
            newPlayerPosition.xPos = playerPosition.xPos;
        } else if (c == 'a') {
            newPlayerPosition.xPos = playerPosition.xPos - 1;
            newPlayerPosition.yPos = playerPosition.yPos;
        } else if (c == 's') {
            newPlayerPosition.yPos = playerPosition.yPos - 1;
            newPlayerPosition.xPos = playerPosition.xPos;
        } else if (c == 'd') {
            newPlayerPosition.xPos = playerPosition.xPos + 1;
            newPlayerPosition.yPos = playerPosition.yPos;
        } else {
            newPlayerPosition = playerPosition;
        }
        if (world[newPlayerPosition.xPos][newPlayerPosition.yPos].equals(Tileset.WALL)) {
            return playerPosition;
        }
        return newPlayerPosition;
    }

    public TETile[][] generateWorld() {
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        initializeWorld(world);
        generateRooms();
        drawRooms(ROOMS, world);
        setPlayer(ROOMS, world);
        return world;
    }

    private static void initializeWorld(TETile[][] tiles) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

    private void setPlayer(ArrayList<Room> rooms, TETile[][] world) {
        playerPosition = new Position(rooms.get(0).position.xPos + 1, rooms.get(0).position.yPos + 1);
        world[playerPosition.xPos][playerPosition.yPos] = Tileset.PLAYER;
    }

    private static void drawRooms(ArrayList<Room> rooms, TETile[][] world) {
        drawSingleRoom(rooms.get(0), world);
        for (int i = 1; i < rooms.size(); i++) {
            drawRoomAndExit(rooms.get(i), world);
        }
    }

    private static void drawRoomAndExit(Room room, TETile[][] world) {
        drawSingleRoom(room, world);
        drawExit(room.exit, world);
    }

    private void generateRooms() {
        generateStartRoom();
        generateNewRoom(RandomUtils.uniform(RANDOM, 10, 100));
    }

    public void generateStartRoom() {
        boolean isEligibleRoom = false;
        Room room = new Room(new Position(0,0), 0, 0,new Position(0,0));
        while (isEligibleRoom == false) {
            int xPos = RandomUtils.uniform(RANDOM, WIDTH);
            int yPos = RandomUtils.uniform(RANDOM, HEIGHT);
            Position position = new Position(xPos, yPos);
            int width = RandomUtils.uniform(RANDOM, 3,WIDTH / 10);
            int height = RandomUtils.uniform(RANDOM, 3,HEIGHT / 7);
            room = new Room(position, width, height, new Position(position.xPos + 1, position.yPos));
            isEligibleRoom = room.isEligibleRoom();
        }
        ROOMS.add(0, room);
    }


    private void generateNewRoom(int times) {
        System.out.println(times);
        for (int i = 0; i < times; i ++) {
            int k = RANDOM.nextInt(ROOMS.size());
            Room room = ROOMS.get(k);
            Position exit = generateRandomExit(room);
            if (exit == null) {
                return;
            }
            Room newRoom = branchOffThisRoom(room, exit);
            if (newRoom.isEligibleRoom() && !checkOverlap(newRoom, room)){
                ROOMS.add(newRoom);
                EXITS.add(exit);
            }
        }
    }

    private Room branchOffThisRoom(Room room, Position exit) {
        Room temp;
        if (exit.equals(null)) {
            temp = sentinel;
        } else if (room.width > 3 && room.height > 3) {
            temp = generateRandomHallWay(room, exit);
        }  else if ((room.width == 3 && exit.xPos == room.position.xPos + 1)
                || (room.height == 3 && exit.yPos == room.position.yPos + 1)) {
            temp = generateRandomNeighborRoom(room, exit);
        } else {
            temp = sentinel;
        }
        return temp;
    }

    private Room generateRandomHallWay(Room current, Position exit) {
        int width = 0;
        int height = 0;
        boolean isEligible = false;
        boolean isOverlap = true;
        int times = 0;
        Position start = current.position;
        Position end = calEndingPosition(start, current.width, current.height);
        Room hallWay = new Room(new Position(0,0), 0, 0, new Position(0,0));
        while ( (isEligible == false || isOverlap == true) && times <= 5 ) {
            if (exit.yPos == start.yPos) {
                width = 3;
                height = RandomUtils.uniform(RANDOM, 3, start.yPos);
            } else if (exit.yPos == end.yPos) {
                width = 3;
                height = RandomUtils.uniform(RANDOM, 3, HEIGHT - end.yPos);
            } else if (exit.xPos == start.xPos) {
                height = 3;
                width = RandomUtils.uniform(RANDOM, 3, start.xPos);
            } else if (exit.xPos == end.xPos) {
                height = 3;
                width = RandomUtils.uniform(RANDOM, 3, WIDTH - end.xPos);
            }
            Position neighboringRoomPos = calHallwayPosition(current, exit, width, height);
            hallWay = new Room(neighboringRoomPos, width, height, exit);
            isEligible = hallWay.isEligibleRoom();
            isOverlap = checkOverlap(hallWay, current);
            times ++;
        }
        return hallWay;
    }

    private Room generateRandomNeighborRoom(Room current, Position exit) {
        boolean isEligible = false;
        boolean isOverlap = true;
        int times = 0;
        Room room = new Room(new Position(0,0), 0, 0, new Position(0,0));
        while ( (isEligible == false || isOverlap == true) && times <= 10 ) {
            int width = RandomUtils.uniform(RANDOM, 4, WIDTH / 10);
            int height = RandomUtils.uniform(RANDOM, 4, HEIGHT / 7);
            int xOff = RandomUtils.uniform(RANDOM, 1, width - 2);
            int yOff = RandomUtils.uniform(RANDOM, 1, height - 2);
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

    private boolean checkOverlap(Room newRoom, Room oldRoom) {
        boolean isOverlap = false;
        if (ROOMS.size() == 1) {
            return false;
        }
        for (Room room : ROOMS) {
            if (room.equals(oldRoom)) {
                continue;
            }
            if ( newRoom.isOverlap(room)) {
                isOverlap = true;
            }
        }
        return isOverlap;
    }

    /** Generate random exit for the given room.
     *
     * @param current the given room.
     * @return exit generated at this room for creation of a new room.
     * tryingTimes is used to avoid infinite loop.
     */
    private Position generateRandomExit(Room current) {
        int tryingTimes = 0;
        boolean isValidExit = false;
        Position exit = null;
        Position start = current.position;
        Position end = calEndingPosition(start, current.width, current.height);
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
        while (isValidExit == false && tryingTimes <= 2 * positions.size()) {
            int i = RANDOM.nextInt(positions.size());
            exit = positions.get(i);
            isValidExit = checkValidExit(exit);
            tryingTimes += 1;
        }
        if (tryingTimes == 2* positions.size()) {
            return null;
        }
        return exit;
    }

    /**
     * Check if a given exit is eligible.
     * @param position the exit to be checked.
     */
    private boolean checkValidExit(Position position) {
        boolean isValidExit = false;
        for (Position exit : EXITS) {
            if (exit.equals(position)) {
                isValidExit = false;
            }
        }
        int xPos = position.xPos;
        int yPos = position.yPos;
        if (xPos >= 2 &&  yPos >= 2 && xPos <= WIDTH - 2 && yPos <= HEIGHT - 2) {
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
    private static Position calHallwayPosition(Room current, Position exit, int width, int height) {
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
        } else if (exit.yPos == end.yPos) {
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
