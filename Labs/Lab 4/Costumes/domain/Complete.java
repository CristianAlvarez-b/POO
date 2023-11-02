package domain;  
 
import java.util.ArrayList;

/**
 * The Complete class represents a complete costume in a costume shop.
 * 
 * @author Juliana Briceño & Cristian Alvarez
 * @version 1.0 30-Oct-2023
 */
public class Complete extends Costume{
    
    private int makeUp;
    private ArrayList<Basic> pieces;
    
    /**
     * Constructs a new Complete costume with the given name, makeup value, and discount.
     * @param name The name of the costume.
     * @param makeUp The makeup value.
     * @param discount The discount for the costume.
     */
    public Complete(String name, int makeUp, int discount){
        this.name=name;
        this.makeUp=makeUp;
        this.discount=discount;
        pieces= new ArrayList<Basic>();
    }


     /**
     * Add a new basic piece to the complete costume.
     * @param b The Basic costume piece to add.
     */ 
    public void addBasic(Basic b){
        pieces.add(b);
    }
       
 
    /**
     * Returns the price of the Complete costume after applying the discount.
     * @return The discounted price of the costume.
     * @throws CostumeShopException if it doesn't have basic pieces.
     */
    @Override
    public int price() throws CostumeShopException{
        if(pieces.size() == 0){
            throw new CostumeShopException(CostumeShopException.COMPLETE_EMPTY);
        }
        int price = makeUp;
        for(Basic b: pieces){
            price += b.price();
        }
        return price -= price*discount/100;
    }
    
    
    /**
     * Returns an estimated price for the Complete costume.
     * For basics where the price cannot be known or has an error, the first or last value is assumed.
     * @param type Either "first" or "last" to specify the type of estimate.
     * @return The estimated price of the costume.
     * @throws CostumeShopException if it doesn't have basic pieces or if the estimate is impossible.
     */
    public int price(String type) throws CostumeShopException{
        if(pieces.size() == 0){
            throw new CostumeShopException(CostumeShopException.COMPLETE_EMPTY);
        }
        int estimatePrice = 0;
        int price = makeUp;
        if(type.equals("first")){
            for(Basic b: pieces){
                try{
                    estimatePrice = b.price();
                    break;
                }catch(CostumeShopException e){
                    continue;
                }
            }
            if(estimatePrice == 0){
                throw new CostumeShopException(CostumeShopException.IMPOSSIBLE);
            }
            for(Basic b: pieces){
                try{
                    price += b.price();
                }catch(CostumeShopException e){
                    price += estimatePrice;
                }
            }
        }else if(type.equals("last")){
            for(Basic b: pieces){
                try{
                    estimatePrice = b.price();
                }catch(CostumeShopException e){
                    continue;
                }
            }
            if(estimatePrice == 0){
                throw new CostumeShopException(CostumeShopException.IMPOSSIBLE);
            }
            for(Basic b: pieces){
                try{
                    price += b.price();
                }catch(CostumeShopException e){
                    price += estimatePrice;
                }
            }
        }else{
            throw new CostumeShopException(CostumeShopException.UNKNOWN_TYPE);
        }
        return price -= price*discount/100;
    }   
    
    
     /**
     * Returns an estimated price for the Complete costume.
     * For basics where the price cannot be known, if makeup is available, the makeup price is assumed.
     * @param makeUp A boolean value indicating whether makeup is available.
     * @return The estimated price of the costume.
     * @throws CostumeShopException if it doesn't have basic pieces, if some basic is unknown and not makeup, or if some basic has an error.
     */
    public int price(boolean makeUp) throws CostumeShopException{
        if(pieces.size() == 0){
            throw new CostumeShopException(CostumeShopException.COMPLETE_EMPTY);
        }
        int estimatePrice = this.makeUp;
        int price = this.makeUp;
        if(makeUp){
            for(Basic b: pieces){
                try{
                    price += b.price();
                }catch(CostumeShopException e){
                    if(e.getMessage().equals(CostumeShopException.PRICE_EMPTY)){
                        price += estimatePrice;
                    }else{
                        throw e;
                    }
                }
            }
        }else{
            throw new CostumeShopException(CostumeShopException.MAKEUP_EMPTY);
        }
        return price -= price*discount/100;
    } 
    
    /**
     * Returns a string representation of the Complete costume's data, including its basic pieces.
     * @return A string representation of the costume's data.
     * @throws CostumeShopException if the data is not complete.
     */
    @Override
    public String data() throws CostumeShopException{
        StringBuffer answer=new StringBuffer();
        answer.append(name+". Maquillaje "+ makeUp+". Descuento: "+ discount);
        for(Basic b: pieces) {
            answer.append("\n\t"+b.data());
        }
        return answer.toString();
    } 
    
    /**
     * Checks if the costume has makeup.
     * @return true if the costume has makeup, false otherwise.
     * @throws CostumeShopException if the makeup value is invalid.
     */
    public boolean isMakeUp() throws domain.CostumeShopException {
        if(this.makeUp > 0){
            return true;
        }
        return false;
    }
}
