package ICPC;
import shapes.*;
import java.awt.*;
import java.lang.Math;
import java.util.*;
/**
 * Represents a polygon-shaped island within the Iceepeecee simulation.
 *
 * An island is defined by its vertices, color, and can have borders drawn around it.
 * This class provides methods to manipulate the visibility of the island and its borders.
 * It also allows retrieval of the island's location and polygon representation.
 *
 * @author Juliana Briceño & Cristian Alvarez
 * @version 1.0
 */
public  class Island
{
    protected Number[][] vertexMatrix;       // Coordinates of the island's vertices
    protected PolygonShape polygon;       // PolygonShape representing the island
    protected ArrayList<PolygonShape> border; // Borders drawn around the island
    /**
     * Create an island.
     * 
     * @param coordinates of the vertices of the island
     * @param color of the island
     */
    public Island(Number[][] matrix, String color){
        this.border = new ArrayList<>();
        this.vertexMatrix = matrix;
        Number[][] points = getPoints();
        this.polygon = new PolygonShape(points[0], points[1], color, 255);
    }
    
    /**
     * Make this island visible.
     */
    public void makeVisible(){
        polygon.makeVisible();
        for(PolygonShape p: border){
            p.makeVisible();
        }
    }
    
    /**
     * Make this island invisible.
     */
    public void makeInvisible(){
        polygon.makeInvisible();
        for(PolygonShape p: border){
            p.makeInvisible();
        }
    }
    
    /**
     * Get the location of the island according to the 
     * coordinates of its vertices.
     */
    public Number[][] getLocation(){
        return this.vertexMatrix;
    }
    
    /**
     * Get the polygon shape representation of the island.
     *
     * @return The PolygonShape representing the island.
     */
    public PolygonShape getPolygon(){
        return this.polygon;
    }
    
    /**
     * Draw borders around the island's edges.
     * The borders are drawn as separate PolygonShapes.
     */
    public void drawBorder(){
        if(border.size() != 0){
            for(PolygonShape p: border){
                p.makeInvisible();
            }
        }
        int numVertices = vertexMatrix.length;
        Number[][] points = getPoints();
        for (int i = 0; i < numVertices; i++) {
            Number x1 = points[0][i];
            Number y1 = points[1][i];
            Number x2 = points[0][(i + 1) % numVertices]; // El módulo asegura que el último punto se conecte con el primero
            Number y2 = points[1][(i + 1) % numVertices];
            Number[] xPoints = {x1, x2};
            Number[] yPoints = {y1, y2};
            PolygonShape arista = new PolygonShape(xPoints, yPoints, "black");
            border.add(arista);
        }
        if(polygon.isVisible()){
            makeVisible();
        }
    }   
    
    /**
     * Erase borders drawn around the island.
     */
    public void eraseBorder(){
        for(PolygonShape p: border){
            p.makeInvisible();
        }
        border.clear();
    }
    
    /**
     * Extracts and returns the x and y coordinates of the island's vertices.
     *
     * @return An array of two integer arrays where the first array contains x-coordinates
     *         and the second array contains y-coordinates of the island's vertices.
     */    
    private Number[][] getPoints(){
        int numVertices = vertexMatrix.length;
        Number [] xPoints = new Number[numVertices];
        Number [] yPoints = new Number[numVertices];
        for(int i = 0; i < numVertices; i++){
            xPoints[i] = vertexMatrix[i][0];
            yPoints[i] = vertexMatrix[i][1];
        }
        Number[][] points = {xPoints, yPoints};
        return points;
    }
}
