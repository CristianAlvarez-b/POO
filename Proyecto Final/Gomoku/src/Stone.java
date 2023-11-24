public class Stone {
    protected char color;
    protected int value;
    public Stone(char color){
        this.color = color;
        this.value = 1;
    }

    public char getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }
}
