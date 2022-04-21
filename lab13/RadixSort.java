
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;


/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {

        int length = asciis.length;
        String[] sorted = new String[length];
        String[] aux = new String[length];
        System.arraycopy(asciis, 0, aux, 0, length);
        int longest = 0;

        for (String s : asciis) {
            int l = s.length();
            if (l > longest) {
                longest = l;
            }
        }

        for (int i = 0; i < longest; i++) {
            System.out.println("sorting digit " + i);
            sortHelperLSD(aux, i, longest);
            System.out.println(Arrays.toString(aux));
        }

        System.arraycopy(aux, 0, sorted, 0, length);

        return sorted;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on. Starting from the smallest digit.
     */
    private static void sortHelperLSD(String[] asciis, int index, int longest) {
        // Optional LSD helper method for required LSD radix sort
        Deque<String>[] temp = new Deque[256];

        for (int i = 0; i < 256; i++) {
            temp[i] = new LinkedList<>();
        }

        for (String s : asciis) {
            int order = getOrder(s, index, longest);
            temp[order].add(s);
        }

        int j = 0;
        while (j < asciis.length) {
            for (int i = 0; i < 256; i++) {
                while (!temp[i].isEmpty()) {
                    asciis[j] = temp[i].removeFirst();
                    j++;
                }
            }
        }
        return;
    }

    private static int getOrder(String s, int index, int longest) {
        int unusefulLength = longest - s.length();
        if (index < unusefulLength) {
            return 0;
        }
        int forwardIndex = convertToForwardIndex(s, index, unusefulLength);

        Character c = s.charAt(forwardIndex);
        int order = (int) c;
        return order;
    }

    private static int convertToForwardIndex(String s, int backWardIndex, int unusefulLength) {
        int length = s.length();
        int forwardIndex = length - 1 - (backWardIndex - unusefulLength);
        return forwardIndex;
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }
}
