package entities;


public class Army extends Habitant implements Upgradeable{

  private int woodCost;
  private int ironCost;
  private int attackPower;
  
  public Army(){
      
  }

  @Override
  public void setValues(int goldCost_, int ironCost_, int woodCost_, int attackPower_, String name_, int hitPoints_, int id_){
        goldCost = goldCost_;
        woodCost = woodCost_;
        ironCost = ironCost_;
        attackPower = attackPower_;
        name = name_;
        hitPoints = hitPoints_;
        id = id_;
        position = null;
        currentLevel = 1;
        size = 1;
        isArmy = true;
  }

  @Override
  public void upgrade(){
      goldCost = goldCost*2;
      ironCost = ironCost*2;
      woodCost = woodCost*2;
      attackPower = attackPower*2;
      hitPoints = hitPoints*2;
      currentLevel++;
  }
  
  @Override
  public int getWoodCost(){
      return ironCost;
  }
  
  @Override
  public int getIronCost(){
      return ironCost;
  }
  
  @Override
  public int getAttackPower(){
      return attackPower;
  }

  @Override
  public void setValues(int goldCost_, String name_, int hitPoints_, int currentLevel_, int attackPower_){
      goldCost = goldCost_;
      name = name_;
      hitPoints = hitPoints_;
      currentLevel = currentLevel_;
      attackPower = attackPower_;
  }
}
