package domain;


/**
 * Write a description of class Exception here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class CostumeShopException extends Exception
{
    public static final String COMPLETE_EMPTY = "El disfraz completo no tiene piezas basicas";
    public static final String PRICE_ERROR = "Hay un error en el precio";
    public static final String PRICE_EMPTY = "Precio desconocido.";
    public static final String UNKNOWN_TYPE = "Tipo desconocido";
    public static final String IMPOSSIBLE = "No se puede calcular el precio estimado";
    public static final String MAKEUP_EMPTY = "MakeUp no puede ser falso";
    public static final String NAME_ALREADY_EXISTS = "El nombre no puede repetirse";
    /**
     * Constructor for objects of class Exception
     */
    public CostumeShopException(String message)
    {
        super(message);
    }
}
