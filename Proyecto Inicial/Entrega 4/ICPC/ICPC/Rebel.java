package ICPC;
import shapes.*;
import java.util.Random; 
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
}

