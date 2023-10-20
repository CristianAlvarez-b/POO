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
    private int theta;           // Theta angle of the photograph
    
    /**
     * Create a photograph with a specified starting and ending coordinates, theta angle, and color.
     *
     * @param from   Coordinates of the starting point of the flight.
     * @param to     Coordinates of the ending point of the flight.
     * @param theta  The theta angle at which the photograph is taken.
     * @param color  The color of the photograph.
     */
    public Photograph(int[] from, int[] to, int theta, String color){
        this.theta = theta;
        double temporal = theta * (Math.PI / 180);
        double distancia1 = from[2] * Math.tan(theta);
        double distancia2 = to[2] * Math.tan(theta);
        int[][] coordinates = calcularPuntosPerpendiculares(from, to, distancia1, distancia2);
        int[] xPoints = new int[coordinates.length];
        int[] yPoints = new int[coordinates.length];
        
        for (int i = 0; i < coordinates.length; i++) {
            xPoints[i] = (int) coordinates[i][0];
        }
        
        for (int i = 0; i < coordinates.length; i++) {
            yPoints[i] = (int) coordinates[i][1];
        }
        polygon = new PolygonShape(xPoints, yPoints, color, 80);
    }
    
    /**
     * Create a photograph with a specified starting and ending coordinates, theta angle (as a double), and color.
     *
     * @param from   Coordinates of the starting point of the flight.
     * @param to     Coordinates of the ending point of the flight.
     * @param theta  The theta angle (as a double) at which the photograph is taken.
     * @param color  The color of the photograph.
     */
    public Photograph(int[] from, int[] to, double theta, String color){
        this.theta = (int) theta;
        double distancia1 = from[2] * Math.tan(theta);
        double distancia2 = to[2] * Math.tan(theta);
        int[][] coordinates = calcularPuntosPerpendiculares(from, to, distancia1, distancia2);
        int[] xPoints = new int[coordinates.length];
        int[] yPoints = new int[coordinates.length];
        
        for (int i = 0; i < coordinates.length; i++) {
            xPoints[i] = (int) coordinates[i][0];
        }
        
        for (int i = 0; i < coordinates.length; i++) {
            yPoints[i] = (int) coordinates[i][1];
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
    private int[][] calcularPuntosPerpendiculares(int[] puntoI, int[] puntoF, double x, double w) {
        // Calculamos el vector que representa el segmento IF
        int vectorIFx = puntoF[0] - puntoI[0];
        int vectorIFy = puntoF[1] - puntoI[1];

        // Calculamos la magnitud del vector IF
        double magnitudIF = Math.sqrt(vectorIFx * vectorIFx + vectorIFy * vectorIFy);

        // Normalizamos el vector IF
        double vectorIFxNormalizado = vectorIFx / magnitudIF;
        double vectorIFyNormalizado = vectorIFy / magnitudIF;

        // Calculamos las coordenadas de los puntos perpendiculares
        int puntoPx = (int) (puntoI[0] + x * vectorIFyNormalizado);
        int puntoPy = (int) (puntoI[1] - x * vectorIFxNormalizado);

        int puntoQx = (int) (puntoF[0] + w * vectorIFyNormalizado);
        int puntoQy = (int) (puntoF[1] - w * vectorIFxNormalizado);

        int puntoRx = (int) (puntoI[0] - x * vectorIFyNormalizado);
        int puntoRy = (int) (puntoI[1] + x * vectorIFxNormalizado);

        int puntoSx = (int) (puntoF[0] - w * vectorIFyNormalizado);
        int puntoSy = (int) (puntoF[1] + w * vectorIFxNormalizado);

        // Creamos los puntos con las coordenadas calculadas
        int[][] puntos = {{puntoPx, puntoPy}, {puntoRx, puntoRy}, {puntoSx, puntoSy}, {puntoQx, puntoQy}};

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
    public int getTheta(){
        return this.theta;
    }
}
