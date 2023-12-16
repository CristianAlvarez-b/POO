package domain;

import java.io.Serializable;

/**
 * Clase que representa una celda en el tablero del juego Gomoku.
 * Implementa la interfaz Serializable para permitir la serialización.
 */
public class Cell implements Serializable {
    protected Board board;
    protected Stone stone;
    protected boolean active;
    protected int[] position;

    /**
     * Constructor de la clase Cell.
     *
     * @param board    El tablero al que pertenece la celda.
     * @param position La posición de la celda en el tablero.
     */
    public Cell(Board board, int[] position) {
        this.board = board;
        this.position = position;
    }
    /**
     * Establece una piedra en la celda.
     *
     * @param stone La piedra que se establecerá en la celda.
     */
    public void setStone(Stone stone) {
        this.stone = stone;
    }

    /**
     * Obtiene la piedra actualmente en la celda.
     *
     * @return La piedra en la celda.
     */
    public Stone getStone() {
        return stone;
    }

    /**
     * Actualiza el estado de la celda en función del turno del juego.
     *
     * @param turn Indica el turno actual del juego.
     * @return El puntaje asociado a la actualización del estado de la celda (si lo hay).
     * @throws Exception Si ocurre una excepción relacionada con el juego Gomoku.
     */
    public int updateState(boolean turn) throws Exception {
        updateTemporaryStoneLife();
        return 0;
    }

    /**
     * Actualiza la vida de una piedra temporal en la celda, eliminándola si ha caducado.
     */
    public void updateTemporaryStoneLife() {
        if (stone != null && stone instanceof Temporary) {
            try {
                ((Temporary) stone).updateLife();
            } catch (GomokuException e) {
                stone = null;
            }
        }
    }

    /**
     * Verifica si la celda está activa.
     *
     * @return true si la celda está activa, false si no lo está.
     */
    public boolean isActive() {
        return active;
    }
}
