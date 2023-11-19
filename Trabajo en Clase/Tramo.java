import java.util.LinkedList;

public class Tramo {
    private int distancia;
    private LinkedList<Estacion> estacionesTramo;

    public Tramo(int distancia, LinkedList<Estacion> estacionesTramo) {
        this.distancia = distancia;
        this.estacionesTramo = estacionesTramo;
    }
}
