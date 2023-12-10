package domain;

public class LimitedStones extends Board{

    public LimitedStones(int rows, int columns) throws Exception {
        super(rows, columns, 0);
    }

    @Override
    public void setPlayers(Player[] players) {
        this.players = players;
        players[0].setCanRefill(false);
        players[1].setCanRefill(false);
    }

    @Override
    public boolean verifyGame(boolean turn) throws GomokuException {
        boolean gameOver = super.verifyGame(turn);
        if(!gameOver){
            if(turn && players[0].getRemainingStones().isEmpty() && players[1].getRemainingStones().isEmpty()){
                throw new GomokuException(GomokuException.DRAW);
            }
        }
        return gameOver;
    }
}
