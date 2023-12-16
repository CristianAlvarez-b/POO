package domain;
import java.awt.*;
/**
 * Clase que representa una piedra "Heavy" en el juego Gomoku.
 * Extiende la clase Stone.
 */
public class Heavy extends Stone{
    /**
     * Constructor de la clase Heavy.
     *
     * @param color Color de la piedra.
     */
    public Heavy(Color color) {
        super(color);
        this.value = 2;
    }
}
