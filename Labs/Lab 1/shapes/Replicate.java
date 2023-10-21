import java.awt.*;
import java.lang.Math;
import java.util.*;
import javax.swing.JOptionPane;

/**
 * A set of hexagonal cells which replicate a painted area.
 *
 * @author Cristian Alvarez and Juliana Briceño
 * @version 1.0 (30 August 2023)
 */
public class Replicate
{
    private ArrayList<Hexagon> hexagonalMatrix;
    private int[][] stateMatrix;
    //private int numRows;
    //private int numColumns;
    
    /**
     * Constructor of the replicate table with a random painted cell.
     * @param rows is the number of rows of the board. Max 10 rows.
     * @param columns is the number of columns of the board. Max 10 columns.
     */
    public Replicate(int rows, int columns){
        //Number of rows and columns
        //numRows = rows;
        //numColumns = columns;
        
        if ((rows <= 10 && columns <= 10) && (rows > 0 && columns > 0)){
            //Random cell
            int dimension = rows * columns;
            Random randI = new Random();
            int randomPosition = randI.nextInt(dimension);
            
            //State matrix
            stateMatrix = new int[rows][columns];
            
            //Hexagonal Matrix
            hexagonalMatrix = new ArrayList<>();
            int xAcum = 20;
            int yAcum = 15;
            for(int i = 0; i < rows; i++){
                xAcum = 15;
                for(int j = 0; j < columns; j++){
                    stateMatrix[i][j] = 0;
                    Hexagon hex = new Hexagon(xAcum, yAcum, i, j);
                    hex.makeVisible();
                    if(randomPosition == hexagonalMatrix.size()){
                        hex.changeColor("blue");
                        stateMatrix[i][j] = 1;
                    }
                    hexagonalMatrix.add(hex);
                    xAcum += 30;
                }
                yAcum += 27;
            }    
            //printStateMatrix();
        } else{
            JOptionPane.showMessageDialog(null, 
            "Rows and Columns must be less than 10 and greater than 0","Error",
            JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    /**
     * Constructor of the replicate table with a given painted cell.
     * @param rows is the number of rows of the board.
     * @param columns is the number of columns of the board.
     * @param xPositionPainted is the specific row of hexagon that will be paint.
     * @param yPositionPainted is the specific column of hexagon that will be paint.
     */
    public Replicate(int rows, int columns, int xPositionPainted, 
                    int yPositionPainted){
        //Number of rows and columns
        //numRows = rows;
        //numColumns = columns;
        
        if ((rows <= 10 && columns <= 10) && (xPositionPainted <= rows && 
        yPositionPainted <= columns) && (rows > 0 && columns > 0) &&
        (xPositionPainted > 0 && yPositionPainted > 0)){
            //State matrix
            stateMatrix = new int[rows][columns];
            
            //Hexagonal Matrix
            hexagonalMatrix = new ArrayList<>();
            int xAcum = 20;
            int yAcum = 15;
            for(int i = 0; i < rows; i++){
                xAcum = 15;
                for(int j = 0; j < columns; j++){
                    stateMatrix[i][j] = 0;
                    Hexagon hex = new Hexagon(xAcum, yAcum, i, j);
                    hex.makeVisible();
                    if(i == xPositionPainted && j == yPositionPainted){
                        hex.changeColor("blue");
                        stateMatrix[i][j] = 1;
                    }
                    hexagonalMatrix.add(hex);
                    xAcum += 30;
                }
                yAcum += 27;
            }  
            //printStateMatrix();
        } else{
            JOptionPane.showMessageDialog(null, 
            "Rows and Columns must be less than 10 and greater than 0, and the cell painted must be in the matrix given.","Error",
            JOptionPane.ERROR_MESSAGE);
        }
        
    }
    /**
     * Change de color of a specific Hexagon
     * @param xPosition is the row of the hexagon to be changed.
     * @param xPosition is the row to be update
     * @param yPosition is the column to be update
     * @param check is the condition to validate the new state.
     */
    private void changeColor(int xPosition, int yPosition, boolean check) {
        for(int i = 0; i < hexagonalMatrix.size(); i++){
            if (hexagonalMatrix.get(i).getRow() == xPosition && hexagonalMatrix.get(i).getColumn() == yPosition && check == true){
                hexagonalMatrix.get(i).changeColor("blue");
                updateState(xPosition, yPosition, true);
                break;
            }    
            if (hexagonalMatrix.get(i).getRow() == xPosition && hexagonalMatrix.get(i).getColumn() == yPosition && check == false){
                hexagonalMatrix.get(i).changeColor("yellow");
                updateState(xPosition, yPosition, false);
                break;
            }
        }
    }
    /**
     * update the MatrixState
     * @param xPosition is the row to be update
     * @param yPosition is the column to be update
     * @param check is the condition to validate the new state.
     */
    private void updateState(int xPosition, int yPosition, boolean check){
        if (check == true){
            stateMatrix[xPosition][yPosition] = 1;
        }else{
            stateMatrix[xPosition][yPosition] = 0;
        }
    }
    /**
     * Print the actual state of the StateMatrix
     */
    private void printStateMatrix(){
        for (int x=0; x < stateMatrix.length; x++) {
          System.out.print("|");
            for (int y=0; y < stateMatrix[x].length; y++) {
                System.out.print (stateMatrix[x][y]);
                if (y!=stateMatrix[x].length-1) System.out.print("\t");
            }
            System.out.println("|");
        }
    }
    /**
     * Function that return the sum of the adjacentcells of a specific cell
     * @param matrix that be analized
     * @param xPos row to analized
     * @param yPos column to analized
     */
    private static int sumAdjacentCells(int[][] matrix, int xPos, int yPos) {
        int sum = 0;
        for (int row = xPos - 1; row <= xPos + 1; row++) {
            for (int col = yPos - 1; col <= yPos + 1; col++) {
                if (row >= 0 && row < matrix.length && col >= 0 && col < matrix[0].length) {
                    sum += matrix[row][col];
                }
            }
        }
        return sum;
    }
    /**
     * In a sequence of discrete steps, each cell in the network simultaneously updates its state by examining 
     * its own state and those of its adjacent neighbors.
     * If it counts an odd number of these neighboring cells that are full, 
     * the next state of the cell will be full; otherwise, it will be empty.
     */
    public void replicate(){
        for (int i = 0; i < stateMatrix.length; i++){
            for (int j = 0; j < stateMatrix[i].length; j++){
                if ((sumAdjacentCells(stateMatrix, i, j) % 2 == 1)) {
                   changeColor(i, j, true); 
                }else{
                    changeColor(i, j, false);
                }
            }
        }
        //printStateMatrix();
    }
    /**
     * invert the columns of a matrix
     * @param matrix to be inverted
     */
    private static void invertColumns(int[][] matrix) {
        int temp;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length / 2; j++) {
                temp = matrix[i][j];
                matrix[i][j] = matrix[i][matrix[0].length - j - 1];
                matrix[i][matrix[0].length - j - 1] = temp;
            }
        }
    }
    /**
     * invert the rows of a matrix
     * @param matrix to be inverted
     */
    private static void invertRows(int[][] matrix) {
        int temp;
        for (int i = 0; i < matrix.length / 2; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                temp = matrix[i][j];
                matrix[i][j] = matrix[matrix.length - i - 1][j];
                matrix[matrix.length - i - 1][j] = temp;
            }
        }
    }
    /**
     * Change the board according to the state matrix
     * @param matrix that is the state matrix.
     */
    private void updateBoard(int[][] matrix){
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if(matrix[i][j]==1){
                    changeColor(i, j, true);
                }else{
                    changeColor(i,j, false);
                }
            }
        }
    }
    /**
     * Reflect horizontality the current board.
     */
    public void reflectHorizontal(){
        invertColumns(stateMatrix);
        updateBoard(stateMatrix);
        //printStateMatrix();
    }
    /**
     * Reflect verticaly the current board.
     */
    public void reflectVertical(){
        invertRows(stateMatrix);
        updateBoard(stateMatrix);
        //printStateMatrix();
    }
}
