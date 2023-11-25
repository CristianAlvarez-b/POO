package domain;

import java.awt.Color;

/**
 * La clase GuardianAnt representa una hormiga guardiana en la colonia. Las hormigas guardiana son de color rojo y tienen la capacidad de proteger la colonia de las abejas.
 */
public class GuardianAnt extends Ant {
    
    /**
     * Constructor para objetos de la clase GuardianAnt.
     * Crea una nueva hormiga guardiana en la fila y columna especificadas en la colonia dada.
     * @param name Nombre de la hormiga guardiana.    * @param colony La colonia a la que pertenece la hormiga guardiana.
     * @param row Fila de ubicación de la hormiga guardiana.
     * @param column Columna de ubicación de la hormiga guardiana.
     */
     public GuardianAnt(Colony colony, int row, int column) {
        super( colony, row, column);
        this.color = Color.red;
    }
    
    /**
     * La hormiga guardiana se mueve hacia la abeja más cercana en la colonia para protegerla.
     */
    @Override
    public void move() {
        int currentRow = this.row;
        int currentColumn = this.column;
        int[] nearestBee = colony.findNearestEntityPosition(currentRow, currentColumn, Bee.class);
        
        int targetRow = nearestBee[0];
        int targetColumn = nearestBee[1];
        
        // Mover la hormiga hacia la abeja (si hay una abeja cercana)
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
                if (targetEntity == null || targetEntity instanceof Bee) {
                    // Mover la hormiga a la nueva ubicación
                    colony.setEntity(currentRow, currentColumn, null);
                    this.row = newRow;
                    this.column = newColumn;
                    colony.setEntity(newRow, newColumn, this);
                }
            }
        }
    }
}
