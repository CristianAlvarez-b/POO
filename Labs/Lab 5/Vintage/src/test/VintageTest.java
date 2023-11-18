package test;
import domain.*;
import static org.junit.Assert.*;

import domain.Vintage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class VintageTest {
    public VintageTest(){
    }
    @Before
    public void setUp(){
    }
    @After
    public void tearDown(){
    }
    @Test
    public void shouldCreateVintage(){
        try{
            Vintage vintage = new Vintage(6,6);
            assertEquals(vintage.getBoard()[0].length, 6);
            assertEquals(vintage.getBoard()[1].length, 6);
        }catch (VintageException e){
            fail();
        }
    }
    @Test
    public void shouldNotCreateVintageIncorrectSize(){
        try{
            new Vintage(-2,6);
            fail();
        }catch (VintageException e){
            assertEquals(e.getMessage(), VintageException.INVALID_SIZE);
        }
    }
    @Test
    public void shouldPlay(){
        try {
            Vintage vintage = new Vintage(2,2);
            char[][][] tablero = {
                    {{'r', 'c'}, {'b', 'c'}},
                    {{'v', 'c'}, {'o', 'c'}}
            };
            vintage.setBoard(tablero);
            vintage.play(0,0,0,1);
            String tableroesperado = """
                    Tablero:
                    [b, c] [r, c]\s
                    [v, c] [o, c]\s
                    """;
            assertEquals(tableroesperado, vintage.toString());
        } catch (VintageException e) {
            fail();
        }
    }
    @Test
    public void shouldNotPlayNotConsecutive(){
        try {
            Vintage vintage = new Vintage(6,6);
            vintage.play(0,0,5,4);
            fail();
        } catch (VintageException e) {
            assertEquals(e.getMessage(), VintageException.INVALID_MOVE);
        }
    }
    @Test
    public void shouldNotPlayMoveOutOfBoard(){
        try {
            Vintage vintage = new Vintage(2,2);
            vintage.play(0,0,5,4);
            fail();
        } catch (VintageException e) {
            assertEquals(e.getMessage(), VintageException.INVALID_MOVE);
        }
    }
    @Test
    public void shouldPlayAndGetPuntuations(){
        try {
            Vintage vintage = new Vintage(5,5);
            vintage.play(0,0,0,1);
            vintage.play(0,0,1,1);
            assertTrue(vintage.getJewels()[0] > 0 || vintage.getJewels()[1] > 0);
        } catch (VintageException e) {
            fail();
        }
    }
    @Test
    public void shouldGameOver(){
        try {
            Vintage vintage = new Vintage(3,3);
            char[][][] tablero = {
                    {{'r', 'n'}, {'b', 'n'}, {'b', 'n'}},
                    {{'v', 'n'}, {'o', 'n'}, {'b', 'n'}},
                    {{'v', 'n'}, {'o', 'n'}, {'b', 'n'}}
            };
            vintage.setBoard(tablero);
            assertTrue(vintage.play(0,0,1,1));
        } catch (VintageException e) {
            fail();
        }
    }
    @Test
    public void shouldChangeTurn() {
        try {
            Vintage vintage = new Vintage(3, 3);
            int turnbefore = vintage.getTurn();
            vintage.play(0,0,0,1);
            int turnafter = vintage.getTurn();
            assertNotEquals(turnafter, turnbefore);
        } catch (VintageException e) {
            fail();
        }
    }
}
