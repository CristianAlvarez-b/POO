package domain;
/**
 * Clase abstracta que representa a una máquina (jugador controlado por la inteligencia artificial) en el juego Gomoku.
 */
public abstract class Machine extends Player{
    /**
     * Método abstracto que define la lógica para que la máquina realice una jugada en el tablero.
     *
     * @throws Exception Posible excepción durante la jugada.
     */
    public abstract void play() throws Exception;
    /**
     * Método que permite a la máquina realizar una jugada en una posición específica del tablero.
     *
     * @param row    Fila en la que realizar la jugada.
     * @param column Columna en la que realizar la jugada.
     * @param stone  Piedra que se va a jugar.
     * @throws Exception Posible excepción durante la jugada.
     */
    public final void play(int row, int column, Stone stone) throws Exception {
        punctuation += board.addStone(row, column, stone);
        Stone myStone = super.eliminateStone(remainingStones, stone.getClass());
        if (myStone.getClass() != Stone.class){
            punctuation += 100; //Si se usa una piedra especial
        }
        if(punctuation >= 1000){
            super.addRandomStone();
            punctuation -= 1000;
        }
    }
}
