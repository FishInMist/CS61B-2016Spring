public class LLDTest{
    public static void main(String[] args){
        LinkedListDeque<Integer> test = new LinkedListDeque<Integer>();
        //System.out.println(test.isEmpty());
        //System.out.println(test.size());
        test.addFirst(1);
        test.addFirst(3);
        test.addLast(5);
        test.addLast(7);
        test.addFirst(9);
        test.printDeque();
        //System.out.println(test.isEmpty());
        //System.out.println(test.size());
        System.out.println(test.get(0));
        System.out.println(test.get(3));
        System.out.println(test.getRecursive(3));
    }
}