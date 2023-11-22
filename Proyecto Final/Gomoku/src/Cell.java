public class Cell {
    protected Board board;
    protected Stone stone;

    public Cell(Board board) {
        this.board = board;
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
