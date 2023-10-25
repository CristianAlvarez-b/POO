package ICPC;
import shapes.*;
/**
 * Write a description of class normal here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Lazy extends Flight
{
    protected boolean photographed = false;
    private PolygonShape shape;
    /**
     * Constructor for objects of class normal
     */
    public Lazy(String color, Number[] from, Number[] to)
    {
        super(color, from, to);
        drawInit();
        makeVisible();
    }
    @Override
    public void makePhoto(Number theta) throws ICPC.IceepeeceeException{
        if(!photographed){
            super.makePhoto(theta);
            photographed = true;
        }else{
            throw new IceepeeceeException("The flight already has one photo.");
        }
    }
    private void drawInit() {
        // Coordenadas del punto de inicio (from)
        double xFrom = from[0].doubleValue();
        double yFrom = from[1].doubleValue();
    
        // Coordenadas del punto final (to)
        double xTo = to[0].doubleValue();
        double yTo = to[1].doubleValue();
    
        // Tamaño de la flecha (ajusta este valor según lo necesites)
        double arrowSize = 5.0;
    
        // Ángulo de inclinación de la línea (calcula el ángulo entre los dos puntos)
        double angle = Math.atan2(yTo - yFrom, xTo - xFrom);
    
        // Longitud de la línea
        double lineLength = Math.sqrt(Math.pow(xTo - xFrom, 2) + Math.pow(yTo - yFrom, 2));
    
        // Puntos para la flecha
        Number[] xPoints = {
            xFrom, // Punto de inicio
            xTo - arrowSize * Math.cos(angle - Math.PI / 6), // Punto final izquierdo de la flecha
            xTo, // Punto final
            xTo - arrowSize * Math.cos(angle + Math.PI / 6) // Punto final derecho de la flecha
        };
    
        Number[] yPoints = {
            yFrom, // Punto de inicio
            yTo - arrowSize * Math.sin(angle - Math.PI / 6), // Punto final izquierdo de la flecha
            yTo, // Punto final
            yTo - arrowSize * Math.sin(angle + Math.PI / 6) // Punto final derecho de la flecha
        };
    
        shape = new PolygonShape(xPoints, yPoints, "black", 80);
        shape.makeVisible();
    }
}
