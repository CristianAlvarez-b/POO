package domain;
import java.awt.*;
public class Temporary extends Stone{
    private int life;

    public Temporary(Color color) {
        super(color);
        this.life = -2; //Porque apenas se pone se actualiza una vez su estado
        // desde las celdas con updateLife y luego se vuelve a actualizar actualizando todoo el tablero
    }
    public void updateLife() throws GomokuException {
        life++;
        if (life>=6){
            throw new GomokuException(GomokuException.MAXIMUS_LIFE);
        }
    }
}
