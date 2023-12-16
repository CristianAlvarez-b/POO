package domain;
import java.awt.*;
/**
 * Clase que representa una piedra temporal en el juego Gomoku.
 */
public class Temporary extends Stone{
    private int life;
    /**
     * Constructor de la clase Temporary.
     *
     * @param color Color de la piedra temporal.
     */
    public Temporary(Color color) {
        super(color);
        this.life = -2; //Porque apenas se pone se actualiza una vez su estado
        // desde las celdas con updateLife y luego se vuelve a actualizar actualizando todoo el tablero
    }
    /**
     * Actualiza el estado de vida de la piedra temporal.
     *
     * @throws GomokuException Si la vida alcanza el límite máximo permitido.
     */
    public void updateLife() throws GomokuException {
        life++;
        if (life>=6){
            throw new GomokuException(GomokuException.MAXIMUS_LIFE);
        }
    }
}
