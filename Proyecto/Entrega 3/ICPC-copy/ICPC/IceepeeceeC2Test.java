package ICPC;
import java.awt.*;
import java.lang.Math;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;
import shapes.*;
/**
 * The test class IceepeeceeTest.
 *
 * @author  Cristian Alvarez - Juliana Briceño
 * @version 1.0
 */
public class IceepeeceeC2Test
{
    private Number[][][] islands;
    private Number[][][] flights;
    private Iceepeecee prueba;
    /**
     * Default constructor for test class IceepeeceeTest
     */
    public IceepeeceeC2Test()
    {
        Number[][][] islands =  {{{20,30},{50,50},{10,50}},{{40,20},{60,10},{75,20},{60,30}},{{45,60},{55,55},{60,60},{55,65}}};
        Number[][][] flights = {{{0,30,20},{78,70,5}},{{55,0,20},{70,60,10}}};
        this.islands = islands;
        this.flights = flights;
        prueba = new Iceepeecee(islands, flights);
        
    }

    //Ciclo 2
    @Test
    public void shouldIceepeecee(){
        assertTrue(prueba.ok());
    }
    
    @Test
    public void shouldPhotograph(){
        prueba.photograph(48.58);
        assertTrue(prueba.ok());
    }
    
    @Test
    public void shouldConsultIslands(){
        String[] islas = prueba.islands();
        String[] shouldIslas = {"red","blue","yellow"};
        assertEquals(islas, shouldIslas); 
    }
    
    @Test
    public void shouldNotConsultIslands(){
        String[] islas = prueba.islands();
        String[] shouldIslas = {};
        assertFalse(islas.equals(shouldIslas)); 
    }
    
    @Test
    public void shouldConsultFlights(){
        String[] flights = prueba.flights();
        String[] shouldFlights = {"red","blue"};
        assertEquals(flights, shouldFlights); 
    }
    
    @Test
    public void shouldNotConsultFlights(){
        String[] flights = prueba.flights();
        String[] shouldFlights = {};
        assertFalse(flights.equals(shouldFlights)); 
    }
    
    @Test
    public void shouldObservedIslands(){
        prueba.photograph(48.58);
        String[] observedIslands = prueba.observedIslands();
        String[] shouldObserved = {"red", "yellow", "blue"};
        assertEquals(observedIslands, shouldObserved);
    }
    
    @Test
    public void shouldNotObservedIslands(){
        prueba.photograph(48.58);
        String[] observedIslands = prueba.observedIslands();
        String[] shouldObserved = {};
        assertFalse(observedIslands.equals(shouldObserved));
    }
    
    @Test
    public void shouldUselessFlights(){
        prueba.photograph(48.58);
        String[] UselessFlights = prueba.uselessFlights();
        String[] should = {};
        assertEquals(UselessFlights, should);
    }
    
    @Test
    public void shouldNotUselessFlights(){
        prueba.photograph(48.58);
        String[] UselessFlights = prueba.uselessFlights();
        String[] should = {"red","blue"};
        assertFalse(UselessFlights.equals(should));
    }
}
