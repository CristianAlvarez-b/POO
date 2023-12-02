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
    }

//    @Override
//    public int updateState(boolean turn){
//        super.updateTemporaryStoneLife();
//        int punctuationCurrentPlayer = 0;
//        int punctuationOtherPlayer = 0;
//        if(stone != null && active){
//            active = false;
//            punctuationCurrentPlayer -= 50;
//            List<int[]> adjacentCells = board.getAdjacentCellPositions(position[0], position[1]);
//            for (int[] position : adjacentCells) {
//                Cell currentCell = board.getCells()[position[0]][position[1]];
//                if(currentCell.getStone().getColor() == stone.getColor()){
//                    punctuationCurrentPlayer -= 50;
//                }else {
//                    punctuationCurrentPlayer += 100;
//                    punctuationOtherPlayer -= 50;
//                }
//                currentCell.setStone(null);
//            }
//        }
//        if (turn){
//            board.getPlayers()[1].addPunctuation(punctuationOtherPlayer);
//        }else {
//            board.getPlayers()[0].addPunctuation(punctuationOtherPlayer);
//        }
//        return punctuationCurrentPlayer;
//    }
}
