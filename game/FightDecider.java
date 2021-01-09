package game;

import entities.Entity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class FightDecider {
    
    protected ArrayList<Entity> attacker;
    protected ArrayList<Entity> defender;
    protected ArrayList<Integer> attackerResources;
    protected ArrayList<Integer> defenderResources;
    
    public FightDecider(){
        this.attacker = null;
        this.defender = null;
    }
    public FightDecider(ArrayList<Entity> Attacker, ArrayList<Entity> Defender, ArrayList<Integer> attackerResources, ArrayList<Integer> defenderResources){
        this.attacker = Attacker;
        this.defender = Defender;
        this.attackerResources = attackerResources;
        this.defenderResources = defenderResources;
    }
    
    public int calculateAttackScore(){
        String name;
        int attScore = 0;
        for (Entity entity : attacker) {
            name = entity.getName();
            if(name.equals("Soldier") || name.equals("Archer") || name.equals("Knight") || name.equals("Catapult")){
                attScore += entity.getAttackPower();
            }
        }
        return attScore * 20;
    }

    public int calculateDefenseScore() {
        String name;
        int defScore = 0;
        for (Entity entity : defender) {
            name = entity.getName();
            if(name.equals("ArcherTower") || name.equals("Cannon")){
                defScore += entity.getAttackPower() * 10 + entity.getHitPoints();
            }
        }
        defScore += ThreadLocalRandom.current().nextInt(30, 151);
        return defScore;
    }
    
    public int calculateDefenseScoreVillage() {
        String name;
        int defScore = 0;
        for (Entity entity : defender) {
            name = entity.getName();
            if(name.equals("ArcherTower") || name.equals("Cannon")){
                defScore += entity.getAttackPower() * 10 + entity.getHitPoints();
            }
        }
        defScore += 50;
        return defScore;
    }
    
    public ArrayList<Integer> simulateAttack(){
        int defenderScore = calculateDefenseScore();
        int attackerScore = calculateAttackScore();
        int random = ThreadLocalRandom.current().nextInt(0, defenderScore+attackerScore);
        int aux;
        ArrayList<Integer> lootedResources;
        if(random > defenderScore){
            aux = ((random - defenderScore) * 75) / attackerScore + 75; 
        }else{
            aux = ((defenderScore - random) * 75) / defenderScore;
        }
        if(aux == 0){
            aux = 1;
        }
        
        if(aux <= 75){
            lootedResources = null;
        }else{
            lootedResources = new ArrayList<>(Arrays.asList((defenderResources.get(0)* (aux-75)) / 100,(defenderResources.get(1)* (aux-75)) / 100, (defenderResources.get(2)* (aux-75)) / 100,(defenderResources.get(3)* (aux-75)) / 100));
        }
        return lootedResources;
    }
}
