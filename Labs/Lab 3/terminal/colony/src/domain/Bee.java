package domain;
import java.awt.Color;

/**
 * Write a description of class Bee here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Bee extends Agent implements Entity
{
    protected char nextState;
    protected Color color;
    protected Colony colony;
    protected int row,column;
    protected String name;
    /**
     * Constructor for objects of class Bee
     */
    public Bee(String name, Colony colony,int row, int column)
    {
        this.name = name;
        this.colony=colony;
        this.row=row;
        this.column=column;
        nextState=Agent.ALIVE;
        state = nextState;
        colony.setEntity(row,column,(Entity)this);  
        color=Color.yellow;
    }
    /**Returns the shape
    @return 
     */
    public final int shape(){
        return Entity.INSECT;
    }
    
    public void act(){
        if (getAge() == 101) {
            state = Agent.DEAD;
            colony.setEntity(row, column, null);
        }else{
            move();
            turn();
        }
    }
    public void move(){
        int currentRow = this.row;
        int currentColumn = this.column;
        int[] nearestFlower = colony.findNearestEntityPosition(currentRow, currentColumn, Flower.class);
        
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
                if (targetEntity == null || targetEntity instanceof Flower) {
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

