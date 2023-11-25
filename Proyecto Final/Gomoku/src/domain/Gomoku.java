package domain;

import java.awt.*;

public class Gomoku{
    private Board board;
    private Player player1;
    private Player player2;
    private int stoneLimit = 800;
    private int timeLimit;
    private int turn;

    public Gomoku(int rows, int columns, int stoneLimit, int timeLimit) throws Exception {
        turn = 0;
        board = new Board(rows, columns);
        this.stoneLimit = stoneLimit;
        this.timeLimit = timeLimit;
        this.player1 = new Human(Color.BLACK, board);
        this.player2 = new Human(Color.WHITE,board);
        board.setPlayers(new Player[]{this.player1,this.player2});
    }
    public void setPlayers(Player player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;
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
    public void reset(int row, int col) throws Exception {
        this.board = new Board(row, col);
    }
    public Cell[][] board() {
        return board.getCells();
    }

}