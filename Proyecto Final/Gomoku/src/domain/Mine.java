package domain;
import java.util.List;
/**
 * Clase que representa una celda especial llamada "Mina" en el juego Gomoku.
 */
public class Mine extends Cell{
    /**
     * Constructor de la clase Mine.
     *
     * @param board    Tablero al que pertenece la mina.
     * @param position Posición de la mina en el tablero.
     */
    public Mine(Board board, int[] position){
        super(board, position);
        active = true;
    }
    /**
     * Establece la piedra en la mina.
     *
     * @param stone Piedra que se establecerá en la mina.
     */
    public void setStone(Stone stone){
        super.setStone(stone);
    }
    /**
     * Actualiza el estado de la mina después de cada turno.
     *
     * @param turn Turno actual del jugador.
     * @return Puntuación del jugador actual tras la actualización.
     */
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
