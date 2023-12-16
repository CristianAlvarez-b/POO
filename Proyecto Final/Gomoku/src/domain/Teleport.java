package domain;
import java.util.*;
/**
 * Clase que representa una celda de teletransporte en el juego Gomoku.
 */
public class Teleport extends Cell{
    /**
     * Constructor de la clase Teleport.
     *
     * @param board    Tablero al que pertenece la celda.
     * @param position Posición de la celda en el tablero.
     */
    public Teleport(Board board, int[] position){
        super(board, position);
        active = true;
    }
    /**
     * Establece una piedra en la celda de teletransporte.
     *
     * @param stone Piedra a establecer en la celda.
     */
    public void setStone(Stone stone){
        super.setStone(stone);
    }
    /**
     * Actualiza el estado de la celda de teletransporte.
     *
     * @param turn Indica el turno actual del juego.
     * @return Puntuación asociada a la actualización del estado.
     */
    @Override
    public int updateState(boolean turn){
        super.updateTemporaryStoneLife();
        if(stone != null && active) {
            active = false;
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
