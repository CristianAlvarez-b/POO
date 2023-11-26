package domain;
import java.util.ArrayList;
import java.awt.*;

public class Fearful extends Machine{
    public Fearful(Color color, Board board) {
        this.color = color;
        this.board = board;
        extraStones = new ArrayList<>();
       // remainingStones = -1;
        punctuation = 0;
    }

    public Fearful(Color color, Board board, int stoneLimit) {
        this(color, board);
        //remainingStones = stoneLimit;
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
