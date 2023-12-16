package domain;
import java.io.Serializable;
import java.util.*;
import java.awt.*;

/**
 * Clase abstracta que representa a un jugador en el juego Gomoku.
 */
public abstract class Player implements Serializable {
    protected String name;
    protected ArrayList<Stone> remainingStones;
    protected boolean canRefill;
    protected Color color;
    protected ArrayList<Stone> extraStones;
    protected Board board;
    protected int punctuation;
    /**
     * Establece el nombre del jugador.
     *
     * @param name Nombre del jugador.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Obtiene el nombre del jugador.
     *
     * @return Nombre del jugador.
     */
    public String getName() {
        return name;
    }
    /**
     * Método abstracto que representa la jugada de un jugador en el juego.
     *
     * @param row    Fila en la que se realiza la jugada.
     * @param column Columna en la que se realiza la jugada.
     * @param stone  Piedra que se juega.
     * @throws Exception Posible excepción durante la jugada.
     */
    public abstract void play(int row, int column, Stone stone) throws Exception;
    /**
     * Agrega una piedra a la lista de piedras.
     *
     * @param stoneList Lista de piedras a la que se agregará la piedra.
     * @param stone     Piedra que se agregará.
     */
    public void addStone(ArrayList<Stone> stoneList, Stone stone){
        stoneList.add(stone);
    }
    /**
     * Agrega puntuación al jugador.
     *
     * @param punctuation Puntuación que se agregará.
     */
    public void addPunctuation(int punctuation){this.punctuation += punctuation;}
    /**
     * Elimina una piedra de la lista de piedras.
     *
     * @param stoneList Lista de piedras de la que se eliminará la piedra.
     * @param type      Tipo de piedra que se eliminará.
     * @return Piedra eliminada.
     * @throws GomokuException Excepción lanzada si no se encuentra la piedra.
     */
    public Stone eliminateStone(ArrayList<Stone> stoneList, Class<?> type) throws GomokuException {
        if(canRefill && stoneList.isEmpty()){
            refillStones(board.getDimension()[0], board.getSpecialPercentage());
        }
        Iterator<Stone> iterator = stoneList.iterator();
        Stone foundStone = null;
        while (iterator.hasNext() && foundStone == null) {
            Stone stone = iterator.next();
            if (type.isInstance(stone) && stone.getClass() == type) {
                iterator.remove();
                foundStone = stone;
            }
        }

        if (foundStone == null) {
            throw new GomokuException(GomokuException.NO_STONE_FOUND);
        }

        return foundStone;
    }
    /**
     * Refresca la lista de piedras disponibles según las dimensiones del tablero y el porcentaje de piedras especiales.
     *
     * @param size                    Tamaño del tablero.
     * @param specialStonesPercentage Porcentaje de piedras especiales.
     */
    public void refillStones(int size, int specialStonesPercentage) {
        if (canRefill) {
            remainingStones.clear(); // Limpiar las piedras restantes
            // Calcular la cantidad de piedras especiales según el porcentaje
            int totalSpecialStones = (int) (size * specialStonesPercentage / 100.0);

            // Distribuir aleatoriamente las piedras especiales entre Temporary y Heavy
            int totalTemporaryStones = new Random().nextInt(totalSpecialStones + 1); // Aleatorio entre 0 y totalSpecialStones
            int totalHeavyStones = totalSpecialStones - totalTemporaryStones;

            // Llenar el ArrayList con piedras especiales
            for (int i = 0; i < totalTemporaryStones; i++) {
                remainingStones.add(new Temporary(color));
            }

            for (int i = 0; i < totalHeavyStones; i++) {
                remainingStones.add(new Heavy(color));
            }

            // Calcular la cantidad de piedras normales
            int totalNormalStones = size - totalSpecialStones;

            // Llenar el ArrayList con piedras normales
            for (int i = 0; i < totalNormalStones; i++) {
                remainingStones.add(new Stone(color));
            }

        }
    }
    /**
     * Agrega una piedra aleatoria a la lista de piedras restantes.
     */
    public void addRandomStone() {
        Random random = new Random();
        int randomIndex = random.nextInt(3);
        switch (randomIndex){
            case 1:
                remainingStones.add(new Stone(color));
                break;
            case 2:
                remainingStones.add(new Heavy(color));
                break;
            case 3:
                remainingStones.add(new Temporary(color));
                break;
        }
    }
    /**
     * Obtiene la lista de piedras restantes.
     *
     * @return Lista de piedras restantes.
     */
    public ArrayList<Stone> getRemainingStones() {
        return remainingStones;
    }
    /**
     * Verifica si la lista de piedras contiene una piedra del tipo especificado.
     *
     * @param tipo Tipo de piedra a verificar.
     * @return true si la lista contiene una piedra del tipo especificado, false en caso contrario.
     */
    public boolean contieneStone(Class<? extends Stone> tipo) {
        boolean contiene = false;
        for (Stone stone : remainingStones) {
            if (tipo.isInstance(stone)) {
                contiene = true;
                break;
            }
        }
        return contiene;
    }
    /**
     * Obtiene el número de piedras del tipo especificado en la lista de piedras restantes.
     *
     * @param stoneType Tipo de piedra a contar.
     * @return Número de piedras del tipo especificado.
     */
    public int numOfType(Class<? extends Stone> stoneType) {
        int count = 0;
        for (Stone stone : remainingStones) {
            if (stone.getClass().equals(stoneType)) {
                count++;
            }
        }
        return count;
    }
    /**
     * Obtiene el color del jugador.
     *
     * @return Color del jugador.
     */
    public Color getColor() {return color;}
    /**
     * Establece el color del jugador.
     *
     * @param color Color que se establecerá para el jugador.
     */
    public void setColor(Color color) {
        this.color = color;
    }
    /**
     * Obtiene la lista de piedras extra del jugador.
     *
     * @return Lista de piedras extra.
     */
    public ArrayList<Stone> getExtraStones() {
        return extraStones;
    }
    /**
     * Obtiene la puntuación del jugador.
     *
     * @return Puntuación del jugador.
     */
    public int getPunctuation() {
        return punctuation;
    }
    /**
     * Establece si el jugador puede rellenar sus piedras.
     *
     * @param canRefill true si el jugador puede rellenar sus piedras, false en caso contrario.
     */
    public void setCanRefill(boolean canRefill) {
        this.canRefill = canRefill;
    }
    /**
     * Obtiene la primera piedra del tipo especificado de la lista de piedras.
     *
     * @param stones Lista de piedras.
     * @param type   Tipo de piedra a buscar.
     * @return Piedra del tipo especificado, o null si no se encuentra.
     */
    public static Stone getFirstStoneOfType(ArrayList<Stone> stones,Class<?> type) {
        for (Stone stone : stones) {
            if (type.isInstance(stone) && stone.getClass().equals(type)) {
                return stone;
            }
        }
        return null; // Si no se encuentra un objeto del tipo especificado
    }
}
