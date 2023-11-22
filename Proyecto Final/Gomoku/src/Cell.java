public class Cell {
    protected Board board;
    private Stone stone;

    public Cell(Board board) {
        this.board = board;
    }

    public void setStone(Stone stone) {
        this.stone = stone;
    }

    public Stone getStone() {
        return stone;
    }
}
