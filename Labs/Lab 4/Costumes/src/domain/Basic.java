package domain;  

/**
 * The Basic class represents a basic costume in a costume shop.
 * 
 * @author Juliana Briceño & Cristian Alvarez
 * @version 1.0 30-Oct-2023
 */

public class Basic extends Costume{
    
    private Integer price;
    
    /**
     * Constructs a new Basic costume with the given name, price, and discount.
     * @param name The name of the costume.
     * @param price The price of the costume.
     * @param discount The discount for the costume.
     */
    public Basic(String name, Integer price, int discount){
        this.name=name;
        this.price=price;
        this.discount=discount;
    }    
    
    /**
     * Returns the price of the Basic costume after applying the discount.
     * @return The discounted price of the costume.
     * @throws CostumeShopException if the price is not available or has an error.
     */
    @Override
    public int price() throws CostumeShopException{
       if (price == null) throw new CostumeShopException(CostumeShopException.PRICE_EMPTY);
       if (price < 1) throw new CostumeShopException(CostumeShopException.PRICE_ERROR);
       return price -= price * discount/100;
    }    
    
    /**
     * Returns a string representation of the Basic costume's data.
     * @return A string representation of the costume's data.
     */
    public String data(){
        return name+". Precio:" +price+".Descuento"+discount;
    }
}
