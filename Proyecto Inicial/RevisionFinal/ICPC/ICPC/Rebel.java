package ICPC;
import shapes.*;
import java.util.Random; 
import java.awt.Color;
/**
 * Write a description of class Rebel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Rebel extends Flight
{
    protected boolean photographed = false;
    private PolygonShape shape;
    /**
     * Constructor for objects of class normal
     */
    public Rebel(String color, Number[] from, Number[] to)
    {
        super(color, from, to);
        makeVisible();
        drawInit();
    }
    @Override
    public void makePhoto(Number theta) throws ICPC.IceepeeceeException{
        Random rand = new Random();
        // Generar un número aleatorio en el rango de 0 a 90
        int numeroAleatorio = rand.nextInt(91);
        if(photograph != null){
            photograph.makeInvisible();
        }
        photograph = new Photograph(from, to, numeroAleatorio, polygon.getColor());
        if(polygon.isVisible()){
            photograph.makeVisible();
            makeVisible(); 
        }
    }
    private void drawInit(){ 
        double xFrom = from[0].doubleValue();
        double yFrom = from[1].doubleValue();
    
        // Coordenadas del punto final (to)
        double xTo = to[0].doubleValue();
        double yTo = to[1].doubleValue();
        
        // Tamaño del triángulo (ajusta este valor según lo necesites)
        double triangleSize = 10;
        
        // Ángulo de inclinación de la línea (calcula el ángulo entre los dos puntos)
        double angle = Math.atan2(yTo - yFrom, xTo - xFrom);
        
        // Coordenadas del centro del triángulo
        double xCenter = xTo - (triangleSize + 5) * Math.cos(angle);
        double yCenter = yTo - (triangleSize + 5) * Math.sin(angle);
        
        // Calcula las coordenadas de los vértices del triángulo
        Number[] xPoints = new Number[3];
        Number[] yPoints = new Number[3];
        
        xPoints[0] = xCenter;
        xPoints[1] = xCenter + triangleSize * Math.cos(angle + Math.PI / 6);
        xPoints[2] = xCenter + triangleSize * Math.cos(angle - Math.PI / 6);
        
        yPoints[0] = yCenter;
        yPoints[1] = yCenter + triangleSize * Math.sin(angle + Math.PI / 6);
        yPoints[2] = yCenter + triangleSize * Math.sin(angle - Math.PI / 6);
        
        shape = new PolygonShape(xPoints, yPoints, "black");
        shape.makeVisible();
    }
}

