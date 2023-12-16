package domain;

import java.awt.*;
import java.io.*;

/**
 * Clase que representa el juego Gomoku. Permite inicializar y gestionar un juego de Gomoku.
 */
public class Gomoku implements Serializable{
    private Board board;
    private Player player1;
    private Player player2;
    private int stoneLimit;
    private int timeLimit;
    private int size;
    private int specialPercentage;

    /**
     * Constructor de la clase Gomoku.
     *
     * @param size              Tamaño del tablero.
     * @param stoneLimit        Límite de piedras por jugador.
     * @param timeLimit         Límite de tiempo de juego.
     * @param porcentajeEspeciales Porcentaje de piedras especiales.
     * @param gameMode          Modo de juego ("Normal", "LimitedStones", "QuickTime").
     * @throws Exception Posible excepción durante la inicialización del juego.
     */
    public Gomoku(int size, int stoneLimit, int timeLimit, int porcentajeEspeciales, String gameMode) throws Exception {
        if(size < 10){
            throw new GomokuException(GomokuException.INVALID_BOARD_SIZE);
        }
        switch (gameMode){
            case "Normal":
                board = new Board(size, size, porcentajeEspeciales);
                break;
            case "LimitedStones":
                board = new LimitedStones(size,size);
                break;
            case "QuickTime":
                board = new QuickTime(size,size,porcentajeEspeciales,timeLimit);
                break;
            default:
                throw new GomokuException(GomokuException.INVALID_GAME_MODE);
        }
        this.stoneLimit = stoneLimit;
        this.timeLimit = timeLimit;
        this.specialPercentage = porcentajeEspeciales;
        this.setPlayers(Human.class, Human.class);
        board.setPlayers(new Player[]{this.player1,this.player2});
        this.size = size;

    }
    /**
     * Método que establece los jugadores para el juego.
     *
     * @param playerClass1 Clase del jugador 1.
     * @param playerClass2 Clase del jugador 2.
     * @throws Exception Posible excepción durante la configuración de los jugadores.
     */
    public void setPlayers(Class<? extends Player> playerClass1, Class<? extends Player> playerClass2) throws Exception {
        this.player1 = playerClass1.getDeclaredConstructor(Color.class, Board.class, int.class, int.class).newInstance(Color.BLACK, board, specialPercentage, stoneLimit);
        this.player2 = playerClass2.getDeclaredConstructor(Color.class, Board.class, int.class, int.class).newInstance(Color.WHITE, board, specialPercentage, stoneLimit);
        board.setPlayers(new Player[]{this.player1,this.player2});
    }
    /**
     * Método que realiza una jugada en el tablero.
     *
     * @param row   Fila de la jugada.
     * @param column Columna de la jugada.
     * @param stone Piedra a colocar en la jugada.
     * @return true si la jugada resulta en una victoria, false en caso contrario.
     * @throws Exception Posible excepción durante la jugada.
     */
    public boolean play(int row, int column, Stone stone) throws Exception {
        boolean turn = board.getTurn();
        if(turn){
            if (player1 instanceof Human){
                player1.play(row, column, stone);
            }else {
                ((Machine)player1).play();
            }
        }else {
            if (player2 instanceof Human){
                player2.play(row, column, stone);
            }else {
                ((Machine)player2).play();
            }
        }
        return board.verifyGame(turn);
    }
    /**
     * Método que devuelve el turno actual del juego.
     *
     * @return true si es el turno del jugador 1, false si es el turno del jugador 2.
     */
    public boolean getTurn(){return board.getTurn();}
    /**
     * Método que devuelve el jugador 1.
     *
     * @return Jugador 1.
     */
    public Player getPlayer1(){return player1;}
    /**
     * Método que devuelve el jugador 2.
     *
     * @return Jugador 2.
     */
    public Player getPlayer2(){return player2;}
    /**
     * Método que devuelve el tablero de juego.
     *
     * @return Matriz de celdas representando el tablero.
     */
    public Cell[][] board() {
        return board.getCells();
    }
    /**
     * Método estático que abre un juego Gomoku desde un archivo.
     *
     * @param archivo Archivo que contiene el juego Gomoku.
     * @return Instancia de la clase Gomoku cargada desde el archivo.
     * @throws GomokuException Posible excepción durante la apertura del archivo.
     * @throws ClassNotFoundException Posible excepción de clase no encontrada durante la deserialización.
     */
    public static Gomoku open(File archivo) throws GomokuException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo))) {
            String header = (String) in.readObject();
            if (!header.equals("Gomoku storage\n")) {
                throw new GomokuException("El archivo no es un archivo de almacenamiento de gomoku válido.");
            }
            return (Gomoku) in.readObject();
        } catch (FileNotFoundException e) {
            throw new GomokuException("El archivo no existe: " + archivo.getName());
        } catch (IOException e) {
            throw new GomokuException("Error al leer el archivo: " + archivo.getName());
        }
    }
    /**
     * Método que guarda el juego Gomoku en un archivo.
     *
     * @param archivo Archivo de destino para guardar el juego.
     * @throws GomokuException Posible excepción durante el guardado del archivo.
     */
    public void save(File archivo) throws GomokuException {
        File archivoFinal = new File(archivo.getPath() + ".dat");
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivoFinal))) {
            out.writeObject("Gomoku storage\n");
            out.writeObject(this);
        } catch (IOException e) {
            throw new GomokuException("Error al escribir en el archivo: "+ archivo.getName());
        }
    }
    /**
     * Método que devuelve el tamaño del tablero.
     *
     * @return Tamaño del tablero.
     */
    public int getSize() {
        return size;
    }
    /**
     * Método que devuelve el tablero de juego.
     *
     * @return Objeto Board que representa el tablero de juego.
     */
    public Board getBoard() {
        return board;
    }
}