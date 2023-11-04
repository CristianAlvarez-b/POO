package test;
import domain.*;


import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;


public class CostumeShopTest {
    public CostumeShopTest(){
    }

    @Before
    public void setUp(){
    }
    @After
    public void tearDown(){
    }
    @Test
    public void shouldAddBasic(){
        try{
            CostumeShop c = new CostumeShop();
            c.addBasic("Zapato", "2000", "10");
            boolean condicion = c.getBasics().containsKey("ZAPATO");
            assertEquals(condicion, true);
        }catch (CostumeShopException e){
            fail("threw exception");
        }


    }
    @Test
    public void shouldNotAddBasic(){
        try {
            CostumeShop c = new CostumeShop();
            c.addBasic("Zapato", "0", "10");
            fail("threw exception");
        } catch (CostumeShopException e) {
            assertEquals(e.getMessage(), CostumeShopException.PRICE_ERROR);
        }
    }
    @Test
    public void shouldAddComplete(){
        try {
            CostumeShop c = new CostumeShop();
            c.addComplete("Fantasma", "10000", "10", "Camisa\nPantalon");
            assertTrue(c.getCostumes().size() == 4);
        } catch (CostumeShopException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void shouldNotAddComplete(){
        try {
            CostumeShop c = new CostumeShop();
            c.addComplete("Zorro", "10000", "10", "Camisa\nPantalon");
            fail("Threw Exception");
        } catch (CostumeShopException e) {
            assertEquals(e.getMessage(), CostumeShopException.NAME_ALREADY_EXISTS);
        }
    }
    @Test
    public void shouldDataComplete(){
        Complete c = new Complete("El zorro", 10000, 0);
        c.addBasic(new Basic("Camisa blanca", 1000, 0));
        try{
            assertEquals(c.data(), "El zorro. Maquillaje 10000. Descuento: 0\n" +
                    "\tCamisa blanca. Precio:1000.Descuento0");
        }catch (CostumeShopException e){
            fail("Threw a exception");
        }
    }
    @Test
    public void shouldDataBasic(){
        Basic b = new Basic("Camisa blanca", 1000, 0);
        assertEquals(b.data(), "Camisa blanca. Precio:1000.Descuento0");
    }
}