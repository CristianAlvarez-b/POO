package domain;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Agressive extends Machine {
    public Agressive(Color color, Board board, int specialStonesPercentage, int stoneLimit) {
        this.canRefill = true;
        this.color = color;
        this.board = board;
        extraStones = new ArrayList<>();
        remainingStones = new ArrayList<>();
        refillStones(stoneLimit, specialStonesPercentage);
        punctuation = 0;
    }

    @Override
    public void play() throws Exception {
        // Obtener las posiciones ocupadas por las fichas del otro jugador
        List<Point> posicionesOcupadasOtroJugador = obtenerPosicionesOcupadasOtroJugador();

        // Generar una lista de posiciones disponibles en el tablero
        List<Point> posicionesDisponibles = obtenerPosicionesDisponibles();

        // Encontrar la posición de bloqueo para evitar la victoria del otro jugador
        Point posicionBloqueo = encontrarPosicionBloqueo(posicionesDisponibles, posicionesOcupadasOtroJugador);
        Point posicionMasCercana = encontrarPosicionMasCercana(posicionesDisponibles, posicionesOcupadasOtroJugador);
        Stone selectedStone;
        if (!remainingStones.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(remainingStones.size());
            selectedStone = remainingStones.get(randomIndex);
        }else{
            selectedStone = new Stone(color);
        }
        play(posicionMasCercana.x, posicionMasCercana.y, selectedStone); // Jugar normalmente
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
    private Point encontrarPosicionMasCercana(List<Point> posicionesDisponibles, List<Point> posicionesOcupadasOtroJugador) {
        double distanciaMinima = Double.MAX_VALUE;
        Point posicionMasCercana = null;

        for (Point disponible : posicionesDisponibles) {
            for (Point ocupada : posicionesOcupadasOtroJugador) {
                double distancia = calcularDistancia(disponible, ocupada);
                if (distancia < distanciaMinima) {
                    distanciaMinima = distancia;
                    posicionMasCercana = disponible;
                }
            }
        }

        return posicionMasCercana;
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
    private double calcularDistancia(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
    }
    private Point encontrarPosicionBloqueo(List<Point> posicionesDisponibles, List<Point> posicionesOcupadasOtroJugador) {
        for (Point disponible : posicionesDisponibles) {
            // Simular la adición de una piedra del otro jugador en la posición disponible
            posicionesOcupadasOtroJugador.add(disponible);

            // Verificar si el otro jugador tendría cuatro piedras en fila al jugar en la posición disponible
            if (verificarAmenazaVictoria(posicionesOcupadasOtroJugador)) {
                // Deshacer la simulación
                posicionesOcupadasOtroJugador.remove(disponible);
                return disponible;
            }

            // Deshacer la simulación
            posicionesOcupadasOtroJugador.remove(disponible);
        }

        // Si no se encuentra ninguna posición de bloqueo, devuelve la posición más cercana
        return encontrarPosicionMasCercana(posicionesDisponibles, posicionesOcupadasOtroJugador);
    }

    private boolean verificarAmenazaVictoria(List<Point> posicionesOcupadasOtroJugador) {
        // Verificar amenaza de victoria en filas, columnas y diagonales
        return verificarAmenazaEnFilas(posicionesOcupadasOtroJugador)
                || verificarAmenazaEnColumnas(posicionesOcupadasOtroJugador)
                || verificarAmenazaEnDiagonales(posicionesOcupadasOtroJugador);
    }

    private boolean verificarAmenazaEnFilas(List<Point> posicionesOcupadasOtroJugador) {
        for (int i = 0; i < board.getCells().length; i++) {
            int consecutivas = 0;
            for (int j = 0; j < board.getCells()[0].length; j++) {
                if (posicionesOcupadasOtroJugador.contains(new Point(i, j))) {
                    consecutivas++;
                    if (consecutivas == 5) {
                        return true;
                    }
                } else {
                    consecutivas = 0;
                }
            }
        }
        return false;
    }

    private boolean verificarAmenazaEnColumnas(List<Point> posicionesOcupadasOtroJugador) {
        for (int j = 0; j < board.getCells()[0].length; j++) {
            int consecutivas = 0;
            for (int i = 0; i < board.getCells().length; i++) {
                if (posicionesOcupadasOtroJugador.contains(new Point(i, j))) {
                    consecutivas++;
                    if (consecutivas == 5) {
                        return true;
                    }
                } else {
                    consecutivas = 0;
                }
            }
        }
        return false;
    }

    private boolean verificarAmenazaEnDiagonales(List<Point> posicionesOcupadasOtroJugador) {
        return verificarAmenazaEnDiagonalPrincipal(posicionesOcupadasOtroJugador)
                || verificarAmenazaEnDiagonalSecundaria(posicionesOcupadasOtroJugador);
    }

    private boolean verificarAmenazaEnDiagonalPrincipal(List<Point> posicionesOcupadasOtroJugador) {
        int consecutivas = 0;
        for (int i = 0; i < board.getCells().length; i++) {
            int j = i;
            if (posicionesOcupadasOtroJugador.contains(new Point(i, j))) {
                consecutivas++;
                if (consecutivas == 5) {
                    return true;
                }
            } else {
                consecutivas = 0;
            }
        }
        return false;
    }

    private boolean verificarAmenazaEnDiagonalSecundaria(List<Point> posicionesOcupadasOtroJugador) {
        int consecutivas = 0;
        for (int i = 0; i < board.getCells().length; i++) {
            int j = board.getCells()[0].length - 1 - i;
            if (posicionesOcupadasOtroJugador.contains(new Point(i, j))) {
                consecutivas++;
                if (consecutivas == 5) {
                    return true;
                }
            } else {
                consecutivas = 0;
            }
        }
        return false;
    }
    @Override
    public void play(int row, int column, Stone stone) throws Exception {
        Stone myStone = super.eliminateStone(remainingStones, stone.getClass());
        if (myStone.getClass() != Stone.class){
            punctuation += 100; //Si se usa una piedra especial
        }
        punctuation += board.addStone(row, column, myStone);
        if(punctuation >= 1000){
            super.addRandomStone();
            punctuation -= 1000;
        }
    }
}
