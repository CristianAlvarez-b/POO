package test;
import ICPC.*;
import java.awt.*;
import java.lang.Math;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;
import shapes.*;
/**
 * The test class IceepeeceeC4Test.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class IceepeeceeC4Test
{
    private Iceepeecee prueba;
    /**
     * Default constructor for test class IceepeeceeC4Test
     */
    public IceepeeceeC4Test()
    {
        prueba = new Iceepeecee(500, 500);
        prueba.makeInvisible();
    }

    //Ciclo 4
    @Test
    public void shouldAddIslandFixed(){
        try
        {
            Number [][] isla1 = {{40,20},{60,10},{75,20},{60,30}};
            prueba.addIsland('f', "red", isla1);
            prueba.makeInvisible();
            assertTrue(prueba.ok());
        }
        catch (ICPC.IceepeeceeException ie)
        {
            assertFalse(prueba.ok());
        }
    }
    @Test
    public void shouldAddIslandSurpring(){
        try
        {
            Number [][] isla1 = {{40,20},{60,10},{75,20},{60,30}};
            prueba.addIsland('s', "red", isla1);
            prueba.makeInvisible();
            assertTrue(prueba.ok());
        }
        catch (ICPC.IceepeeceeException ie)
        {
            assertFalse(prueba.ok());
        }
    }
    @Test
    public void shouldNotDeleteFixedIsland(){
        try
        {
            Number [][] isla1 = {{40,20},{60,10},{75,20},{60,30}};
            prueba.addIsland('f', "red", isla1);
            prueba.makeInvisible();
            prueba.delIsland("red");
        }
        catch (ICPC.IceepeeceeException ie)
        {
            prueba.makeInvisible();
            assertEquals("Fixed island can't be delete.", ie.getMessage());
        }
    }  
    @Test
    public void shouldGetLocationSurprisingIsland(){
        try
        {
            Number [][] isla1 = {{40,20},{60,10},{75,20},{60,30}};
            prueba.addIsland('s', "red", isla1);
            Number [][] location = prueba.islandLocation("red");
            prueba.makeInvisible();
            Number [][] answer = {{40,20},{60,10},{75,20}};
            assertEquals(answer, location);
        }
        catch (ICPC.IceepeeceeException ie)
        {
            assertFalse(prueba.ok());
        }
    }
    @Test
    public void shouldGetLocationSurprisingIsland3Vertex(){
        try
        {
            Number [][] isla1 = {{40,20},{60,10},{75,20}};
            prueba.addIsland('f', "red", isla1);
            prueba.makeInvisible();
            Number [][] location = prueba.islandLocation("red");
            Number [][] answer = {{40,20},{60,10},{75,20}};
            assertEquals(answer, location);
        }
        catch (ICPC.IceepeeceeException ie)
        {
            assertFalse(prueba.ok());
        }
    }
    @Test
    public void shouldNotAddIslandUknown(){
        try
        {
            Number [][] isla1 = {{40,20},{60,10},{75,20}};
            prueba.addIsland('w', "red", isla1);
        }
        catch (ICPC.IceepeeceeException ie)
        {
            assertEquals(ie.getMessage(), "Unknow type.");
        }
    }
    @Test
    public void shouldAddFlightLazy(){
        try
        {
            Number [] from = {0,30,20};
            Number [] to = {78,70,5};
            prueba.addFlight('l', "red", from, to);
            prueba.makeInvisible();
            assertTrue(prueba.ok());
        }
        catch (ICPC.IceepeeceeException ie)
        {
            assertFalse(prueba.ok());
        }
    }
    @Test
    public void shouldPhotoFlightLazy(){
        try
        {
            Number [] from = {0,30,20};
            Number [] to = {78,70,5};
            prueba.addFlight('l', "red", from, to);
            prueba.photograph(50);
            prueba.makeInvisible();
            assertTrue(prueba.ok());
        }
        catch (ICPC.IceepeeceeException ie)
        {
            assertFalse(prueba.ok());
        }
    }
    @Test
    public void shouldNotPhotoFlightLazy(){
        try
        {
            Number [] from = {0,30,20};
            Number [] to = {78,70,5};
            prueba.addFlight('l', "red", from, to);
            prueba.photograph(50);
            prueba.makeInvisible();
            assertTrue(prueba.ok());
            prueba.photograph(40);
            prueba.makeInvisible();
        }
        catch (ICPC.IceepeeceeException ie)
        {
            prueba.makeInvisible();
            assertEquals("The flight already has one photo.", ie.getMessage());
        }
    }
    @Test
    public void shouldAddFlightFlat(){
        try
        {
            Number [] from = {0,30,20};
            Number [] to = {78,70,5};
            prueba.addFlight('f', "red", from, to);
            prueba.makeInvisible();
            assertTrue(prueba.ok());
        }
        catch (ICPC.IceepeeceeException ie)
        {
            assertFalse(prueba.ok());
        }
    }
    @Test
    public void shouldSameAltitudeFlightFlat(){
        try
        {
            Number [] from = {0,30,20};
            Number [] to = {78,70,5};
            prueba.addFlight('f', "red", from, to);
            Number [][] location = prueba.flightLocation("red");
            assertEquals(location[0][2],location[1][2]);
        }
        catch (ICPC.IceepeeceeException ie)
        {
            assertFalse(prueba.ok());
        }
    }
    @Test
    public void shouldAddFlightRebel(){
        try
        {
            Number [] from = {0,30,20};
            Number [] to = {78,70,5};
            prueba.addFlight('r', "red", from, to);
            prueba.makeInvisible();
            assertTrue(prueba.ok());
        }
        catch (ICPC.IceepeeceeException ie)
        {
            assertFalse(prueba.ok());
        }
    }
    @Test
    public void shouldPhotoFlightRebel(){
        try
        {
            Number [] from = {0,30,20};
            Number [] to = {78,70,5};
            prueba.addFlight('r', "red", from, to);
            prueba.photograph(50);
            prueba.makeInvisible();
            Number answer = prueba.flightCamera("red");
            assertTrue(answer.intValue() != 50);
        }
        catch (ICPC.IceepeeceeException ie)
        {
            assertFalse(prueba.ok());
        }
    }
    @Test
    public void shouldNotAddFlightUknown(){
        try
        {
            Number [] from = {0,30,20};
            Number [] to = {78,70,5};
            prueba.addFlight('z', "red", from, to);
        }
        catch (ICPC.IceepeeceeException ie)
        {
            assertEquals(ie.getMessage(), "Unknow type.");
        }
    }
}