package domain;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Fearful extends Machine {
    public Fearful(Color color, Board board, int specialStonesPercentage, int stoneLimit) {
        this.canRefill = true;
        this.color = color;
        this.board = board;
        remainingStones = new ArrayList<>();
        extraStones = new ArrayList<>();
        refillStones(stoneLimit, specialStonesPercentage);
        punctuation = 0;
    }

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
            play(posicionMasAlejada.x, posicionMasAlejada.y, selectedStone);
        }
    }
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

    private double calcularDistancia(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
    }

    @Override
    public void play(int row, int column, Stone stone) throws Exception {
        Stone myStone = super.eliminateStone(remainingStones, stone.getClass());
        if (myStone.getClass() != Stone.class){
            punctuation += 100; //Si se usa una piedra especial
        }
        punctuation += board.addStone(row, column, stone);
    }
}
