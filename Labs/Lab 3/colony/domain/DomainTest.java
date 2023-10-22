package domain;

import java.awt.*;
import java.lang.Math;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;

/**
 * The test class Test.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class DomainTest
{
    private Colony colony;
    public DomainTest(){
        Colony colony = new Colony();
        this.colony = colony;
        
    }
    @Test
    public void ShouldDie(){
        Ant ant = new Ant("TestAnt", colony, 15, 15);
        for(int i=0; i<50; i++){
            colony.ticTac();    
        }
        assertEquals(ant.state, 'd');
    }
    @Test
    public void ShouldHungryAnt(){
        HungryAnt ant = new HungryAnt("rachel",colony, 5, 5);
        for(int i=0; i<50; i++){
            colony.ticTac();    
        }
        assertTrue(ant.getHungry());
    }
    @Test
    public void ShouldOpenFlower(){
        Flower flower = new Flower("rose",colony, 28, 7);
        for(int i=0; i<4; i++){
            colony.ticTac();    
        }
        assertTrue(flower.is());
    }
    @Test
    public void ShouldMoveGuardian(){
        Bee bee1 = new Bee("juliana", colony, 8, 20);
        Bee bee2 = new Bee("cristian", colony, 22,10);
        GuardianAnt guard = new GuardianAnt("juliana", colony, 0, 29);
        for(int i=0; i<20; i++){
            colony.ticTac();    
        }
        assertEquals(colony.getEntity(8, 10), null);
    }
    @Test
    public void ShouldNotMoveGuardian(){
        GuardianAnt guard = new GuardianAnt("juliana", colony, 0, 29);
        for(int i=0; i<20; i++){
            colony.ticTac();    
        }
        assertEquals(colony.getEntity(8, 29), colony.getEntity(8, 29));
    }
    @Test
    public void ShouldMoveBee(){
        Bee bee1 = new Bee("juliana", colony, 3, 3);
        Flower flower = new Flower("rose",colony, 5, 5);
         for(int i=0; i<20; i++){
            colony.ticTac();    
        }
        assertTrue(colony.getEntity(3,3) == null);
    }
    @Test
    public void ShouldNotMoveBee(){
        Bee bee1 = new Bee("juliana", colony, 3, 3);
         for(int i=0; i<20; i++){
            colony.ticTac();    
        }
        assertEquals(colony.getEntity(3,3), colony.getEntity(3,3));
    }
}
