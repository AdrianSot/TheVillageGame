package entities;

public class ProductionBuilding extends Building {

  public static int INITIAL_CAPACITY = 20;
  public static int INITIAL_PRODUCTION_TIME = 20;
  
  private int productionTime;

  private int capacity;

  private boolean collectable;
  
  public ProductionBuilding(){
      
  }
  @Override
  public void setValues(int goldCost_, int ironCost_, int woodCost_, int attackPower_,String name_, int hitPoints_, int id_){
        goldCost = goldCost_;
        woodCost = woodCost_;
        ironCost = ironCost_;
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
      ironCost = ironCost*2;
      woodCost = woodCost*2;
      hitPoints = hitPoints*2;
      currentLevel++;
  }

  public void collect() {
  }

  public boolean isCollectable() {
  return false;
  }

  public int getCapacity() {
  return 0;
  }

  public int getProductionTime() {
  return 0;
  }

  public void produce() {
  }

}