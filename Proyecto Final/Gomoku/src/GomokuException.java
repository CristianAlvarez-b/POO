public class GomokuException extends Exception{
    public static final String OUT_OF_BOARD = "Posicion fuera del tablero";
    public static final String NEGATIVE_POSITION = "Posicion invalida";
    public static final String STONE_OVERLOAP = "Ya hay una priedra en la casilla";
    public static final String FULL_BOARD = "Tablero lleno";

    public GomokuException(String message){
        super(message);
    }
}
