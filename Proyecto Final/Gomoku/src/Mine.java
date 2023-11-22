public class Mine extends Cell{
    private boolean active;
    public Mine(Board board){
        super(board);
        active = true;
    }

    public void setStone(Stone stone){
        super.setStone(stone);
        active = false;
    }

    @Override
    public int updateState(){
        int punctuation = 0;
        if(stone != null){

        }
        return punctuation;
    }
}
