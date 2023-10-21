import java.awt.*;
import java.lang.Math;
import java.util.*;
/**
 * A triangle that can be manipulated and that draws itself on a canvas.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0  (15 July 2000)
 */

public class Triangle{
    
    public static byte VERTICES=3;
    
    private int height;
    private int width;
    private int xPosition;
    private int yPosition;
    private String color;
    private boolean isVisible;

    /**
     * Create a new triangle at default position with default color.
     */
    public Triangle(){
        height = 30;
        width = 40;
        xPosition = 140;
        yPosition = 15;
        color = "green";
        isVisible = false;
    }
    /**
     * Create a new triangle at defalut position with default color with a given area.
     * @param area of the triangle.
     */
    public Triangle(double area){
        height = (int) Math.round(2*area/10);
        width = 10;
        xPosition = 140;
        yPosition = 15;
        color = "green";
        isVisible = true;
    }
    /**
     * Make this triangle visible. If it was already visible, do nothing.
     */
    public void makeVisible(){
        isVisible = true;
        draw();
    }
    
    /**
     * Make this triangle invisible. If it was already invisible, do nothing.
     */
    public void makeInvisible(){
        erase();
        isVisible = false;
    }
    
    /**
     * Move the triangle a few pixels to the right.
     */
    public void moveRight(){
        moveHorizontal(20);
    }

    /**
     * Move the triangle a few pixels to the left.
     */
    public void moveLeft(){
        moveHorizontal(-20);
    }

    /**
     * Move the triangle a few pixels up.
     */
    public void moveUp(){
        moveVertical(-20);
    }

    /**
     * Move the triangle a few pixels down.
     */
    public void moveDown(){
        moveVertical(20);
    }

    /**
     * Move the triangle horizontally.
     * @param distance the desired distance in pixels
     */
    public void moveHorizontal(int distance){
        erase();
        xPosition += distance;
        draw();
    }

    /**
     * Move the triangle vertically.
     * @param distance the desired distance in pixels
     */
    public void moveVertical(int distance){
        erase();
        yPosition += distance;
        draw();
    }

    /**
     * Slowly move the triangle horizontally.
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
     * Slowly move the triangle vertically.
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
     * @param newHeight the new height in pixels. newHeight must be >=0.
     * @param newWidht the new width in pixels. newWidht must be >=0.
     */
    public void changeSize(int newHeight, int newWidth) {
        erase();
        height = newHeight;
        width = newWidth;
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
     * Draw the triangle with current specifications on screen.
     */
    private void draw(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            int[] xpoints = { xPosition, xPosition + (width/2), xPosition - (width/2) };
            int[] ypoints = { yPosition, yPosition + height, yPosition + height };
            canvas.draw(this, color, new Polygon(xpoints, ypoints, 3));
            canvas.wait(10);
        }
    }

    /*
     * Erase the triangle on screen.
     */
    private void erase(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
    /**
     * Return the area of a triangle
     */
    
    public double area() {
        double area;
        area = (this.width * this.height) / 2;
        return area;
    }
    /**
     * Convert a triangle into a equilateral without changing the area.
     * 
     */
    public void equilateral(){
        double side = Math.sqrt((area() * 4) / Math.sqrt(3));
        int newWidth = (int) Math.round(side);
        double h = (newWidth * Math.sqrt(3)) / 2;
        int newHeight = (int) Math.round(h);
        changeSize( newHeight, newWidth);
    }
    
    /**
     * Change the color of a triangle a number of n times.
     * @param times number of color changes.
     */
    public void blink(int times){
        String[] colors = {"red", "yellow", "blue", "green","magenta", "black"};
        Random random = new Random();
        String originalColor = this.color;
        if (times > 0){
            int randomIndex;
            String newColor;
            for(int i = 0; i < times; i++){
                if (i % 2 == 0){
                    do {
                        randomIndex = random.nextInt(colors.length);
                        newColor = colors[randomIndex];
                    } while(newColor.equals(originalColor));
                    changeColor(newColor);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                   changeColor(originalColor); 
                }
            }
        } else {
            System.out.println("Times can't be negative or equal to 0.");
        }
    }
    /**
     * Calculate the perimeter of a triangle.
     */
    public double perimeter(){
        double side = Math.sqrt(Math.pow(this.width / 2, 2) + Math.pow(this.height, 2));
        double perimeter = this.width + 2*side;
        return perimeter;
    }
}