public class OffByN implements CharacterComparator {
    private int x;
    public OffByN(int N) {
        x = N;
    }
    @Override
    public boolean equalChars(char a, char b) {
        return (a - b) == x || (a - b) == -x;
    }
}
