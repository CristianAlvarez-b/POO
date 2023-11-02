package ICPC;
import shapes.*;
/**
 * Write a description of class normal here.
 * 
 * @author (Cristian Alvarez - Juliana Briceño) 
 * @version (2.0)
 */
public class Fixed extends Island
{
    /**
     * Constructor for objects of class normal
     */
    public Fixed(Number[][] matrix, String color)
    {
        super(matrix, color);
        drawBorder();
        makeVisible();
    }
    
     /**
     * Dibuja los bordes de la forma.
     */
    @Override
    public void drawBorder(){
        if(border.size() != 0){
            for(PolygonShape p: border){
                p.makeInvisible();
            }
        }
        int numVertices = vertexMatrix.length;
        Number[][] points = super.getPoints();
        for (int i = 0; i < numVertices; i++) {
            Number x1 = points[0][i];
            Number y1 = points[1][i];
            Number x2 = points[0][(i + 1) % numVertices]; // El módulo asegura que el último punto se conecte con el primero
            Number y2 = points[1][(i + 1) % numVertices];
            Number[] xPoints = {x1, x2};
            Number[] yPoints = {y1, y2};
            PolygonShape arista = new PolygonShape(xPoints, yPoints, "brown");
            border.add(arista);
        }
        makeVisible();
    }
}

