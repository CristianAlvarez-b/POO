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
    public void play(int row1, int column1, int row2, int column2) throws VintageException {
        if (isValidPosition(row1, column1) && isValidPosition(row2, column2)) {
            turn += 1;
            // Intercambiar los valores en las posiciones especificadas
            char temp = board[row1][column1][0];
            board[row1][column1][0] = board[row2][column2][0];
            board[row2][column2][0] = temp;

            validateConsecutiveGems();

        } else {
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
        int puntuation = 0;

        for (int i = 0; i < rows; i++) {
            char validateColor = board[i][i][0];
            int[][] toEliminate = {{i, i}};

            for (int j = i + 1; j < columns; j++) {
                char currentGem = board[j][j][0];

                if (validateColor == currentGem) {
                    int[] currentPosition = {j, j};
                    toEliminate = agregarFila(toEliminate, currentPosition);
                } else {
                    if (toEliminate.length >= 3) {
                        puntuation += toEliminate.length;
                        eliminateGems(toEliminate);
                    } else {
                        toEliminate = new int[][]{{j, j}};
                        validateColor = board[j][j][0];
                    }
                }
            }

            // Verificar puntaje para la última posición en la diagonal principal
            if (toEliminate.length >= 3) {
                puntuation += toEliminate.length;
                eliminateGems(toEliminate);
            }
        }
        dropJewel();
        return puntuation;
    }

    private int validateSecondaryDiagonal() {
        int rows = board.length;
        int columns = board[0].length;
        int puntuation = 0;

        for (int i = 0; i < rows; i++) {
            char validateColor = board[i][columns - 1 - i][0];
            int[][] toEliminate = {{i, columns - 1 - i}};

            for (int j = i + 1; j < rows; j++) {
                char currentGem = board[j][columns - 1 - j][0];

                if (validateColor == currentGem) {
                    int[] currentPosition = {j, columns - 1 - j};
                    toEliminate = agregarFila(toEliminate, currentPosition);
                } else {
                    if (toEliminate.length >= 3) {
                        puntuation += toEliminate.length;
                        eliminateGems(toEliminate);
                    } else {
                        toEliminate = new int[][]{{j, columns - 1 - j}};
                        validateColor = board[j][columns - 1 - j][0];
                    }
                }
            }

            // Verificar puntaje para la última posición en la diagonal secundaria
            if (toEliminate.length >= 3) {
                puntuation += toEliminate.length;
                eliminateGems(toEliminate);
            }
        }
        dropJewel();
        return puntuation;
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

//    private void validateAndRemoveConsecutive(int row, int column) {
//        char currentGem = board[row][column][0];
//        int consecutiveCount = 1;
//
//        // Verificar horizontalmente hacia la derecha
//        consecutiveCount += countConsecutiveGems(row, column, 0, 1, currentGem);
//
//        // Verificar horizontalmente hacia la izquierda
//        consecutiveCount += countConsecutiveGems(row, column, 0, -1, currentGem);
//
//        // Verificar verticalmente hacia abajo
//        consecutiveCount += countConsecutiveGems(row, column, 1, 0, currentGem);
//
//        // Verificar verticalmente hacia arriba
//        consecutiveCount += countConsecutiveGems(row, column, -1, 0, currentGem);
//
//        // Verificar en diagonal hacia abajo y a la derecha
//        consecutiveCount += countConsecutiveGems(row, column, 1, 1, currentGem);
//
//        // Verificar en diagonal hacia arriba y a la izquierda
//        consecutiveCount += countConsecutiveGems(row, column, -1, -1, currentGem);
//
//        // Verificar en diagonal hacia arriba y a la derecha
//        consecutiveCount += countConsecutiveGems(row, column, -1, 1, currentGem);
//
//        // Verificar en diagonal hacia abajo y a la izquierda
//        consecutiveCount += countConsecutiveGems(row, column, 1, -1, currentGem);
//
//        // Si hay 3 o más gemas consecutivas, eliminarlas
//        if (consecutiveCount >= 3) {
//            removeConsecutiveGems(row, column);
//        }
//    }
//    private void removeConsecutiveGems(int startRow, int startColumn) {
//        char targetGem = board[startRow][startColumn][0];
//
//        // Eliminar gemas consecutivas en la misma fila
//        for (int j = startColumn; j < board[0].length && board[startRow][j][0] == targetGem; j++) {
//            board[startRow][j][0] = 't'; // Cambiar el valor de la gema por 'n'
//            board[startRow][j][1] = 'n'; // Cambiar el valor del fondo por 'n'
//        }
//
//        // Eliminar gemas consecutivas en la misma columna
//        for (int i = startRow; i < board.length && board[i][startColumn][0] == targetGem; i++) {
//            board[i][startColumn][0] = 't'; // Cambiar el valor de la gema por 'n'
//            board[i][startColumn][1] = 'n'; // Cambiar el valor del fondo por 'n'
//        }
//
//        // Hacer que las gemas caigan desde arriba en la columna
//        dropJewel(startColumn);
//    }

    private void dropJewel() {
//        for (int column = 0; column < board[0].length; column++){
//            for (int row = board.length-1; row >= 0 ; row--){
//                if ()
//            }
//        }
//
//        // Generar nueva gema en la parte superior de la columna
//        Random random = new Random();
//        String possibleChars = "rbavolm";
//        int randomIndex = random.nextInt(possibleChars.length());
//        board[0][column][0] = possibleChars.charAt(randomIndex); // Nueva gema
//        board[0][column][1] = 'c'; // Nuevo fondo
//    }
}

    private int countConsecutiveGems(int startRow, int startColumn, int rowDirection, int columnDirection, char targetGem) {
        int count = 0;
        int currentRow = startRow + rowDirection;
        int currentColumn = startColumn + columnDirection;

        while (isValidPosition(currentRow, currentColumn) && board[currentRow][currentColumn][0] == targetGem) {
            count++;
            currentRow += rowDirection;
            currentColumn += columnDirection;
        }

        return count;
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


