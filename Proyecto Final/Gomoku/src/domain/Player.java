package domain;
import java.io.Serializable;
import java.util.*;
import java.awt.*;


public abstract class Player implements Serializable {
    protected String name;
    protected ArrayList<Stone> remainingStones;
    protected boolean canRefill;
    protected Color color;
    protected ArrayList<Stone> extraStones;
    protected Board board;
    protected int punctuation;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void play(int row, int column, Stone stone) throws Exception;

    public void addStone(ArrayList<Stone> stoneList, Stone stone){
        stoneList.add(stone);
    }

    public void addPunctuation(int punctuation){this.punctuation += punctuation;}

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

    public ArrayList<Stone> getRemainingStones() {
        return remainingStones;
    }
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
    public int numOfType(Class<? extends Stone> stoneType) {
        int count = 0;
        for (Stone stone : remainingStones) {
            if (stone.getClass().equals(stoneType)) {
                count++;
            }
        }
        return count;
    }

    public Color getColor() {return color;}

    public void setColor(Color color) {
        this.color = color;
    }

    public ArrayList<Stone> getExtraStones() {
        return extraStones;
    }

    public int getPunctuation() {
        return punctuation;
    }

    public void setCanRefill(boolean canRefill) {
        this.canRefill = canRefill;
    }
}
