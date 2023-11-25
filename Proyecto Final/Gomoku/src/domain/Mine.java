package domain;
import java.util.List;

public class Mine extends Cell{
    private boolean active;
    public Mine(Board board, int[] position){
        super(board, position);
        active = true;
    }

    public void setStone(Stone stone){
        super.setStone(stone);
        active = false;
    }

    @Override
    public int updateState(){
        int punctuation = 0;
        if(stone != null){
            punctuation -= 50;
            List<int[]> adjacentCells = board.getAdjacentCellPositions(position[0], position[1]);
            for (int[] position : adjacentCells) {
                Cell currentCell = board.getCells()[position[0]][position[1]];
                if(currentCell.getStone().getColor() == stone.getColor()){
                    punctuation -= 50;
                }else {
                    punctuation += 100;
                }
                currentCell.setStone(null);
            }
        }
        return punctuation;
    }

    public boolean isActive() {
        return active;
    }
}
