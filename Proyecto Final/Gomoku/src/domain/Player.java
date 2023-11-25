package domain;
import java.util.ArrayList;
import java.awt.*;

public abstract class Player {
    protected int remainingStones;
    protected Color color;
    ArrayList<Stone> extraStones;
    protected Board board;
    protected int punctuation;

    public abstract void play(int row, int column, Stone stone) throws GomokuException;

    public void addExtraStone(Stone stone){
        extraStones.add(stone);
    }

    public void eliminateExtraStone(int index){
        extraStones.remove(index);
    }
}