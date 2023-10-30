package domain;  
 
import java.util.ArrayList;

public class Complete extends Costume{
   

    private int makeUp;
    private ArrayList<Basic> pieces;
    
    /**
     * Constructs a new complete custom
     * @param name 
     * @param makeUp
     * @param discount 
     */
    public Complete(String name, int makeUp, int discount){
        this.name=name;
        this.makeUp=makeUp;
        this.discount=discount;
        pieces= new ArrayList<Basic>();
    }


     /**
     * Add a new basic piece
     * @param b
     */   
    public void addBasic(Basic b){
        pieces.add(b);
    }
       
 
    /**
     * 
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
     * Calculates an estimate price
     * For basics where the price cannot be known or has error, the first o last value is assumed
     * @param type (first, last) 
     * @return 
     * @throws CostumeShopException COMPLETE_EMPTY, if it don't have basics. IMPOSSIBLE, if it can't be calculated
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
     * Calculates an estimate price
     * For basics where the price cannot be known, if makeUp then the makeUp is assumed
     * @param unknown 
     * @return 
     * @throws CostumeShopException COMPLETE_EMPTY, if it don't have basics. PRICE_UNKNOWN, if some basic is unknown and not makeUp. PRICE_ERROR, if some basic has error
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
    
    @Override
    public String data() throws CostumeShopException{
        StringBuffer answer=new StringBuffer();
        answer.append(name+". Maquillaje "+ makeUp+". Descuento: "+ discount);
        for(Basic b: pieces) {
            answer.append("\n\t"+b.data());
        }
        return answer.toString();
    } 
    
    public boolean isMakeUp() throws domain.CostumeShopException {
        if(this.makeUp > 0){
            return true;
        }
        return false;
    }
}
