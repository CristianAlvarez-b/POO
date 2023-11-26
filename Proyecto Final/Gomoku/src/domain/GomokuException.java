package domain;
public class GomokuException extends Exception{
    public static final String OUT_OF_BOARD = "Posicion fuera del tablero";
    public static final String NEGATIVE_POSITION = "Posicion invalida";
    public static final String STONE_OVERLOAP = "Ya hay una priedra en la casilla";
    public static final String FULL_BOARD = "Tablero lleno";
    public static final String INVALID_BOARD_SIZE = "Tamano invalido del tablero.";

    public GomokuException(String message){
        super(message);
    }
}
