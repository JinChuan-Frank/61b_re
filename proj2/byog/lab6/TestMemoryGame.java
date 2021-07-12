package byog.lab6;

public class TestMemoryGame {
    public static void testGenerateRandomString() {
        MemoryGame memoryGame = new MemoryGame(50, 50, 909867);
        System.out.println(memoryGame.generateRandomString(5));
    }

    private static void testDrawFrame() {
        MemoryGame memoryGame = new MemoryGame(50, 30, 909867);
        memoryGame.drawFrame("sdjfhg");
    }
    public static void main(String[] args) {
        testDrawFrame();
    }
}
