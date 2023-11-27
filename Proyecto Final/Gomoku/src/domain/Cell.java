package domain;
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

    public int updateState(boolean turn) throws Exception {
        updateTemporaryStoneLife();
        return 0;
    }
    public void updateTemporaryStoneLife() {
        if (stone != null && stone instanceof Temporary) {
            try {
                ((Temporary) stone).updateLife();
            } catch (GomokuException e) {
                stone = null;
            }
        }
    }
}
