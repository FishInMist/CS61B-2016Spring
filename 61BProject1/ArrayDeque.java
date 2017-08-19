public class ArrayDeque<Item> implements Deque<Item>{
    private Item[] items;
    private int size;
    private int sizefactor = 4;
    private int nextFirst;
    private int nextLast;
    //constructor
    public ArrayDeque(){
        items = (Item[]) new Object[8];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }
    
    private void resize(int capacity){
        Item[] a = (Item[]) new Object[capacity];
        int runner = nextFirst + 1;
        for(int i = 0; i < size; i++){
            if(runner == items.length)runner = 0;
            a[i] = items[runner];
            runner++;
        }
        items = a;
        nextFirst = items.length - 1;
        nextLast = size;
    }
    @Override
    public void addFirst(Item item){
        if(size == items.length){
            resize(size * sizefactor);
        }
        items[nextFirst] = item;
        if(nextFirst == 0) nextFirst = items.length -1;
        else nextFirst--;
        size++;
    }
    @Override
    public void addLast(Item item){
        if(size == items.length){
            resize(size * sizefactor);
        }
        items[nextLast] = item;
        if(nextLast == items.length - 1) nextLast = 0;
        else nextLast++;
        size++;
    }
    @Override
    public boolean isEmpty(){
        return size == 0;
    }
    @Override
    public int size(){
        return size;
    }
    @Override
    public void printDeque(){
        System.out.println("Size:" + size + " Length: " + items.length + " Ratio: " + ((float)size/items.length)*100 + "%");
        int runner = nextFirst + 1;
        for(int i = 0; i < size; i++){
            if(runner == items.length)runner = 0;
            System.out.print(items[runner] + " ");
            runner++;
        }
        System.out.println();
    }
    @Override
    public Item removeFirst(){
        if(size == items.length / sizefactor){
            resize(items.length / sizefactor);
        }
        int position = nextFirst + 1;
        if(position == items.length) position = 0;
        Item result = items[position];
        items[position] = null;
        nextFirst = position;
        size--;
        return result;
    }
    @Override
    public Item removeLast(){
        if(size == items.length / sizefactor){
            resize(items.length / sizefactor);
        }
        int position = nextLast - 1;
        if(position == -1) position = items.length - 1;
        Item result = items[position];
        items[position] = null;
        nextLast = position;
        size--;
        return result;
    }
    @Override
    public Item get(int index){
        if(index > size) return null;
        int position = index + nextFirst + 1;
        if(position >= items.length) position = position - items.length;
        return items[position];
    }
}