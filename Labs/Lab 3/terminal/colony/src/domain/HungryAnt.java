package domain;

import java.awt.Color;

/**
 * La clase HungryAnt representa una hormiga hambrienta en la colonia. Las hormigas hambrientas cambian de color cuando tienen hambre y buscan comida en la colonia.
 */
public class HungryAnt extends Ant {
    private int tictac = 0;
    private boolean foundFood = false;
    private boolean hungry = true;

    /**
     * Constructor para objetos de la clase HungryAnt.
     * Crea una nueva hormiga hambrienta en la fila y columna especificadas en la colonia dada.
     * @param name Nombre de la hormiga hambrienta.
     * @param colony La colonia a la que pertenece la hormiga hambrienta.
     * @param row Fila de ubicación de la hormiga hambrienta.
     * @param column Columna de ubicación de la hormiga hambrienta.
     */
    public HungryAnt(String name, Colony colony, int row, int column) {
        super(name, colony, row, column);
        this.color = Color.green;
    }

    /**
     * El método move permite que la hormiga hambrienta busque comida en la colonia y cambie de comportamiento cuando tiene hambre.
     */
    @Override
    public void move() {
        if (tictac == 10) {
            hungry = true;
            tictac = 0;
        }
        int currentRow = this.row;
        int currentColumn = this.column;
        int[] nearestFood = colony.findNearestEntityPosition(currentRow, currentColumn, Food.class);
        
        if (!hungry || nearestFood.length == 0) {
            super.move();
            tictac++;
        } else {
            int targetRow = nearestFood[0];
            int targetColumn = nearestFood[1];
            
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
                
                if (newRow >= 0 && newRow < 30 && newColumn >= 0 && newColumn < 30) {
                    Entity targetEntity = colony.getEntity(newRow, newColumn);
                    if (targetEntity == null || (targetEntity instanceof Food && hungry == true)) {
                        if (targetEntity instanceof Food) {
                            hungry = false;
                        }
                        colony.setEntity(currentRow, currentColumn, null);
                        this.row = newRow;
                        this.column = newColumn;
                        colony.setEntity(newRow, newColumn, this);
                    }
                }
            }
        }
    }

    /**
     * Obtiene el estado de hambre de la hormiga hambrienta.
     * @return `true` si la hormiga está hambrienta, `false` si no lo está.
     */
    public boolean getHungry() {
        return this.hungry;
    }
}
