/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import exceptions.TypeNotFoundException;

/**
 *
 * @author adrian
 */
public class EntityFactory {
    public Entity getEntity(String entityType){
      if(entityType == null){
         return null;
      }		
      if(entityType.equalsIgnoreCase("Collector") || entityType.equalsIgnoreCase("Worker") || entityType.equalsIgnoreCase("Proletariat")  ){
         return new Proletariat();
         
      } else if(entityType.equalsIgnoreCase("Soldier") || entityType.equalsIgnoreCase("Archer") || entityType.equalsIgnoreCase("Knight") || entityType.equalsIgnoreCase("Catapult") || entityType.equalsIgnoreCase("Army")){
         return new Army();
         
      } else if(entityType.equalsIgnoreCase("Farm") || entityType.equalsIgnoreCase("GoldMine") || entityType.equalsIgnoreCase("IronMine") || entityType.equalsIgnoreCase("LumberHill") || entityType.equalsIgnoreCase("ProductionBuilding")){
         return new ProductionBuilding();
         
      } else if(entityType.equalsIgnoreCase("ArcherTower") || entityType.equalsIgnoreCase("Cannon") || entityType.equalsIgnoreCase("DefenseBuilding")){
         return new DefenseBuilding();
         
      } else if(entityType.equalsIgnoreCase("VillageHall")){
         return new Building();
         
      } else{
          throw new TypeNotFoundException("Type with that name could not be found");
      }
          
      
   }
}
