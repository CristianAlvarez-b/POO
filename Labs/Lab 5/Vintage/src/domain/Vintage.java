package domain;
import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Vintage {

    private char[][][] board;
    private char[] jewels;

    public Vintage(int row, int column) {
        board = new char[row][column][2];
        fillBoardWithRandomChars();
        jewels = new char[0];

    }
    public void play(int row1, int column1, int row2, int column2) throws VintageException {
        if (isValidPosition(row1, column1) && isValidPosition(row2, column2)) {
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
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                validateAndRemoveConsecutive(i, j);
            }
        }
    }
    private void validateAndRemoveConsecutive(int row, int column) {
        char currentGem = board[row][column][0];
        int consecutiveCount = 1;

        // Verificar horizontalmente hacia la derecha
        consecutiveCount += countConsecutiveGems(row, column, 0, 1, currentGem);

        // Verificar horizontalmente hacia la izquierda
        consecutiveCount += countConsecutiveGems(row, column, 0, -1, currentGem);

        // Verificar verticalmente hacia abajo
        consecutiveCount += countConsecutiveGems(row, column, 1, 0, currentGem);

        // Verificar verticalmente hacia arriba
        consecutiveCount += countConsecutiveGems(row, column, -1, 0, currentGem);

        // Verificar en diagonal hacia abajo y a la derecha
        consecutiveCount += countConsecutiveGems(row, column, 1, 1, currentGem);

        // Verificar en diagonal hacia arriba y a la izquierda
        consecutiveCount += countConsecutiveGems(row, column, -1, -1, currentGem);

        // Verificar en diagonal hacia arriba y a la derecha
        consecutiveCount += countConsecutiveGems(row, column, -1, 1, currentGem);

        // Verificar en diagonal hacia abajo y a la izquierda
        consecutiveCount += countConsecutiveGems(row, column, 1, -1, currentGem);

        // Si hay 3 o más gemas consecutivas, eliminarlas
        if (consecutiveCount >= 3) {
            removeConsecutiveGems(row, column);
        }
    }
    private void removeConsecutiveGems(int startRow, int startColumn) {
        char targetGem = board[startRow][startColumn][0];

        // Eliminar gemas consecutivas en la misma fila
        for (int j = startColumn; j < board[0].length && board[startRow][j][0] == targetGem; j++) {
            board[startRow][j][0] = 't'; // Cambiar el valor de la gema por 'n'
            board[startRow][j][1] = 'n'; // Cambiar el valor del fondo por 'n'
        }

        // Eliminar gemas consecutivas en la misma columna
        for (int i = startRow; i < board.length && board[i][startColumn][0] == targetGem; i++) {
            board[i][startColumn][0] = 't'; // Cambiar el valor de la gema por 'n'
            board[i][startColumn][1] = 'n'; // Cambiar el valor del fondo por 'n'
        }

        // Hacer que las gemas caigan desde arriba en la columna
        dropJewel(startColumn);
    }

    private void dropJewel(int column) {
        // Mover las gemas hacia abajo en la columna
        for (int i = board.length - 1; i > 0; i--) {
            board[i][column][0] = board[i - 1][column][0];
            board[i][column][1] = board[i - 1][column][1];
        }

        // Generar nueva gema en la parte superior de la columna
        Random random = new Random();
        String possibleChars = "rbavolm";
        int randomIndex = random.nextInt(possibleChars.length());
        board[0][column][0] = possibleChars.charAt(randomIndex); // Nueva gema
        board[0][column][1] = 'c'; // Nuevo fondo
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


