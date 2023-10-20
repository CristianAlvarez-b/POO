
import java.awt.*;
import java.lang.Math;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;
/**
 * The test class IceepeeceeC1Test.
 *
 * @author  Cristian Alvarez - Juliana Briceño
 * @version 1.0
 */
public class IceepeeceeC1Test
{
    private Iceepeecee prueba;
    /**
     * Default constructor for test class IceepeeceeC1Test
     */
    public IceepeeceeC1Test()
    {
        prueba = new Iceepeecee(500, 500);
    }
    
    @Test
    public void shouldIceepeecee(){
        int lenght = 500;
        int width = 500;
        Iceepeecee prueba1 = new Iceepeecee(lenght, width);
        assertTrue(prueba1.ok());
    }
    
    @Test
    public void shouldAddIslands(){
        int [][] points = {{20,30},{50,50},{10,50}};
        prueba.addIsland("red", points);
        assertTrue(prueba.ok());
    }
    
    @Test
    public void shouldNotAddIslands(){
        int [][] points = {{20,30},{50,50},{10,50}};
        prueba.addIsland("red", points);
        int [][] points2 = {{40,20},{60,10},{75,20},{60,30}};
        try {
            prueba.addIsland("red", points);
        } catch (IllegalArgumentException e) {
            // La excepción se esperaba
            assertEquals(e.getMessage(), "The key '" + "red" + "' already exists in the HashMap.");
            assertFalse(prueba.ok());
            return;
        }
    }
    
    @Test
    public void shouldDeleteIslands(){
        int [][] points = {{20,30},{50,50},{10,50}};
        prueba.addIsland("red", points);
        prueba.delIsland("red");
        assertTrue(prueba.ok());
    }
    
    @Test
    public void shouldNotDeleteIslands(){
        try {
            prueba.delIsland("red");
        } catch (IllegalArgumentException e) {
            // La excepción se esperaba
            assertEquals(e.getMessage(), "The key '" + "red" + "' doesn't exists in the HashMap.");
            assertFalse(prueba.ok());
            return;
        }
    }
    
    @Test
    public void shouldAddFlights(){
        int[] from = {0,30,20};  
        int[] to = {78,70,5};
        prueba.addFlight("red", from, to);
        assertTrue(prueba.ok());
    }
    
    @Test
    public void shouldNotAddFlights(){
        int[] from = {0,30,20};  
        int[] to = {78,70,5};
        prueba.addFlight("red", from, to);
        try {
            prueba.addFlight("red", from, to);
        } catch (IllegalArgumentException e) {
            // La excepción se esperaba
            assertEquals(e.getMessage(), "The key '" + "red" + "' already exists in the HashMap.");
            assertFalse(prueba.ok());
            return;
        }
    }
    
    @Test
    public void shouldDeleteFlights(){
        int[] from = {0,30,20};  
        int[] to = {78,70,5};
        prueba.addFlight("red", from, to);
        prueba.delFlight("red");
        assertTrue(prueba.ok());
    }
    
    @Test
    public void shouldNotDeleteFlights(){
        try {
            prueba.delFlight("red");
        } catch (IllegalArgumentException e) {
            // La excepción se esperaba
            assertEquals(e.getMessage(), "The key '" + "red" + "' doesn't exists in the HashMap.");
            assertFalse(prueba.ok());
            return;
        }
    }
    
    @Test
    public void shouldPhotographFlight(){
        int[] from = {0,30,20};  
        int[] to = {78,70,5};
        prueba.addFlight("red", from, to);
        prueba.photograph("red",48);
        assertTrue(prueba.ok());
    }
    
    @Test
    public void shouldPhotograph(){
        int[] from = {0,30,20};  
        int[] to = {78,70,5};
        prueba.addFlight("red", from, to);
        int[] from2 = {55,0,20};
        int[] to2 = {70,60,10};
        prueba.addFlight("blue", from, to);
        prueba.photograph(48);
        assertTrue(prueba.ok());
    }
    
    @Test
    public void shouldConsultIslandLocation(){
        int [][] points = {{20,30},{50,50},{10,50}};
        prueba.addIsland("red", points);
        assertEquals(prueba.islandLocation("red"), points);
    }
    
    @Test
    public void shouldNotConsultIslandLocation(){
        try {
            prueba.islandLocation("red");
        } catch (IllegalArgumentException e) {
            // La excepción se esperaba
            assertEquals(e.getMessage(), "The key '" + "red" + "' doesn't exists in the HashMap.");
            assertFalse(prueba.ok());
            return;
        }
    }
    
    @Test
    public void shouldConsultFlightLocation(){
        int[] from = {0,30,20};  
        int[] to = {78,70,5};
        prueba.addFlight("red", from, to);
        int[][] should = {{0,30,20},{78,70,5}};
        assertEquals(prueba.flightLocation("red"), should);
    }
    
    @Test
    public void shouldNotConsultFlightLocation(){
        try {
            prueba.flightLocation("red");
        } catch (IllegalArgumentException e) {
            // La excepción se esperaba
            assertEquals(e.getMessage(), "The key '" + "red" + "' doesn't exists in the HashMap.");
            assertFalse(prueba.ok());
            return;
        }
    }
    
    @Test
    public void shouldConsultFlightCamera(){
        int[] from = {0,30,20};  
        int[] to = {78,70,5};
        prueba.addFlight("red", from, to);
        prueba.photograph(48);
        int should = 48;
        assertEquals(prueba.flightCamera("red"), should);
    }
    
    @Test
    public void shouldNotConsultFlightCameraWithoutPhotograph(){
        int[] from = {0,30,20};  
        int[] to = {78,70,5};
        prueba.addFlight("red", from, to);
        try {
            prueba.flightCamera("red");
        } catch (IllegalArgumentException e) {
            // La excepción se esperaba
            assertEquals(e.getMessage(), "The key '" + "red" + "' doesn't have photograph.");
            assertFalse(prueba.ok());
            return;
        }
    }
    
    @Test
    public void shouldNotConsultFlightCamera(){
        try {
            prueba.flightCamera("red");
        } catch (IllegalArgumentException e) {
            // La excepción se esperaba
            assertEquals(e.getMessage(), "The key '" + "red" + "' doesn't exists in the HashMap.");
            assertFalse(prueba.ok());
            return;
        }
    }
    
    @Test
    public void shouldmakeVisible(){
        prueba.makeVisible();
        assertTrue(prueba.isVisible());
    }
    
    @Test
    public void shouldmakeInVisible(){
        prueba.makeInvisible();
        assertFalse(prueba.isVisible());
    }
}