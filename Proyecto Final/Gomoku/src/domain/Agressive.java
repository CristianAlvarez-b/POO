package domain;
import java.util.ArrayList;
import java.awt.*;

public class Agressive extends Machine{
    public Agressive(Color color, Board board) {
        this.color = color;
        this.board = board;
        extraStones = new ArrayList<>();
        remainingStones = new ArrayList<>();
        refillStones(board.getDimension()[0]*board.getDimension()[1], 0);
        punctuation = 0;
    }

    public Agressive(Color color, Board board, int stoneLimit) {
        this(color, board);
        refillStones(stoneLimit, 0);
    }

    @Override
    public void play() throws Exception {
        //Calcula la posicion y de forma random decide que piedra
        play(0,0,new Stone(color));
    }

    @Override
    public void play(int row, int column, Stone stone) throws Exception {
        punctuation += board.addStone(row, column, stone);
    }
}
