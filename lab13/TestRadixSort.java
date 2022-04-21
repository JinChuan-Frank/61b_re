import java.util.Arrays;

public class TestRadixSort {

    public static void main(String[] args) {
        String[] original1 = {"youth", "boy3", "z", "apple", "boy0", "boy1"};
        String[] original2 = {"a3", "a2", "b", "a0", "a5"};
        String[] sorted = RadixSort.sort(original1);
        System.out.println(Arrays.toString(sorted));
    }
}
