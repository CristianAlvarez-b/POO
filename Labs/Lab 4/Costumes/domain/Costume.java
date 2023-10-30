package domain;

/**
 * The abstract Costume class represents a costume in a costume shop. Subclasses of this class
 * should implement the price and data methods to provide specific functionality for different types of costumes.
 * 
 * @author Juliana Briceño & Cristian Alvarez
 * @version 1.0 30-Oct-2023
 */

public abstract class Costume{
    
    protected String name;
    protected int discount;
    
    /**
     * Returns the name of the costume.
     * @return The name of the costume.
     */
    public String name(){
        return name;
    }

 
    /**
     * Returns the price of the costume.
     * @return The price of the costume.
     * @throws CostumeShopException if the price is not available or has an error.
     */
    public abstract int price() throws CostumeShopException;
    
    
    
    /**
     * Returns a textual representation of the costume's data.
     * @return A string representation of the costume's data.
     * @throws CostumeShopException if the data is not complete.
     */   
    public abstract String data() throws CostumeShopException;

}
