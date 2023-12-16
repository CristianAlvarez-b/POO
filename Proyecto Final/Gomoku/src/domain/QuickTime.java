package domain;

import javax.swing.*;

/**
 * Clase que representa un tablero de juego con límite de tiempo para cada jugador en el juego Gomoku.
 */
public class QuickTime extends Board{
    private int timeJ1;
    private int timeJ2;
    private boolean isPlayerTurn;

    private int timeLimit;
    private Timer tiempo;
    /**
     * Constructor de la clase QuickTime.
     *
     * @param rows                    Número de filas del tablero.
     * @param columns                 Número de columnas del tablero.
     * @param specialStonesPercentage Porcentaje de piedras especiales en el tablero.
     * @param timeLimit               Límite de tiempo para cada jugador en segundos.
     * @throws Exception Posible excepción al crear el tablero.
     */
    public QuickTime(int rows, int columns, int specialStonesPercentage, int timeLimit) throws Exception {
        super(rows, columns, specialStonesPercentage);
        this.timeLimit = timeLimit;
        isPlayerTurn = true; // Inicia el juego con el turno del jugador 1
        startPlayerTimer();
    }
    /**
     * Método que verifica el estado del juego y cambia el turno del jugador si no hay un ganador.
     *
     * @param turn Turno actual del jugador.
     * @return true si el juego ha terminado, false en caso contrario.
     * @throws GomokuException Excepción lanzada si se detecta un problema con el tiempo.
     */
    @Override
    public boolean verifyGame(boolean turn) throws GomokuException {
        boolean gameOver = super.verifyGame(turn);
        if (!gameOver) {
            switchPlayerTurn();
        }
        return gameOver;
    }

    /**
     * Cambia el turno del jugador actual.
     */
    private void switchPlayerTurn() {
        isPlayerTurn = !isPlayerTurn;
    }

    /**
     * Inicia el temporizador para cada jugador.
     */
    private void startPlayerTimer() {
        timeJ1 = timeLimit / 2;
        timeJ2 = timeLimit / 2;
        tiempo = new Timer(1000, e -> {
            if(isPlayerTurn){
                timeJ1--;
                if(timeJ1 == 0){
                    tiempo.stop();
                }
            }else{
                timeJ2--;
                if(timeJ2 == 0){
                    tiempo.stop();
                }
            }
        });

        tiempo.start(); // Inicia el temporizador fuera del bloque del temporizador
    }
    /**
     * Obtiene una representación del tiempo actual de ambos jugadores.
     *
     * @return Cadena que representa el tiempo actual de ambos jugadores.
     * @throws GomokuException Excepción lanzada si se detecta un problema con el tiempo.
     */
    @Override
    public String obtenerTiempoActual() throws GomokuException {
        int tiempoRestante1 =  timeJ1;
        int tiempoRestante2 = timeJ2;
        if(tiempoRestante1 == 0 || tiempoRestante2 == 0){
            throw new GomokuException(GomokuException.TIME);
        }
        int minutos = tiempoRestante1 / 60;
        int segundosRestantes = tiempoRestante1 % 60;
        int minutos2 = tiempoRestante2 / 60;
        int segundosRestantes2 = tiempoRestante2 % 60;
//        System.out.println(String.format("%02d:%02d", minutos, segundosRestantes));
        String time1 = String.format("%02d:%02d", minutos, segundosRestantes);
        String time2 = String.format("%02d:%02d", minutos2, segundosRestantes2);
        return ("TIEMPO J1: " + time1 + "           TIEMPO J2: " + time2);
    }
}
