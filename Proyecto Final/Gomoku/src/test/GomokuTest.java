package test;
import domain.*;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class GomokuTest {
    @Test
    void testInvalidBoardSizeInitialization() {
        try{
           new Gomoku(5, 800, 60, 0, "Normal");
           fail("Algo inesperado a ocurrido");
        }catch (Exception e){
            assertEquals(e.getMessage(), GomokuException.INVALID_BOARD_SIZE);
        }
    }

    @Test
    void testValidInitialization() {
        try{
            Gomoku gomoku = new Gomoku(10, 800, 60, 0, "Normal");
            assertNotNull(gomoku);
        }catch (Exception e){
            fail("Algo inesperado a ocurrido");
        }
    }

    @Test
    void testSetPlayers() {
        Gomoku gomoku;
        try {
            gomoku = new Gomoku(15, 800, 60, 0, "Normal");
            assertNotNull(gomoku.getPlayer1());
            assertNotNull(gomoku.getPlayer2());
        } catch (Exception e) {
            fail("Algo inesperado a ocurrido");
        }
    }
    @Test
    void testShouldWinInQuickTime(){
        try{
            Gomoku gomoku = new Gomoku(15, 800, 100, 0, "QuickTime");
            gomoku.play(0, 0, new Stone(Color.BLACK));
            gomoku.play(1, 0, new Stone(Color.WHITE));
            gomoku.play(0, 1, new Stone(Color.BLACK));
            gomoku.play(1, 1, new Stone(Color.WHITE));
            gomoku.play(0, 2, new Stone(Color.BLACK));
            gomoku.play(1, 2, new Stone(Color.WHITE));
            gomoku.play(0, 3, new Stone(Color.BLACK));
            gomoku.play(1, 3, new Stone(Color.WHITE));
            assertTrue(gomoku.play(0, 4, new Stone(Color.BLACK)));
        }catch (Exception e){
            fail("Algo inesperado a ocurrido");
        }
    }
    @Test
    void testShouldWinInLimitedStones(){
        try{
            Gomoku gomoku = new Gomoku(15, 10, 100, 0, "LimitedStones");
            gomoku.play(0, 0, new Stone(Color.BLACK));
            gomoku.play(1, 0, new Stone(Color.WHITE));
            gomoku.play(0, 1, new Stone(Color.BLACK));
            gomoku.play(1, 1, new Stone(Color.WHITE));
            gomoku.play(0, 2, new Stone(Color.BLACK));
            gomoku.play(1, 2, new Stone(Color.WHITE));
            gomoku.play(0, 3, new Stone(Color.BLACK));
            gomoku.play(1, 3, new Stone(Color.WHITE));
            assertTrue(gomoku.play(0, 4, new Stone(Color.BLACK)));
        }catch (Exception e){
            fail("Algo inesperado a ocurrido");
        }
    }
    @Test
    void testShouldEndGameQuickTime(){
        try{
            Gomoku gomoku = new Gomoku(15, 800, 2, 0, "QuickTime");
            gomoku.play(0, 0, new Stone(Color.BLACK));
            gomoku.play(1, 0, new Stone(Color.WHITE));
        }catch (Exception e){
            assertEquals(e.getMessage(), GomokuException.TIME);
        }
    }
    @Test
    void testShouldEndGameLimitedStones(){
        try{
            Gomoku gomoku = new Gomoku(15, 1, 2, 0, "LimitedStones");
            gomoku.play(0, 0, new Stone(Color.BLACK));
            gomoku.play(1, 0, new Stone(Color.WHITE));
        }catch (Exception e){
            assertEquals(e.getMessage(), GomokuException.DRAW);
        }
    }
    @Test
    void shouldWinRow() throws Exception {
        Gomoku gomoku = new Gomoku(10, 800, 60, 0, "Normal");
        assertDoesNotThrow(() -> gomoku.setPlayers(Human.class, Human.class));
        // Simulate a win condition
        gomoku.play(0, 0, new Stone(Color.BLACK));
        gomoku.play(1, 0, new Stone(Color.WHITE));
        gomoku.play(0, 1, new Stone(Color.BLACK));
        gomoku.play(1, 1, new Stone(Color.WHITE));
        gomoku.play(0, 2, new Stone(Color.BLACK));
        gomoku.play(1, 2, new Stone(Color.WHITE));
        gomoku.play(0, 3, new Stone(Color.BLACK));
        gomoku.play(1, 3, new Stone(Color.WHITE));
        assertTrue(gomoku.play(0, 4, new Stone(Color.BLACK))); // Assuming winning condition
    }
    @Test
    void shouldWinColumn() throws Exception {
        Gomoku gomoku = new Gomoku(10, 800, 60, 0, "Normal");
        assertDoesNotThrow(() -> gomoku.setPlayers(Human.class, Human.class));
        // Simulate a win condition
        gomoku.play(0, 0, new Stone(Color.BLACK));
        gomoku.play(1, 1, new Stone(Color.WHITE));
        gomoku.play(0, 1, new Stone(Color.BLACK));
        gomoku.play(2, 4, new Stone(Color.WHITE));
        gomoku.play(0, 2, new Stone(Color.BLACK));
        gomoku.play(1, 2, new Stone(Color.WHITE));
        gomoku.play(0, 3, new Stone(Color.BLACK));
        gomoku.play(1, 3, new Stone(Color.WHITE));
        assertTrue(gomoku.play(0, 4, new Stone(Color.BLACK))); // Assuming winning condition
    }

    @Test
    void testFearfulmachine() throws Exception {
        Gomoku gomoku = new Gomoku(15, 800, 60, 0, "Normal");
        assertDoesNotThrow(() -> gomoku.setPlayers(Human.class, Fearful.class));
        gomoku.play(0, 0, new Stone(Color.BLACK));
        gomoku.play(1, 0, new Stone(Color.WHITE));
        gomoku.getBoard();
        System.out.println("No se que esta pasando");
    }
    @Test
    void testQuickTime() throws  Exception{
        Gomoku gomoku = new Gomoku(15, 800, 100, 0, "QuickTime");
        gomoku.play(0, 0, new Stone(Color.BLACK));
        gomoku.play(1, 0, new Stone(Color.WHITE));
        System.out.println(gomoku.getBoard().getSegundosTranscurridos());
    }

    @Test
    void shouldPlay() throws Exception {
        Gomoku gomoku = new Gomoku(15, 800, 60, 0, "Normal");
        assertDoesNotThrow(() -> gomoku.setPlayers(Human.class, Human.class));

        // Assuming play method returns true if the game is over
        assertFalse(gomoku.play(1, 1, new Stone(Color.BLACK))); // Assuming BLACK goes first
        assertFalse(gomoku.play(1, 2, new Stone(Color.WHITE)));
        // Continue with your test cases based on the game logic
    }
    @Test
    void shouldNotPlaceStoneInOccupiedPosition() throws Exception {
        Gomoku gomoku = new Gomoku(15, 800, 60, 0, "Normal");
        assertDoesNotThrow(() -> gomoku.setPlayers(Human.class, Human.class));

        // Place a stone in a valid position
        assertFalse(gomoku.play(1, 1, new Stone(Color.BLACK))); // Assuming BLACK goes first

        // Attempt to place a stone in the same position, should throw an exception
        assertThrows(GomokuException.class, () -> gomoku.play(1, 1, new Stone(Color.WHITE)));
    }

    @Test
    void shouldNotPlaceStoneOutsideBoardBounds() throws Exception {
        Gomoku gomoku = new Gomoku(15, 800, 60, 0, "Normal");
        assertDoesNotThrow(() -> gomoku.setPlayers(Human.class, Human.class));

        // Attempt to place a stone outside the board bounds, should throw an exception
        assertThrows(GomokuException.class, () -> gomoku.play(16, 16, new Stone(Color.BLACK)));
    }

    @Test
    void shouldWin() throws Exception {
        Gomoku gomoku = new Gomoku(10, 800, 60, 0, "Normal");
        assertDoesNotThrow(() -> gomoku.setPlayers(Human.class, Human.class));
        // Simulate a win condition
        gomoku.play(0, 0, new Stone(Color.BLACK));
        gomoku.play(1, 0, new Stone(Color.WHITE));
        gomoku.play(0, 1, new Stone(Color.BLACK));
        gomoku.play(1, 1, new Stone(Color.WHITE));
        gomoku.play(0, 2, new Stone(Color.BLACK));
        gomoku.play(1, 2, new Stone(Color.WHITE));
        gomoku.play(0, 3, new Stone(Color.BLACK));
        gomoku.play(1, 3, new Stone(Color.WHITE));
        assertTrue(gomoku.play(0, 4, new Stone(Color.BLACK))); // Assuming winning condition
    }
}
