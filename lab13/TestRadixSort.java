import java.util.Arrays;

public class TestRadixSort {

    public static void main(String[] args) {
        String[] original = {"youth", "apple", "boy"};
        String[] sorted = RadixSort.sort(original);
        System.out.println(Arrays.toString(sorted));
    }
}
