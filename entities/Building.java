package entities;



public class Building extends Entity implements Upgradeable{

  BuildingType buildingType;

  int woodCost;

  int ironCost;

  int constructionTime;

  int upgradeTime;

  public BuildingType getBuildingType() {
  return buildingType;
  }
  
  public Building(){
  }
  
  @Override
  public void setValues(int goldCost_, int ironCost_, int woodCost_, int attackPower, String name_, int hitPoints_, int id){
        goldCost = goldCost_;
        woodCost = woodCost_;
        ironCost = ironCost_;
        name = name_;
        hitPoints = hitPoints_;
        id = id;
        position = null;
        currentLevel = 1;
        size = 1;
  }
  
  @Override
  public int getWoodCost(){
      return ironCost;
  }
  
  @Override
  public int getIronCost(){
      return ironCost;
  }

  public void upgrade(){
      currentLevel++;
      hitPoints = hitPoints * 2;
  }

}
