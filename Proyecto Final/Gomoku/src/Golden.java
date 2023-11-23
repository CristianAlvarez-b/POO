import java.util.*;
import java.lang.*;
public class Golden extends Cell{
    private boolean active;
    public Golden(Board board, int[] position){
        super(board, position);
        active = true;
    }

    public void setStone(Stone stone){
        super.setStone(stone);
        active = false;
    }
    public boolean isActive() {
        return active;
    }
    @Override
    public int updateState(){
        if (stone != null){
            //En construccion
            //Escoger piedra random y darsela al jugar correspondiente con addExtraStone
            //Lo conocemos por el color de la piedra de esta casilla y el color del jugador
        }
        return 0;
    }


}
