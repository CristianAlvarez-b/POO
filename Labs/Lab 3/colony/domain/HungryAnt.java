package domain;
import java.awt.Color;

/**
 * Write a description of class HungryAnt here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HungryAnt extends Ant
{
    // instance variables - replace the example below with your own
    private int tictac;
    private boolean foundFood = false;
    private boolean hungry = true;
    /**
     * Constructor for objects of class HungryAnt
     */
    public HungryAnt(String name, Colony colony,int row, int column){
        super(name, colony,row, column);
        this.color = Color.green;
        tictac = 0;
    }
    
    @Override
    public void move() {
        if(tictac == 10){
            hungry = true;
            tictac = 0;
        }
        int currentRow = this.row;
        int currentColumn = this.column;
        int[] nearestFood = colony.findNearestEntityPosition(currentRow, currentColumn, Food.class);
        if (!hungry || nearestFood.length == 0) {
            super.move();
            tictac++;
            // Si la hormiga ya ha encontrado comida, comportarse como la superclase
        } else {
            // Encontrar la ubicación de la comida más cercana
           
            int targetRow = nearestFood[0];
            int targetColumn = nearestFood[1];
            // Mover la hormiga hacia la comida (si hay comida)
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
                    if (targetEntity == null || (targetEntity instanceof Food && hungry == true)) {
                        // Mover la hormiga a la nueva ubicación
                        if(targetEntity instanceof Food){
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
    }


