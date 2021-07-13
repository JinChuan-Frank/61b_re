package byog.lab6;

public class TestMemoryGame {
    MemoryGame memoryGame = new MemoryGame(50, 50, 909867);
    public void testGenerateRandomString() {

        System.out.println(memoryGame.generateRandomString(5));
    }

    private void testDrawFrame() {
        memoryGame.drawFrame("sdjfhg");
    }

    public void testFlashSequence() {
        memoryGame.flashSequence("lkjhg");
    }

    public void testSolicitNCharString() {

    }

    public static void main(String[] args) {
        TestMemoryGame testMemoryGame = new TestMemoryGame();
        testMemoryGame.testFlashSequence();
    }
}
