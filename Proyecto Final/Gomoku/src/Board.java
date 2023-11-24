import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Board {
    private final int[] dimension;
    private Cell[][] cells;
    private Player[] players;
    public Board(int rows, int columns) throws Exception {
        dimension = new int[]{rows, columns};
        fillCells();
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    private void fillCells() throws Exception {
        cells = new Cell[dimension[0]][dimension[1]];

        // Llenar la matriz con celdas normales
        for (int i = 0; i < dimension[0]; i++) {
            for (int j = 0; j < dimension[1]; j++) {
                cells[i][j] = new Cell(this, new int[]{i,j});
            }
        }

        // Calcular la cantidad total de celdas especiales
        int totalSpecialCells = (int) (dimension[0] * dimension[1] * 0.1); // El 10% de las celdas

        // Colocar celdas especiales de manera aleatoria
        placeSpecialCells(totalSpecialCells, Mine.class);
        placeSpecialCells(totalSpecialCells, Golden.class);
        placeSpecialCells(totalSpecialCells, Teleport.class);
    }

    private void placeSpecialCells(int totalSpecialCells, Class<? extends Cell> specialCellClass) throws Exception{
        Random random = new Random();

        for (int k = 0; k < totalSpecialCells; k++) {
            int i, j;

            // Buscar una posición aleatoria que no esté ocupada por una celda especial
            do {
                i = random.nextInt(dimension[0]);
                j = random.nextInt(dimension[1]);
            } while (specialCellClass.isInstance(cells[i][j]));

            // Colocar la celda especial en la posición aleatoria
            cells[i][j] = specialCellClass.getDeclaredConstructor(Board.class, int[].class).newInstance(this, new int[]{i,j});
        }
    }

    public int addStone(int row, int column, Stone stone) throws GomokuException {
        int punctuation = 0;
        if (!isBoardFull()) {
            if (isValidPosition(row, column)) {
                Cell cell = cells[row][column];
                if (!cellHasStone(cell)) {
                    cell.setStone(stone);
                    punctuation = cell.updateState();
                } else {
                    throw new GomokuException(GomokuException.STONE_OVERLOAP);
                }
            } else {
                throw new GomokuException(GomokuException.OUT_OF_BOARD);
            }
        }else {
            throw new GomokuException(GomokuException.FULL_BOARD);
        }
        return punctuation;
    }

    private boolean isValidPosition(int row, int column) throws GomokuException{
        if(row < 0 || column < 0){
            throw new GomokuException(GomokuException.NEGATIVE_POSITION);
        }
        return row < dimension[0] && column < dimension[1];
    }

    private boolean cellHasStone(Cell cell) {
        return cell.getStone() != null;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < dimension[0]; i++) {
            for (int j = 0; j < dimension[1]; j++) {
                if (!cellHasStone(cells[i][j])) {
                    return false; // Si alguna celda no tiene piedra, el tablero no está lleno
                }
            }
        }
        return true; // Todas las celdas tienen piedra, el tablero está lleno
    }

    public Cell[][] getCells() {
        return cells;
    }

    public int[] getDimension() {
        return dimension;
    }

    public List<int[]> getAdjacentCellPositions(int row, int column) {
        List<int[]> positions = new ArrayList<>();

        // Definir los límites para iterar sobre las celdas adyacentes
        int startRow = Math.max(0, row - 1);
        int endRow = Math.min(dimension[0] - 1, row + 1);
        int startColumn = Math.max(0, column - 1);
        int endColumn = Math.min(dimension[1] - 1, column + 1);

        // Iterar sobre las celdas adyacentes y agregar sus posiciones a la lista
        for (int i = startRow; i <= endRow; i++) {
            for (int j = startColumn; j <= endColumn; j++) {
                // Ignorar la celda actual (la posición dada)
                if (i != row || j != column) {
                    positions.add(new int[]{i, j});
                }
            }
        }

        return positions;
    }

    public boolean verifyGame(){
        //En construccion
        return false;
    }

    public Player[] getPlayers() {
        return players;
    }
}
