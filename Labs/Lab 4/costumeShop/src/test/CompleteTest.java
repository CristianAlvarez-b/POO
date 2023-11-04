package test;
import domain.*;


import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class CompleteTest{
   
    
    public CompleteTest(){
    }


    @Before
    public void setUp(){    
    }

    @After
    public void tearDown(){
    }
    
 
    @Test
    public void shouldCalculateTheCostOfACompleteCostume(){
        Complete c = new Complete("El zorro", 10000, 0);
        c.addBasic(new Basic("Camisa blanca", 20000, 0));
        c.addBasic(new Basic("Pantalon negro", 10000, 0));
        c.addBasic(new Basic("Capa negra", 10000, 0));
        try {
           assertEquals(50000,c.price());
        } catch (CostumeShopException e){
            fail("Threw a exception");
        }    
    }    
    
    @Test
    public void shouldCalculateTheCostOfACompleteCostumeWithDiscount(){
        Complete c = new Complete("El zorro", 10000, 10);
        c.addBasic(new Basic("Camisa blanca", 20000, 10));
        c.addBasic(new Basic("Pantalon negro", 10000, 10));
        c.addBasic(new Basic("Capa negra", 10000, 10));
        try {
           assertEquals(41400,c.price());
        } catch (CostumeShopException e){
            fail("Threw a exception");
        }    
    }  
    
    @Test
    public void shouldThrowExceptionIfACompleteCostumeHasNoBasicCustom(){
        Complete c= new Complete("El zorro", 10000, 10);
        try { 
           c.price();
           fail("Did not throw exception");
        } catch (CostumeShopException e) {
            assertEquals(CostumeShopException.COMPLETE_EMPTY,e.getMessage());
        }    
    }    
    
    
    @Test
    public void shouldThrowExceptionIfThereIsErrorInPrice(){
        Complete c = new Complete("El zorro", 10000, 0);
        c.addBasic(new Basic("Camisa blanca", 20000, 0));
        c.addBasic(new Basic("Pantalon negro", -10000, 0));
        c.addBasic(new Basic("Capa negra", 10000, 0));
        try { 
           c.price();
           fail("Did not throw exception");
        } catch (CostumeShopException e) {
            assertEquals(CostumeShopException.PRICE_ERROR,e.getMessage());
        }    
    }     
    
    @Test
    public void shouldThrowExceptionIfPriceIsNotKnown(){
        Complete c = new Complete("El zorro", 10000, 0);
        c.addBasic(new Basic("Camisa blanca", null, 0));
        c.addBasic(new Basic("Pantalon negro", 10000, 0));
        c.addBasic(new Basic("Capa negra", 10000, 0));
        try { 
           c.price();
           fail("Did not throw exception");
        } catch (CostumeShopException e) {
            assertEquals(CostumeShopException.PRICE_EMPTY,e.getMessage());
        }    
    }  
    @Test
    public void shouldCalculatedTheEstimatePriceFirst(){
        Complete c = new Complete("El zorro", 10000, 0);
        c.addBasic(new Basic("Camisa blanca", null, 0));
        c.addBasic(new Basic("Pantalon negro", 10000, 0));
        c.addBasic(new Basic("Capa negra", -10000, 0));
        c.addBasic(new Basic("Capa negra", 20000, 0));
        try { 
           assertEquals(60000, c.price("first"));
        } catch (CostumeShopException e) {
            fail("Threw a exception");
        }  
    }
    @Test
    public void shouldCalculatedEstimatePriceLast(){
        Complete c = new Complete("El zorro", 10000, 0);
        c.addBasic(new Basic("Camisa blanca", null, 0));
        c.addBasic(new Basic("Pantalon negro", 10000, 0));
        c.addBasic(new Basic("Capa negra", -10000, 0));
        c.addBasic(new Basic("Capa negra", 20000, 0));
        try { 
           assertEquals(80000, c.price("last"));
        } catch (CostumeShopException e) {
            fail("Threw a exception");
        }  
    }
    @Test
    public void shouldThrowExceptionIfACompleteCostumeHasNoBasicCustomInPriceType(){
        Complete c= new Complete("El zorro", 10000, 10);
        try { 
           c.price("first");
           fail("Did not throw exception");
        } catch (CostumeShopException e) {
            assertEquals(CostumeShopException.COMPLETE_EMPTY,e.getMessage());
        }     
    }
    @Test
    public void shouldThrowExceptionIfPriceCannotBeCalculatedTypeFirst(){
        Complete c = new Complete("El zorro", 10000, 0);
        c.addBasic(new Basic("Camisa blanca", null, 0));
        c.addBasic(new Basic("Pantalon negro", null, 0));
        c.addBasic(new Basic("Capa negra", null, 0));
        c.addBasic(new Basic("Capa negra", -20000, 0));
        try { 
           c.price("first");
           fail("Did not throw exception");
        } catch (CostumeShopException e) {
            assertEquals(CostumeShopException.IMPOSSIBLE,e.getMessage());
        }  
    }
    @Test
    public void shouldThrowExceptionIfPriceCannotBeCalculatedTypeLast(){
        Complete c = new Complete("El zorro", 10000, 0);
        c.addBasic(new Basic("Camisa blanca", null, 0));
        c.addBasic(new Basic("Pantalon negro", null, 0));
        c.addBasic(new Basic("Capa negra", null, 0));
        c.addBasic(new Basic("Capa negra", -20000, 0));
        try { 
           c.price("last");
           fail("Did not throw exception");
        } catch (CostumeShopException e) {
            assertEquals(CostumeShopException.IMPOSSIBLE,e.getMessage());
        }  
    }
    @Test
    public void shouldThrowExceptionIfUnkwownType(){
        Complete c = new Complete("El zorro", 10000, 0);
        c.addBasic(new Basic("Camisa blanca", null, 0));
        c.addBasic(new Basic("Pantalon negro", 10000, 0));
        c.addBasic(new Basic("Capa negra", -10000, 0));
        c.addBasic(new Basic("Capa negra", 20000, 0));
        try { 
           c.price("second");
           fail("Threw a exception");
        } catch (CostumeShopException e) {
           assertEquals(CostumeShopException.UNKNOWN_TYPE,e.getMessage()); 
        }  
    }
    @Test
    public void shouldCalculatedEstimatePriceMakeUp(){
        Complete c = new Complete("El zorro", 10000, 0);
        c.addBasic(new Basic("Camisa blanca", null, 0));
        c.addBasic(new Basic("Pantalon negro", 10000, 0));
        c.addBasic(new Basic("Capa negra", null, 0));
        c.addBasic(new Basic("Capa negra", 20000, 0));
        try { 
           assertEquals(60000, c.price(c.isMakeUp()));
        } catch (CostumeShopException e) {
            fail("Threw a exception");
        }  
    }
    @Test
    public void shouldCalculatedEstimatePriceMakeUpdiscount(){
        Complete c = new Complete("El zorro", 10000, 10);
        c.addBasic(new Basic("Camisa blanca", null, 10));
        c.addBasic(new Basic("Pantalon negro", 10000, 10));
        c.addBasic(new Basic("Capa negra", null, 10));
        try {
            assertEquals(33300, c.price(c.isMakeUp()));
        } catch (CostumeShopException e) {
            fail("Threw a exception");
        }
    }
    @Test
    public void shouldThrowExceptionIfMakeUpFalse(){
        Complete c = new Complete("El zorro", 0, 0);
        c.addBasic(new Basic("Camisa blanca", null, 0));
        c.addBasic(new Basic("Pantalon negro", 10000, 0));
        c.addBasic(new Basic("Capa negra", null, 0));
        c.addBasic(new Basic("Capa negra", 20000, 0));
        try { 
            c.price(c.isMakeUp());
            fail("Threw a exception");
        } catch (CostumeShopException e) {
            assertEquals(CostumeShopException.MAKEUP_EMPTY,e.getMessage()); 
        }  
    }
    @Test
    public void shouldThrowExceptionIfPriceError(){
        Complete c = new Complete("El zorro", 10000, 0);
        c.addBasic(new Basic("Camisa blanca", null, 0));
        c.addBasic(new Basic("Pantalon negro", 10000, 0));
        c.addBasic(new Basic("Capa negra", -10000, 0));
        c.addBasic(new Basic("Capa negra", 20000, 0));
        try { 
            c.price(c.isMakeUp());
            fail("Threw a exception");
        } catch (CostumeShopException e) {
            assertEquals(CostumeShopException.PRICE_ERROR,e.getMessage()); 
        }  
    }
    @Test
    public void shouldAddBasic(){
        Complete c = new Complete("El zorro", 10000, 0);
        c.addBasic(new Basic("Camisa blanca", null, 0));
        assertEquals(1, c.getPieces().size());
    }
    @Test
    public void shouldNotAddBasic(){
        Complete c = new Complete("El zorro", 10000, 0);
        c.addBasic(new Basic("Camisa blanca", 0, 0));
        try {
            c.price();
        } catch (CostumeShopException e) {
            assertEquals(e.getMessage(), CostumeShopException.PRICE_ERROR);
        }
    }
    @Test
    public void shouldAddComplete(){
        Complete c = new Complete("El zorro", 10000, 0);
        c.addBasic(new Basic("Camisa blanca", 1000, 0));
        try {
            assertEquals(11000, c.price());
        } catch (CostumeShopException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void shouldNotAddComplete(){
        Complete c = new Complete("El zorro", 10000, 0);
        c.addBasic(new Basic("Camisa blanca", 0, 0));
        try {
            c.price();
        } catch (CostumeShopException e) {
            assertEquals(e.getMessage(), CostumeShopException.PRICE_ERROR);
        }
    }

}