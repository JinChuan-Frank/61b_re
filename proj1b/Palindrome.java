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
        String reversedWord = "";
        for (int i = 0; i < word.length(); i += 1) {
            reversedWord += originalWordArray.removeLast();
        }
        System.out.println(" reversed :" + reversedWord);
        if (reversedWord.equals(word) || word.length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isPalindrome() {
        return true;
    }

}
