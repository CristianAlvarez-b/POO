import java.util.LinkedList;

public class Ruta implements Comparable<Ruta>{
    private String nombre;
    private LinkedList<Estacion> estacionesRuta;

    public Ruta(String name, LinkedList<Estacion> estacionesRuta) {
        this.nombre = name;
        this.estacionesRuta = estacionesRuta;
    }

    @Override
    public int compareTo(Ruta otraRuta) {
        return this.nombre.compareTo(otraRuta.nombre);
    }

    public LinkedList<Estacion> getEstacionesRuta() {
        return estacionesRuta;
    }
}
