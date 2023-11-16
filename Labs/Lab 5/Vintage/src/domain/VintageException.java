package domain;

public class VintageException extends Exception{
    public final static String INVALID_MOVE = "Movimiento no valido.";

    public VintageException(String message){
        super(message);
    }
}
