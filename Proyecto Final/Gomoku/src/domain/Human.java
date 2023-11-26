package domain;
import java.util.ArrayList;
import java.awt.*;

public class Human extends Player{
    public Human(Color color, Board board) {
        this.color = color;
        this.board = board;
        extraStones = new ArrayList<>();
        remainingStones = new ArrayList<>();
        refillStones(board.getDimension()[0]*board.getDimension()[1], 0);
        punctuation = 0;
    }

    public Human(Color color, Board board, int stoneLimit) {
        this(color, board);
        refillStones(stoneLimit, 0);
    }

    @Override
    public void play(int row, int column, Stone stone) throws GomokuException {
        punctuation += board.addStone(row, column, stone);
    }




}
