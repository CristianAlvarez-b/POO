package test;
import domain.*;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class GomokuTest {
    @Test
    void testInvalidBoardSizeInitialization() {
        assertThrows(GomokuException.class, () -> new Gomoku(5, 800, 60)); // Invalid board size
    }

    @Test
    void testValidInitialization() {
        assertDoesNotThrow(() -> new Gomoku(10, 800, 60));
    }


    @Test
    void testSetPlayers() throws Exception {
        Gomoku gomoku = new Gomoku(15, 800, 60);
        assertDoesNotThrow(() -> gomoku.setPlayers(Human.class, Human.class));
        assertNotNull(gomoku.getPlayer1());
        assertNotNull(gomoku.getPlayer2());
    }

    @Test
    void shouldPlay() throws Exception {
        Gomoku gomoku = new Gomoku(15, 800, 60);
        assertDoesNotThrow(() -> gomoku.setPlayers(Human.class, Human.class));

        // Assuming play method returns true if the game is over
        assertFalse(gomoku.play(1, 1, new Stone(Color.BLACK))); // Assuming BLACK goes first
        assertFalse(gomoku.play(1, 2, new Stone(Color.WHITE)));
        // Continue with your test cases based on the game logic
    }
    @Test
    void shouldNotPlaceStoneInOccupiedPosition() throws Exception {
        Gomoku gomoku = new Gomoku(15, 800, 60);
        assertDoesNotThrow(() -> gomoku.setPlayers(Human.class, Human.class));

        // Place a stone in a valid position
        assertFalse(gomoku.play(1, 1, new Stone(Color.BLACK))); // Assuming BLACK goes first

        // Attempt to place a stone in the same position, should throw an exception
        assertThrows(GomokuException.class, () -> gomoku.play(1, 1, new Stone(Color.WHITE)));
    }

    @Test
    void shouldNotPlaceStoneOutsideBoardBounds() throws Exception {
        Gomoku gomoku = new Gomoku(15, 800, 60);
        assertDoesNotThrow(() -> gomoku.setPlayers(Human.class, Human.class));

        // Attempt to place a stone outside the board bounds, should throw an exception
        assertThrows(GomokuException.class, () -> gomoku.play(16, 16, new Stone(Color.BLACK)));
    }
    @Test
    void shouldWin() throws Exception {
        Gomoku gomoku = new Gomoku(15, 800, 60);
        assertDoesNotThrow(() -> gomoku.setPlayers(Human.class, Human.class));

        // Simulate a win condition
        gomoku.play(1, 3, new Stone(Color.BLACK));
        gomoku.play(1, 4, new Stone(Color.BLACK));
        gomoku.play(1, 5, new Stone(Color.BLACK));
        gomoku.play(1, 6, new Stone(Color.BLACK));
        assertTrue(gomoku.play(1, 7, new Stone(Color.BLACK))); // Assuming winning condition
    }

}
