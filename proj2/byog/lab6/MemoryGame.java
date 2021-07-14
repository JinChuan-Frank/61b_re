package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }
        long seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, long seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        StringBuilder stringBuilder = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            Character character = CHARACTERS[rand.nextInt(CHARACTERS.length)];
            stringBuilder.append(character);
        }
        return stringBuilder.toString();
    }

    /**
     * Take the string and display it in the center of the screen
     * @param s the string to be displayed
     */
    public void drawFrame(String s) {
        int halfWidth = width / 2;
        int halfHeight = height / 2;
        Font font = new Font("Arial", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(halfWidth, halfHeight, s);
        StdDraw.show();

        //TODO: If game is not over, display relevant game information at the top of the screen
    }

    /**
     * Display each character in letters, making sure to blank the screen between letters
     * @param letters the string to be displayed in its single letters one by one.
     */
    public void flashSequence(String letters) {
        for (int i = 0; i < letters.length(); i ++ ) {
            StdDraw.pause(500);
            char c = letters.charAt(i);
            drawFrame(Character.toString(c));
            StdDraw.pause(1000);
        }
        StdDraw.clear(Color.black);
        StdDraw.show();
    }

    /**
     * Read n letters of player input
     * @param n length of input allowed, equals the length of string displayed.
     * @return
     */
    public String solicitNCharsInput(int n) {
        String input = "";
        drawFrame(input);
        while (input.length() < n) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            input = input + key;
            drawFrame(input);
            StdDraw.pause(500);

        }
        return input;
    }

    public void startGame() {
        this.round = 1;
        this.gameOver = false;
        while (!gameOver) {
            drawFrame("Round:" + " " + round);
            generateRandomString(round);

            round += 1;
        }
        //TODO: Set any relevant variables before the game starts

        //TODO: Establish Game loop
    }

}
