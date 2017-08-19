public class Palindrome{    
    public static Deque<Character> wordToDeque(String word){
        Deque<Character> result = new LinkedListDeque<Character>();
        for(int i = 0; i < word.length(); i++){
            result.addLast(word.charAt(i));
        }
        return result;
    }
    
    public static boolean isPalindrome(String word, CharacterComparator cc){
        if(word.length() == 0 || word.length() == 1) return true;
        Deque<Character> list = wordToDeque(word);
        for(int i = 0; i < list.size() / 2; i++){
            if(!cc.equalChars(list.get(i), list.get(list.size() - 1 - i))){
                return false;
            }
        }
        return true;
    }
}