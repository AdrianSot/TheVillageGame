package gui;

import entities.Entity;
import entities.ProductionBuilding;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class View {
    String msgOut = "";
    
    public void showPlayerStats(String name, int food, int wood, int gold, int iron, int population, int maxPopulation){
        printMessage("\nPlayer stats:"
            + "\nName: " + name
            + "\nFood: " + food
            + "\nWood: " + wood
            + "\nGold: " + gold
            + "\nIron: " + iron
            + "\nPopulation: " + population
            + "\nMax population: " + maxPopulation+"\n");
    }
    
    public void showEntities(ArrayList<Entity> myEntities){
        printMessage("\nEntities:\n");
        for(int i = 0; i < myEntities.size(); i++){
            Entity entity = myEntities.get(i);
            printMessage("Id:"  + entity.getId() + " Name:" + entity.getName() + " Level:" + entity.getLevel() + " HitPoints:" + entity.getHitPoints());
            if(entity.isArmy()){
                printMessage(" AttackPower:" + entity.getAttackPower());
            }
            printMessage(" Available:" + entity.isAvailable()+"\n");
        }
        if(myEntities.isEmpty()){
            printMessage("No entities.\n");
        }
    }
    
    public void showUpgradeCosts(ArrayList<Entity> myEntities){
        for(int i = 0; i < myEntities.size(); i++){
            Entity entity = myEntities.get(i);
            printMessage(entity.getId() + ".-" + " Name:" + entity.getName() + " Level:" + entity.getLevel() + " HitPoints:" + entity.getHitPoints());
            if(entity.isArmy()){
                printMessage(" AttackPower:" + entity.getAttackPower());
            }
            if(entity.getId() == 0){
                printMessage(" Food:" + (100 + 200*(entity.getLevel() - 1)));
            }
            int level = entity.getLevel();
            printMessage(" Gold:"  + (entity.getGoldCost() * (level + 1)) + " Iron:"  + (entity.getIronCost() * (level + 1)) + " Wood:"  + (entity.getWoodCost() * (level + 1)) );
            printMessage(" Available:" + entity.isAvailable() + "\n");
            }
        if(myEntities.isEmpty()){
            printMessage("No entities.\n");
        }
    }
    
    public void showCollectables(ArrayList<Entity> myEntities, HashMap<Integer,Date> toBeCollected){
        for (Map.Entry mapElement : toBeCollected.entrySet()) { 
                Integer key = (Integer)mapElement.getKey(); 
                Date value = (Date)mapElement.getValue();
                printMessage("\n" + "Id:" + key + " Name:" + myEntities.get(key).getName() + " Production:" + (ProductionBuilding.INITIAL_CAPACITY * myEntities.get(key).getLevel()));
                Date actual = new Date();
                if(value.before(actual)){
                    printMessage(" Collectable:true");
                }else{
                    printMessage(" Collectable:in " + ((int)(value.getTime() - actual.getTime())/1000) +" secs");
                }
            } 
    }
    
    public void showVillage(ArrayList<? extends Entity> village){
        printMessage("\nEnemy Village:\n");
        
        village.forEach((entity) -> {printMessage("Name:" + entity.getName() + " Level:" + entity.getLevel() + " HitPoints:" + entity.getHitPoints()+"\n");});
    }
    
    public void printMainOptionMenu(){
        printMessage(
                    "\nOptions menu.\n"
                    + "0.- Stats \n"
                    + "1.- Entities \n"      
                    + "2.- Create \n"
                    + "3.- Upgrade \n"
                    + "4.- Waiting times \n"
                    + "5.- Collect \n"
                    + "6.- Fight \n"
                    + "7.- Test village \n"
                    + "8.- Exit \n"
                    + "Select an option:"
        );
    }
    
    public void printInvalidNumber(){
        printMessage("\nSelect a valid number and try again.\n");
    }
    
    public void printMessage(String msg){
        msgOut += msg;
    }
    
    public String getMessage(){
        return msgOut.replace("\n", "%1%");
    }
    
    public void clean(){
        msgOut = "";
    }
    
    
}
