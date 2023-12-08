package domain;

import java.io.Serializable;

public class GomokuException extends Exception implements Serializable {
    public static final String OUT_OF_BOARD = "Posicion fuera del tablero";
    public static final String NEGATIVE_POSITION = "Posicion invalida";
    public static final String STONE_OVERLOAP = "Ya hay una piedra en la casilla";
    public static final String FULL_BOARD = "Tablero lleno";
    public static final String INVALID_BOARD_SIZE = "Tamano invalido del tablero.";
    public static final String NO_STONE_FOUND = "Piedra no encontrada.";
    public static final String MAXIMUS_LIFE = "Vida maxima alcanzada";
    public static final String DRAW = "Empate";

    public GomokuException(String message){
        super(message);
    }
}
