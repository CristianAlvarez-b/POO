package domain;
import java.util.ArrayList;
import java.awt.*;
/**
 * Clase que representa a un jugador humano en el juego Gomoku.
 * Extiende la clase Player.
 */
public class Human extends Player{
    /**
     * Constructor de la clase Human.
     *
     * @param color                   Color del jugador.
     * @param board                   Tablero en el que juega el jugador.
     * @param specialStonesPercentage Porcentaje de piedras especiales.
     * @param stoneLimit              Límite de piedras que puede tener el jugador.
     */
    public Human(Color color, Board board, int specialStonesPercentage, int stoneLimit) {
        this.canRefill = true;
        this.color = color;
        this.board = board;
        extraStones = new ArrayList<>();
        remainingStones = new ArrayList<>();
        refillStones(stoneLimit, specialStonesPercentage);
        punctuation = 0;
    }
    /**
     * Método que permite a un jugador humano realizar una jugada.
     *
     * @param row    Fila en la que se coloca la piedra.
     * @param column Columna en la que se coloca la piedra.
     * @param stone  Piedra que se coloca en el tablero.
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
