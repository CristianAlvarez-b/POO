import java.util.HashMap;

public class Estacion {
    private String nombre;
    private char nivelOcupacion;
    private int tiempoDeEspera;
    private HashMap<String, Ruta> rutas;
    public Estacion(String nombre, char nivelOcupacion, int tiempoDeEspera) {
        this.nombre = nombre;
        this.nivelOcupacion = nivelOcupacion;
        this.tiempoDeEspera = tiempoDeEspera;
        this.rutas = new HashMap<>(); // Inicializar el HashMap en el constructor
    }
    public int getTiempoDeEspera(){
        return tiempoDeEspera;
    }
    public HashMap<String, Ruta> getRutas(){
        return this.rutas;
    }

}
