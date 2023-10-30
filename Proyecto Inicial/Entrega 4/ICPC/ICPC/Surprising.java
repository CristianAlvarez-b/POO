package ICPC;
import shapes.*;
public class Surprising extends Island
{
    /**
     * Constructor para objetos de la clase Surprising.
     * 
     * @param matrix Una matriz de números que representa las coordenadas de los vértices de la forma.
     * @param color El color de la forma.
     */
    public Surprising(Number[][] matrix, String color)
    {
        super(matrix, color);
        drawBorder();
    }
    
    /**
     * Obtiene la ubicación de la forma y actualiza su estado.
     * 
     * @return Una matriz de números que representa la ubicación de la forma.
     */
    @Override
    public Number[][] getLocation(){
        try
        {
            this.updateState();
            Number[][] points = getPoints();
            polygon.changePoints(points[0], points[1]); 
            eraseBorder();
            drawBorder();
            return this.vertexMatrix;
        }
        catch (ICPC.IceepeeceeException ie)
        {
            return this.vertexMatrix;
        }
    }
    
    /**
     * Actualiza el estado de la forma, eliminando un vértice de la matriz.
     * 
     * @throws ICPC.IceepeeceeException Si la cantidad de vértices es menor o igual a 3.
     */
    public void updateState() throws ICPC.IceepeeceeException {
        if(vertexMatrix.length <= 3){
            throw new IceepeeceeException("La cantidad mínima de vértices es 3.");
        }
        Number[][] newMatrix = new Number[vertexMatrix.length - 1][vertexMatrix[0].length];
        for (int i = 0; i < vertexMatrix.length - 1; i++) {
            for (int j = 0; j < vertexMatrix[0].length; j++) {
                newMatrix[i][j] = vertexMatrix[i][j];
            }
        }
        this.vertexMatrix = newMatrix;
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
            Number x2 = points[0][(i + 1) % numVertices];
            Number y2 = points[1][(i + 1) % numVertices];
            Number[] xPoints = {x1, x2};
            Number[] yPoints = {y1, y2};
            PolygonShape arista = new PolygonShape(xPoints, yPoints, "hotpink");
            border.add(arista);
        }
        makeVisible();
    }
}
