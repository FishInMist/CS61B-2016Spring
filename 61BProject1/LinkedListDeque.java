public class LinkedListDeque<Item> implements Deque<Item>{
    
    public class Node{
        public Item item;
        public Node prev;
        public Node next;
        //constructor inside the class defination
        public Node(Item i, Node p, Node n){
            item = i;
            prev = p;
            next = n;
        }
    }
    
    private Node sentinel;
    private int size = 0;
    
    public LinkedListDeque(){
        Item i = (Item) new Object();//use Object() is required here!!!
        sentinel = new Node(i, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }
    @Override
    public void addFirst(Item item){
        sentinel.next = new Node(item, sentinel, sentinel.next);
        sentinel.next.next.prev = sentinel.next; //remeber to change the point position here!!!
        if(sentinel.prev == sentinel){
            sentinel.prev = sentinel.next;
        }
        size++;
    }
    @Override
    public void addLast(Item item){
        sentinel.prev.next = new Node(item, sentinel.prev, sentinel);
        sentinel.prev = sentinel.prev.next;
        if(sentinel.next == sentinel){
            sentinel.next = sentinel.prev;
        }
        size++;
    }
    @Override
    public boolean isEmpty(){
        return sentinel.next == sentinel;
    }
    @Override
    public int size(){
        return size;
    }
    @Override
    public void printDeque(){
        Node runner = sentinel.next;
        while(runner != sentinel){
            System.out.print(runner.item + " ");
            runner = runner.next;
        }
        System.out.println();
    }
    @Override
    public Item removeFirst(){
        if(sentinel.next == sentinel){return null;} // emplty list
        else{
            Item result = sentinel.next.item;
            sentinel.next.next.prev = sentinel;
            sentinel.next = sentinel.next.next;
            size--;
            return result;
        }
    }
    @Override
    public Item removeLast(){
        if(sentinel.prev == sentinel) {return null;}
        else{
            Item result = sentinel.prev.item;
            sentinel.prev.prev.next = sentinel;
            sentinel.prev = sentinel.prev.prev;
            size--;
            return result;
        }
    }
    @Override
    public Item get(int index){
        if(index > size) return null;
        Node runner = sentinel;
        for(int i = 0; i <= index; i++){
            runner = runner.next;
        }
        return runner.item;
    }
    
    public Item getRecursive(int index){
        if(index > size) return null;
        return getRecursive(sentinel.next, index);
    }
    private Item getRecursive(Node s, int index){
        if(index == 0) return s.item;
        else{
            return getRecursive(s.next, index - 1);
        }
    }
}