package domain;
import java.awt.*;
import java.util.*;
import java.lang.*;
import java.util.List;

public class Golden extends Cell{
    //private boolean active;
    public Golden(Board board, int[] position){
        super(board, position);
        active = true;
    }

    public void setStone(Stone stone){
        super.setStone(stone);
    }
    @Override
    public int updateState(boolean turn) throws Exception {
        super.updateTemporaryStoneLife();
        if (stone != null && active){
            active = false;
            Stone newStone = getRandomStone(stone.getColor());
            if (board.getPlayers()[0].getColor().equals(stone.getColor())){
                board.getPlayers()[0].addStone(board.getPlayers()[0].getExtraStones(),newStone);
            }else {
                board.getPlayers()[1].addStone(board.getPlayers()[1].getExtraStones(),newStone);
            }
        }
        return 0;
    }
    private Stone getRandomStone(Color color) throws Exception {
        List<Class<? extends Stone>> stoneClasses = new ArrayList<>();
        stoneClasses.add(Stone.class);
        stoneClasses.add(Temporary.class);
        stoneClasses.add(Heavy.class);

        Random random = new Random();
        int randomIndex = random.nextInt(stoneClasses.size());
        Class<? extends Stone> selectedStoneClass = stoneClasses.get(0);

        return selectedStoneClass.getDeclaredConstructor(Color.class).newInstance(color);
    }


}
