package game;

import ChallengeDecision.Arbitrer;
import ChallengeDecision.ChallengeAttack;
import ChallengeDecision.ChallengeDefense;
import ChallengeDecision.ChallengeEntitySet;
import ChallengeDecision.ChallengeResource;
import ChallengeDecision.ChallengeResult;
import entities.Entity;
import java.util.ArrayList;
import java.util.List;

public class FightAdapter extends FightDecider{
    
    ArrayList<Double> attackerResourcesD;
    ArrayList<Double> defenderResourcesD;
    
    public FightAdapter(ArrayList<Entity> Attacker_, ArrayList<Entity> Defender_, ArrayList<Integer> attackerResources_, ArrayList<Integer> defenderResources_){
        attacker = Attacker_;
        defender = Defender_;
        attackerResources = attackerResources_;
        defenderResources = defenderResources_;
        attackerResourcesD = new ArrayList<>();
        defenderResourcesD = new ArrayList<>();
        
        for(Integer value : attackerResources){
            attackerResourcesD.add(Double.valueOf(value));
        }
        for(Integer value : defenderResources){
            defenderResourcesD.add(Double.valueOf(value));
        }
    }
    
    @Override
    public ArrayList<Integer> simulateAttack(){
        ChallengeResult challengeResult;
        ChallengeEntitySet challenger = createChallengeEntitySet(attacker, attackerResourcesD);
        ChallengeEntitySet challengee = createChallengeEntitySet(defender, defenderResourcesD);
        
        ArrayList<Integer> lootedResources = new ArrayList<>();

        challengeResult = Arbitrer.challengeDecide(challenger, challengee);
        
        if(challengeResult.getChallengeWon()){
            for(ChallengeResource resource : challengeResult.getLoot()){
                double aux = (double)resource.getProperty();
                lootedResources.add((int)aux);
            }
            return lootedResources;
        }else{
            return null;
        }
    }
    
    private ChallengeEntitySet createChallengeEntitySet(ArrayList<Entity> village, ArrayList<Double> resources){
        List<ChallengeAttack<Double,Double>> entityAttackList = new ArrayList<>();
        List<ChallengeDefense<Double,Double>> entityDefenseList = new ArrayList<>();
        List<ChallengeResource<Double,Double>> entityResourceList = new ArrayList<>();
        
        String name;
        for (Entity entity : village) {
            name = entity.getName();
            if(name.equals("Soldier") || name.equals("Archer") || name.equals("Knight") || name.equals("Catapult")){
                entityAttackList.add(new ChallengeAttack(Double.valueOf(entity.getAttackPower()),Double.valueOf(entity.getHitPoints())));
            }
            else if(name.equals("ArcherTower") || name.equals("Cannon")){
                entityDefenseList.add(new ChallengeDefense(Double.valueOf(entity.getAttackPower()),Double.valueOf(entity.getHitPoints())));
            }
        }
        
        for (Double element : resources){
            entityResourceList.add(new ChallengeResource(element));
        }
        
        return new ChallengeEntitySet(entityAttackList,entityDefenseList,entityResourceList);
    }
}
