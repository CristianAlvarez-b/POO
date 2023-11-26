package domain;
import java.util.*;
import java.awt.*;


public abstract class Player {
    protected ArrayList<Stone> remainingStones;
    protected boolean canRefill = true;
    protected Color color;
    protected ArrayList<Stone> extraStones;
    protected Board board;
    protected int punctuation;

    public abstract void play(int row, int column, Stone stone) throws GomokuException;

    public void addStone(ArrayList<Stone> stoneList, Stone stone){
        stoneList.add(stone);
    }

    public void eliminateStone(ArrayList<Stone> stoneList,Stone stone){
        stoneList.remove(stone);
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
}
