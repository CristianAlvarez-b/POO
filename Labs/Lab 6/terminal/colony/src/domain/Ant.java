package domain;

import java.awt.Color;
import java.util.Random;

/**
 * La clase Ant representa una hormiga en la colonia.
 * Las hormigas se mueven, cambian de dirección y pueden morir después de cierta edad.
 */
public class Ant extends Agent implements Entity {
    protected char nextState;
    protected Color color;
    protected Colony colony;
    protected int row, column;
    protected String name;

    /**
     * Crea una nueva hormiga en la fila y columna especificadas en la colonia dada.
     * Cada nueva hormiga se inicia como viva en el estado inicial.
     * @param name Nombre de la hormiga.
     * @param colony La colonia a la que pertenece la hormiga.
     * @param row Fila de ubicación de la hormiga.
     * @param column Columna de ubicación de la hormiga.
     */
    public Ant(String name, Colony colony, int row, int column) {
        this.name = name;
        this.colony = colony;
        this.row = row;
        this.column = column;
        nextState = Agent.ALIVE;
        state = nextState;
        colony.setEntity(row, column, (Entity) this);
        color = Color.black;
    }

    /**
     * Devuelve la forma de la hormiga.
     * @return Entero que representa la forma de la hormiga.
     */
    public final int shape() {
        return Entity.INSECT;
    }

    /**
     * Obtiene la fila en la que se encuentra la hormiga.
     * @return Valor entero que representa la fila.
     */
    public final int getRow() {
        return row;
    }

    /**
     * Obtiene la columna en la que se encuentra la hormiga.
     * @return Valor entero que representa la columna.
     */
    public final int getColumn() {
        return column;
    }

    /**
     * Obtiene el nombre de la hormiga.
     * @return El nombre de la hormiga.
     */
    public final String getName() {
        return name;
    }

    /**
     * Obtiene el color de la hormiga.
     * @return El color de la hormiga.
     */
    public final Color getColor() {
        return color;
    }

    /**
     * Realiza las acciones de la hormiga en un paso de tiempo.
     * Si la hormiga alcanza una edad de 49, muere; de lo contrario, se mueve y cambia de dirección.
     */
    public void act() {
        if (getAge() == 49) {
            state = Agent.DEAD;
            colony.setEntity(row, column, null);
        } else {
            move();
            turn();
        }
    }

    /**
     * Mueve la hormiga en una dirección aleatoria si la nueva ubicación está dentro de los límites de la colonia.
     */
    public void move() {
        Random random = new Random();
        int direction = random.nextInt(4); // 0: arriba, 1: abajo, 2: izquierda, 3: derecha

        int newRow = this.row;
        int newColumn = this.column;

        switch (direction) {
            case 0:
                newRow--;
                break;
            case 1:
                newRow++;
                break;
            case 2:
                newColumn--;
                break;
            case 3:
                newColumn++;
                break;
        }

        if (30 > newRow && newRow > 0 && newColumn > 0 && newColumn < 30) {
            // Mover la hormiga a la nueva ubicación
            Entity targetEntity = colony.getEntity(newRow, newColumn);
            if (targetEntity == null) {
                colony.setEntity(row, column, null);
                this.row = newRow;
                this.column = newColumn;
                colony.setEntity(row, column, (Entity) this);
            }
        }
    }
}
