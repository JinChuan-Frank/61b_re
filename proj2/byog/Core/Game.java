package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Game {
    public static final int WIDTH = 80;
    public static final int HEIGHT = 36;
    int halfWidth = WIDTH / 2;
    int halfHeight = HEIGHT / 2;
    boolean isPlayerTurn;


    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        displayMainMenu();
    }

    private void displayMainMenu() {
        setCanvas();
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.white);
        Font bigFont = new Font("Arial", Font.BOLD, 60);
        Font smallFont = new Font("Arial", Font.BOLD, 40);
        StdDraw.setFont(bigFont);
        StdDraw.text(halfWidth, HEIGHT / 3 * 2, "CS 61B : THE GAME");
        StdDraw.setFont(smallFont);
        StdDraw.text(halfWidth, halfHeight, "New Game (N)");
        StdDraw.text(halfWidth, halfHeight - 5, "Load Game (L)");
        StdDraw.text(halfWidth, halfHeight - 10, "Quit (Q)");
        StdDraw.show();
        solicitUserInput();
    }

    private void setCanvas() {
        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
    }

    private void solicitUserInput() {
        isPlayerTurn = true;
        while (isPlayerTurn) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            Character.toLowerCase(key);
            if (key == 'n') {
                isPlayerTurn = false;
                newGame();
            } else if (key == 'l') {
                loadGame();
            } else if (key == 'q') {
                quitGame();
            }
        }
    }

    private void newGame() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.white);
        Font bigFont = new Font("Arial", Font.BOLD, 60);
        StdDraw.setFont(bigFont);
        StdDraw.text(halfWidth, halfHeight, "Please enter seed, press 'S' to end");
        StdDraw.show();
        long seed = Long.parseLong(readSeed());
        MapGenerator mapGenerator = new MapGenerator(seed);
        TETile[][] world = mapGenerator.generateWorld();
        isPlayerTurn = true;
        showMapAndTrackMovement(mapGenerator, world);
    }

    private void loadGame() {
        displayLoadGame();

        try (FileInputStream fi = new FileInputStream("world.txt")) {
            ObjectInputStream os = new ObjectInputStream(fi);
            MapGenerator mapGenerator = (MapGenerator) os.readObject();
            TETile[][] world = (TETile[][]) os.readObject();
            os.close();

            showMapAndTrackMovement(mapGenerator, world);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void displayLoadGame() {
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.clear(Color.black);
        drawFrame("Loading game ......");
        StdDraw.pause(1000);
    }

    private void quitGame() {
        StdDraw.clear(Color.black);
        drawFrame("Quiting game....");
        StdDraw.pause(1000);
        System.exit(0);
    }


    private void saveGame(MapGenerator mapGenerator, TETile[][] world)  {

        try (FileOutputStream fs = new FileOutputStream("world.txt")) {
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(mapGenerator);
            os.writeObject(world);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displaySaveGame() {
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.clear(Color.black);
        drawFrame("Saving game ......");
        StdDraw.pause(1000);
    }

    private void showMapAndTrackMovement(MapGenerator mapGenerator, TETile[][] world) {
        TERenderer teRenderer = new TERenderer();
        teRenderer.initialize(WIDTH, HEIGHT);
        teRenderer.renderFrame(world);
        boolean isUserCommand = false;

        while (isPlayerTurn) {
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            headUpDisplay(x, y, world);
            if (StdDraw.mouseX() != x || StdDraw.mouseY() != y) {
                teRenderer.renderFrame(world);
                continue;
            }
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char input = StdDraw.nextKeyTyped();

            Character.toLowerCase(input);
            mapGenerator.movePlayer(input, world);
            teRenderer.renderFrame(world);
            if (input == ':') {
                isUserCommand = true;
            }
            if (isUserCommand && input == 'q') {
                displaySaveGame();
                saveGame(mapGenerator, world);
                quitGame();
            }
        }
    }

    private void headUpDisplay(double x, double y, TETile[][] world) {
        try {
            String text = "";
            int xOffSet = (int) x;
            int yOffSet = (int) y;
            TETile teTile = world[xOffSet][yOffSet];

            if (teTile.equals(Tileset.WALL)) {
                text = "Wall";
            } else if (teTile.equals(Tileset.FLOOR)) {
                text = "Floor";
            } else if (teTile.equals(Tileset.PLAYER)) {
                text = "Player";
            }

            StdDraw.setPenColor(Color.WHITE);
            StdDraw.textLeft(1, HEIGHT - 1, text);
            StdDraw.show();

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid");
        }
    }

    private char readInput() {
        isPlayerTurn = true;
        char input = ' ';
        while (isPlayerTurn) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            input = StdDraw.nextKeyTyped();
            isPlayerTurn = false;
        }
        return input;
    }

    private String readSeed() {
        StdDraw.setPenColor(Color.white);
        isPlayerTurn = true;
        String input = "";
        while (isPlayerTurn) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            StdDraw.clear(Color.BLACK);
            char key = StdDraw.nextKeyTyped();
            Character.toLowerCase(key);
            if (key == 's') {
                break;
            }
            input = input + key;
            drawFrame(input);
        }

        isPlayerTurn = false;
        return input;

    }

    private void drawFrame(String s) {
        Font bigFont = new Font("Arial", Font.BOLD, 30);
        StdDraw.setFont(bigFont);
        StdDraw.text(halfWidth, halfHeight, s);
        StdDraw.show();
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        input = input.toLowerCase();
        //System.out.println(input);
        TETile[][] finalWorldFrame = null;
        if (input.startsWith("n")) {
            finalWorldFrame = playWithInputStringNewGame(input);
        } else if (input.startsWith("l")) {
            finalWorldFrame = playWithInputStringLoadGame(input);
        } else if (input.startsWith("q")) {
            System.exit(0);
        } else {
            System.out.println("Please enter valid input");
            System.exit(0);
        }

        return finalWorldFrame;
    }

    private TETile[][] playWithInputStringNewGame(String input) {
        String substring = input.substring(input.indexOf('n') + 1, input.indexOf('s'));
        MapGenerator mapGenerator = new MapGenerator(Long.parseLong(substring));
        TETile[][] worldFrame = mapGenerator.generateWorld();
        if (input.length() == substring.length() + 2) {
            return worldFrame;
        }

        String command = input.substring(input.indexOf('s') + 1);
        char[] commandArr  = command.toCharArray();
        for (char c : commandArr) {
            if (c == ':') {
                break;
            }
            mapGenerator.movePlayer(c, worldFrame);
        }
        if (command.contains(":q")) {
            saveGame(mapGenerator, worldFrame);
        }

        return worldFrame;
    }

    private TETile[][] playWithInputStringLoadGame(String input) {

        TETile[][] worldFrame = null;
        try (FileInputStream fi = new FileInputStream("world.txt")) {
            ObjectInputStream os = new ObjectInputStream(fi);
            MapGenerator mapGenerator = (MapGenerator) os.readObject();
            TETile[][] world = (TETile[][]) os.readObject();
            os.close();
            if (input.equals("l:q")) {
                return world;
            }
            worldFrame = world;
            String command = input.substring(input.indexOf('l') + 1);
            if (command.contains(":")) {
                command = command.substring(0, command.indexOf(':'));
            }
            char[] arr = command.toCharArray();
            for (char c : arr) {
                mapGenerator.movePlayer(c, worldFrame);
            }
            if (command.contains(":Q") || command.contains(":q")) {
                saveGame(mapGenerator, worldFrame);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return worldFrame;
    }

}
