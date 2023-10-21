package domain;
import java.awt.Color;

/**
 * Write a description of class Flower here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Flower implements Entity
{
    // instance variables - replace the example below with your own
    protected Color color; 
    protected boolean isOpen;
    protected Colony colony;
    protected int row,column;
    protected String name;
    /**
     * Constructor for objects of class Flower
     */
    public Flower(String name, Colony colony,int row, int column)
    {
        this.name = name;
        isOpen = true;
        this.colony=colony;
        this.row=row;
        this.column=column;
        colony.setEntity(row,column,(Entity)this);  
        this.color = Color.green;
    }
    
    public void act(){
        if(isOpen){
            color = Color.red;
            isOpen = false;
        }else{
            color = Color.green;
            isOpen = true;                     
        }
    }
    
    public final int shape(){
        return Entity.FLOWER;
    }
    
    @Override
    public boolean is(){
        if(!isOpen){
            return false;
        }
        return true;
    }
     public final Color getColor(){
        return color;
    }
}
