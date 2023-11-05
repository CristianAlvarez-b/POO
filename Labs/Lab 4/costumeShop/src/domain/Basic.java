package domain;  

public class Basic extends Costume{
    
    private Integer price;

    /**
     * Constructs a new basic costume.
     *
     * @param name     The name of the basic costume.
     * @param price    The price of the basic costume.
     * @param discount The discount percentage for the costume.
     */
    public Basic(String name, Integer price, int discount){
        this.name=name;
        this.price=price;
        this.discount=discount;
    }

    /**
     * Calculates the price of the basic costume, considering the price and discount.
     *
     * @return The total price of the basic costume.
     * @throws CostumeShopException If there is an error in the price calculation.
     */
    @Override
    public int price() throws CostumeShopException{
       if (price == null) throw new CostumeShopException(CostumeShopException.PRICE_EMPTY);
       if (price < 1) throw new CostumeShopException(CostumeShopException.PRICE_ERROR);
       return price -= price * discount/100;
    }    

    public int getDiscount(){
        return this.discount;
    }
    public String data(){
        return name+". Precio:" +price+".Descuento"+discount;
    }
    public String getName(){ return this.name;}
}
