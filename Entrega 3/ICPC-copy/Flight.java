import java.awt.*;
import java.lang.Math;
import java.util.*;
/**
 * Represents the graphic representation of the route of a flight within the Iceepeecee simulation.
 *
 * A flight is defined by its color, starting and ending coordinates, and can have a photograph associated with it.
 * This class provides methods to manipulate the visibility of the flight, take photographs along the route,
 * and retrieve information about the flight and its associated photograph.
 *
 * @author Juliana Briceño & Cristian Alvarez
 * @version 1.0
 */
public class Flight
{
    private Number[] from;             // Starting coordinates of the flight
    private Number[] to;               // Ending coordinates of the flight
    private PolygonShape polygon;   // PolygonShape representing the flight's route
    private Photograph photograph;  // Photograph taken along the flight's route
    
    /**
     * Create a flight with a specified color, starting, and ending coordinates.
     *
     * @param color The color of the flight.
     * @param from  Coordinates of the starting point of the flight.
     * @param to    Coordinates of the ending point of the flight.
     */
    public Flight(String color, Number[] from, Number[] to){
        this.from = from;
        this.to = to;
        Number[] xPoints = new Number[2];
        Number[] yPoints = new Number[2];
        
        xPoints[0] = from[0];
        xPoints[1] = to[0];
        yPoints[0] = from[1];
        yPoints[1] = to[1];
        
        this.polygon = new PolygonShape(xPoints, yPoints, color);
        this.photograph = null;
    }
    
    /**
     * Make this flight and its associated photograph visible.
     */
    public void makeVisible(){
        polygon.makeVisible();
        if(photograph != null){
           photograph.makeVisible(); 
        }
    }
    
    /**
     * Make this flight and its associated photograph invisible.
     */
    public void makeInvisible(){
        polygon.makeInvisible();
        if(photograph != null){
           photograph.makeInvisible(); 
        }
    }
    
    /**
     * Get the location of the flight represented as starting and ending coordinates.
     *
     * @return An array containing the starting and ending coordinates of the flight.
     */
    public Number[][] getLocation(){
        Number [][] location = {from, to};
        return location;
    }
    
    /**
     * Capture a photograph along the flight's route with a specified theta angle.
     * The photograph is associated with the flight.
     *
     * @param theta The angle at which the photograph is taken.
     */
    public void makePhoto(Number theta){
        if(photograph != null){
            photograph.makeInvisible();
        }
        photograph = new Photograph(from, to, theta, polygon.getColor());
        if(polygon.isVisible()){
            photograph.makeVisible();
            makeVisible(); 
        }
    }
    
    /**
     * Get the photograph associated with the flight.
     *
     * @return The Photograph object associated with the flight, or null if no photograph has been taken.
     */
    public Photograph getPhotograph(){
        return this.photograph;
    }
    
}
