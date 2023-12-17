package domain;
import javax.swing.Timer;
import java.awt.*;
import java.io.Serializable;
import java.util.*;
import java.util.List;

public class Board implements Serializable {
    private final int[] dimension;
    protected Cell[][] cells;
    protected Player[] players;
    protected boolean turn;
    protected Timer timer;
    protected int segundosTranscurridos;
    private int specialPercentage;
    private boolean flag = false;

    /**
     * Constructor de la clase Board.
     *
     * @param rows               Número de filas del tablero.
     * @param columns            Número de columnas del tablero.
     * @param specialPercentage Porcentaje de celdas especiales en el tablero.
     * @throws Exception Posible excepción durante la creación del tablero.
     */
    public Board(int rows, int columns, int specialPercentage) throws Exception {
        turn = true;
        this.specialPercentage = specialPercentage;
        dimension = new int[]{rows, columns};
        fillCells();
        Class<? extends Cell>[] clasesEspeciales = new Class[]{Mine.class, Golden.class, Teleport.class};
        placeSpecialCells(clasesEspeciales,specialPercentage);
        this.segundosTranscurridos = 0;
        starTimer();
    }
    /**
     * Inicia el temporizador del juego.
     */
    public void starTimer(){
        this.timer = new Timer(1000, e -> segundosTranscurridos++);
        this.timer.start();
    }
    /**
     * Establece los jugadores que participarán en el juego.
     *
     * @param players Arreglo de jugadores.
     */
    public void setPlayers(Player[] players) {
        this.players = players;
    }
    /**
     * Llena la matriz de celdas con celdas normales.
     */
    private void fillCells(){
        cells = new Cell[dimension[0]][dimension[1]];

        // Llenar la matriz con celdas normales
        for (int i = 0; i < dimension[0]; i++) {
            for (int j = 0; j < dimension[1]; j++) {
                cells[i][j] = new Cell(this, new int[]{i,j});
            }
        }
    }
    /**
     * Coloca celdas especiales en el tablero según un porcentaje dado.
     *
     * @param specialCellClasses   Clases de celdas especiales disponibles.
     * @param specialCellPercentage Porcentaje de celdas especiales en el tablero.
     * @throws Exception Posible excepción durante la colocación de celdas especiales.
     */
    protected final void placeSpecialCells(Class<? extends Cell>[] specialCellClasses, int specialCellPercentage) throws Exception {
        int totalSpecialCells = dimension[0] * dimension[1] * specialCellPercentage / 100;

        Random random = new Random();

        for (int k = 0; k < totalSpecialCells; k++) {
            int i, j;

            // Buscar una posición aleatoria que no esté ocupada por una celda especial
            do {
                i = random.nextInt(dimension[0]);
                j = random.nextInt(dimension[1]);
            } while (isSpecialCell(cells[i][j], specialCellClasses));

            // Colocar la celda especial en la posición aleatoria
            int randomIndex = random.nextInt(specialCellClasses.length);
            Class<? extends Cell> selectedSpecialCellClass = specialCellClasses[randomIndex];
            cells[i][j] = selectedSpecialCellClass.getDeclaredConstructor(Board.class, int[].class).newInstance(this, new int[]{i, j});
        }
    }
    /**
     * Verifica si una celda es una celda especial.
     *
     * @param cell                Celda a verificar.
     * @param specialCellClasses Clases de celdas especiales disponibles.
     * @return true si la celda es especial, false en caso contrario.
     */
    private boolean isSpecialCell(Cell cell, Class<? extends Cell>[] specialCellClasses) {
        for (Class<? extends Cell> specialCellClass : specialCellClasses) {
            if (specialCellClass.isInstance(cell)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Agrega una piedra a una posición específica en el tablero.
     *
     * @param row    Fila de la posición.
     * @param column Columna de la posición.
     * @param stone  Piedra a agregar.
     * @return Puntuación acumulada después de agregar la piedra.
     * @throws Exception Posible excepción durante la adición de la piedra.
     */
    public final int addStone(int row, int column, Stone stone) throws Exception {
        int punctuation = 0;
        if (isValidPosition(row, column)) {
            Cell cell = cells[row][column];
            if (!cellHasStone(cell)) {
                cell.setStone(stone);
                punctuation += cell.updateState(turn);
                updateStateForAllCells(); // Actualizar el estado del tablero después de agregar la piedra
            } else {
                throw new GomokuException(GomokuException.STONE_OVERLOAP);
            }
        } else {
            throw new GomokuException(GomokuException.OUT_OF_BOARD);
        }
        turn = !turn;
        return punctuation;
    }
    /**
     * Actualiza el estado de todas las celdas en el tablero.
     * Este método invoca el método `updateState` de cada celda en el tablero.
     *
     * @throws Exception Posible excepción durante la actualización del estado.
     */
    public final void updateStateForAllCells() throws Exception {
        for (int i = 0; i < dimension[0]; i++) {
            for (int j = 0; j < dimension[1]; j++) {
                Cell cell = cells[i][j];
                cell.updateState(turn);
            }
        }
    }
    /**
     * Verifica si una posición dada es válida en el tablero.
     *
     * @param row    Fila de la posición.
     * @param column Columna de la posición.
     * @return true si la posición es válida, false en caso contrario.
     * @throws GomokuException Si la posición es negativa.
     */
    private boolean isValidPosition(int row, int column) throws GomokuException{
        if(row < 0 || column < 0){
            throw new GomokuException(GomokuException.NEGATIVE_POSITION);
        }
        return row < dimension[0] && column < dimension[1];
    }
    /**
     * Verifica si una celda tiene una piedra.
     *
     * @param cell Celda a verificar.
     * @return true si la celda tiene una piedra, false en caso contrario.
     */
    public boolean cellHasStone(Cell cell) {
        return cell.getStone() != null;
    }
    /**
     * Verifica si el tablero está completamente lleno, es decir, si todas las celdas tienen una piedra.
     *
     * @return true si el tablero está lleno, false en caso contrario.
     */
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
    /**
     * Obtiene la matriz de celdas del tablero.
     *
     * @return Matriz de celdas del tablero.
     */
    public final Cell[][] getCells() {
        return cells;
    }
    /**
     * Obtiene las dimensiones del tablero (número de filas y columnas).
     *
     * @return Arreglo de dos elementos que representa las dimensiones del tablero.
     */
    public final int[] getDimension() {
        return dimension;
    }
    /**
     * Obtiene las posiciones de las celdas adyacentes a una posición dada en el tablero.
     *
     * @param row    Fila de la posición.
     * @param column Columna de la posición.
     * @return Lista de arreglos de dos elementos que representan las posiciones de las celdas adyacentes.
     */
    public final List<int[]> getAdjacentCellPositions(int row, int column) {
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
    /**
     * Obtiene el estado actual del turno en el tablero.
     *
     * @return true si es el turno del primer jugador, false si es el turno del segundo jugador.
     */
    public final boolean getTurn(){return turn;}
    /**
     * Establece el estado del turno en el tablero.
     *
     * @param turn true si es el turno del primer jugador, false si es el turno del segundo jugador.
     */
    public void setTurn(boolean turn) {
        this.turn = turn;
    }
    /**
     * Verifica el estado del juego, incluida la condición de tablero lleno.
     *
     * @param turn true si es el turno del primer jugador, false si es el turno del segundo jugador.
     * @return true si hay un ganador, false si no hay ganador o el juego aún no ha terminado.
     * @throws GomokuException Si el tablero está completamente lleno.
     */
    public boolean verifyGame(boolean turn) throws GomokuException {
        // Verificar si el tablero esta lleno
        if(isBoardFull()){
            throw new GomokuException(GomokuException.FULL_BOARD);
        }
        //Verificar si se gano en alguna direccion
        return checkRows(turn) || checkColumns(turn) || checkDiagonalLeftToRight(turn) || checkDiagonalRightToLeft(turn);
    }
    /**
     * Verifica la presencia de secuencias ganadoras en las filas del tablero.
     *
     * @param turn true si es el turno del primer jugador, false si es el turno del segundo jugador.
     * @return true si hay una secuencia ganadora en las filas, false si no la hay.
     */
    public boolean checkRows(boolean turn) {
        int columns = cells[0].length;
        Color playerColor = turn ? players[0].getColor() : players[1].getColor();
        if(playerColor.equals(players[0].getColor())){
            playerColor = Color.BLACK;
        }else{
            playerColor = Color.WHITE;
        }

        // Iterar sobre las filas del tablero
        for (Cell[] cell : cells) {
            int stoneCount = 0;
            // Iterar sobre las columnas de la fila actual
            for (int column = 0; column < columns; column++) {
                stoneCount = updateStoneCount(stoneCount, cell[column], playerColor);
                // Verificar si el jugador actual ha alcanzado 5 piedras consecutivas
                if (stoneCount == 5) {
                    return true;  // El jugador actual ha ganado
                }
            }
        }
        return false;  // No se encontraron secuencias ganadoras en las filas
    }
    /**
     * Verifica la presencia de secuencias ganadoras en las columnas del tablero.
     *
     * @param turn true si es el turno del primer jugador, false si es el turno del segundo jugador.
     * @return true si hay una secuencia ganadora en las columnas, false si no la hay.
     */
    public boolean checkColumns(boolean turn) {
        int columns = cells[0].length;
        Color playerColor = turn ? players[0].getColor() : players[1].getColor();
        if(playerColor.equals(players[0].getColor())){
            playerColor = Color.BLACK;
        }else{
            playerColor = Color.WHITE;
        }

        for (int column = 0; column < columns; column++) {
            int stoneCount = 0;

            for (Cell[] cell : cells) {
                stoneCount = updateStoneCount(stoneCount, cell[column], playerColor);

                if (stoneCount == 5) {
                    return true;  // El jugador actual ha ganado
                }
            }
        }
        return false;
    }

    /**
     * Verifica la presencia de secuencias ganadoras en las diagonales de izquierda a derecha del tablero.
     *
     * @param turn true si es el turno del primer jugador, false si es el turno del segundo jugador.
     * @return true si hay una secuencia ganadora en las diagonales de izquierda a derecha, false si no la hay.
     */
    private boolean checkDiagonalLeftToRight(boolean turn) {
        int rows = cells.length;
        int columns = cells[0].length;
        Color playerColor = turn ? players[0].getColor() : players[1].getColor();
        if(playerColor.equals(players[0].getColor())){
            playerColor = Color.BLACK;
        }else{
            playerColor = Color.WHITE;
        }

        for (int k = 0; k < rows + columns - 1; k++) {
            int startRow = Math.max(0, k - columns + 1);
            int count = Math.min(k + 1, Math.min(rows, columns - startRow));
            int stoneCount = 0;

            for (int j = 0; j < count; j++) {
                int row = startRow + j;
                int col = Math.min(columns - 1, k - j);

                stoneCount = updateStoneCount(stoneCount, cells[row][col], playerColor);

                if (stoneCount == 5) {
                    return true;  // El jugador actual ha ganado
                }
            }
        }
        return false;
    }
    /**
     * Verifica la presencia de secuencias ganadoras en las diagonales de derecha a izquierda del tablero.
     *
     * @param turn true si es el turno del primer jugador, false si es el turno del segundo jugador.
     * @return true si hay una secuencia ganadora en las diagonales de derecha a izquierda, false si no la hay.
     */
    private boolean checkDiagonalRightToLeft(boolean turn) {
        int rows = cells.length;
        int columns = cells[0].length;
        Color playerColor = turn ? players[0].getColor() : players[1].getColor();
        if(playerColor.equals(players[0].getColor())){
            playerColor = Color.BLACK;
        }else{
            playerColor = Color.WHITE;
        }

        for (int k = 0; k < rows + columns - 1; k++) {
            int startRow = Math.max(0, k - columns + 1);
            int count = Math.min(k + 1, Math.min(rows, columns - startRow));
            int stoneCount = 0;

            for (int j = 0; j < count; j++) {
                int row = startRow + j;
                int col = columns - 1 - (k - j);

                if (row >= 0 && row < rows && col >= 0 && col < columns) {
                    stoneCount = updateStoneCount(stoneCount, cells[row][col], playerColor);

                    if (stoneCount == 5) {
                        return true;  // El jugador actual ha ganado
                    }
                }
            }
        }
        return false;
    }
    /**
     * Maneja la adición de piedras extra al jugador actual.
     *
     * @param currentPlayer       El jugador actual que recibe la piedra extra.
     * @param selectedStoneArray  Un array que almacena la piedra seleccionada.
     * @return La piedra extra que se ha manejado.
     * @throws Exception Si hay un problema al manejar las piedras extra.
     */
    public Stone handleExtraStones(Player currentPlayer, Stone[] selectedStoneArray) throws Exception {
        Stone extraStone = currentPlayer.getExtraStones().get(0);
        currentPlayer.getRemainingStones().add(0, extraStone);
        if (extraStone.getClass() == Stone.class) {
            selectedStoneArray[0] = Player.getFirstStoneOfType(currentPlayer.getExtraStones(), extraStone.getClass());
            currentPlayer.eliminateStone(currentPlayer.getExtraStones(), extraStone.getClass());
            flag = true;
            return selectedStoneArray[0];
        }
        if (selectedStoneArray[0] == null) {
            handleRemainingStones(currentPlayer, selectedStoneArray);
        }
        currentPlayer.eliminateStone(currentPlayer.getExtraStones(), extraStone.getClass());
        return selectedStoneArray[0];
    }
    /**
     * Maneja la selección de piedras restantes para el jugador actual.
     *
     * @param currentPlayer       El jugador actual que selecciona una piedra restante.
     * @param selectedStoneArray  Un array que almacena la piedra seleccionada.
     * @return La piedra seleccionada que se ha manejado.
     */
    public Stone handleRemainingStones(Player currentPlayer, Stone[] selectedStoneArray){
        if (selectedStoneArray[0] == null) {
            selectedStoneArray[0] = Player.getFirstStoneOfType(currentPlayer.getRemainingStones(), currentPlayer.getRemainingStones().get(currentPlayer.getRemainingStones().size() - 1).getClass());
        }
        if(flag){
            selectedStoneArray[0] = Player.getFirstStoneOfType(currentPlayer.getRemainingStones(), Stone.class);
            flag = false;
        }
        return selectedStoneArray[0];
    }
    /**
     * Actualiza el conteo de piedras consecutivas en una dirección específica.
     *
     * @param stoneCount El conteo actual de piedras consecutivas.
     * @param cell       La celda actual que se está evaluando.
     * @param playerColor El color del jugador actual.
     * @return El nuevo conteo de piedras consecutivas después de evaluar la celda actual.
     */
    private int updateStoneCount(int stoneCount, Cell cell, Color playerColor) {
        if (cell.getStone() != null) {
            return playerColor.equals(cell.getStone().getColor()) ? stoneCount + cell.getStone().getValue() : 0;
        } else {
            return 0;
        }
    }
    /**
     * Obtiene el tiempo actual transcurrido en el juego en formato de cadena.
     *
     * @return Cadena que representa el tiempo transcurrido en el formato "TIEMPO: MM:SS".
     * @throws GomokuException Si ocurre una excepción relacionada con el juego Gomoku.
     */
    public String obtenerTiempoActual() throws GomokuException{
        int minutos = segundosTranscurridos / 60;
        int segundosRestantes = segundosTranscurridos % 60;
        return ("TIEMPO: " + String.format("%02d:%02d", minutos, segundosRestantes));
    }
    /**
     * Establece una celda en una posición específica del tablero.
     *
     * @param row  Indice de fila de la celda.
     * @param col  Indice de columna de la celda.
     * @param cell La celda que se establecerá en la posición especificada.
     */
    public void setCell(int row, int col, Cell cell) {
        this.cells[row][col] = cell;
    }
    /**
     * Verifica si se ha activado la bandera en el tablero.
     *
     * @return true si la bandera está activada, false si no lo está.
     */
    public boolean isFlag() {
        return flag;
    }
    /**
     * Obtiene el porcentaje de celdas especiales en el tablero.
     *
     * @return El porcentaje de celdas especiales en el tablero.
     */
    public int getSpecialPercentage() {
        return specialPercentage;
    }
    /**
     * Obtiene la lista de jugadores asociados al tablero.
     *
     * @return La lista de jugadores asociados al tablero.
     */
    public final Player[] getPlayers() {
        return players;
    }

}
