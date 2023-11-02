package domain;


/**
 * The CostumeShopException class is a custom exception used to handle various exceptions in a costume shop application.
 * It extends the built-in Exception class and provides specific exception messages for different scenarios.
 * 
 * @author Juliana Briceño & Cristian Alvarez
 * @version 1.0 30-Oct-2023
 */
public class CostumeShopException extends Exception
{
    public static final String COMPLETE_EMPTY = "El disfraz completo no tiene piezas basicas";
    public static final String PRICE_ERROR = "Hay un error en el precio";
    public static final String PRICE_EMPTY = "Precio desconocido.";
    public static final String UNKNOWN_TYPE = "Tipo desconocido";
    public static final String IMPOSSIBLE = "No se puede calcular el precio estimado";
    public static final String MAKEUP_EMPTY = "MakeUp no puede ser falso";
    /**
     * Constructor for objects of class Exception
     */
    public CostumeShopException(String message)
    {
        super(message);
    }
}
