package domain;
import java.util.ArrayList;
import java.awt.*;
import java.util.Random;
import java.util.List;

/**
 * Clase que representa una implementación específica de la inteligencia artificial para el juego Gomoku.
 * Extiende la clase Machine e implementa estrategias avanzadas de juego.
 */
public class Expert extends Machine{
    /**
     * Constructor de la clase Expert.
     *
     * @param color                    Color asignado al jugador experto.
     * @param board                    Tablero de juego asociado al experto.
     * @param specialStonesPercentage Porcentaje de piedras especiales en el tablero.
     * @param stoneLimit               Límite de piedras para el jugador.
     */
    public Expert(Color color, Board board, int specialStonesPercentage, int stoneLimit) {
        this.canRefill = true;
        this.color = color;
        this.board = board;
        extraStones = new ArrayList<>();
        remainingStones = new ArrayList<>();
        refillStones(stoneLimit, specialStonesPercentage);
        punctuation = 0;
    }

    /**
     * Método que implementa la estrategia de juego para el jugador experto.
     *
     * @throws Exception Si ocurre una excepción relacionada con el juego Gomoku.
     */
    @Override
    public void play() throws Exception {
        int[] winningPosition = findWinningPosition(color);

        // Prioriza la jugada ganadora si es posible
        if (winningPosition != null) {
            Random random = new Random();
            int randomIndex = random.nextInt(remainingStones.size());
            Stone selectedStone = remainingStones.isEmpty() ? new Stone(color) : remainingStones.get(randomIndex);
            super.play(winningPosition[0], winningPosition[1], selectedStone);
        } else {
            int[] bestAdjacentMove = findBestAdjacentMove(color);

            // Si hay una jugada adyacente que continúa la secuencia más larga, juega esa posición
            if (bestAdjacentMove != null) {
                Random random = new Random();
                int randomIndex = random.nextInt(remainingStones.size());
                Stone selectedStone = remainingStones.isEmpty() ? new Stone(color) : remainingStones.get(randomIndex);
                super.play(bestAdjacentMove[0], bestAdjacentMove[1], selectedStone);
            } else {
                // Si no se encontró una piedra propia o no hay jugada adyacente posible, realiza una jugada aleatoria
                playRandomMove();
            }
        }
    }

    /**
     * Verifica si una celda contiene una piedra propia.
     *
     * @param cell La celda a verificar.
     * @return true si la celda contiene una piedra propia, false de lo contrario.
     */
    private boolean cellHasOwnStone(Cell cell) {
        return cell.getStone() != null && cell.getStone().getColor().equals(Color.WHITE);
    }
    /**
     * Encuentra la mejor jugada adyacente que continúa la secuencia más larga.
     *
     * @param playerColor El color del jugador actual.
     * @return Las coordenadas de la mejor jugada adyacente, o null si no se encuentra ninguna.
     * @throws GomokuException Si ocurre una excepción relacionada con el juego Gomoku.
     */
    private int[] findBestAdjacentMove(Color playerColor) throws GomokuException {
        int[] bestMove = null;
        int maxLength = 0;

        // Recorre todas las celdas del tablero
        for (int i = 0; i < board.getDimension()[0]; i++) {
            for (int j = 0; j < board.getDimension()[1]; j++) {
                Cell cell = board.getCells()[i][j];

                // Busca una piedra propia en la celda actual
                if (cellHasOwnStone(cell)) {
                    // Busca la posición adyacente que continúa la secuencia más larga
                    List<int[]> adjacentPositions = board.getAdjacentCellPositions(i, j);

                    for (int[] position : adjacentPositions) {
                        int row = position[0];
                        int column = position[1];

                        if (isValidPosition(row, column) && !board.cellHasStone(board.getCells()[row][column])) {
                            int length = calculateMaxLength(row, column, playerColor);

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
     * Realiza una jugada aleatoria.
     *
     * @throws Exception Si no se encuentra una posición válida después de 100 intentos.
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
                    Random random2 = new Random();
                    int randomIndex = random2.nextInt(remainingStones.size());
                    Stone selectedStone = remainingStones.isEmpty() ? new Stone(color) : remainingStones.get(randomIndex);
                    super.play(row, column, selectedStone);
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
     * Calcula la longitud máxima de la secuencia en una dirección específica desde una posición dada.
     *
     * @param row         Fila de la posición inicial.
     * @param column      Columna de la posición inicial.
     * @param playerColor Color del jugador actual.
     * @return La longitud máxima de la secuencia en la dirección especificada.
     * @throws GomokuException Si ocurre una excepción relacionada con el juego Gomoku.
     */
    private int calculateMaxLength(int row, int column, Color playerColor) throws GomokuException {
        int maxLength = 0;

        // Verifica la longitud máxima en la fila horizontal
        int horizontalLength = calculateLengthInDirection(row, column, playerColor, 0, 1)
                + calculateLengthInDirection(row, column, playerColor, 0, -1);

        // Verifica la longitud máxima en la columna vertical
        int verticalLength = calculateLengthInDirection(row, column, playerColor, 1, 0)
                + calculateLengthInDirection(row, column, playerColor, -1, 0);

        // Actualiza la longitud máxima
        maxLength = Math.max(maxLength, horizontalLength);
        maxLength = Math.max(maxLength, verticalLength);

        return maxLength;
    }
    /**
     * Calcula la longitud de la secuencia en una dirección específica desde una posición dada.
     *
     * @param row           Fila de la posición inicial.
     * @param column        Columna de la posición inicial.
     * @param playerColor   Color del jugador actual.
     * @param rowDirection  Dirección en la fila.
     * @param colDirection  Dirección en la columna.
     * @return La longitud de la secuencia en la dirección especificada.
     * @throws GomokuException Si ocurre una excepción relacionada con el juego Gomoku.
     */
    private int calculateLengthInDirection(int row, int column, Color playerColor, int rowDirection, int colDirection) throws GomokuException {
        Cell[][] cells = board.getCells();
        int length = 0;

        int currentRow = row + rowDirection;
        int currentCol = column + colDirection;

        while (isValidPosition(currentRow, currentCol) &&
                board.cellHasStone(cells[currentRow][currentCol]) &&
                cells[currentRow][currentCol].getStone().getColor().equals(Color.WHITE)) {
            length++;
            currentRow += rowDirection;
            currentCol += colDirection;
        }

        return length;
    }
    /**
     * Verifica si una posición es válida en el tablero.
     *
     * @param row    Fila de la posición.
     * @param column Columna de la posición.
     * @return true si la posición es válida, false de lo contrario.
     */
    private boolean isValidPosition(int row, int column){
        return row >= 0 && column >= 0 && row < board.getDimension()[0] && column < board.getDimension()[1];
    }

    /**
     * Encuentra una posición ganadora para el jugador actual.
     *
     * @param playerColor Color del jugador actual.
     * @return Las coordenadas de la posición ganadora, o null si no se encuentra ninguna.
     */
    private int[] findWinningPosition(Color playerColor){
        Cell[][] cells = board.getCells();
        int rows = cells.length;
        int columns = cells[0].length;

        // Itera sobre todas las celdas para buscar una posición ganadora
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                // Verifica si la posición actual es ganadora para el jugador actual
                if (isValidPosition(i, j) && !board.cellHasStone(cells[i][j]) && canWin(i, j, playerColor)) {
                    return new int[]{i, j};
                }
            }
        }

        return null;  // No se encontró una posición ganadora
    }

    /**
     * Verifica si una posición puede llevar a la victoria del jugador actual.
     *
     * @param row         Fila de la posición.
     * @param column      Columna de la posición.
     * @param playerColor Color del jugador actual.
     * @return true si la posición puede llevar a la victoria, false de lo contrario.
     */
    private boolean canWin(int row, int column, Color playerColor) {
        int consecutiveStones = 0;
        for (int j = 0; j < board.getDimension()[1]; j++) {
            if (board.cellHasStone(board.getCells()[row][column]) && board.getCells()[row][j].getStone().getColor().equals(playerColor)) {
                consecutiveStones++;
            } else {
                consecutiveStones = 0;
            }

            if (consecutiveStones == 5) {
                return true;
            }
        }
        return false;  // No hay una secuencia ganadora en la posición actual
    }
}
