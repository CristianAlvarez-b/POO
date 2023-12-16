package domain;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Clase que representa un jugador automatizado agresivo en un juego de tablero.
 * Extiende la clase abstracta Machine.
 */
public class Agressive extends Machine {
    /**
     * Constructor de la clase Agressive.
     *
     * @param color                    Color de las piedras del jugador.
     * @param board                    Tablero en el que se juega.
     * @param specialStonesPercentage Porcentaje de piedras especiales en el conjunto de piedras.
     * @param stoneLimit               Límite de piedras que puede tener el jugador.
     */
    public Agressive(Color color, Board board, int specialStonesPercentage, int stoneLimit) {
        this.canRefill = true;
        this.color = color;
        this.board = board;
        extraStones = new ArrayList<>();
        remainingStones = new ArrayList<>();
        refillStones(stoneLimit, specialStonesPercentage);
        punctuation = 0;
    }
    /**
     * Método que realiza la jugada del jugador automatizado agresivo.
     *
     * @throws Exception Posible excepción durante el juego.
     */

    @Override
    public void play() throws Exception {

        int[] bestAdjacentMove = findBestAdjacentMove(color);
        // Si hay una jugada adyacente que continúa la secuencia más larga, juega esa posición
        if (bestAdjacentMove != null) {
            Random random = new Random();
            int randomIndex = random.nextInt(remainingStones.size());
            Stone selectedStone = remainingStones.isEmpty() ? new Stone(color) : remainingStones.get(randomIndex);
            super.play(bestAdjacentMove[0], bestAdjacentMove[1], selectedStone);
        } else {
            //Si no encuentra, juega random
            playRandomMove();
        }
    }
    /**
     * Método que verifica si una celda contiene una piedra del oponente.
     *
     * @param cell        Celda a verificar.
     * @param playerColor Color del jugador.
     * @return true si la celda contiene una piedra del oponente, false en caso contrario.
     */
    private boolean cellHasOpponentStone(Cell cell, Color playerColor) {
        return cell.getStone() != null && !cell.getStone().getColor().equals(playerColor);
    }
    /**
     * Método que realiza una jugada en una posición aleatoria válida.
     *
     * @throws Exception Posible excepción durante la jugada.
     */
    private void playRandomMove() throws Exception {
        Random random = new Random();

        // Realiza hasta 100 intentos para encontrar una posición válida
        for (int attempt = 0; attempt < 100; attempt++) {
            int row = random.nextInt(board.getDimension()[0]);
            int column = random.nextInt(board.getDimension()[1]);

            try {
                // Verifica que la posición esté dentro de los límites y la celda esté vacía
                if (isValidPosition(row, column) && !board.cellHasStone(board.getCells()[row][column])) {
                    super.play(row, column, new Stone(color));
                    // Si la jugada se realiza con éxito, la máquina ha realizado una jugada
                    return;
                }
            } catch (Exception ignored) {
                // Ignoramos excepciones que indiquen que la celda está ocupada o fuera de los límites
            }
        }
        // Si no se pudo realizar una jugada válida después de 100 intentos, lanza una excepción
        throw new Exception("No se pudo encontrar una posición válida para la jugada aleatoria.");
    }
    /**
     * Método que encuentra la mejor jugada adyacente que continúa la cadena rival más larga.
     *
     * @param playerColor Color del jugador.
     * @return Arreglo de dos elementos con las coordenadas [fila, columna] de la mejor jugada adyacente.
     * @throws GomokuException Posible excepción durante la búsqueda.
     */
    private int[] findBestAdjacentMove(Color playerColor) throws GomokuException {
        int[] bestMove = null;
        int maxLength = 0;

        // Recorre todas las celdas del tablero
        for (int i = 0; i < board.getDimension()[0]; i++) {
            for (int j = 0; j < board.getDimension()[1]; j++) {
                Cell cell = board.getCells()[i][j];

                // Busca una piedra rival en la celda actual
                if (cellHasOpponentStone(cell, playerColor)) {
                    // Busca la posición adyacente que continúa la secuencia más larga
                    List<int[]> adjacentPositions = board.getAdjacentCellPositions(i, j);

                    for (int[] position : adjacentPositions) {
                        int row = position[0];
                        int column = position[1];

                        if (isValidPosition(row, column) && !board.cellHasStone(board.getCells()[row][column])) {
                            int length = calculateMaxLength(row, column);
                            if (length > maxLength) {
                                maxLength = length;
                                bestMove = new int[]{row, column};
                            }
                        }
                    }
                }
            }
        }

        return bestMove;
    }
    /**
     * Calcula la longitud máxima de la cadena del jugador en la posición dada.
     *
     * @param row         Fila de la posición.
     * @param column      Columna de la posición.
     * @return Longitud máxima de la cadena del jugador en la posición dada.
     * @throws GomokuException Posible excepción durante el cálculo.
     */
    private int calculateMaxLength(int row, int column) throws GomokuException {
        int maxLength = 0;

        // Verifica la longitud máxima en la fila horizontal
        int horizontalLength = calculateLengthInDirection(row, column,  0, 1)
                + calculateLengthInDirection(row, column,  0, -1);

        // Verifica la longitud máxima en la columna vertical
        int verticalLength = calculateLengthInDirection(row, column,  1, 0)
                + calculateLengthInDirection(row, column,  -1, 0);

        // Actualiza la longitud máxima
        maxLength = Math.max(maxLength, horizontalLength);
        maxLength = Math.max(maxLength, verticalLength);

        return maxLength;
    }
    /**
     * Calcula la longitud en una dirección específica desde la posición dada.
     *
     * @param row          Fila de la posición.
     * @param column       Columna de la posición.
     * @param rowDirection Dirección en la fila (puede ser 1, 0, -1).
     * @param colDirection Dirección en la columna (puede ser 1, 0, -1).
     * @return Longitud en la dirección especificada desde la posición dada.
     * @throws GomokuException Posible excepción durante el cálculo.
     */
    private int calculateLengthInDirection(int row, int column, int rowDirection, int colDirection) throws GomokuException {
        Cell[][] cells = board.getCells();
        int length = 0;

        int currentRow = row + rowDirection;
        int currentCol = column + colDirection;

        while (isValidPosition(currentRow, currentCol) &&
                board.cellHasStone(cells[currentRow][currentCol]) &&
                cells[currentRow][currentCol].getStone().getColor().equals(Color.BLACK)) {
            length++;
            currentRow += rowDirection;
            currentCol += colDirection;
        }

        return length;
    }
    /**
     * Verifica si la posición dada está dentro de los límites del tablero.
     *
     * @param row    Fila de la posición.
     * @param column Columna de la posición.
     * @return true si la posición es válida, false en caso contrario.
     */
    private boolean isValidPosition(int row, int column){
        return row >= 0 && column >= 0 && row < board.getDimension()[0] && column < board.getDimension()[1];
    }
}
