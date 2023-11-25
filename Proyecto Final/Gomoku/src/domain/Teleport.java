package domain;
import java.util.*;

public class Teleport extends Cell{
    private boolean active;
    public Teleport(Board board, int[] position){
        super(board, position);
        active = true;
    }

    public void setStone(Stone stone){
        super.setStone(stone);
        active = false;
    }
    public boolean isActive() {
        return active;
    }
    @Override
    public int updateState(){
        if(stone != null) {
            Stone stone = this.stone;
            this.stone = null;
            int i, j;
            Random random = new Random();
            // Buscar una posición aleatoria que no esté ocupada por una celda especial
            do {
                i = random.nextInt(board.getDimension()[0]);
                j = random.nextInt(board.getDimension()[1]);
            } while (board.getCells()[i][j].getStone() != null && board.getCells()[i][j] != this);

            board.getCells()[i][j].setStone(stone);
        }
        return 0;
    }
}
