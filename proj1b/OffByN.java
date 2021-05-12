public class OffByN implements CharacterComparator {
    int x;
    public OffByN(int N) {
        x = N;
    }
    @Override
    public boolean equalChars(char x, char y) {
        if ((x - y) == this.x || (x - y) == -this.x) {
            return true;
        } else {
            return false;
        }
    }
}
