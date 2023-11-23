public class Cell {
    protected Board board;
    protected Stone stone;
    protected int[] position;

    public Cell(Board board, int[] position) {
        this.board = board;
        this.position = position;
    }

    public void setStone(Stone stone) {
        this.stone = stone;
    }

    public Stone getStone() {
        return stone;
    }

    public int updateState(){
        return 0;
    }
}
