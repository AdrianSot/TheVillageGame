package game;

import gui.Gui;
import java.util.Scanner;
import java.util.HashMap;
import player.Player;
import entities.*;
import entities.HabitantType;
import entities.ProductionBuilding;
import gui.View;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;


class InitialValues{
    int gold;
    int iron;
    int wood;
    int hp;
    int attackPower;
    public InitialValues(int gold_, int iron_, int wood_, int hp_, int attackPower_){
        gold = gold_;
        iron = iron_;
        wood = wood_;
        hp = hp_;
        attackPower = attackPower_;
    }
}



public class GameEngine {
    
    private final HashMap<String,InitialValues> initialValues = new HashMap<>();
   
    
    public Player myPlayer;
    public Gui  myGui;
    public View myView;
    int o = 1;
    
    enum Selection {STATS,ENTITIES,CREATE,UPDATE,WAITING_TIMES,COLLECT,FIGHT,TEST,EXIT};
    enum GameState {
    CHOOSING_NAME,
    NAME_SET,
    MAIN_MENU,
    CHOOSING_ENTITY_TYPE,
    CHOOSING_ENTITY_UPGRADE,
    CREATING_HABITANT,
    CHOOSING_BUILDING,
    COLLECTING,
    FIGHTING,
    TEST,
    MAIN_MENU_SET,
    CHOOSING_ENTITY_TYPE_SET,
    CHOOSING_ENTITY_UPGRADE_SET,
    CREATING_HABITANT_SET,
    CHOOSING_BUILDING_SET,
    COLLECTING_SET,
    FIGHTING_SET,
    TEST_SET
    };
    private Selection selection = Selection.STATS;
    private GameState gameState = GameState.CHOOSING_NAME;

    public GameEngine(){
        myPlayer = new Player();
        myView = new View();
        initialValues();
        
    }
    
    
    public String runGame(String theInput){
        String output = null;
        
        
        if(gameState == GameState.CHOOSING_NAME){
            myView.printMessage("Welcome to The Village.\n");
            myView.printMessage("Enter your name: ");
            //String playerName = input.next();
            
            gameState = GameState.NAME_SET;
            output = getNclean();
            return output;
        }
        
        if(gameState == GameState.NAME_SET){
            if(!authenticate(theInput)){
                myView.printMessage("Not a registered username. Goodbye");
                output = getNclean();
                return output;
            }
            myView.printMessage("The game has started.\n");
            myPlayer.setName(theInput);
        
            EntityFactory entityFactory = new EntityFactory();
            Entity entity = entityFactory.getEntity("VillageHall");
            entity.setValues(initialValues.get("VillageHall").gold,initialValues.get("VillageHall").iron,initialValues.get("VillageHall").wood, 0,"VillageHall",initialValues.get("VillageHall").hp, 1);
            myPlayer.myEntities.add(entity);
            myPlayer.setGuardTime(timeUntilAvailable(60));
            
            gameState = GameState.MAIN_MENU;
        }
        
        if(gameState == GameState.MAIN_MENU){
            
            selection = Selection.STATS;
            
            mainMenu();
            
            gameState = GameState.MAIN_MENU_SET;
            output = getNclean();
            return output;
            
        }
        
        if(gameState == GameState.MAIN_MENU_SET){
            try{
                //selection = Selection.values()[Integer.parseInt(input.next())];
                selection = Selection.values()[Integer.parseInt(theInput)];
            }catch(Exception e){
                myView.printInvalidNumber();
            }
        }
        
        int decider = -1;
        int option = -1;
        
        switch(gameState){
            case CHOOSING_ENTITY_TYPE:
                selection = Selection.CREATE;
                break;
            case CHOOSING_ENTITY_UPGRADE:
                selection = Selection.UPDATE;
                break;
            case CREATING_HABITANT:
                selection = Selection.CREATE;
                option = 0;
                break;
            case CHOOSING_BUILDING:
                selection = Selection.CREATE;
                option = 1;
                break;
            case COLLECTING:
                selection = Selection.COLLECT;
                break;
            case FIGHTING:
                selection = Selection.FIGHT;
                break;
            case TEST:
                selection = Selection.TEST;
                break;
            case CHOOSING_ENTITY_TYPE_SET:
                selection = Selection.CREATE;
                option = Integer.parseInt(theInput);
                break;
            case CHOOSING_ENTITY_UPGRADE_SET:
                selection = Selection.UPDATE;
                option = Integer.parseInt(theInput);
                break;
            case CREATING_HABITANT_SET:
                selection = Selection.CREATE;
                option = 0;
                decider = Integer.parseInt(theInput);
                break;
            case CHOOSING_BUILDING_SET:
                selection = Selection.CREATE;
                option = 1;
                decider = Integer.parseInt(theInput);
                break;
            case COLLECTING_SET:
                selection = Selection.COLLECT;
                decider = Integer.parseInt(theInput);
                break;
            case FIGHTING_SET:
                selection = Selection.FIGHT;
                decider = Integer.parseInt(theInput);
                break;
            case TEST_SET:
                selection = Selection.TEST;
                decider = Integer.parseInt(theInput);
                break;
        }
           
    
        switch(selection){
            case STATS:
                myView.showPlayerStats(myPlayer.getName(),myPlayer.getFood(),myPlayer.getWood(),myPlayer.getGold(),myPlayer.getIron(),myPlayer.getPopulation(),myPlayer.getMaxPopulation());
                break;
            case ENTITIES:
                updateAvailableStatus();
                myView.showEntities(myPlayer.myEntities);
                break;
            case CREATE:
                    if(option == -1){
                        myView.printMessage(
                            "\n"
                            + "0.- Habitant \n"
                            + "1.- Building \n"
                            + "Select an option:"
                        );
                        gameState = GameState.CHOOSING_ENTITY_TYPE_SET;
                        output = getNclean();
                        return output;
                    }
                    
                        //option = Integer.parseInt(input.next());
                    if(option == 0){
                        output = createHabitant(decider);
                        if(output == "exit"){
                            mainMenu();
                            gameState = GameState.MAIN_MENU_SET;
                            output = getNclean();
                            return output;
                        }
                        if(decider != -1){
                            output += createHabitant(-1);
                        }
                        return output;
                    }else if(option == 1){
                        output = createBuilding(decider);
                        if(output == "exit"){
                            mainMenu();
                            gameState = GameState.MAIN_MENU_SET;
                            output = getNclean();
                            return output;
                        }
                        if(decider != -1){
                            output += createBuilding(-1);
                        }
                        return output;
                    }else{
                        myView.printInvalidNumber();
                        gameState = GameState.CHOOSING_ENTITY_TYPE_SET;
                        myView.printMessage(
                            "\n"
                            + "0.- Habitant \n"
                            + "1.- Building \n"
                            + "Select an option:"
                        );
                        output = getNclean();
                        return output;
                    }

            case UPDATE:
                if(option == -1){
                    updateMenu();
                    gameState = GameState.CHOOSING_ENTITY_UPGRADE_SET;
                    output = getNclean();
                    return output;
                }
                //option = Integer.parseInt(input.next());
                int goBack = myPlayer.myEntities.size();
                if(option < 0 || option > goBack){
                    myView.printInvalidNumber();
                    gameState = GameState.CHOOSING_ENTITY_UPGRADE_SET;
                    updateMenu();
                }
                else if(option == goBack){
                    mainMenu();
                    gameState = GameState.MAIN_MENU_SET;
                }else{
                    upgradeEntity(option);
                    gameState = GameState.CHOOSING_ENTITY_UPGRADE_SET;
                    updateMenu();
                }
                output = getNclean();
                return output;
                
            case WAITING_TIMES:
                waitingTimes();
                break;
            case COLLECT:
                if(decider == -1){
                    collect(decider);
                    output = getNclean();
                    gameState = GameState.COLLECTING_SET;
                    return output;
                }
                collect(decider);
                output = getNclean();
                if(output.equals("exit")){
                    break;
                }
                else{
                    collect(-1);
                    output += getNclean();
                    gameState = GameState.COLLECTING_SET;
                    return output;
                }
                
            case FIGHT:
                if(decider == -1){
                    attack(decider);
                    output = getNclean();
                    gameState = GameState.FIGHTING_SET;
                    return output;
                }
                attack(decider);
                output = getNclean();
                if(output.equals("exit")){
                    break;
                }
                else{
                    attack(-1);
                    output += getNclean();
                    gameState = GameState.FIGHTING_SET;
                    return output;
                }
            case TEST:
                if(decider == -1){
                    testVillage(decider);
                    output = getNclean();
                    gameState = GameState.TEST_SET;
                    return output;
                }
                testVillage(decider);
                output = getNclean();
                if(output.equals("exit")){
                    break;
                }
                else{
                    testVillage(-1);
                    output += getNclean();
                    gameState = GameState.TEST_SET;
                    return output;
                }
            case EXIT:
                myView.clean();
                return "Game over";
            default:
                myView.printInvalidNumber();
                

        }
        
        mainMenu();
        gameState = GameState.MAIN_MENU_SET;
        
        output = getNclean();
        

        
        return output;
    }
    
    public void mainMenu(){
        if(myPlayer.getGuardTime().after(new Date())){
                Date actual = new Date();
                int time = ((int)(myPlayer.getGuardTime().getTime() - actual.getTime())/1000);
                myView.printMessage("\nGuard time of the village expires in " +time+ " seconds.");
            }else{
                myView.printMessage("\nThe guard time of the village has expired, it can be attacked.");
            }
        HomeVillageAttackThread attack = new HomeVillageAttackThread();
        attack.start();
        myView.printMainOptionMenu();
    }

    public void updateMenu(){
        myView.printMessage(
            "\n"
            + "An upgrade doubles the current stats. Upgrading VillageHall increseas max population. Costs for upgrading: \n"
        );
        updateAvailableStatus();
        myView.showUpgradeCosts(myPlayer.myEntities);
        int goBack = myPlayer.myEntities.size();
        myView.printMessage(goBack+ ".- Go Back" + "\nSelect entity to upgrade:");
    }
    public String getNclean(){
        String out = myView.getMessage();
        myView.clean();
        return out;
    }
    
    public boolean authenticate(String user){
        try {
            File myObj = new File("usernames.txt");
            try (Scanner myReader = new Scanner(myObj)) {
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    if(data.equals(user)){
                        return true;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return false;
    }
    
    
    public String createHabitant(int in) {
        int option = in;
        String output = null;
        String[] habitants = {"Collector","Worker","Soldier","Archer","Knight","Catapult"};
        if(in == -1){
            myView.printMessage("\nHabitant type to create:\n");
            for (int i = 0; i < habitants.length; i++) {
                myView.printMessage(i + ".-" + habitants[i] + " Gold:" + initialValues.get(habitants[i]).gold + " Iron:" + initialValues.get(habitants[i]).iron + " Wood:" + initialValues.get(habitants[i]).wood +"\n" );
            }
            myView.printMessage("6.-Go back\n");    
            myView.printMessage("Select an option:");
            gameState = GameState.CREATING_HABITANT_SET;
            output = getNclean();
            return output;
            
        }
            //option = Integer.parseInt(input.next());
            if(option < 0 || option > habitants.length){
                myView.printInvalidNumber();
                gameState = GameState.CREATING_HABITANT;
                output = getNclean();
                return output;
            }else{
                if(myPlayer.getPopulation() >= myPlayer.getMaxPopulation() && option != 6){
                    myView.printMessage("\nThere is no space for another habitant.\n");
                    gameState = GameState.MAIN_MENU_SET;
                    output = "exit";
                    return output;
                }
                String habType;
                HabitantType habitantType = HabitantType.Collector;
                boolean isArmy = true;
                switch(option){
                    case 0: //Collector
                        habType = "Collector";
                        habitantType = HabitantType.Collector;
                        isArmy = false;
                        break;
                    case 1: //Worker
                        habType = "Worker";
                        habitantType = HabitantType.Worker;
                        isArmy = false;
                        break;
                    case 2: //Soldier
                        habType = "Soldier";
                        habitantType = HabitantType.Soldier;
                        break;
                    case 3: //Archer
                        habType = "Archer";
                        habitantType = HabitantType.Archer;
                        break;
                    case 4: //Knight
                        habType = "Knight";
                        habitantType = HabitantType.Knight;
                        
                        break;
                    case 5: //Catapult
                        habType = "Catapult";
                        habitantType = HabitantType.Catapult;
                        break;
                    case 6:
                        gameState = GameState.MAIN_MENU_SET;
                        output = "exit";
                        return output;
                    default:
                        habType = "none";
                }
                if("none".equals(habType)){
                    myView.printInvalidNumber();
                    gameState = GameState.CREATING_HABITANT;
                    output = getNclean();
                    return output;
                }
                if(myPlayer.getGold() >= initialValues.get(habType).gold && myPlayer.getIron() >= initialValues.get(habType).iron && myPlayer.getWood() >= initialValues.get(habType).wood){
                    Entity entity;
                    EntityFactory entityFactory = new EntityFactory();
                    entity = entityFactory.getEntity(habType);
                    if(!isArmy){
                        entity.setValues(initialValues.get(habType).gold,0,0,0,habType,initialValues.get(habType).hp, myPlayer.getIdCount());
                        myPlayer.setIdCount(myPlayer.getIdCount()+1);
                        if("Collector".equals(entity.getName())){
                            myPlayer.unavailableCollectors.put(entity.getId(), timeUntilAvailable(entity.getGoldCost()*3));
                            myPlayer.setNumCollectors(myPlayer.getNumCollectors()+1);
                        }else if("Worker".equals(entity.getName())){
                            myPlayer.unavailableWorkers.put(entity.getId(), timeUntilAvailable(entity.getGoldCost()*3));
                            myPlayer.setNumWorkers(myPlayer.getNumWorkers()+1);
                        }
                        
                    }else{
                        entity.setValues(initialValues.get(habType).gold, initialValues.get(habType).iron, initialValues.get(habType).wood, initialValues.get(habType).attackPower,habType,initialValues.get(habType).hp, myPlayer.getIdCount());
                        myPlayer.setIdCount(myPlayer.getIdCount()+1);
                        myPlayer.setIron(myPlayer.getIron() - initialValues.get(habType).iron);
                        myPlayer.setWood(myPlayer.getWood() - initialValues.get(habType).wood);
                        
                        myPlayer.unavailableThings.put(entity.getId(), timeUntilAvailable(entity.getGoldCost()*4));
                       
                    }    
                    myPlayer.setGold(myPlayer.getGold() - initialValues.get(habType).gold);
                    myPlayer.setPopulation(myPlayer.getPopulation() + 1);
                    myPlayer.myEntities.add(entity);
                    entity.available(false);
                    myView.printMessage("\n" + habType + " is being created.\n");
                        
                }else{
                    myView.printMessage("\nYou do not have the required resources.\n");
                }
                
                gameState = GameState.CREATING_HABITANT;
                output = getNclean();
                return output;
            }
        
    }
    
    
    public Date timeUntilAvailable(int waitingSecs){
        Calendar date = Calendar.getInstance();
        long time = date.getTimeInMillis();
        Date newDate = new Date(time + (waitingSecs)*(1000));
        return newDate;
    }
    
    public void initialValues(){
        //List of initial values for the entity properties list
        initialValues.put("Collector",new InitialValues(2,0,0,5,0)); //gold, iron, wood, hp, att power
        initialValues.put("Worker",new InitialValues(4,0,0,10,0));
        initialValues.put("Soldier",new InitialValues(6,4,2,15,3));
        initialValues.put("Archer",new InitialValues(6,2,4,12,2));
        initialValues.put("Knight",new InitialValues(8,8,4,20,4));
        initialValues.put("Catapult",new InitialValues(10,5,10,30,5));
        
        initialValues.put("VillageHall",new InitialValues(0,0,0,100,0));
        initialValues.put("Farm",new InitialValues(3,3,3,30,0));
        initialValues.put("GoldMine",new InitialValues(3,4,5,30,0));
        initialValues.put("IronMine",new InitialValues(5,3,4,30,0));
        initialValues.put("LumberHill",new InitialValues(4,5,3,30,0));
        initialValues.put("ArcherTower",new InitialValues(8,8,8,50,5));
        initialValues.put("Cannon",new InitialValues(8,10,8,50,6));
    }

    public String createBuilding(int in) {
        int option = in;
        String[] buildings = {"Farm","GoldMine","IronMine","LumberHill","ArcherTower","Cannon"};
        String output;
        if(in == -1){
            myView.printMessage("\nBuilding type to create:\n");
            for (int i = 0; i < buildings.length; i++) {
                myView.printMessage(i + ".-" + buildings[i] + " Gold:" + initialValues.get(buildings[i]).gold + " Iron:" + initialValues.get(buildings[i]).iron + " Wood:" + initialValues.get(buildings[i]).wood +"\n" );
            }
            myView.printMessage("6.-Go back\n");    
            myView.printMessage("Select an option:");
            gameState = GameState.CHOOSING_BUILDING_SET;
            output = getNclean();
            return output;
        }
            //option = Integer.parseInt(input.next());
            if(option < 0 || option > buildings.length){
                myView.printInvalidNumber();
                gameState = GameState.CHOOSING_BUILDING;
                output = getNclean();
                return output;
            }else{
                String buildType;
                BuildingType buildingType = BuildingType.Farm;
                boolean isArmy = true;
                switch(option){
                    case 0: 
                        buildType = "Farm";
                        buildingType = BuildingType.Farm;
                        isArmy = false;
                        break;
                    case 1: 
                        buildType = "GoldMine";
                        buildingType = BuildingType.GoldMine;
                        isArmy = false;
                        break;
                    case 2: 
                        buildType = "IronMine";
                        buildingType = BuildingType.IronMine;
                        isArmy = false;
                        break;
                    case 3: 
                        buildType = "LumberHill";
                        buildingType = BuildingType.LumberHill;
                        isArmy = false;
                        break;
                    case 4: 
                        buildType = "ArcherTower";
                        buildingType = BuildingType.ArcherTower;
                        break;
                    case 5: 
                        buildType = "Cannon";
                        buildingType = BuildingType.Cannon;
                        break;
                    case 6:
                        gameState = GameState.MAIN_MENU_SET;
                        output = "exit";
                        return output;
                    default:
                        buildType = "none";
                }
                if("none".equals(buildType)){
                    myView.printInvalidNumber();
                    gameState = GameState.CHOOSING_BUILDING;
                    output = getNclean();
                    return output;
                }
                if(!isThereAvailableWorker()) {
                    myView.printMessage("\nThere are no available workers.\n");
                    gameState = GameState.CHOOSING_BUILDING;
                    output = getNclean();
                    return output;
                }
                if(myPlayer.getGold() >= initialValues.get(buildType).gold && myPlayer.getIron() >= initialValues.get(buildType).iron && myPlayer.getWood() >= initialValues.get(buildType).wood){
                    Entity entity;
                    EntityFactory entityFactory = new EntityFactory();
                    entity = entityFactory.getEntity(buildType);
                    if(!isArmy){
                        entity.setValues(initialValues.get(buildType).gold,initialValues.get(buildType).iron,initialValues.get(buildType).wood, 0,buildType,initialValues.get(buildType).hp, myPlayer.getIdCount());
                        myPlayer.setIdCount(myPlayer.getIdCount()+1);
                        myPlayer.toBeCollected.put(entity.getId(), timeUntilAvailable(entity.getGoldCost() + ProductionBuilding.INITIAL_PRODUCTION_TIME));
                    }else{
                        entity.setValues(initialValues.get(buildType).gold,initialValues.get(buildType).iron,initialValues.get(buildType).wood, initialValues.get(buildType).attackPower,buildType,initialValues.get(buildType).hp, myPlayer.getIdCount());
                        myPlayer.setIdCount(myPlayer.getIdCount()+1);
                    }    
                    myPlayer.setGold(myPlayer.getGold() - initialValues.get(buildType).gold);
                    myPlayer.setIron(myPlayer.getIron() - initialValues.get(buildType).iron);
                    myPlayer.setWood(myPlayer.getWood() - initialValues.get(buildType).wood);
                    
                    myPlayer.unavailableThings.put(entity.getId(), timeUntilAvailable(entity.getGoldCost()*4));
                    myPlayer.unavailableWorkers.put(getAvailableWorker().getId(), timeUntilAvailable(entity.getGoldCost()*4));
                    getAvailableWorker().available(false);
                    
                    entity.available(false);
                        
                    myPlayer.myEntities.add(entity);
                    myView.printMessage("\n" + buildType + " is being created.\n");
                        
                }else{
                    myView.printMessage("\nYou do not have the required resources.\n");
                }
                
                gameState = GameState.CHOOSING_BUILDING;
                output = getNclean();
                return output;
            }
        
    }
    
    public void upgradeEntity(int id){
        Entity entity = myPlayer.myEntities.get(id);
        if(id < 0 || id >= myPlayer.myEntities.size()){
            return;
        }
        if(!entity.isAvailable()){
            myView.printMessage("\nThe entity is not available right now.\n");
            return;
        }
        
        if(entity.getName().equals("Farm") || entity.getName().equals("GoldMine") || entity.getName().equals("IronMine") || entity.getName().equals("LumberHill") || 
                    entity.getName().equals("ArcherTower") || entity.getName().equals("Cannon") || entity.getName().equals("VillageHall") )
        {
            if(!isThereAvailableWorker()) {
                myView.printMessage("\nThere are no available workers.\n");
                return;
            }        
        }
        
        if(id == 0){
            int foodCost = (100 + 200*(entity.getLevel() - 1));
            
            if(myPlayer.getFood() >= foodCost){
                if(entity.getLevel() >= entity.getMaxLevel()){
                    myView.printMessage("\nEntity is at max level.\n");
                    return;
                }
                entity.upgrade();
                myPlayer.setFood(myPlayer.getFood() - foodCost);
                myPlayer.setMaxPopulation(myPlayer.getMaxPopulation() + 5);
                myPlayer.unavailableThings.put(entity.getId(), timeUntilAvailable(foodCost));
                entity.available(false);
                myPlayer.unavailableWorkers.put(getAvailableWorker().getId(), timeUntilAvailable(foodCost));
                getAvailableWorker().available(false);

            }else{
                myView.printMessage("\nYou do not have the required resources.\n");
                return;
            }
        }
        else if(myPlayer.getGold() >= entity.getGoldCost()*2 && myPlayer.getIron() >= entity.getIronCost()*2 && myPlayer.getWood() >= entity.getWoodCost()*2){

            if(entity.getLevel() >= entity.getMaxLevel()){
                myView.printMessage("\nEntity is at max level.\n");
                return;
            }
            entity.upgrade();
            myPlayer.setGold(myPlayer.getGold() - entity.getGoldCost()*2);
            myPlayer.setIron(myPlayer.getIron() - entity.getIronCost()*2);
            myPlayer.setWood(myPlayer.getWood() - entity.getWoodCost()*2);

            if("Collector".equals(entity.getName())){
                myPlayer.unavailableCollectors.put(entity.getId(), timeUntilAvailable(entity.getGoldCost()*4));
            }else if("Worker".equals(entity.getName())){
                myPlayer.unavailableWorkers.put(entity.getId(), timeUntilAvailable(entity.getGoldCost()*4));
            }else{
                myPlayer.unavailableThings.put(entity.getId(), timeUntilAvailable(entity.getGoldCost()*4));
            }
            if(entity.getName().equals("Farm") || entity.getName().equals("GoldMine") || entity.getName().equals("IronMine") || entity.getName().equals("LumberHill")){
                myPlayer.toBeCollected.put(entity.getId(), timeUntilAvailable(entity.getGoldCost()*2 + ProductionBuilding.INITIAL_PRODUCTION_TIME));
            }
            
            if(entity.getName().equals("Farm") || entity.getName().equals("GoldMine") || entity.getName().equals("IronMine") || entity.getName().equals("LumberHill") || 
                    entity.getName().equals("ArcherTower") || entity.getName().equals("Cannon"))
            {
                        myPlayer.unavailableWorkers.put(getAvailableWorker().getId(), timeUntilAvailable(entity.getGoldCost()*4));
                        getAvailableWorker().available(false);
            }
            
            entity.available(false);
            
                
        }else{
            myView.printMessage("\nYou do not have the required resources.\n");
            return;
        }
        myView.printMessage("\n" + entity.getName() + " with id:" + entity.getId() + " is being upgraded to level " + entity.getLevel()+"\n");
    }
    
    public void updateAvailableStatus(){
        myPlayer.myEntities.stream().filter((entity) -> (!entity.isAvailable())).forEachOrdered((entity) -> {
            boolean isNowAvailable;
            if("Collector".equals(entity.getName())){
                isNowAvailable = myPlayer.unavailableCollectors.get(entity.getId()).before(new Date());
            }else if("Worker".equals(entity.getName())){
                isNowAvailable = myPlayer.unavailableWorkers.get(entity.getId()).before(new Date());
            }else{
                isNowAvailable = myPlayer.unavailableThings.get(entity.getId()).before(new Date());
            }
            entity.available(isNowAvailable);
        });
    }
    
    public boolean isThereAvailableWorker(){
        updateAvailableStatus();
        int busyWorkers = 0;
        Entity entity;
        for (Map.Entry<Integer, Date> entry : myPlayer.unavailableWorkers.entrySet()) {
            entity = myPlayer.myEntities.get(entry.getKey());
            if(!entity.isAvailable()){
                busyWorkers++;
            }
	}
        return busyWorkers < myPlayer.getNumWorkers();
    }
    
    public Entity getAvailableWorker(){
        Entity ent = null;
        for (Entity entity : myPlayer.myEntities) {
            if(entity.getName().equals("Worker") && entity.isAvailable()){
                ent = entity;
            }
        }
        return ent;
    }
    
     public boolean isThereAvailableCollector(){
        updateAvailableStatus();
        int busyCollectors = 0;
        Entity entity;
        for (Map.Entry<Integer, Date> entry : myPlayer.unavailableCollectors.entrySet()) {
            entity = myPlayer.myEntities.get(entry.getKey());
            if(!entity.isAvailable()){
                busyCollectors++;
            }
	}
        return busyCollectors < myPlayer.getNumCollectors();
    }
    
    public Entity getAvailableCollector(){
        Entity ent = null;
        for (Entity entity : myPlayer.myEntities) {
            if(entity.getName().equals("Collector") && entity.isAvailable()){
                ent = entity;
            }
        }
        return ent;
    }
    
    public void waitingTimes(){
        updateAvailableStatus();
        myView.printMessage("\nWaiting times for entities in creation or in upgrade:\n");
        if(myPlayer.unavailableCollectors.isEmpty() && myPlayer.unavailableWorkers.isEmpty() && myPlayer.unavailableThings.isEmpty()){
            myView.printMessage("There is currently no entity with a creation or upgrade waiting time.\n");
            return;
        }
        int counter = 0;
        for (Entity entity : myPlayer.myEntities) {
            if(!entity.isAvailable()){
                int time;
                Date value;
                Date actual = new Date();
                if(entity.getName().equals("Collector")){
                    value = myPlayer.unavailableCollectors.get(entity.getId());
                }else if(entity.getName().equals("Worker")){
                    value = myPlayer.unavailableWorkers.get(entity.getId());
                }else{
                    value = myPlayer.unavailableThings.get(entity.getId());
                }
                time = ((int)(value.getTime() - actual.getTime())/1000);
                myView.printMessage("Id:" + entity.getId() + " Name:" + entity.getName() + " Available in:" + time + "secs\n");
                counter++;
            }
        }
        if(counter == 0){
            myView.printMessage("There is currently no entity with a creation or upgrade waiting time.\n");
        }
    }
    
    public void collect(int in){
        int option = in;
        if(option == -1){
            myView.printMessage(
                "\nSelect production Building ID to collect:"
                + "\nId:0 To Go back"
            );
            
            myView.showCollectables(myPlayer.myEntities,myPlayer.toBeCollected);
            
            
            if(myPlayer.toBeCollected.isEmpty()){
                myView.printMessage("\nThere is nothing collectable.");
                
            }
            myView.printMessage("\nSelect building ID:");
            return;
        }
            
            
            if(option == 0){
                myView.printMessage("exit");
                return;
            }
             
            if(myPlayer.toBeCollected.get(option) != null ){
                
                if(myPlayer.toBeCollected.get(option).after(new Date())){
                    myView.printMessage("\nProduction is not ready to be collected.\n");
                    return;
                }
                if(!isThereAvailableCollector()){
                    myView.printMessage("\nThere is no available collector.\n");
                    return;
                }
                
                Entity entity = myPlayer.myEntities.get(option);
                myPlayer.toBeCollected.put(option,timeUntilAvailable(ProductionBuilding.INITIAL_PRODUCTION_TIME * entity.getLevel()));
                switch(entity.getName()){
                    case "Farm":
                        myPlayer.setFood(myPlayer.getFood() + ProductionBuilding.INITIAL_CAPACITY * entity.getLevel() );
                        break;
                    case "GoldMine":
                        myPlayer.setGold(myPlayer.getGold() + ProductionBuilding.INITIAL_CAPACITY * entity.getLevel() );
                        break;
                    case "IronMine":
                        myPlayer.setIron(myPlayer.getIron() + ProductionBuilding.INITIAL_CAPACITY * entity.getLevel() );
                        break;
                    case "LumberHill":
                        myPlayer.setWood(myPlayer.getWood() + ProductionBuilding.INITIAL_CAPACITY * entity.getLevel() );
                        break;
                        
                }
                Entity ent = getAvailableCollector();
                myPlayer.unavailableCollectors.put(ent.getId(),timeUntilAvailable(50-(entity.getLevel()-1)*5));
                ent.available(false);
                
                myView.printMessage("\nProduction from building with Id:" + entity.getId() + " is being collected.\n");
                
            }else{
                myView.printInvalidNumber();
            }
        
    }
    

    public ArrayList<Entity> generateVillageToAttack(){
        ArrayList<Entity> enemyVillage = new ArrayList<>(); 
        Entity ent;
        EntityFactory entityFactory = new EntityFactory();
        String name = "";
        int level = 0,gold = 0, hp = 0, randomNum = 0, levelGain = 0, attackPower = 0;
        for (Entity entity : myPlayer.myEntities) {
            randomNum = ThreadLocalRandom.current().nextInt(0, 3);
            
            if(entity.getId()==0){
                randomNum = 1;
            }
            
            levelGain = ThreadLocalRandom.current().nextInt(0, 3);
            
            name = entity.getName();
            
            if(level == 1 && levelGain == 0){
                levelGain = 1;
            }
            
            level = entity.getLevel() - 1 + levelGain;
            gold = (entity.getGoldCost()/entity.getLevel()) * level;
            hp = (entity.getHitPoints()/entity.getLevel()) * level;
            if(entity.getAttackPower() != 0){
                attackPower = (entity.getAttackPower()/entity.getLevel()) * level;
            }else {
                attackPower = 0;
            }
            
            for(int i = 0; i < randomNum; i++){
                ent = entityFactory.getEntity(name);
                ent.setValues(gold,name,hp,level,attackPower);
                enemyVillage.add(ent);                
            }

        }
        return enemyVillage;
    }
    
    public ArrayList<Entity> generateVillageForTest(){
        ArrayList<Entity> enemyVillage = new ArrayList<>(); 
        Entity ent;
        EntityFactory entityFactory = new EntityFactory();
        String name = "";
        int level = 0,gold = 0, hp = 0, randomNum = 0, levelGain = 0, attackPower = 0;
        ent = entityFactory.getEntity("Soldier");
        ent.setValues(6,"Soldier",15,1,3);
        for (Entity entity : myPlayer.myEntities) {
            randomNum = ThreadLocalRandom.current().nextInt(0, 3);
            
            if(entity.getId()==0){
                randomNum = 1;
            }
            
            levelGain = ThreadLocalRandom.current().nextInt(0, 3);
            
            name = entity.getName();
            
            if(level == 1 && levelGain == 0){
                levelGain = 1;
            }
            
            level = entity.getLevel() - 1 + levelGain;
            gold = (entity.getGoldCost()/entity.getLevel()) * level;
            hp = (entity.getHitPoints()/entity.getLevel()) * level;
            if(entity.getAttackPower() != 0){
                attackPower = (entity.getAttackPower()/entity.getLevel()) * level;
            }else {
                attackPower = 0;
            }
            
            for(int i = 0; i < randomNum; i++){
                if(name.equals("ArcherTower")){
                    name = "Archer";
                }
                if(name.equals("Cannon")){
                    name = "Catapult";
                }
                ent = entityFactory.getEntity(name);
                ent.setValues(gold,name,hp,level,attackPower);
                
                if(attackPower>0){
                    enemyVillage.add(ent);                
                }
            }

        }
        return enemyVillage;
    }
    
    public void attack(int in){
        int option = in;
        
        ArrayList<Entity> enVillage;
        enVillage = generateVillageToAttack();


        int lootGold,lootIron,lootWood,lootFood, defScore, attScore; 
        lootGold = myPlayer.getGold() + ThreadLocalRandom.current().nextInt(-myPlayer.getGold()/3,myPlayer.getGold()/3);
        lootIron = myPlayer.getIron() + ThreadLocalRandom.current().nextInt(-myPlayer.getIron()/3,myPlayer.getIron()/3);
        lootWood = myPlayer.getWood() + ThreadLocalRandom.current().nextInt(-myPlayer.getWood()/3,myPlayer.getWood()/3);
        lootFood = myPlayer.getFood() + ThreadLocalRandom.current().nextInt(-myPlayer.getWood()/3,myPlayer.getWood()/3);

        ArrayList<Integer> attackerResources = new ArrayList<>(Arrays.asList(myPlayer.getGold(),myPlayer.getIron(),myPlayer.getWood(), myPlayer.getFood()));
        ArrayList<Integer> defenderResources = new ArrayList<>(Arrays.asList(lootGold,lootIron, lootWood,lootFood));


        FightDecider fightDecider;
        fightDecider = new FightAdapter(myPlayer.myEntities, enVillage, attackerResources, defenderResources);

        defScore = fightDecider.calculateDefenseScore();
        attScore = fightDecider.calculateAttackScore();
        
        if(option == -1){
            myView.showVillage(enVillage);
            myView.printMessage("\nSelect enemy village to attack.\n");
            myView.printMessage("Enemy's resources: Gold =" +lootGold+ " ,Iron =" +lootIron+ " ,Wood =" +lootWood+ ",Food =" +lootFood+"\n");
            myView.printMessage("Enemy's defense score =" + defScore+"\n" );
            myView.printMessage("Your attack score =" + attScore+"\n" );
            myView.printMessage(
                            "\n"
                            + "0.- Attack \n"
                            + "1.- Next \n"
                            + "2.- Exit \n"
                            + "Select an option:"
                        );
            return;
        }
            
            if(option == 0){
                ArrayList<Integer> lootedResources = fightDecider.simulateAttack();
                if(lootedResources == null){
                    myView.printMessage("\nThe enemy defended well. You lost the attack.\n");
                }else{
                    int goldWon, ironWon, woodWon,foodWon;
                    goldWon = lootedResources.get(0);
                    ironWon = lootedResources.get(1);
                    woodWon = lootedResources.get(2);
                    foodWon = lootedResources.get(3);
                    
                    myPlayer.setGold(myPlayer.getGold() + goldWon);
                    myPlayer.setIron(myPlayer.getIron() + ironWon);
                    myPlayer.setWood(myPlayer.getWood() + woodWon);
                    myPlayer.setFood(myPlayer.getFood() + foodWon);

                    myView.printMessage("\nYou defeated the enemy village.\n");
                    myView.printMessage("Loot obtained: Gold=" + goldWon+ " ,Iron=" +ironWon+ " ,Wood=" +woodWon+ " ,Food=" +foodWon+"\n");
                }
                return;
                
            }else if(option == 1){
                
            }else if(option == 2){
                myView.printMessage("exit");
            }else{
                myView.printInvalidNumber();
            }
        
        
    }
    
    public void testVillage(int in){
        int option = in;
        
        ArrayList<Entity> enVillage;
        enVillage = generateVillageForTest();


        int lootGold,lootIron,lootWood,lootFood, defScore, attScore; 
        lootGold = myPlayer.getGold() + ThreadLocalRandom.current().nextInt(-myPlayer.getGold()/3,myPlayer.getGold()/3);
        lootIron = myPlayer.getIron() + ThreadLocalRandom.current().nextInt(-myPlayer.getIron()/3,myPlayer.getIron()/3);
        lootWood = myPlayer.getWood() + ThreadLocalRandom.current().nextInt(-myPlayer.getWood()/3,myPlayer.getWood()/3);
        lootFood = myPlayer.getFood() + ThreadLocalRandom.current().nextInt(-myPlayer.getWood()/3,myPlayer.getWood()/3);

        ArrayList<Integer> attackerResources = new ArrayList<>(Arrays.asList(myPlayer.getGold(),myPlayer.getIron(),myPlayer.getWood(), myPlayer.getFood()));
        ArrayList<Integer> defenderResources = new ArrayList<>(Arrays.asList(lootGold,lootIron, lootWood,lootFood));


        FightDecider fightDecider;
        fightDecider = new FightAdapter(enVillage, myPlayer.myEntities, attackerResources, defenderResources);

        defScore = fightDecider.calculateDefenseScoreVillage();
        attScore = fightDecider.calculateAttackScore();
        
        if(option == -1){
            myView.showVillage(enVillage);
            myView.printMessage("\nSelect enemy army to test defense of village.\n");
            myView.printMessage("Enemy's attack score =" + attScore+"\n" );
            myView.printMessage("Your defense score =" + defScore+"\n" );
            myView.printMessage(
                            "\n"
                            + "0.- Test \n"
                            + "1.- Next \n"
                            + "2.- Exit \n"
                            + "Select an option:"
                        );
            return;
        }
            
            if(option == 0){
                ArrayList<Integer> lootedResources = fightDecider.simulateAttack();
                if(lootedResources == null){
                    myView.printMessage("\nYour village defended well.\n");
                }else{
                    int goldWon, ironWon, woodWon,foodWon;
                    goldWon = lootedResources.get(0);
                    ironWon = lootedResources.get(1);
                    woodWon = lootedResources.get(2);
                    foodWon = lootedResources.get(3);
                    
                    myPlayer.setGold(myPlayer.getGold() + goldWon);
                    myPlayer.setIron(myPlayer.getIron() + ironWon);
                    myPlayer.setWood(myPlayer.getWood() + woodWon);
                    myPlayer.setFood(myPlayer.getFood() + foodWon);

                    myView.printMessage("\nYour village lost against the attacking army.\n");
                    
                }
                return;
                
            }else if(option == 1){
                
            }else if(option == 2){
                myView.printMessage("exit");
            }else{
                myView.printInvalidNumber();
            }
        
        
    }
    
    public class HomeVillageAttackThread extends Thread{
        @Override
        public void run(){
            try{
                if(myPlayer.getGuardTime().before(new Date())){
                    int attSuccess = ThreadLocalRandom.current().nextInt(0, 101);
                    if(attSuccess <= 50){

                        class LootLost{
                            public int gold;
                            public int iron;
                            public int wood;
                            public int food;
                        }

                        LootLost lootLost = new LootLost();
                        lootLost.gold = (myPlayer.getGold()*attSuccess)/100;
                        lootLost.iron = (myPlayer.getIron()*attSuccess)/100;
                        lootLost.wood = (myPlayer.getWood()*attSuccess)/100;
                        lootLost.food = (myPlayer.getFood()*attSuccess)/100;

                        myPlayer.setGold(myPlayer.getGold() - lootLost.gold);
                        myPlayer.setWood(myPlayer.getWood() - lootLost.wood);
                        myPlayer.setIron(myPlayer.getIron() - lootLost.iron);
                        myPlayer.setFood(myPlayer.getFood() - lootLost.food);

                        myView.printMessage("\nYour village was attacked and looted! You lost: " +lootLost.gold+ " in gold, " +lootLost.iron+ " in iron, " +lootLost.wood+ " in wood, " +lootLost.food+ "in food.\n");
                        myView.printMessage("You cannot be attacked until your guard time expires   .\n");
                        myPlayer.setGuardTime(timeUntilAvailable(600));
                    }
                }
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }

}
