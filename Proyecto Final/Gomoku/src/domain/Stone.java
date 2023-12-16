package domain;
import java.awt.*;
import java.io.Serializable;
/**
 * Clase que representa una piedra en el juego Gomoku.
 */
public class Stone implements Serializable{
    protected Color color;
    protected int value;
    /**
     * Constructor de la clase Stone.
     *
     * @param color Color de la piedra.
     */
    public Stone(Color color){
        this.color = color;
        this.value = 1;
    }

    /**
     * Obtiene el color de la piedra.
     *
     * @return Color de la piedra.
     */
    public Color getColor() {
        return color;
    }
    /**
     * Obtiene el valor de la piedra.
     *
     * @return Valor de la piedra.
     */
    public int getValue() {
        return value;
    }
}
