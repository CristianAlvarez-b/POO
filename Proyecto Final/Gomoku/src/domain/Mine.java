package domain;
import java.util.List;

public class Mine extends Cell{
    public Mine(Board board, int[] position){
        super(board, position);
        active = true;
    }

    public void setStone(Stone stone){
        super.setStone(stone);
    }

    @Override
    public int updateState(boolean turn) {
        super.updateTemporaryStoneLife();
        int punctuationCurrentPlayer = 0;
        int punctuationOtherPlayer = 0;

        if (stone != null && active) {
            active = false;
            punctuationCurrentPlayer -= 50;

            List<int[]> adjacentCells = board.getAdjacentCellPositions(position[0], position[1]);

            for (int[] adjacentPosition : adjacentCells) {
                Cell adjacentCell = board.getCells()[adjacentPosition[0]][adjacentPosition[1]];

                if (adjacentCell.getStone() != null) {
                    if (adjacentCell.getStone().getColor() == stone.getColor()) {
                        // Piedra propia perdida
                        punctuationCurrentPlayer -= 50;
                    } else {
                        // Piedra del contrincante explotada
                        punctuationCurrentPlayer += 100;
                        punctuationOtherPlayer -= 50;
                    }

                    adjacentCell.setStone(null);  // Limpiar la piedra de la celda adyacente
                }
            }

            this.stone = null;  // Limpiar la piedra de la mina
        }

        if (turn) {
            board.getPlayers()[1].addPunctuation(punctuationOtherPlayer);
        } else {
            board.getPlayers()[0].addPunctuation(punctuationOtherPlayer);
        }

        return punctuationCurrentPlayer;
    }

}
