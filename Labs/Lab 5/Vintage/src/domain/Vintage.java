package domain;
import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Vintage {

    private char[][][] board;
    private int[] jewels;
    private int turn;

    public Vintage(int row, int column) {
        board = new char[row][column][2];
        fillBoardWithRandomChars();
        jewels = new int[]{0,0};
        turn = 0;
    }
    public boolean play(int row1, int column1, int row2, int column2) throws VintageException {
        if (isValidMove(row1,column1,row2,column2)) {
            turn += 1;
            // Intercambiar los valores en las posiciones especificadas
            char temp = board[row1][column1][0];
            board[row1][column1][0] = board[row2][column2][0];
            board[row2][column2][0] = temp;

            validateConsecutiveGems();

            return verifyGame();

        } else {
            throw new VintageException(VintageException.INVALID_MOVE);
        }
    }

    private boolean verifyGame(){
        boolean gameOver = true;
        for (int row = 0; row < board.length && gameOver; row++){
            for (int column = 0; column < board[0].length && gameOver; column++){
                if (board[row][column][1] != 'n'){
                    gameOver = false;
                }
            }
        }
        return gameOver;
    }

    private boolean isValidMove(int row1, int column1, int row2, int column2)throws VintageException{
        if (isValidPosition(row1, column1) && isValidPosition(row2, column2)){
            // Verificar si las posiciones están alrededor (arriba, abajo, o en cualquier diagonal)
            int rowDifference = Math.abs(row2 - row1);
            int columnDifference = Math.abs(column2 - column1);
            // Las posiciones están alrededor si la diferencia en filas y columnas es de 1 o menos
            if (rowDifference <= 1 && columnDifference <= 1) {
                return true;
            } else {
                throw new VintageException(VintageException.INVALID_MOVE);
            }
        }else {
            throw new VintageException(VintageException.INVALID_MOVE);
        }
    }

    private void validateConsecutiveGems() {
        int punctuation = validateRows() + validateColumns() + validateMainDiagonal()+
                validateSecondaryDiagonal();
        if (turn%2 == 1){
            jewels[0] += punctuation;
        }else {
            jewels[1] += punctuation;
        }
    }

    private int validateRows(){
        int rows = board.length;
        int columns = board[0].length;
        int punctuation = 0;
        // Validar filas
        for (int row = 0; row < rows; row ++){
            char validateColor = board[row][0][0];
            int[][] toEliminate = {{row,0}};
            for (int column  = 1; column < columns; column++){
                char currentGem = board[row][column][0];
                if (validateColor == currentGem){
                    int[] currentPosition = {row,column};
                    toEliminate = agregarFila(toEliminate,currentPosition);
                }else {
                    if(toEliminate.length >= 3){
                        punctuation += toEliminate.length;
                        eliminateGems(toEliminate);
                    }else {
                        toEliminate = new int[][]{{row,column}};
                        validateColor = board[row][column][0];
                    }
                }
            }
            // Verificar puntaje para la última fila
            if(toEliminate.length >= 3){
                punctuation += toEliminate.length;
                eliminateGems(toEliminate);
            }
        }
        dropJewel();
        return punctuation;
    }

    private int validateColumns() {
        int rows = board.length;
        int columns = board[0].length;
        int punctuation = 0;
        // Validar columnas
        for (int column = 0; column < columns; column++) {
            char validateColor = board[0][column][0];
            int[][] toEliminate = {{0, column}};

            for (int row = 1; row < rows; row++) {
                char currentGem = board[row][column][0];

                if (validateColor == currentGem) {
                    int[] currentPosition = {row, column};
                    toEliminate = agregarFila(toEliminate, currentPosition);
                } else {
                    if (toEliminate.length >= 3) {
                        punctuation += toEliminate.length;
                        eliminateGems(toEliminate);
                    } else {
                        toEliminate = new int[][]{{row, column}};
                        validateColor = board[row][column][0];
                    }
                }
            }
            // Verificar puntaje para la última columna
            if (toEliminate.length >= 3) {
                punctuation += toEliminate.length;
                eliminateGems(toEliminate);
            }
        }
        dropJewel();
        return punctuation;
    }
    private int validateMainDiagonal() {
        int rows = board.length;
        int columns = board[0].length;
        int punctuation = 0;

        for (int row = 2; row < rows; row++){
            char validateColor = board[row][0][0];
            int[][] toEliminate = {{row,0}};
            for (int i = 1; i <= row; i++){
                char currentGem = board[row-i][i][0];
                if (validateColor == currentGem){
                    int[] currentPosition = {row-i,i};
                    toEliminate = agregarFila(toEliminate,currentPosition);
                }else {
                    if(toEliminate.length >= 3){
                        punctuation += toEliminate.length;
                        eliminateGems(toEliminate);
                    }else {
                        toEliminate = new int[][]{{row-i,i}};
                        validateColor = board[row-i][i][0];
                    }
                }
            }
            // Verificar puntaje para la última fila
            if(toEliminate.length >= 3){
                punctuation += toEliminate.length;
                eliminateGems(toEliminate);
            }
        }


        for (int row = rows-3; row > 0; row--){
            char validateColor = board[row][columns-1][0];
            int[][] toEliminate = {{row,columns-1}};
            for (int i = 1; i < rows-row; i++){
                char currentGem = board[row+i][columns-1-i][0];
                if (validateColor == currentGem){
                    int[] currentPosition = {row+i,columns-1-i};
                    toEliminate = agregarFila(toEliminate,currentPosition);
                }else {
                    if(toEliminate.length >= 3){
                        punctuation += toEliminate.length;
                        eliminateGems(toEliminate);
                    }else {
                        toEliminate = new int[][]{{row+i,columns-1-i}};
                        validateColor = board[row+i][columns-1-i][0];
                    }
                }
            }
            // Verificar puntaje para la última fila
            if(toEliminate.length >= 3){
                punctuation += toEliminate.length;
                eliminateGems(toEliminate);
            }
        }

        dropJewel();
        return punctuation;
    }

    private int validateSecondaryDiagonal() {
        int rows = board.length;
        int columns = board[0].length;
        int punctuation = 0;

        for (int row = rows-3; row >= 0; row--){
            char validateColor = board[row][0][0];
            int[][] toEliminate = {{row,0}};
            for (int i = 1; i < rows-row; i++){
                char currentGem = board[row+i][i][0];
                if (validateColor == currentGem){
                    int[] currentPosition = {row+i,i};
                    toEliminate = agregarFila(toEliminate,currentPosition);
                }else {
                    if(toEliminate.length >= 3){
                        punctuation += toEliminate.length;
                        eliminateGems(toEliminate);
                    }else {
                        toEliminate = new int[][]{{row+i,i}};
                        validateColor = board[row+i][i][0];
                    }
                }
            }
            // Verificar puntaje para la última fila
            if(toEliminate.length >= 3){
                punctuation += toEliminate.length;
                eliminateGems(toEliminate);
            }
        }


        for (int row = 2; row < rows-1; row++){
            char validateColor = board[row][columns-1][0];
            int[][] toEliminate = {{row,columns-1}};
            for (int i = 1; i <= row; i++){
                char currentGem = board[row-i][columns-1-i][0];
                if (validateColor == currentGem){
                    int[] currentPosition = {row-i,columns-1-i};
                    toEliminate = agregarFila(toEliminate,currentPosition);
                }else {
                    if(toEliminate.length >= 3){
                        punctuation += toEliminate.length;
                        eliminateGems(toEliminate);
                    }else {
                        toEliminate = new int[][]{{row-i,columns-1-i}};
                        validateColor = board[row-i][columns-1-i][0];
                    }
                }
            }
            // Verificar puntaje para la última fila
            if(toEliminate.length >= 3){
                punctuation += toEliminate.length;
                eliminateGems(toEliminate);
            }
        }

        dropJewel();
        return punctuation;
    }

    private int[][] agregarFila(int[][] matriz, int[] nuevaFila) {
        int filasOriginales = matriz.length;
        int columnasOriginales = matriz[0].length;

        // Crear una nueva matriz con una fila adicional
        int[][] nuevaMatriz = new int[filasOriginales + 1][columnasOriginales];

        // Copiar las filas originales a la nueva matriz
        for (int i = 0; i < filasOriginales; i++) {
            System.arraycopy(matriz[i], 0, nuevaMatriz[i], 0, columnasOriginales);
        }

        // Agregar la nueva fila a la última posición de la nueva matriz
        System.arraycopy(nuevaFila, 0, nuevaMatriz[filasOriginales], 0, columnasOriginales);

        return nuevaMatriz;
    }

    private void eliminateGems(int[][] toEliminate){
        for (int[] jewelPosition : toEliminate){
            int row = jewelPosition[0];
            int column = jewelPosition[1];

            if (board[row][column][1] == 'c'){
                board[row][column][1] = 'n';
            }
            board[row][column][0] = 'w';
        }
    }


    private void dropJewel() {
        for (int column = 0; column < board[0].length; column++){
            for (int row = board.length-1; row >= 0 ; row--){
                if (board[row][column][0] == 'w'){
                    board[row][column][0] = fillJewel(row,column);
                }
            }
        }

    }

    private char fillJewel(int row, int column){
        char color = 'w';
        for (int r = row-1; color=='w' && r>=0; r--){
            if (board[r][column][0] != color){
                color = board[r][column][0];
                board[r][column][0] = 'w';
            }
        }
        if (color == 'w'){
            Random random = new Random();
            String POSSIBLE_CHARS = "rbavolm";
            int randomIndex = random.nextInt(POSSIBLE_CHARS.length());
            color = POSSIBLE_CHARS.charAt(randomIndex); // Nueva gema
        }
        return color;
    }

    private boolean isValidPosition(int row, int column) {
        return row >= 0 && row < board.length && column >= 0 && column < board[0].length;
    }
    private void fillBoardWithRandomChars() {
        Random random = new Random();
        String possibleChars = "rbavolm";

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                int randomIndex = random.nextInt(possibleChars.length());
                char[] newJewel = {possibleChars.charAt(randomIndex), 'c'};
                board[i][j] = newJewel;
            }
        }
    }
    public void printBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print("('" + board[i][j][0] + "', '" + board[i][j][1] + "') ");
            }
            System.out.println();
        }
    }
    public char[][][] getBoard(){
        return board;
    }

    public static void main(String[] args) {
        int rows = 5;
        int columns = 5;
        Vintage vintage = new Vintage(rows, columns);
        vintage.printBoard();
        try{
        vintage.play(0,0,1,1);
        }catch (VintageException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        vintage.printBoard();
    }
}


