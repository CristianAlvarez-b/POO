package domain;

/**
 * Clase que representa un tipo de tablero con piedras limitadas en el juego Gomoku.
 * Extiende la clase Board.
 */
public class LimitedStones extends Board{
    /**
     * Constructor de la clase LimitedStones.
     *
     * @param rows    Número de filas del tablero.
     * @param columns Número de columnas del tablero.
     * @throws Exception Posible excepción durante la creación del tablero.
     */
    public LimitedStones(int rows, int columns) throws Exception {
        super(rows, columns, 0);
    }
    /**
     * Método para establecer los jugadores en el tablero con piedras limitadas.
     * Desactiva la capacidad de recarga de piedras para ambos jugadores.
     *
     * @param players Arreglo de jugadores.
     */
    @Override
    public void setPlayers(Player[] players) {
        this.players = players;
        players[0].setCanRefill(false);
        players[1].setCanRefill(false);
    }
    /**
     * Método para verificar el estado del juego en un tablero con piedras limitadas.
     *
     * @param turn Turno actual.
     * @return true si el juego ha terminado, false de lo contrario.
     * @throws GomokuException Excepción específica del juego Gomoku.
     */
    @Override
    public boolean verifyGame(boolean turn) throws GomokuException {
        boolean gameOver = super.verifyGame(turn);
        if(!gameOver){
            if(players[0].getRemainingStones().isEmpty() && players[1].getRemainingStones().isEmpty()){
                throw new GomokuException(GomokuException.DRAW);
            }
        }
        return gameOver;
    }
}
