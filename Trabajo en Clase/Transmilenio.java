import java.util.*;

public class Transmilenio {
    private ArrayList<Troncal> troncales;
    private HashMap<String,Estacion> estaciones;
    public Transmilenio(){
        troncales = new ArrayList<>();
        estaciones = new HashMap<>();
    }
    public void addTroncal(Troncal troncal){
        troncales.add(troncal);
    }
    public void addEstacion(String nombre, char nivelOcupacion, int tiempoDeEspera){
        estaciones.put(nombre, new Estacion(nombre, nivelOcupacion, tiempoDeEspera));
    }
    //1
    public int tiempoDeEsperaEstacion(String name){
        return estaciones.get(name).getTiempoDeEspera();
    }
    //2
    public TreeSet<String> obtenerRutas() {
        TreeSet<String> rutas = new TreeSet<>();
        for (String nombre : estaciones.keySet()) {
            rutas.addAll(estaciones.get(nombre).getRutas().keySet());
        }
        return rutas;
    }
    //3
    public int numeroParadasEntreEstacionesConRuta(String nombreRuta, String estacionOrigen, String estacionDestino) {
        Estacion origen = estaciones.get(estacionOrigen);
        Estacion destino = estaciones.get(estacionDestino);

        if (origen != null && destino != null && origen.getRutas().containsKey(nombreRuta) && destino.getRutas().containsKey(nombreRuta)) {
            Ruta ruta = origen.getRutas().get(nombreRuta);

            LinkedList<Estacion> estacionesRuta = ruta.getEstacionesRuta();
            int indiceOrigen = estacionesRuta.indexOf(origen);
            int indiceDestino = estacionesRuta.indexOf(destino);

            if (indiceOrigen != -1 && indiceDestino != -1 && indiceOrigen <= indiceDestino) {
                // El número de paradas es la diferencia entre los índices
                return indiceDestino - indiceOrigen;
            }
        }
        // Si no se encuentra la ruta o las estaciones en la ruta, o el índice de origen es mayor que el de destino, retorna -1
        return -1;
    }
    //4
    public Set<Ruta> obtenerRutasEntreEstaciones(String estacionOrigen, String estacionDestino) {
        Set<Ruta> rutasEntreEstaciones = new TreeSet<>(new Comparator<Ruta>() {
            @Override
            public int compare(Ruta ruta1, Ruta ruta2) {
                // Comparar por número de paradas
                int comparacionParadas = Integer.compare(ruta1.getNumeroParadas(), ruta2.getNumeroParadas());

                // Si tienen el mismo número de paradas, comparar alfabéticamente por nombre de la ruta
                return (comparacionParadas == 0) ? ruta1.getNombre().compareTo(ruta2.getNombre()) : comparacionParadas;
            }
        });

        Estacion origen = estaciones.get(estacionOrigen);
        Estacion destino = estaciones.get(estacionDestino);

        if (origen != null && destino != null) {
            for (Ruta rutaOrigen : origen.getRutas().values()) {
                if (rutaOrigen.contieneEstacion(destino)) {
                    rutasEntreEstaciones.add(rutaOrigen);
                }
            }
        }
        return rutasEntreEstaciones;
    }
    //5
}

