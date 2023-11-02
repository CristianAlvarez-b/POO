package ICPC;
import shapes.*;

public class Flat extends Flight
{
    protected boolean photographed = false;
    private PolygonShape shape;
    /**
     * Constructor for objects of class normal
     */
    public Flat(String color, Number[] from, Number[] to)
    {
        super(color, from, to);
        this.to[2] = this.from[2];
        drawInit();
        makeVisible();
    }
    /**
     * Dibuja la flecha inicial del vuelo con base en las coordenadas del punto de inicio y final.
     * Calcula la dirección y el tamaño de la flecha.
     */
    private void drawInit() {
        // Coordenadas del punto de inicio (from)
        double xFrom = from[0].doubleValue();
        double yFrom = from[1].doubleValue();
    
        // Coordenadas del punto final (to)
        double xTo = to[0].doubleValue();
        double yTo = to[1].doubleValue();
    
        // Tamaño del cuadrado (ajusta este valor según lo necesites)
        double squareSize = 10;
    
        // Ángulo de inclinación de la línea (calcula el ángulo entre los dos puntos)
        double angle = Math.atan2(yTo - yFrom, xTo - xFrom);
    
        // Longitud de la línea
        double lineLength = Math.sqrt(Math.pow(xTo - xFrom, 2) + Math.pow(yTo - yFrom, 2));
    
        // Coordenadas del centro del cuadrado
        // Coordenadas del centro del cuadrado
        double xCenter = xTo - (squareSize + 5) * Math.cos(angle);
        double yCenter = yTo - (squareSize + 5) * Math.sin(angle);

    
        // Calcula las coordenadas de los vértices del cuadrado
        Number[] xPoints = new Number[4];
        Number[] yPoints = new Number[4];
    
        xPoints[0] = xCenter - squareSize / 2;
        xPoints[1] = xCenter + squareSize / 2;
        xPoints[2] = xCenter + squareSize / 2;
        xPoints[3] = xCenter - squareSize / 2;
    
        yPoints[0] = yCenter - squareSize / 2;
        yPoints[1] = yCenter - squareSize / 2;
        yPoints[2] = yCenter + squareSize / 2;
        yPoints[3] = yCenter + squareSize / 2;
    
        shape = new PolygonShape(xPoints, yPoints, "black");
        shape.makeVisible();
    }
}
