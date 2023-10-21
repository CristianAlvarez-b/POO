package domain;
import java.awt.Color;
import java.util.Random;
/**Information about a Ant<br>
<b>(colony,row,column,age,state,nextState, color)</b><br>
<br>
 */
public class Ant extends Agent implements Entity{
    protected char nextState;
    protected Color color;
    protected Colony colony;
    protected int row,column;
    protected String name;
    /**Create a new Ant (<b>row,column</b>) in the colony <b>colony</b>.
     * Every new Ant is going to be alive in the following state.
     * @param colony 
     * @param row 
     * @param column 
     */
    public Ant(String name, Colony colony,int row, int column){
        this.name = name;
        this.colony=colony;
        this.row=row;
        this.column=column;
        nextState=Agent.ALIVE;
        state = nextState;
        colony.setEntity(row,column,(Entity)this);  
        color=Color.black;
    }

    /**Returns the shape
    @return 
     */
    public final int shape(){
        return Entity.INSECT;
    }
    
    /**Returns the row
    @return 
     */
    public final int getRow(){
        return row;
    }

    /**Returns tha column
    @return 
     */
    public final int getColumn(){
        return column;
    }
    
    public final String getName(){
        return name;
    }
    
    /**Returns the color
    @return 
     */
    public final Color getColor(){
        return color;
    }

    public void act() {
        if (getAge() == 49) {
            state = Agent.DEAD;
            colony.setEntity(row, column, null);
        }else{
            move();
            turn();
        }
    }

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
    
        if (30 > newRow && newRow > 0 && newColumn > 0 && newColumn < 30){
        // Mover la hormiga a la nueva ubicación
        Entity targetEntity = colony.getEntity(newRow, newColumn);
            if (targetEntity == null) {
                colony.setEntity(row, column, null);
                this.row = newRow;
                this.column = newColumn;
                colony.setEntity(row, column, (Entity)this);
        }
        }
    }
}
