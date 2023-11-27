package domain;
import java.awt.*;
public class Temporary extends Stone{
    private int life;

    public Temporary(Color color) {
        super(color);
        this.life = 0;
    }
    public void updateLife() throws GomokuException {
        life++;
        if (life>=3){
            throw new GomokuException(GomokuException.MAXIMUS_LIFE);
        }
    }

}
