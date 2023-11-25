package domain;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public class Gomoku{
    private Board board;
    private Player player1;
    private Player player2;
    private int stoneLimit = 800;
    private int timeLimit;
    private int turn;

    public Gomoku(int size, int stoneLimit, int timeLimit) throws Exception {
        turn = 0;
        board = new Board(size, size);
        this.stoneLimit = stoneLimit;
        this.timeLimit = timeLimit;
        this.setPlayers(Human.class, Human.class);
        board.setPlayers(new Player[]{this.player1,this.player2});
    }
    public void setPlayers(Class<? extends Player> playerClass1, Class<? extends Player> playerClass2) throws Exception {
        this.player1 = playerClass1.getDeclaredConstructor(Color.class, Board.class).newInstance(Color.BLACK, board);
        this.player2 = playerClass2.getDeclaredConstructor(Color.class, Board.class).newInstance(Color.WHITE, board);
        board.setPlayers(new Player[]{this.player1,this.player2});
    }
    public boolean play(int row, int column, Stone stone) throws GomokuException{
        if(turn%2 == 0){
            if (player1 instanceof Human){
                player1.play(row, column, stone);
            }else {
                ((Machine)player1).play();
            }
        }else {
            if (player2 instanceof Human){
                player2.play(row, column, stone);
            }else {
                ((Machine)player2).play();
            }
        }
        turn += 1;
        return board.verifyGame();
    }

    public Cell[][] board() {
        return board.getCells();
    }

}