import java.awt.*;

/**
 * A Hexagon that can be manipulated and that draws itself on a canvas.
 * 
 * @author  Cristian Alvarez and Juliana Briceño
 * @version 1.0  (26 August 2023)
 */

public class Hexagon{
    
    public static int VERTICES=6;
    
    private int sideLength;
    private int xPosition;
    private int yPosition;
    private String color;
    private boolean isVisible;
    private int row;
    private int column;
    
    public int getColumn(){
        return column;
    }
    public int getRow(){
        return row;
    }
      /**
     * Create a new hexagon at a given position with a default color
     */
    public Hexagon(int xPosition, int yPosition, int row, int column){
        sideLength = 30;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        color = "yellow";
        isVisible = false;
        this.row = row;
        this.column = column;
    }

    /**
     * Create a new Hexagon at default position with default color.
     */
    public Hexagon(){
        sideLength = 60;
        xPosition = 140;
        yPosition = 50;
        color = "green";
        isVisible = false;
    }

    /**
     * Make this Hexagon visible. If it was already visible, do nothing.
     */
    public void makeVisible(){
        isVisible = true;
        draw();
    }
    
    /**
     * Make this Hexagon invisible. If it was already invisible, do nothing.
     */
    public void makeInvisible(){
        erase();
        isVisible = false;
    }
    
    /**
     * Move the Hexagon a few pixels to the right.
     */
    public void moveRight(){
        moveHorizontal(20);
    }

    /**
     * Move the Hexagon a few pixels to the left.
     */
    public void moveLeft(){
        moveHorizontal(-20);
    }

    /**
     * Move the Hexagon a few pixels up.
     */
    public void moveUp(){
        moveVertical(-20);
    }

    /**
     * Move the Hexagon a few pixels down.
     */
    public void moveDown(){
        moveVertical(20);
    }

    /**
     * Move the Hexagon horizontally.
     * @param distance the desired distance in pixels
     */
    public void moveHorizontal(int distance){
        erase();
        xPosition += distance;
        draw();
    }

    /**
     * Move the Hexagon vertically.
     * @param distance the desired distance in pixels
     */
    public void moveVertical(int distance){
        erase();
        yPosition += distance;
        draw();
    }

    /**
     * Slowly move the Hexagon horizontally.
     * @param distance the desired distance in pixels
     */
    public void slowMoveHorizontal(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            xPosition += delta;
            draw();
        }
    }

    /**
     * Slowly move the Hexagon vertically.
     * @param distance the desired distance in pixels
     */
    public void slowMoveVertical(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            yPosition += delta;
            draw();
        }
    }

    /**
     * Change the size to the new size

     * @param newWidht the new sideLength in pixels. newWidht must be >=0.
     */
    public void changeSize(int newSize) {
        erase();
        sideLength = newSize;
        draw();
    }
    
    /**
     * Change the color. 
     * @param color the new color. Valid colors are "red", "yellow", "blue", "green",
     * "magenta" and "black".
     */
    public void changeColor(String newColor){
        color = newColor;
        draw();
    }

    /*
     * Draw the Hexagon with current specifications on screen.
     */
    private void draw() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            int radius = sideLength / 2; // Distancia desde el centro a un vértice
            int[] xpoints = new int[6];
            int[] ypoints = new int[6];
    
            for (int i = 0; i < 6; i++) {
                double angle = 2 * Math.PI * i / 6; // Ángulo entre cada vértice
                xpoints[i] = xPosition + (int) (radius * Math.cos(angle));
                ypoints[i] = yPosition + (int) (radius * Math.sin(angle));
            }
            canvas.draw(this, color, new Polygon(xpoints, ypoints, 6));
            canvas.wait(10);
        }
    }
    /*
     * Erase the Hexagon on screen.
     */
    private void erase(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
}
