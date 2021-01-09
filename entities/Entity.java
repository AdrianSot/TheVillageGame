package entities;

import java.awt.Point;

public abstract class Entity {

  String name;
  int hitPoints;
  int goldCost;
  Point position;
  int currentLevel;
  final int maxLevel = 5;
  int size;
  int id;
  boolean isArmy = false;
  boolean available = true;
  
  public Entity(){
      
  }
  
  public String getName(){
      return name;
  }

  public int getGoldCost() {
  return goldCost;
  }

  public Point getPosition() {
  return position;
  }

  public int getLevel() {
  return currentLevel;
  }

  public int getMaxLevel() {
  return maxLevel;
  }

  public int getSize() {
  return size;
  }

  public int getId() {
  return id;
  }

  public int getHitPoints() {
  return hitPoints;
  }
  
  public boolean isArmy(){
  return isArmy;
  }
  
  public boolean isAvailable(){
  return available;
  }
  
  public void available(boolean a){
      available = a;
  }
  
  public int getAttackPower(){
  return 0;
  }
  
  public int getIronCost(){
  return 0;
  }
  
  public int getWoodCost(){
  return 0;
  }
  
  public void upgrade(){
  }
  
  public void setValues(int goldCost_, int ironCost_, int woodCost_,int attackPower_, String name_, int hitPoints_, int id_){
      
  }
  
  public void setValues(int goldCost_, String name_, int hitPoints_, int currentLevel_){
      goldCost = goldCost_;
      name = name_;
      hitPoints = hitPoints_;
      currentLevel = currentLevel_;
  }
  
  public void setValues(int goldCost_, String name_, int hitPoints_, int currentLevel_, int attackPower_){
      goldCost = goldCost_;
      name = name_;
      hitPoints = hitPoints_;
      currentLevel = currentLevel_;
  }

}
