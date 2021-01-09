package entities;

public class Proletariat extends Habitant {

  private boolean busy;

  public Proletariat(){
      
  }
  @Override
  public void setValues(int goldCost_, int ironCost_, int woodCost_, int attackPower_,String name_, int hitPoints_, int id_){
        
        goldCost = goldCost_;
        name = name_;
        hitPoints = hitPoints_;
        id = id_;
        position = null;
        currentLevel = 1;
        size = 1;
  }
  
  @Override
  public void upgrade(){
      goldCost = goldCost*2;
      hitPoints = hitPoints*2;
      currentLevel++;
  }
  
  public boolean isBusy() {
  return false;
  }

  public void work() {
  }

}