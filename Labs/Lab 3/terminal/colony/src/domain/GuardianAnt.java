package domain;
import java.awt.Color;

/**
 * Write a description of class AngryAnt here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GuardianAnt extends Ant
{
    
    /**
     * Constructor for objects of class AngryAnt
     */
     public GuardianAnt(String name, Colony colony,int row, int column){
        super(name, colony,row, column);
        this.color = Color.red;
    }
    
    @Override
    public void move(){
        int currentRow = this.row;
        int currentColumn = this.column;
        int[] nearestFlower = colony.findNearestEntityPosition(currentRow, currentColumn, Bee.class);
        
        int targetRow = nearestFlower[0];
        int targetColumn = nearestFlower[1];
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
