import org.junit.Test;

public class OffByOne implements CharacterComparator{
    @Override
    public boolean equalChars(char x, char y){
        if (x == y) {
            return true;
        } else {
            return false;
        }
    }

}
