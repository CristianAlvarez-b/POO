package domain;
import java.util.ArrayList;
import java.awt.*;

public class Fearful extends Machine{
    public Fearful(Color color, Board board, int specialStonesPercentage, int stoneLimit) {
        this.color = color;
        this.board = board;
        extraStones = new ArrayList<>();
        remainingStones = new ArrayList<>();
        refillStones(stoneLimit, specialStonesPercentage);
        punctuation = 0;
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
