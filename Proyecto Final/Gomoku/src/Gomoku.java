public class Gomoku{
    private Board board;
    private Player player1;
    private Player player2;
    private int stoneLimit;
    private int timeLimit;
    private int turn;

    public Gomoku(int rows, int columns, Player player1, Player player2) throws Exception {
        turn = 0;
        board = new Board(rows, columns);
        this.player1 = player1;
        this.player2 = player2;
        board.setPlayers(new Player[]{this.player1,this.player2});
    }

    public Gomoku(int rows, int columns, Player player1, Player player2, int stoneLimit) throws Exception {
        this(rows, columns, player1, player2);
        this.stoneLimit = stoneLimit;
    }

    public Gomoku(int timeLimit,int rows, int columns, Player player1, Player player2) throws Exception {
        this(rows, columns, player1, player2);
        this.timeLimit = timeLimit;
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