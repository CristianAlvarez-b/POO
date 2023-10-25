package ICPC;
import java.awt.*;
import java.lang.Math;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;
import shapes.*;
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
        Number [][] points = {{20,30},{50,50},{10,50}};
        try
        {
            prueba.addIsland("red", points);
        }
        catch (IceepeeceeException ie)
        {
            ie.printStackTrace();
        }
        assertTrue(prueba.ok());
    }
    
    @Test
    public void shouldNotAddIslandsRepeatKey(){
        try {
            Number [][] points = {{20,30},{50,50},{10,50}};
            prueba.addIsland("red", points);
            Number [][] points2 = {{40,20},{60,10},{75,20},{60,30}};
            prueba.addIsland("red", points);
        } catch (IceepeeceeException e) {
            // La excepción se esperaba
            assertEquals(e.getMessage(), "The key '" + "red" + "' already exists in the HashMap.");
            assertFalse(prueba.ok());
            return;
        }
    }
    
    @Test
    public void shouldNotAddIslandsOutOfMap(){
        try {
            Number [][] points = {{20000,30},{50,50},{10,50}};
            prueba.addIsland("red", points);
            Number [][] points2 = {{40,20},{60,10},{75,20},{60,30}};
            prueba.addIsland("red", points);
        } catch (IceepeeceeException e) {
            // La excepción se esperaba
            assertEquals(e.getMessage(), "The Island '" + "red" + "' it's out of map.");
            assertFalse(prueba.ok());
            return;
        }
    }
    
    @Test
    public void shouldNotAddIslandsOverloap(){
        try {
            Number [][] points = {{20,30},{50,50},{10,50}};
            prueba.addIsland("red", points);
            Number [][] points2 = {{20,30},{50,50},{10,50}};
            prueba.addIsland("blue", points2);
        } catch (IceepeeceeException e) {
            // La excepción se esperaba
            assertEquals(e.getMessage(), "The island can't be overlap.");
            assertFalse(prueba.ok());
            return;
        }
    }
    
    @Test
    public void shouldDeleteIslands(){
        try
        {
            prueba.delIsland("red");
            Number [][] points = {{20,30},{50,50},{10,50}};
            prueba.addIsland("red", points);
            assertTrue(prueba.ok());
        }
        catch (IceepeeceeException ie)
        {
            ie.printStackTrace();
        }
    }
    
    @Test
    public void shouldNotDeleteIslands(){
        try {
            prueba.delIsland("red");
        } catch (IceepeeceeException e) {
            // La excepción se esperaba
            assertEquals(e.getMessage(), "The key '" + "red" + "' doesn't exists in the HashMap.");
            assertFalse(prueba.ok());
            return;
        }
    }
    
    @Test
    public void shouldAddFlights(){
        Number[] from = {0,30,20};  
        Number[] to = {78,70,5};
        try
        {
            prueba.addFlight("red", from, to);
        }
        catch (IceepeeceeException ie)
        {
            ie.printStackTrace();
        }
        assertTrue(prueba.ok());
    }
    
    @Test
    public void shouldNotAddFlightsRepeatKey(){
        try {
            Number[] from = {0,30,20};  
            Number[] to = {78,70,5};
            prueba.addFlight("red", from, to);
            prueba.addFlight("red", from, to);
        } catch (IceepeeceeException e) {
            // La excepción se esperaba
            assertEquals(e.getMessage(), "The key '" + "red" + "' already exists in the HashMap.");
            assertFalse(prueba.ok());
            return;
        }
    }
    
    @Test
    public void shouldNotAddFlightsOutOfMap(){
        try {
            Number[] from = {0,30,20};  
            Number[] to = {7800,70,5};
            prueba.addFlight("red", from, to);
            prueba.addFlight("red", from, to);
        } catch (IceepeeceeException e) {
            // La excepción se esperaba
            assertEquals(e.getMessage(), "The Flight '" + "red" + "' it's out of map.");
            assertFalse(prueba.ok());
            return;
        }
    }
    
    @Test
    public void shouldDeleteFlights(){
        Number[] from = {0,30,20};  
        Number[] to = {78,70,5};
        try
        {
            prueba.addFlight("red", from, to);
            prueba.delFlight("red");
        }
        catch (IceepeeceeException ie)
        {
            ie.printStackTrace();
        }
        assertTrue(prueba.ok());
    }
    
    @Test
    public void shouldNotDeleteFlights(){
        try {
            prueba.delFlight("red");
        } catch (IceepeeceeException e) {
            // La excepción se esperaba
            assertEquals(e.getMessage(), "The key '" + "red" + "' doesn't exists in the HashMap.");
            assertFalse(prueba.ok());
            return;
        }
    }
    
    @Test
    public void shouldPhotographFlight(){
        Number[] from = {0,30,20};  
        Number[] to = {78,70,5};
        try
        {
            prueba.addFlight("red", from, to);
            prueba.photograph("red",48);
        }
        catch (IceepeeceeException ie)
        {
            ie.printStackTrace();
        }
        assertTrue(prueba.ok());
    }
    
    @Test
    public void shouldPhotograph(){
        Number[] from = {0,30,20};  
        Number[] to = {78,70,5};
        try
        {
            prueba.addFlight("red", from, to);
            Number[] from2 = {55,0,20};
            Number[] to2 = {70,60,10};
            prueba.addFlight("blue", from, to);
            prueba.photograph(48);
        }
        catch (IceepeeceeException ie)
        {
            ie.printStackTrace();
        }
        
        assertTrue(prueba.ok());
    }
    
    @Test
    public void shouldConsultIslandLocation(){
        Number [][] points = {{20,30},{50,50},{10,50}};
        try
        {
            prueba.addIsland("red", points);
            assertEquals(prueba.islandLocation("red"), points);
        }
        catch (IceepeeceeException ie)
        {
            ie.printStackTrace();
        }
    }
    
    @Test
    public void shouldNotConsultIslandLocation(){
        try {
            prueba.islandLocation("red");
        } catch (IceepeeceeException e) {
            // La excepción se esperaba
            assertEquals(e.getMessage(), "The key '" + "red" + "' doesn't exists in the HashMap.");
            assertFalse(prueba.ok());
            return;
        }
    }
    
    @Test
    public void shouldConsultFlightLocation(){
        Number[] from = {0,30,20};  
        Number[] to = {78,70,5};
        try
        {
            prueba.addFlight("red", from, to);
            Number[][] should = {{0,30,20},{78,70,5}};
            assertEquals(prueba.flightLocation("red"), should);
        }
        catch (IceepeeceeException ie)
        {
            ie.printStackTrace();
        }
    }
    
    @Test
    public void shouldNotConsultFlightLocation(){
        try {
            prueba.flightLocation("red");
        } catch (IceepeeceeException e) {
            // La excepción se esperaba
            assertEquals(e.getMessage(), "The key '" + "red" + "' doesn't exists in the HashMap.");
            assertFalse(prueba.ok());
            return;
        }
    }
    
    @Test
    public void shouldConsultFlightCamera(){
        Number[] from = {0,30,20};  
        Number[] to = {78,70,5};
        try
        {
            prueba.addFlight("red", from, to);
            prueba.photograph(48);
            Number should = 48;
            assertEquals(prueba.flightCamera("red"), should);
        }
        catch (IceepeeceeException ie)
        {
            ie.printStackTrace();
        }
        
    }
    
    @Test
    public void shouldNotConsultFlightCameraWithoutPhotograph(){
        Number[] from = {0,30,20};  
        Number[] to = {78,70,5};
        try {
            prueba.addFlight("red", from, to);
            prueba.flightCamera("red");
        } catch (IceepeeceeException e) {
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
        } catch (IceepeeceeException e) {
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