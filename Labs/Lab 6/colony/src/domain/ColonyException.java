package domain;

public class ColonyException extends Exception{
    public static final String ERROR_GENERAL = "Algo salio mal.";
    public ColonyException(String message){
        super(message);
    }
}
