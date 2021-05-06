public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        char[] wordArray = word.toCharArray();
        ArrayDeque wordDeque = new ArrayDeque();
        for (char c : wordArray) {
            wordDeque.addLast(c);
        }
        return wordDeque;
    }
}
