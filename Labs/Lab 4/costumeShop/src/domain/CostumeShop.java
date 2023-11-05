package domain; 

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * CostumeShop
 * @author POOB  
 * @version ECI 2022
 */

public class CostumeShop{
    private LinkedList<Costume> costumes;
    private HashMap<String,Basic> basics;

    /**
     * Create a CostumeShop
     */
    public CostumeShop() throws CostumeShopException {
        costumes = new LinkedList<Costume>();
        basics = new HashMap<String,Basic>();
        addSome();
    }

    private void addSome() throws CostumeShopException {
        String [][] basics = {{"Camisa","5000","10"},
                              {"Pantalon","10000","20"}};
        for (String [] c: basics){
            addBasic(c[0],c[1],c[2]);
        }
        String [][] Complete = {{"Zorro", "2000","0","Camisa\nPantalon"}};
        for (String [] s: Complete){
            addComplete(s[0],s[1],s[2],s[3]);
        }
    }

    public HashMap getBasics(){
        return basics;
    }
    public LinkedList getCostumes(){
        return costumes;
    }
    /**
     * Consult a costume
     * @param name
     * @return 
     */
    public Costume consult(String name){
        Costume c=null;
        for(int i=0;i<costumes.size() && c == null;i++){
            if (costumes.get(i).name().compareToIgnoreCase(name)==0) 
               c=costumes.get(i);
        }
        return c;
    }

    
    /**
     * Add a new basic costume
     * @param name 
     * @param price
     * @param discount
    */
    public void addBasic(String name, String price, String discount) throws CostumeShopException {
        for (Costume costume : costumes) {
            if (costume.name().equalsIgnoreCase(name)) {
                throw new CostumeShopException(CostumeShopException.NAME_ALREADY_EXISTS);
            }
        }
        try{
            if(Integer.parseInt(discount) < 0 || Integer.parseInt(discount) > 100){
                throw new CostumeShopException(CostumeShopException.DISCOUNT_ERROR);
            }
            Integer prices = Integer.parseInt(price);
            if(prices < 0){
                prices = 0;
            }
            Basic nc=new Basic(name, prices,Integer.parseInt(discount));
            costumes.add(nc);
            basics.put(name.toUpperCase(),nc);
        }catch(Exception e){
            if(e instanceof NumberFormatException){
                throw new CostumeShopException(CostumeShopException.PRICE_ERROR);
            }
            throw e;
        }
    }
    
    /**
     * Add a new Complete costume
     * @param name 
     * @param makeUp
     * @param theBasics
    */
    public void addComplete(String name, String makeUp, String discount, String theBasics) throws CostumeShopException {
        for (Costume costume : costumes) {
            if (costume.name().equalsIgnoreCase(name)) {
                throw new CostumeShopException(CostumeShopException.NAME_ALREADY_EXISTS);
            }
        }
        if(Integer.parseInt(discount) < 0 || Integer.parseInt(discount) > 100){
            throw new CostumeShopException(CostumeShopException.DISCOUNT_ERROR);
        }
        Complete c = new Complete(name,Integer.parseInt(makeUp),Integer.parseInt(discount));
        String [] aBasics= theBasics.split("\n");
        for (String b : aBasics){
            c.addBasic(basics.get(b.toUpperCase()));
        }
        costumes.add(c);
    }

    /**
     * Consults the costumes that start with a prefix
     * @param  
     * @return 
     */
    public LinkedList<Costume> select(String prefix) throws CostumeShopException{
        try {
            LinkedList<Costume> answers = new LinkedList<>();
            prefix = prefix.toUpperCase();
            for (int i = 0; i < costumes.size(); i++) {
                if (costumes.get(i).name().toUpperCase().startsWith(prefix)) {
                    answers.add(costumes.get(i));
                }
            }
            return answers;
        }catch (Exception e){
            throw new CostumeShopException(CostumeShopException.SEARCH_ERROR);
        }
    }


    
    /**
     * Consult selected costumes
     * @param selected
     * @return  
     */
    public String data(LinkedList<Costume> selected){
        StringBuffer answer=new StringBuffer();
        answer.append(costumes.size()+ " disfraces\n");
        for(Costume p : selected) {
            try{
                answer.append('>' + p.data());
                answer.append("\n");
            }catch(CostumeShopException e){
                answer.append("**** "+e.getMessage());
            }
        }    
        return answer.toString();
    }
    
    
     /**
     * Return the data of costumes with a prefix
     * @param prefix
     * @return  
     */ 
    public String search(String prefix) throws CostumeShopException {
        return data(select(prefix));
    }
    
    
    /**
     * Return the data of all costumes
     * @return  
     */    
    public String toString(){
        System.out.println("Data");
        return data(costumes);
    }
    
    /**
     * Consult the number of costumes
     * @return 
     */
    public int numberCostumes(){
        return costumes.size();
    }

}
