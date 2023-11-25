package domain;

import java.awt.Color;

/**
 * La clase Bee representa una abeja en la colonia. Las abejas buscan flores en la colonia y pueden morir después de cierta edad.
 */
public class Bee extends Agent implements Entity {
    protected char nextState;
    protected Color color;
    protected Colony colony;
    protected int row, column;
    protected String name;

    /**
     * Constructor para objetos de la clase Bee.
     * Crea una nueva abeja en la fila y columna especificadas en la colonia dada.
     * @param name Nombre de la abeja.
     * @param colony La colonia a la que pertenece la abeja.
     * @param row Fila de ubicación de la abeja.
     * @param column Columna de ubicación de la abeja.
     */
    public Bee(String name, Colony colony, int row, int column) {
        this.name = name;
        this.colony = colony;
        this.row = row;
        this.column = column;
        nextState = Agent.ALIVE;
        state = nextState;
        colony.setEntity(row, column, (Entity) this);
        color = Color.yellow;
    }

    /**
     * Obtiene la forma de la abeja.
     * @return Valor entero que representa la forma de la abeja (INSECT).
     */
    public final int shape() {
        return Entity.INSECT;
    }

    /**
     * Realiza las acciones de la abeja en un paso de tiempo. La abeja muere después de cierta edad.
     */
    public void act() {
        if (getAge() == 101) {
            state = Agent.DEAD;
            colony.setEntity(row, column, null);
        } else {
            move();
            turn();
        }
    }

    /**
     * La abeja se mueve hacia la flor más cercana en la colonia.
     */
    public void move() {
        int currentRow = this.row;
        int currentColumn = this.column;
        int[] nearestFlower = colony.findNearestEntityPosition(currentRow, currentColumn, Flower.class);
        
        int targetRow = nearestFlower[0];
        int targetColumn = nearestFlower[1];
        
        // Mover la abeja hacia la flor (si hay una flor cercana)
        if (targetRow != -1 && targetColumn != -1) {
            int newRow = currentRow;
            int newColumn = currentColumn;

            if (targetRow > currentRow) {
                newRow++;
            } else if (targetRow < currentRow) {
                newRow--;
            }

            if (targetColumn > currentColumn) {
                newColumn++;
            } else if (targetColumn < currentColumn) {
                newColumn--;
            }
            
            // Verificar que la nueva ubicación sea válida
            if (newRow >= 0 && newRow < 30 && newColumn >= 0 && newColumn < 30) {
                Entity targetEntity = colony.getEntity(newRow, newColumn);
                if (targetEntity == null || targetEntity instanceof Flower) {
                    // Mover la abeja a la nueva ubicación
                    colony.setEntity(currentRow, currentColumn, null);
                    this.row = newRow;
                    this.column = newColumn;
                    colony.setEntity(newRow, newColumn, this);
                }
            }
        }
    }
}
