package domain;

import java.awt.Color;

/**
 * La interfaz Entity representa una entidad dentro de un contexto de simulación.
 * Puede ser una entidad de forma redonda, cuadrada, un insecto o una flor.
 */
public interface Entity {
    int ROUND = 1;
    int SQUARE = 2;
    int INSECT = 3;
    int FLOWER = 4;

    /**
     * Realiza una acción relacionada con la entidad.
     */
    void act();

    /**
     * Obtiene la forma de la entidad.
     * @return Valor entero que representa la forma de la entidad (por defecto, cuadrada).
     */
    default int shape() {
        return SQUARE;
    }

    /**
     * Obtiene el color de la entidad.
     * @return Color de la entidad (por defecto, naranja).
     */
    default Color getColor() {
        return Color.orange;
    }

    /**
     * Comprueba si la entidad "es" una entidad. Este método puede ser implementado por subclases.
     * @return `true` si la entidad es considerada como tal, `false` en caso contrario (por defecto, `true`).
     */
    default boolean is() {
        return true;
    }
}
