public class TestPalindrome{
    public static void main(String[] args){
        String word = "flake";
        Palindrome test = new Palindrome();
        Deque<Character> result = test.wordToDeque(word);
        result.printDeque();
        System.out.println(test.isPalindrome(word, new OffByOne()));
        System.out.println(test.isPalindrome(word, new OffByN(5)));
    }
}