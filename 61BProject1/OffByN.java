public class OffByN implements CharacterComparator{
    int N;
    public OffByN(int N){
        this.N = N;
    }
    @Override
    public boolean equalChars(char x, char y){
        int difference = x - y;
        if(Math.abs(difference) != N){
            return false;
        }
        return true;
    }
}