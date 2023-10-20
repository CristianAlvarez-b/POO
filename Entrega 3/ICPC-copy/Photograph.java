import java.awt.*;
import java.lang.Math;
import java.util.*;
/**
 * Represents a photograph taken along the route of a flight within the Iceepeecee simulation.
 *
 * A photograph is created based on the given starting and ending coordinates of the flight,
 * a specified theta angle, and a color. This class calculates the points perpendicular to
 * the flight route and creates a PolygonShape to represent the photograph.
 *
 * @author Cristian Alvarez - Juliana Briceño
 * @version 1.0
 */
public class Photograph
{
    private PolygonShape polygon; // PolygonShape representing the photograph
    private Number theta;           // Theta angle of the photograph
    /**
     * Create a photograph with a specified starting and ending coordinates, theta angle, and color.
     *
     * @param from   Coordinates of the starting point of the flight.
     * @param to     Coordinates of the ending point of the flight.
     * @param theta  The theta angle at which the photograph is taken.
     * @param color  The color of the photograph.
     */
    public Photograph(Number[] from, Number[] to, Number theta, String color){
        this.theta = theta;
        double temporal = Math.toRadians(theta.doubleValue());
        double distancia1 = from[2].doubleValue() * Math.tan(temporal);
        double distancia2 = to[2].doubleValue() * Math.tan(temporal);
        Number[][] coordinates = calcularPuntosPerpendiculares(from, to, distancia1, distancia2);
        Number[] xPoints = new Number[coordinates.length];
        Number[] yPoints = new Number[coordinates.length];
        
        for (int i = 0; i < coordinates.length; i++) {
            xPoints[i] =  coordinates[i][0];
        }
        
        for (int i = 0; i < coordinates.length; i++) {
            yPoints[i] =  coordinates[i][1];
        }
        polygon = new PolygonShape(xPoints, yPoints, color, 80);
    }
    
    /**
     * Calculates the coordinates of points that are perpendicular to the line segment defined by two points.
     * The perpendicular points are determined based on specified distances 'x' and 'w' from the original line segment.
     *
     * @param puntoI The coordinates of the starting point of the line segment.
     * @param puntoF The coordinates of the ending point of the line segment.
     * @param x      The distance 'x' from the starting point for the perpendicular points.
     * @param w      The distance 'w' from the ending point for the perpendicular points.
     * @return An array of two-dimensional integer arrays representing the coordinates of the calculated points.
     *         The array contains four points: P, Q, R, and S, in that order.
     *         - Point P is 'x' distance away from the starting point in the perpendicular direction.
     *         - Point Q is 'w' distance away from the ending point in the perpendicular direction.
     *         - Point R is 'x' distance away from the starting point in the opposite perpendicular direction.
     *         - Point S is 'w' distance away from the ending point in the opposite perpendicular direction.
     */
    private Number[][] calcularPuntosPerpendiculares(Number[] puntoI, Number[] puntoF, double x, double w) {
        // Calculamos el vector que representa el segmento IF
        double vectorIFx = puntoF[0].doubleValue() - puntoI[0].doubleValue();
        double vectorIFy = puntoF[1].doubleValue() - puntoI[1].doubleValue();
        double magnitudIF = Math.hypot(vectorIFx, vectorIFy);
    
        double vectorIFxNormalizado = vectorIFx / magnitudIF;
        double vectorIFyNormalizado = vectorIFy / magnitudIF;
    
        double puntoPx = puntoI[0].doubleValue() - x * vectorIFyNormalizado;
        double puntoPy = puntoI[1].doubleValue() + x * vectorIFxNormalizado;
        
        double puntoQx = puntoF[0].doubleValue() - w * vectorIFyNormalizado;
        double puntoQy = puntoF[1].doubleValue() + w * vectorIFxNormalizado;
        
        double puntoRx = puntoI[0].doubleValue() + x * vectorIFyNormalizado;
        double puntoRy = puntoI[1].doubleValue() - x * vectorIFxNormalizado;
        
        double puntoSx = puntoF[0].doubleValue() + w * vectorIFyNormalizado;
        double puntoSy = puntoF[1].doubleValue() - w * vectorIFxNormalizado;
        
        Number[][] puntos = {{puntoPx, puntoPy}, {puntoRx, puntoRy}, {puntoSx, puntoSy}, {puntoQx, puntoQy}};
        return puntos;
    }
    
    /**
     * Make this flight visible.
     */
    public void makeVisible(){
        polygon.makeVisible();
    }
    
    /**
     * Make this flight invisible.
     */
    public void makeInvisible(){
        polygon.makeInvisible();
    }
    
    /**
     * Get the polygon shape associated with this photograph.
     *
     * @return The polygon shape object representing the graphic representation of this photograph.
     */
    public PolygonShape getPolygon(){
        return this.polygon;
    }
    
    /**
     * Get the theta (angle) associated with this photograph.
     *
     * @return The theta value, in degrees, at which this photograph was taken.
     */
    public Number getTheta(){
        return this.theta;
    }
    
    private int[] redondeo(Number[] list) {
        int[] redondeoList = new int[list.length]; // Inicializa el arreglo con la longitud correcta
        for (int i = 0; i < list.length; i++) {
            redondeoList[i] = list[i].intValue(); // Convierte y almacena el valor redondeado en el arreglo
        }
        return redondeoList;
    }
}
