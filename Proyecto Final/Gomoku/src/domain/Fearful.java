package domain;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Clase que representa una implementación específica de la inteligencia artificial para el juego Gomoku.
 * Extiende la clase Machine e implementa una estrategia de juego temerosa.
 */
public class Fearful extends Machine {
    /**
     * Constructor de la clase Fearful.
     *
     * @param color                    Color asignado al jugador temeroso.
     * @param board                    Tablero de juego asociado al temeroso.
     * @param specialStonesPercentage Porcentaje de piedras especiales en el tablero.
     * @param stoneLimit               Límite de piedras para el jugador.
     */
    public Fearful(Color color, Board board, int specialStonesPercentage, int stoneLimit) {
        this.canRefill = true;
        this.color = color;
        this.board = board;
        remainingStones = new ArrayList<>();
        extraStones = new ArrayList<>();
        refillStones(stoneLimit, specialStonesPercentage);
        punctuation = 0;
    }
    /**
     * Método que implementa la estrategia de juego para el jugador temeroso.
     *
     * @throws Exception Si ocurre una excepción durante el juego Gomoku.
     */
    @Override
    public void play() throws Exception {
        // Obtener las posiciones ocupadas por las fichas del otro jugador
        List<Point> posicionesOcupadasOtroJugador = obtenerPosicionesOcupadasOtroJugador();

        // Generar una lista de posiciones disponibles en el tablero
        List<Point> posicionesDisponibles = obtenerPosicionesDisponibles();

        // Encontrar la posición más alejada del otro jugador
        Point posicionMasAlejada = encontrarPosicionMasAlejada(posicionesDisponibles, posicionesOcupadasOtroJugador);
        //Stone selectedStone = getRemainingStones().get(0);
        Stone selectedStone;
        if (!remainingStones.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(remainingStones.size());
            selectedStone = remainingStones.get(randomIndex);
        }else{
            selectedStone = new Stone(color);
        }
        // Jugar la piedra en la posición más alejada
        if (posicionMasAlejada != null) {
            super.play(posicionMasAlejada.x, posicionMasAlejada.y, selectedStone);
        }else{
            playRandomMove();
        }
    }
    /**
     * Método que realiza una jugada en una posición aleatoria válida.
     *
     * @throws Exception Posible excepción durante la jugada.
     */
    private void playRandomMove() throws Exception {
        Random random = new Random();

        // Realiza hasta 100 intentos para encontrar una posición válida
        for (int attempt = 0; attempt < 100; attempt++) {
            int row = random.nextInt(board.getDimension()[0]);
            int column = random.nextInt(board.getDimension()[1]);

            try {
                // Verifica que la posición esté dentro de los límites y la celda esté vacía
                if (isValidPosition(row, column) && !board.cellHasStone(board.getCells()[row][column])) {
                    Random random2 = new Random();
                    int randomIndex = random2.nextInt(remainingStones.size());
                    Stone selectedStone = remainingStones.isEmpty() ? new Stone(color) : remainingStones.get(randomIndex);
                    super.play(row, column, selectedStone);
                    // Si la jugada se realiza con éxito, la máquina ha realizado una jugada
                    return;
                }
            } catch (Exception ignored) {
                // Ignoramos excepciones que indiquen que la celda está ocupada o fuera de los límites
            }
        }
        // Si no se pudo realizar una jugada válida después de 100 intentos, lanza una excepción
        throw new Exception("No se pudo encontrar una posición válida para la jugada aleatoria.");
    }
    /**
     * Método que obtiene las posiciones ocupadas por las fichas del otro jugador.
     *
     * @return Lista de puntos que representan las posiciones ocupadas por las fichas del otro jugador.
     */
    private List<Point> obtenerPosicionesOcupadasOtroJugador() {
        List<Point> posicionesOcupadasOtroJugador = new ArrayList<>();
        Color otroColor = (color == Color.BLACK) ? Color.WHITE : Color.BLACK;

        for (int i = 0; i < board.getCells().length; i++) {
            for (int j = 0; j < board.getCells()[0].length; j++) {
                // Verificar si la celda tiene una piedra y si su color es el del otro jugador
                if (board.getCells()[i][j].getStone() != null && board.getCells()[i][j].getStone().getColor().equals(otroColor)) {
                    posicionesOcupadasOtroJugador.add(new Point(i, j));
                }
            }
        }

        return posicionesOcupadasOtroJugador;
    }

    /**
     * Método que obtiene las posiciones disponibles en el tablero.
     *
     * @return Lista de puntos que representan las posiciones disponibles en el tablero.
     */
    private List<Point> obtenerPosicionesDisponibles() {
        List<Point> posicionesDisponibles = new ArrayList<>();

        for (int i = 0; i < board.getCells()[0].length; i++) {
            for (int j = 0; j < board.getCells().length; j++) {
                if (board.getCells()[i][j].getStone() == null) {
                    posicionesDisponibles.add(new Point(i, j));
                }
            }
        }

        return posicionesDisponibles;
    }
    /**
     * Método que encuentra la posición más alejada de las posiciones ocupadas por el otro jugador.
     *
     * @param posicionesDisponibles      Lista de puntos que representan las posiciones disponibles en el tablero.
     * @param posicionesOcupadasOtroJugador Lista de puntos que representan las posiciones ocupadas por las fichas del otro jugador.
     * @return Punto que representa la posición más alejada del otro jugador.
     */
    private Point encontrarPosicionMasAlejada(List<Point> posicionesDisponibles, List<Point> posicionesOcupadasOtroJugador) {
        double distanciaMaxima = Double.MIN_VALUE;
        Point posicionMasAlejada = null;

        for (Point disponible : posicionesDisponibles) {
            double distanciaMinima = Double.MAX_VALUE;

            for (Point ocupada : posicionesOcupadasOtroJugador) {
                double distancia = calcularDistancia(disponible, ocupada);
                distanciaMinima = Math.min(distanciaMinima, distancia);
            }

            if (distanciaMinima > distanciaMaxima) {
                distanciaMaxima = distanciaMinima;
                posicionMasAlejada = disponible;
            }
        }

        return posicionMasAlejada;
    }
    /**
     * Método que calcula la distancia euclidiana entre dos puntos en el tablero.
     *
     * @param p1 Primer punto.
     * @param p2 Segundo punto.
     * @return Distancia euclidiana entre los dos puntos.
     */
    private double calcularDistancia(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
    }
    /**
     * Verifica si la posición dada está dentro de los límites del tablero.
     *
     * @param row    Fila de la posición.
     * @param column Columna de la posición.
     * @return true si la posición es válida, false en caso contrario.
     */
    private boolean isValidPosition(int row, int column){
        return row >= 0 && column >= 0 && row < board.getDimension()[0] && column < board.getDimension()[1];
    }
}
