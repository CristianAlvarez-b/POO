package domain;
import java.awt.Color;

/*No olviden adicionar la documentacion*/
public interface Entity{
  int ROUND = 1;
  int SQUARE = 2;
  int INSECT = 3; 
  int FLOWER = 4;
   
  void act();
  
  default int shape(){
      return SQUARE;
  }
  
  default Color getColor(){
      return Color.orange;
  }
  
  default boolean is(){
      return true;
  }
  
}
