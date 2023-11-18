package domain;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class Vintage {

    private char[][][] board;
    private int[] jewels;
    private int turn;

    public Vintage(int row, int column) throws VintageException{
        if(row < 0 || column < 0){
            throw new VintageException(VintageException.INVALID_SIZE);
        }
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
        int punctuation = validateRows() + validateColumns() + traverseDiagonalLeftToRight()+
                traverseDiagonalRightToLeft();
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

private int traverseDiagonalLeftToRight() {
    int rows = board.length;
    int columns = board[0].length;
    int punctuation = 0;

    for (int k = 0; k < rows + columns - 1; k++) {
        int startRow = Math.max(0, k - columns + 1);
        int count = Math.min(k + 1, Math.min(rows, columns - startRow));
        char validateColor = board[startRow][0][0];
        int[][] toEliminate = {{startRow,0}};

        for (int j = 0; j < count; j++) {
            int row = startRow + j;
            int col = Math.min(columns - 1, Math.max(0, k - startRow - j));
            char currentGem = board[row][col][0];
            if (validateColor == currentGem) {
                int[] currentPosition = {row, col};
                toEliminate = agregarFila(toEliminate, currentPosition);
            } else {
                if (toEliminate.length >= 3) {
                    punctuation += toEliminate.length;
                    eliminateGems(toEliminate);
                } else {
                    toEliminate = new int[][]{{row, col}};
                    validateColor = board[row][col][0];
                }
            }

        }
        if (toEliminate.length >= 3) {
            punctuation += toEliminate.length;
            eliminateGems(toEliminate);
        }
    }
    dropJewel();
    return punctuation;
}

    private int traverseDiagonalRightToLeft() {
        int rows = board.length;
        int columns = board[0].length;
        int punctuation = 0;


        for (int k = 0; k < rows + columns - 1; k++) {
            int startRow = Math.max(0, k - columns + 1);
            int count = Math.min(k + 1, Math.min(rows, columns - startRow));
            char validateColor = board[startRow][columns - 1][0];
            int[][] toEliminate = {{startRow,columns - 1}};

            for (int j = 0; j < count; j++) {
                int row = startRow + j;
                int col = Math.min(columns - 1, Math.max(0, k - startRow - j));
                char currentGem = board[row][col][0];

                if (validateColor == currentGem) {
                    int[] currentPosition = {row, col};
                    toEliminate = agregarFila(toEliminate, currentPosition);
                } else {
                    if (toEliminate.length >= 3) {
                        punctuation += toEliminate.length;
                        eliminateGems(toEliminate);
                    } else {
                        toEliminate = new int[][]{{row, col}};
                        validateColor = board[row][col][0];
                    }
                }
            }
            if (toEliminate.length >= 3) {
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

    public void setBoard(char[][][] board){
        this.board = board;
    }
    public void setJewels(int[] jewels){
        this.jewels = jewels;
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
    public char[][][] getBoard(){
        return board;
    }
    public int[] getJewels(){return jewels;}
    public int getTurn(){
        return turn;
    }
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        // Detalles sobre el tablero
        result.append("Tablero:\n");
        for (char[][] row : board) {
            for (char[] gem : row) {
                result.append("[");
                for (char color : gem) {
                    result.append(color).append(", ");
                }
                result.setLength(result.length() - 2); // Remove the trailing comma and space
                result.append("] ");
            }
            result.append("\n");
        }
        return result.toString();
    }
    public static void main(String[] args) {
        try {
            // Crea un objeto Vintage con un tablero de 3 filas y 3 columnas
            Vintage vintageGame = new Vintage(3, 3);

            // Imprime el estado inicial del tablero
            System.out.println("Estado inicial del tablero:");
            System.out.println(vintageGame);

            // Realiza algunos movimientos para probar el juego
            vintageGame.play(0, 0, 0, 1);
            vintageGame.play(1, 1, 1, 2);

            // Imprime el estado del tablero después de los movimientos
            System.out.println("Estado del tablero después de los movimientos:");
            System.out.println(vintageGame);

        } catch (VintageException e) {
            e.printStackTrace();
        }
    }
}


