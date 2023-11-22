import java.util.*;

public class Board {
    private int[] dimension;
    private int turn;
    private Cell[][] cells;
    public Board(int rows, int columns){
        dimension = new int[]{rows, columns};
        turn = 0;
        fillCells();
    }

    private void fillCells() {
        cells = new Cell[dimension[0]][dimension[1]];

        // Llenar la matriz con celdas normales
        for (int i = 0; i < dimension[0]; i++) {
            for (int j = 0; j < dimension[1]; j++) {
                cells[i][j] = new Cell(this);
            }
        }

        // Calcular la cantidad total de celdas especiales
        int totalSpecialCells = (int) (dimension[0] * dimension[1] * 0.1); // El 10% de las celdas

        // Colocar celdas especiales de manera aleatoria
        placeSpecialCells(totalSpecialCells, Mine.class);
        placeSpecialCells(totalSpecialCells, Golden.class);
        placeSpecialCells(totalSpecialCells, Teleport.class);
    }

    private void placeSpecialCells(int totalSpecialCells, Class<? extends Cell> specialCellClass) {
        Random random = new Random();

        for (int k = 0; k < totalSpecialCells; k++) {
            int i, j;

            // Buscar una posición aleatoria que no esté ocupada por una celda especial
            do {
                i = random.nextInt(dimension[0]);
                j = random.nextInt(dimension[1]);
            } while (specialCellClass.isInstance(cells[i][j]));

            // Colocar la celda especial en la posición aleatoria
            try {
                cells[i][j] = specialCellClass.getDeclaredConstructor(Board.class).newInstance(this);
            } catch (Exception e) {
                e.printStackTrace(); // Manejar la excepción según tu lógica
            }
        }
    }

    public void addStone(int row, int column, Stone stone) throws GomokuException {
        if (!isBoardFull()) {
            if (isValidPosition(row, column)) {
                Cell cell = cells[row][column];
                if (!cellHasStone(cell)) {
                    cell.setStone(stone);
                } else {
                    throw new GomokuException(GomokuException.STONE_OVERLOAP);
                }
            } else {
                throw new GomokuException(GomokuException.OUT_OF_BOARD);
            }
        }else {
            throw new GomokuException(GomokuException.FULL_BOARD);
        }
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
}
