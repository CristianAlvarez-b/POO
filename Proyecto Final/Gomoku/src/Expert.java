import java.util.ArrayList;

public class Expert extends Machine{
    public Expert(char color, Board board) {
        this.color = color;
        this.board = board;
        extraStones = new ArrayList<>();
        remainingStones = -1;
        punctuation = 0;
    }

    public Expert(char color, Board board, int stoneLimit) {
        this(color, board);
        remainingStones = stoneLimit;
    }
    @Override
    public void play() throws GomokuException {
        //Calcula la posicion y de forma random decide que piedra
        play(0,0,new Stone(color));
    }

    @Override
    public void play(int row, int column, Stone stone) throws GomokuException {
        punctuation += board.addStone(row, column, stone);
    }
}
