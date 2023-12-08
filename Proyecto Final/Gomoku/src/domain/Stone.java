package domain;
import java.awt.*;
import java.io.Serializable;

public class Stone implements Serializable {
    protected Color color;
    protected int value;
    public Stone(Color color){
        this.color = color;
        this.value = 1;
    }

    public Color getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }
}
