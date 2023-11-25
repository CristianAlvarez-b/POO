package domain;

import java.awt.Color;

/**
 * La clase Flower representa una flor en la colonia. Las flores pueden estar abiertas o cerradas y cambian de color.
 */
public class Flower implements Entity {
    protected Color color;
    protected boolean isOpen;
    protected Colony colony;
    protected int row, column;
    protected String name;

    /**
     * Constructor para objetos de la clase Flower.
     * Crea una nueva flor en la fila y columna especificadas en la colonia dada.
     * @param name Nombre de la flor.
     * @param colony La colonia a la que pertenece la flor.
     * @param row Fila de ubicación de la flor.
     * @param column Columna de ubicación de la flor.
     */
    public Flower(String name, Colony colony, int row, int column) {
        this.name = name;
        isOpen = true;
        this.colony = colony;
        this.row = row;
        this.column = column;
        colony.setEntity(row, column, (Entity) this);
        this.color = Color.red;
    }

    /**
     * Realiza una acción relacionada con la flor, alternando entre los colores verde y rojo para simular la apertura y cierre de la flor.
     */
    public void act() {
        if (isOpen) {
            color = Color.green;
            isOpen = false;
        } else {
            color = Color.red;
            isOpen = true;
        }
    }

    /**
     * Obtiene la forma de la flor.
     * @return Valor entero que representa la forma de la flor (FLORES).
     */
    public final int shape() {
        return Entity.FLOWER;
    }

    /**
     * Comprueba si la flor "es" una flor abierta (isOpen).
     * @return `true` si la flor está abierta, `false` si está cerrada.
     */
    @Override
    public boolean is() {
        return isOpen;
    }

    /**
     * Obtiene el color de la flor.
     * @return Color de la flor (puede ser verde o rojo).
     */
    public final Color getColor() {
        return color;
    }
}
