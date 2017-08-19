public class TestIn{
    public static void main(String[] args){
        In in = new In("./data/planets.txt");
        System.out.println(in.readString());
    }
}