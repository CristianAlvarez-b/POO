import java.awt.*;
import java.lang.Math;
import java.util.*;
/**
 * PolygonShape is a class that creates n-vertex polygons that 
 * draw themselves on a Canvas.
 * 
 * @author (Juliana Briceño & Cristian Alvarez)
 * @version (1.0)
 */
public class PolygonShape {
    private Number[] xPoints;
    private Number[] yPoints;
    private int numVertices;
    private String color;
    private boolean visible;
    private int alpha;
     /**
     * Create a polygon shape with the specified coordinates and color.
     * 
     * @param xPoints The x-coordinates of the polygon's vertices.
     * @param yPoints The y-coordinates of the polygon's vertices.
     * @param color The color of the polygon.
     */
    public PolygonShape(Number [] xPoints, Number[] yPoints, String color) {
        this.numVertices = xPoints.length;
        this.xPoints = xPoints;
        this.yPoints = yPoints;
        //System.out.println(numVertices);
        this.color = color;
        this.visible = false;
        this.alpha = 255;
    }
    
    /**
     * Create a polygon shape with the specified coordinates, color, and alpha transparency.
     * 
     * @param xPoints The x-coordinates of the polygon's vertices.
     * @param yPoints The y-coordinates of the polygon's vertices.
     * @param color The color of the polygon.
     * @param alpha The alpha transparency value (0-255) of the polygon.
     */
    public PolygonShape(Number [] xPoints, Number[] yPoints, String color, int alpha) {
        this.numVertices = xPoints.length;
        this.xPoints = xPoints;
        this.yPoints = yPoints;
        //System.out.println(numVertices);
        this.color = color;
        this.visible = false;
        this.alpha = alpha;
    }
    
    /**
     * Make this polygon shape visible. If it was already visible, do nothing.
     */
    public void makeVisible() {
        visible = true;
        draw();
    }
    
    /**
     * Make this polygon shape invisible. If it was already invisible, do nothing.
     */
    public void makeInvisible() {
        erase();
        visible = false;
    }
    
    /**
     * Check if this polygon shape is visible.
     * 
     * @return true if the polygon shape is visible, false otherwise.
     */
    public boolean isVisible(){
        return this.visible;
    }
    
    /**
     * Get the color of this polygon shape.
     * 
     * @return The color of the polygon shape.
     */
    public String getColor(){
        return color;
    }
    
    /**
     * Draw this polygon.
     */
    private void draw() {
        int[] drawPointsX = redondeo(xPoints);
        int[] drawPointsY = redondeo(yPoints);
        if (visible) {
                Canvas canvas = Canvas.getCanvas();
                canvas.draw(this, color, new Polygon(drawPointsX, drawPointsY, numVertices), alpha);
        }
    }
    
    /**
     * Erase this polygon.
     */
    private void erase() {
        if (visible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
    
    /**
     * Check if this polygon shape contains another polygon shape.
     * 
     * @param other The other polygon shape to check for containment.
     * @return true if this polygon contains the other polygon, false otherwise.
     */
    public boolean verificaContencion(PolygonShape other) {
        for (int i = 0; i < other.numVertices; i++) {
            double x = other.xPoints[i].doubleValue();
            double y = other.yPoints[i].doubleValue();
            if (!this.contains(x, y)) {
                return false; // Si un vértice no está contenido, el polígono no lo contiene completamente
            }
        }
        return true; // Todos los vértices están dentro, el polígono lo contiene completamente
    }
    
    public boolean verificaInterseccion(PolygonShape other) {
        for (int i = 0; i < this.numVertices; i++) {
            int x1 = this.xPoints[i].intValue();
            int y1 = this.yPoints[i].intValue();
            int x2 = this.xPoints[(i + 1) % this.numVertices].intValue();
            int y2 = this.yPoints[(i + 1) % this.numVertices].intValue();

            for (int j = 0; j < other.numVertices; j++) {
                int x3 = other.xPoints[j].intValue();
                int y3 = other.yPoints[j].intValue();
                int x4 = other.xPoints[(j + 1) % other.numVertices].intValue();
                int y4 = other.yPoints[(j + 1) % other.numVertices].intValue();
                // Comprobar si las líneas (x1, y1) - (x2, y2) y (x3, y3) - (x4, y4) se intersectan
                if (seIntersectan(x1, y1, x2, y2, x3, y3, x4, y4)) {
                    return true; // Hay intersección
                }
            }
        }
        return false; // No hay intersección
    }
    /**
     * Checks if a point with given coordinates is contained within the polygon.
     * 
     * @param x The x-coordinate of the point to be checked.
     * @param y The y-coordinate of the point to be checked.
     * @return `true` if the point (x, y) is inside the polygon, `false` otherwise.
     */
    private boolean contains(Number x, Number y) {
        double xValue = x.doubleValue();
        double yValue = y.doubleValue();
        int[] drawPointsX = redondeo(xPoints);
        int[] drawPointsY = redondeo(yPoints);
        Polygon polygon = new Polygon(drawPointsX, drawPointsY, numVertices);
        return polygon.contains(xValue, yValue);
    }
    /**
     * Checks if two line segments defined by their endpoints intersect.
     * 
     * @param x1 The x-coordinate of the first endpoint of the first line segment.
     * @param y1 The y-coordinate of the first endpoint of the first line segment.
     * @param x2 The x-coordinate of the second endpoint of the first line segment.
     * @param y2 The y-coordinate of the second endpoint of the first line segment.
     * @param x3 The x-coordinate of the first endpoint of the second line segment.
     * @param y3 The y-coordinate of the first endpoint of the second line segment.
     * @param x4 The x-coordinate of the second endpoint of the second line segment.
     * @param y4 The y-coordinate of the second endpoint of the second line segment.
     * @return `true` if the two line segments intersect, `false` otherwise.
     */
    private boolean seIntersectan(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        // Calcula las pendientes de las dos líneas
        double m1 = (y2 - y1) / (double)(x2 - x1); // Pendiente de la primera línea
        double m2 = (y4 - y3) / (double)(x4 - x3); // Pendiente de la segunda línea
        // Si las pendientes son iguales, las líneas son paralelas y no se intersectan
        if (m1 == m2) {
            return false;
        }
        // Calcula las coordenadas del punto de intersección
        double xIntersect = (m1 * x1 - y1 - m2 * x3 + y3) / (m1 - m2);
        double yIntersect = m1 * (xIntersect - x1) + y1;
        // Verifica si el punto de intersección está dentro de los segmentos de ambas líneas
        boolean dentroLinea1 = (xIntersect >= Math.min(x1, x2) && xIntersect <= Math.max(x1, x2))
                && (yIntersect >= Math.min(y1, y2) && yIntersect <= Math.max(y1, y2));
        boolean dentroLinea2 = (xIntersect >= Math.min(x3, x4) && xIntersect <= Math.max(x3, x4))
                && (yIntersect >= Math.min(y3, y4) && yIntersect <= Math.max(y3, y4));
        // Las líneas se intersectan si el punto de intersección está dentro de ambos segmentos
        return dentroLinea1 && dentroLinea2;
    }
    
    private int[] redondeo(Number[] list) {
        int[] redondeoList = new int[list.length]; // Inicializa el arreglo con la longitud correcta
        for (int i = 0; i < list.length; i++) {
            redondeoList[i] = list[i].intValue(); // Convierte y almacena el valor redondeado en el arreglo
        }
        return redondeoList;
    }
}

