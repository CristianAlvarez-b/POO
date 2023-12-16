package domain;
import java.awt.*;
import java.util.*;
import java.lang.*;
import java.util.List;

/**
 * Clase que representa una celda especial llamada "Golden" en el juego Gomoku.
 * Extiende la clase Cell y tiene la capacidad de generar una piedra aleatoria adicional al ser ocupada.
 */
public class Golden extends Cell{
    /**
     * Constructor de la clase Golden.
     *
     * @param board    Tablero de juego asociado a la celda dorada.
     * @param position Posición de la celda en el tablero.
     */
    public Golden(Board board, int[] position){
        super(board, position);
        active = true;
    }
    /**
     * Método que establece una piedra en la celda dorada.
     *
     * @param stone Piedra a establecer en la celda dorada.
     */
    public void setStone(Stone stone){
        super.setStone(stone);
    }
    /**
     * Método que actualiza el estado de la celda dorada al ser ocupada por una piedra.
     *
     * @param turn Indica el turno actual del jugador.
     * @return Valor entero (siempre 0 en este caso).
     * @throws Exception Posible excepción durante la actualización del estado.
     */
    @Override
    public int updateState(boolean turn) throws Exception {
        super.updateTemporaryStoneLife();
        if (stone != null && active){
            active = false;
            Stone newStone = getRandomStone(stone.getColor());
            if (Color.BLACK.equals(stone.getColor())){
                board.getPlayers()[0].addStone(board.getPlayers()[0].getExtraStones(),newStone);
            }else {
                board.getPlayers()[1].addStone(board.getPlayers()[1].getExtraStones(),newStone);
            }
        }
        return 0;
    }
    /**
     * Método que genera una piedra aleatoria entre las disponibles.
     *
     * @param color Color de la piedra a generar.
     * @return Piedra generada aleatoriamente.
     * @throws Exception Posible excepción durante la generación de la piedra.
     */
    private Stone getRandomStone(Color color) throws Exception {
        List<Class<? extends Stone>> stoneClasses = new ArrayList<>();
        stoneClasses.add(Stone.class);
        stoneClasses.add(Temporary.class);
        stoneClasses.add(Heavy.class);

        Random random = new Random();
        int randomIndex = random.nextInt(stoneClasses.size());
        Class<? extends Stone> selectedStoneClass = stoneClasses.get(randomIndex);

        return selectedStoneClass.getDeclaredConstructor(Color.class).newInstance(color);
    }
}
