package player;

import map.Map;
import game.GameEngine;
import java.util.ArrayList;
import entities.Entity;
import java.util.Date;
import java.util.HashMap;



public class Player {

  private String name;
  private int gold;
  private int iron;
  private int wood;
  private int food;
  private int population;
  private int maxPopulation;
  private int numWorkers;
  private int numCollectors;

  public Map myMap;
  public ArrayList<Entity> myEntities;
  public HashMap<Integer,Date> toBeCollected;
  public HashMap<Integer,Date> unavailableThings;
  public HashMap<Integer,Date> unavailableCollectors;
  public HashMap<Integer,Date> unavailableWorkers;
  
  private Date guardTime;
  private int idCount;


  public Player(){
      myMap = new Map();
      myEntities = new ArrayList<>();
      toBeCollected = new HashMap<>();
      unavailableThings = new HashMap<>();
      unavailableCollectors = new HashMap<>();
      unavailableWorkers = new HashMap<>();
      name = "";
      gold = 200;
      iron = 200;
      wood = 200;
      food = 200;
      population = 0;
      maxPopulation = 5;
      numWorkers = 0;
      numCollectors = 0;
      idCount = 1;
  }
  
  public void setName(String name_) {
  name = name_;
  }

  public String getName() {
  return name;
  }
  
  public void setGuardTime(Date guardTime_) {
  guardTime = guardTime_;
  }

  public Date getGuardTime() {
  return guardTime;
  }
  
  public void setIdCount(int idCount_) {
  idCount = idCount_;
  }

  public int getIdCount() {
  return idCount;
  }

  public void setGold(int gold_) {
      gold = gold_;
  }

  public void setIron(int iron_) {
      iron = iron_;
  }

  public void setWood(int wood_) {
      wood = wood_;
  }

  public void setFood(int food_) {
      food = food_;
  }

  public void setPopulation(int population_) {
      population = population_;
  }

  public void setMaxPopulation(int maxPopulation_) {
      maxPopulation = maxPopulation_;
  }

  public void setNumWorkers(int numWorkers_){
      numWorkers = numWorkers_;
  }

  public void setNumCollectors(int numCollectors_) {
      numCollectors = numCollectors_;
  }

  public int getGold() {
  return gold;
  }

  public int getIron() {
  return iron;
  }

  public int getWood() {
  return wood;
  }

  public int getFood() {
  return food;
  }

  public int getMaxPopulation() {
  return maxPopulation;
  }

  public int getNumWorkers() {
  return numWorkers;
  }

  public int getNumCollectors() {
  return numCollectors;
  }

  public int getPopulation() {
  return population;
  }

}