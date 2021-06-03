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
        //System.out.println(" reversed :" + reversedWord);
        return reversedWord.equals(word) || word.length() == 0;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Palindrome p = new Palindrome();
        Deque<Character> d = p.wordToDeque(word);
        int length = word.length();
        if (length % 2 == 0) {
            for (int i = 0; i < length / 2; i += 1) {
                if (cc.equalChars(d.get(i), d.get(length - 1 - i))) {
                    continue;
                } else {
                    return false;
                }
            }
        } else  {
            for (int i = 0; i < (length - 1) / 2; i += 1) {
                if (cc.equalChars(d.get(i), d.get(length - 1 - i))) {
                    continue;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

}
