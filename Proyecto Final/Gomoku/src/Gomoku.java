public class Gomoku{
    private Board board;
    private Player player1;
    private Player player2;

    public Gomoku(int rows, int columns, Player player1, Player player2) {
        board = new Board(rows, columns);
    }
}