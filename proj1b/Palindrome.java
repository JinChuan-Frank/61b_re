public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        char[] wordArray = word.toCharArray();
        ArrayDeque<Character> wordDeque = new ArrayDeque();
        for (char c : wordArray) {
            wordDeque.addLast(c);
        }
        return wordDeque;
    }

    public boolean isPalindrome(String word) {
        Palindrome p = new Palindrome();
        Deque<Character> originalWordArray = p.wordToDeque(word);
        String reversedWord = reverse(originalWordArray);
        System.out.print(reversedWord + " ");
        if (reversedWord.equals(word) || word.length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    private String reverse(Deque<Character> d) {
        String reversedWord = "";
        for (int i = 0; i < d.size(); i += 1) {
            reversedWord += d.removeLast();
        }
        return reversedWord;
    }
}
