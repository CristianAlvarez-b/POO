package ICPC;
import shapes.*;
/**
 * Write a description of class normal here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Surprising extends Island
{
    /**
     * Constructor for objects of class normal
     */
    public Surprising(Number[][] matrix, String color)
    {
        super(matrix, color);
        drawBorder();
    }
    
    public void updateState() throws ICPC.IceepeeceeException {
        if(vertexMatrix.length <= 3){
            throw new IceepeeceeException("The minimum vertex are 3.");
        }
        Number[][] newMatrix = new Number[vertexMatrix.length - 1][vertexMatrix[0].length];
        for (int i = 0; i < vertexMatrix.length - 1; i++) {
            for (int j = 0; j < vertexMatrix[0].length; j++) {
                newMatrix[i][j] = vertexMatrix[i][j];
            }
        }
        this.vertexMatrix = newMatrix;
    }
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
            PolygonShape arista = new PolygonShape(xPoints, yPoints, "hotpink");
            border.add(arista);
        }
        makeVisible();
    }
}