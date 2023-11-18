package domain;

public class VintageException extends Exception{
    public final static String INVALID_MOVE = "Movimiento no valido.";
    public final static String INVALID_SIZE = "Tamaño del tablero invalido";

    public VintageException(String message){
        super(message);
    }
}
