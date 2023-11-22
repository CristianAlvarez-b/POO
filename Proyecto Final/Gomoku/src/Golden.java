public class Golden extends Cell{
    private boolean active;
    public Golden(Board board){
        super(board);
        active = true;
    }

    public void setStone(Stone stone){
        super.setStone(stone);
        active = false;
    }
}
