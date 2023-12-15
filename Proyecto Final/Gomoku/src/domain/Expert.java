package domain;
import java.util.ArrayList;
import java.awt.*;
import java.util.Random;
import java.util.List;


public class Expert extends Machine{
    public Expert(Color color, Board board, int specialStonesPercentage, int stoneLimit) {
        this.canRefill = true;
        this.color = color;
        this.board = board;
        extraStones = new ArrayList<>();
        remainingStones = new ArrayList<>();
        refillStones(stoneLimit, specialStonesPercentage);
        punctuation = 0;
    }


    @Override
    public void play() throws Exception {
        int[] winningPosition = findWinningPosition(color);

        // Prioriza la jugada ganadora si es posible
        if (winningPosition != null) {
            Random random = new Random();
            int randomIndex = random.nextInt(remainingStones.size());
            Stone selectedStone = remainingStones.isEmpty() ? new Stone(color) : remainingStones.get(randomIndex);
            play(winningPosition[0], winningPosition[1], selectedStone);
        } else {
            int[] bestAdjacentMove = findBestAdjacentMove(color);

            // Si hay una jugada adyacente que continúa la secuencia más larga, juega esa posición
            if (bestAdjacentMove != null) {
                Random random = new Random();
                int randomIndex = random.nextInt(remainingStones.size());
                Stone selectedStone = remainingStones.isEmpty() ? new Stone(color) : remainingStones.get(randomIndex);
                play(bestAdjacentMove[0], bestAdjacentMove[1], selectedStone);
            } else {
                // Si no se encontró una piedra propia, realiza una jugada aleatoria
                playRandomMove();
            }
        }
    }

    private boolean cellHasOwnStone(Cell cell) {
        return cell.getStone() != null && cell.getStone().getColor().equals(color);
    }

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
    private void playRandomMove() throws Exception {
        Random random = new Random();

        // Realiza hasta 100 intentos para encontrar una posición válida
        for (int attempt = 0; attempt < 100; attempt++) {
            int row = random.nextInt(board.getDimension()[0]);
            int column = random.nextInt(board.getDimension()[1]);

            try {
                // Verifica que la posición esté dentro de los límites y la celda esté vacía
                if (isValidPosition(row, column) && !board.cellHasStone(board.getCells()[row][column])) {
                    play(row, column, new Stone(color));
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
    private int calculateMaxLength(int row, int column, Color playerColor) throws GomokuException {
        Cell[][] cells = board.getCells();
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

    private int calculateLengthInDirection(int row, int column, Color playerColor, int rowDirection, int colDirection) throws GomokuException {
        Cell[][] cells = board.getCells();
        int length = 0;

        int currentRow = row + rowDirection;
        int currentCol = column + colDirection;

        while (isValidPosition(currentRow, currentCol) &&
                board.cellHasStone(cells[currentRow][currentCol]) &&
                cells[currentRow][currentCol].getStone().getColor().equals(playerColor)) {
            length++;
            currentRow += rowDirection;
            currentCol += colDirection;
        }

        return length;
    }
    private boolean isValidPosition(int row, int column) throws GomokuException {
        return row >= 0 && column >= 0 && row < board.getDimension()[0] && column < board.getDimension()[1];
    }
    private int[] findWinningPosition(Color playerColor) throws GomokuException {
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

        // Puedes agregar lógica similar para verificar columnas y diagonales según tu implementación específica.

        return false;  // No hay una secuencia ganadora en la posición actual
    }

    @Override
    public void play(int row, int column, Stone stone) throws Exception {
        Stone myStone = super.eliminateStone(remainingStones, stone.getClass());
        if (myStone.getClass() != Stone.class){
            punctuation += 100; //Si se usa una piedra especial
        }
        punctuation += board.addStone(row, column, myStone);
        if(punctuation >= 1000){
            super.addRandomStone();
            punctuation -= 1000;
        }
    }
}
