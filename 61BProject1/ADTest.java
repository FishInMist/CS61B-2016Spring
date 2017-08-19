public class ADTest{
    public static void main(String[] args){
        ArrayDeque<Integer> test = new ArrayDeque<Integer>();
        test.addFirst(1);
        test.addFirst(3);
        test.addLast(5);
        test.addLast(7);
        test.addFirst(9);
        test.printDeque();
        //System.out.println(test.size());
        //System.out.println(test.removeLast());
        //System.out.println(test.removeLast());
        //System.out.println(test.removeLast());
        //System.out.println(test.removeFirst());
        test.addFirst(2);
        test.addFirst(4);
        test.addLast(6);
        test.addLast(8);
        test.addFirst(10);
        test.printDeque();
        System.out.println("get: " + test.get(11));
        System.out.println(test.removeLast());
        System.out.println(test.removeLast());
        System.out.println(test.removeLast());
        System.out.println(test.removeFirst());
        test.printDeque();
    }
}