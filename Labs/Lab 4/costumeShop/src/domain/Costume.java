package domain;



public abstract class Costume{
    
    protected String name;
    protected int discount;
    
    /**
     * Return the name
     * @return
     */
    public String name(){
        return name;
    }

 
    /**
     * Return price
     * @return
     * @throws CostumeShopException, if the price is not available or has an error
     */
    public abstract int price() throws CostumeShopException;
    
    
    
    /**
     * Return the representation as string
     * @return
     * @throws CostumeShopException, if the data is not complete
     */    
    public abstract String data() throws CostumeShopException;

}
