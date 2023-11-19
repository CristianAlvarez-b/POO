import java.util.*;

public class Troncal {
    private LinkedList<Tramo> tramos;
    private String nombre;
    private int velocidadPromedio;

    public Troncal(LinkedList<Tramo> tramos, String nombre, int velocidadPromedio) {
        this.tramos = tramos;
        this.nombre = nombre;
        this.velocidadPromedio = velocidadPromedio;
    }
}
