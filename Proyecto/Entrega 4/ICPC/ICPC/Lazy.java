package ICPC;
import shapes.*;

public class Lazy extends Flight
{
    protected boolean photographed = false;
    private PolygonShape shape;
    /**
     * Constructor para objetos de la clase Lazy.
     * 
     * @param color El color de la flecha del vuelo.
     * @param from Las coordenadas del punto de inicio del vuelo.
     * @param to Las coordenadas del punto final del vuelo.
     */
    public Lazy(String color, Number[] from, Number[] to)
    {
        super(color, from, to);
        drawInit();
        makeVisible();
    }
    
    /**
     * Toma una foto del vuelo con la flecha. Solo se permite tomar una foto una vez.
     * 
     * @param theta El ángulo de la foto.
     * @throws ICPC.IceepeeceeException Si se intenta tomar una segunda foto del vuelo.
     */
    @Override
    public void makePhoto(Number theta) throws ICPC.IceepeeceeException{
        if(!photographed){
            super.makePhoto(theta);
            photographed = true;
        }else{
            throw new IceepeeceeException("El vuelo ya tiene una foto.");
        }
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