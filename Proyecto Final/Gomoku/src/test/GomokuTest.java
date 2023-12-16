package test;
import domain.*;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class GomokuTest {
    //Probando Inicializaciones
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
    //Probando modos de juego
    @Test
    void testInvalidGameMode(){
        try {
            new Gomoku(15, 800, 60, 0, "invalidGame");
        }catch (Exception e) {
            assertEquals(e.getMessage(), GomokuException.INVALID_GAME_MODE);
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
    //Probando tipos de piedras
    @Test
    void testHeavyStones(){
        try{
            Gomoku gomoku = new Gomoku(15, 1, 2, 0, "Normal");
            gomoku.getPlayer1().getRemainingStones().add(new Heavy(Color.BLACK));
            gomoku.play(0,0, new Heavy(Color.BLACK));
            assertEquals(gomoku.board()[0][0].getStone().getValue(), 2);
            assertEquals(100, gomoku.getPlayer1().getPunctuation());
        }catch (Exception e){
            fail("Algo inesperado ha ocurrido.");
        }
    }
    @Test
    void testTemporaryStones(){
        try{
            Gomoku gomoku = new Gomoku(15, 1, 2, 0, "Normal");
            gomoku.getPlayer1().getRemainingStones().add(new Temporary(Color.BLACK));
            gomoku.play(0,0, new Temporary(Color.BLACK));
            gomoku.play(0,1, new Stone(Color.WHITE));
            gomoku.play(1,1, new Stone(Color.BLACK));
            gomoku.play(0,8, new Stone(Color.WHITE));
            gomoku.play(0,3, new Stone(Color.BLACK));
            gomoku.play(0,5, new Stone(Color.WHITE));
            gomoku.play(0,6, new Stone(Color.BLACK));
            gomoku.play(0,7, new Stone(Color.WHITE));
            assertNull(gomoku.board()[0][0].getStone());
            assertEquals(100, gomoku.getPlayer1().getPunctuation());
        }catch (Exception e){
            fail("Algo inesperado ha ocurrido.");
        }
    }
    //Probando casillas especiales
    @Test
    void testMineCell(){
        try{
            Gomoku gomoku = new Gomoku(15, 1, 2, 0, "Normal");
            gomoku.getBoard().setCell(0,0, new Mine(gomoku.getBoard(), new int[]{0, 0}));
            gomoku.play(0,1, new Stone(Color.BLACK));
            gomoku.play(0,0, new Stone(Color.WHITE));
            assertNull(gomoku.board()[0][0].getStone());
            assertNull(gomoku.board()[0][1].getStone());
            assertEquals(-50, gomoku.getPlayer1().getPunctuation());
            assertEquals(50, gomoku.getPlayer2().getPunctuation());
        }catch (Exception e){
            fail("Algo inesperado ha ocurrido.");
        }
    }
    @Test
    void testTeleportCell(){
        try{
            Gomoku gomoku = new Gomoku(15, 1, 2, 0, "Normal");
            gomoku.getBoard().setCell(0,0, new Teleport(gomoku.getBoard(), new int[]{0, 0}));
            gomoku.play(0,1, new Stone(Color.BLACK));
            gomoku.play(0,0, new Stone(Color.WHITE));
            assertNull(gomoku.board()[0][0].getStone());
        }catch (Exception e){
            fail("Algo inesperado ha ocurrido.");
        }
    }
    @Test
    void testGoldenCell(){
        try{
            Gomoku gomoku = new Gomoku(15, 1, 2, 0, "Normal");
            gomoku.getBoard().setCell(0,0, new Golden(gomoku.getBoard(), new int[]{0, 0}));
            gomoku.play(0,1, new Stone(Color.BLACK));
            gomoku.play(0,0, new Stone(Color.WHITE));
            assertNotNull(gomoku.getPlayer2().getExtraStones());
        }catch (Exception e){
            fail("Algo inesperado ha ocurrido.");
        }
    }

    //Probando condiciones de victoria
    @Test
    void shouldWinRow() {
        try{
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
            assertTrue(gomoku.play(0, 4, new Stone(Color.BLACK)));
        }catch (Exception e){
            fail("Algo inesperado ha ocurrido.");
        }
    }
    @Test
    void shouldWinColumn() {
        try{
            Gomoku gomoku = new Gomoku(10, 800, 60, 0, "Normal");
            gomoku.play(0, 0, new Stone(Color.BLACK));
            gomoku.play(1, 1, new Stone(Color.WHITE));
            gomoku.play(0, 1, new Stone(Color.BLACK));
            gomoku.play(2, 4, new Stone(Color.WHITE));
            gomoku.play(0, 2, new Stone(Color.BLACK));
            gomoku.play(1, 2, new Stone(Color.WHITE));
            gomoku.play(0, 3, new Stone(Color.BLACK));
            gomoku.play(1, 3, new Stone(Color.WHITE));
            assertTrue(gomoku.play(0, 4, new Stone(Color.BLACK))); // Assuming winning condition
        }catch (Exception e){
            fail("Algo inesperado ha ocurrido.");
        }
    }
    @Test
    void shouldWinDiagonal() {
        try{
            Gomoku gomoku = new Gomoku(10, 800, 60, 0, "Normal");
            gomoku.play(0, 0, new Stone(Color.BLACK));
            gomoku.play(4, 1, new Stone(Color.WHITE));
            gomoku.play(1, 1, new Stone(Color.BLACK));
            gomoku.play(5, 4, new Stone(Color.WHITE));
            gomoku.play(2, 2, new Stone(Color.BLACK));
            gomoku.play(4, 2, new Stone(Color.WHITE));
            gomoku.play(3, 3, new Stone(Color.BLACK));
            gomoku.play(4, 3, new Stone(Color.WHITE));
            assertTrue(gomoku.play(4, 4, new Stone(Color.BLACK))); // Assuming winning condition
        }catch (Exception e){
            fail("Algo inesperado ha ocurrido.");
        }
    }
    @Test
    void shouldWinWithSpecialStones()  {
        try{
            Gomoku gomoku = new Gomoku(10, 800, 60, 0, "Normal");
            gomoku.getPlayer1().getRemainingStones().add(new Heavy(Color.BLACK));
            gomoku.getPlayer1().getRemainingStones().add(new Heavy(Color.BLACK));
            gomoku.play(0, 0, new Stone(Color.BLACK));
            gomoku.play(4, 1, new Stone(Color.WHITE));
            gomoku.play(1, 1, new Heavy(Color.BLACK));
            gomoku.play(5, 4, new Stone(Color.WHITE));
            assertTrue(gomoku.play(2, 2, new Heavy(Color.BLACK)));
        }catch (Exception e){
            fail("Algo inesperado ha ocurrido.");
        }
    }
    //Probando los tipos de máquinas
    @Test
    void testFearfulmachine()  {
        try{
            Gomoku gomoku = new Gomoku(15, 800, 60, 0, "Normal");
            gomoku.setPlayers(Human.class, Fearful.class);
            gomoku.play(0, 0, new Stone(Color.BLACK));
            gomoku.play(1, 0, new Stone(Color.WHITE));
            assertNull(gomoku.board()[0][1].getStone());
            assertNull(gomoku.board()[1][1].getStone());
            assertNull(gomoku.board()[1][0].getStone());
        }catch (Exception e){
            fail("Algo inesperado ha ocurrido.");
        }
    }
    @Test
    void testAgressiveMachine()  {
        try{
            Gomoku gomoku = new Gomoku(15, 800, 60, 0, "Normal");
            gomoku.setPlayers(Human.class, Agressive.class);
            gomoku.play(0, 0, new Stone(Color.BLACK));
            gomoku.play(1, 0, new Stone(Color.WHITE));
            assertTrue(gomoku.board()[0][1].getStone() != null || gomoku.board()[1][1].getStone() != null || gomoku.board()[1][0].getStone() != null);
        }catch (Exception e){
            fail("Algo inesperado ha ocurrido.");
        }
    }
    @Test
    void testExpertMachineShouldWin()  {
        try{
            Gomoku gomoku = new Gomoku(15, 800, 60, 0, "Normal");
            gomoku.setPlayers(Human.class, Expert.class);
            gomoku.play(0, 0, new Stone(Color.BLACK));
            gomoku.play(0, 0, new Stone(Color.WHITE));
            gomoku.play(0, 1, new Stone(Color.BLACK));
            gomoku.play(0, 0, new Stone(Color.WHITE));
            gomoku.play(0, 3, new Stone(Color.BLACK));
            gomoku.play(0, 0, new Stone(Color.WHITE));
            gomoku.play(0, 8, new Stone(Color.BLACK));
            gomoku.play(0, 0, new Stone(Color.WHITE));
            gomoku.play(0, 7, new Stone(Color.BLACK));
            assertTrue(gomoku.play(1, 0, new Stone(Color.WHITE)));
        }catch (Exception e){
            fail("Algo inesperado ha ocurrido.");
        }
    }

    //Probando escenarios de juego
    @Test
    void testShouldPlay(){
        try{
            Gomoku gomoku = new Gomoku(15, 800, 60, 0, "Normal");
            assertDoesNotThrow(() -> gomoku.setPlayers(Human.class, Human.class));
            gomoku.play(1, 1, new Stone(Color.BLACK));
            gomoku.play(1, 2, new Stone(Color.WHITE));
            // Assuming play method returns true if the game is over
            assertEquals(gomoku.board()[1][1].getStone().getColor(), Color.BLACK);
            assertEquals(gomoku.board()[1][2].getStone().getColor(), Color.WHITE);
        }catch (Exception e){
            fail("Algo inesperado ha ocurrido.");
        }
    }
    @Test
    void testShouldNotPlayInvalidPosition(){
        try{
            Gomoku gomoku = new Gomoku(15, 800, 60, 0, "Normal");
            gomoku.play(-1, 1, new Stone(Color.BLACK));
        }catch (Exception e){
            assertEquals(e.getMessage(), GomokuException.NEGATIVE_POSITION);
        }
    }
    @Test
    void testShouldNotPlayWithStoneEmpty(){
        try{
            Gomoku gomoku = new Gomoku(15, 800, 60, 0, "Normal");
            gomoku.play(1, 1, new Heavy(Color.BLACK));
        }catch (Exception e){
            assertEquals(e.getMessage(), GomokuException.NO_STONE_FOUND);
        }
    }
    @Test
    void shouldNotPlaceStoneInOccupiedPosition(){
        try{
            Gomoku gomoku = new Gomoku(15, 800, 60, 0, "Normal");
            gomoku.setPlayers(Human.class, Human.class);

            gomoku.play(1, 1, new Stone(Color.BLACK));
            gomoku.play(1, 1, new Stone(Color.WHITE));
        }catch (Exception e){
            assertEquals(e.getMessage(), GomokuException.STONE_OVERLOAP);
        }
    }

    @Test
    void shouldNotPlaceStoneOutsideBoardBounds(){
        try{
            Gomoku gomoku = new Gomoku(15, 800, 60, 0, "Normal");
            gomoku.setPlayers(Human.class, Human.class);
            gomoku.play(16, 16, new Stone(Color.BLACK));
        }catch (Exception e){
            assertEquals(e.getMessage(), GomokuException.OUT_OF_BOARD);
        }
    }
}
