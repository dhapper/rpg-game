package battlelogic;

import entities.Entity;
import entities.NPC;
import entities.Player;
import gamestates.Battle;
import gamestates.GameState;
import main.Game;

import static utilz.Constants.BattleConstants.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class BattleManager implements Runnable {

	private Battle battle;
    private Entity[] players;
    private ArrayList<BattleState> battleStates;
    private ArrayList<BattleState> turnOrderedBattleStates;
    private DamageCalculation damageCalc;
    private int battleType;
    
    private volatile boolean battleOver = false;
    
    public BattleManager(Battle battle, Entity[] players, int battleType) {
    	this.battle = battle;
    	this.players = players;
    	this.battleType = battleType;
    	
    	this.battleStates = new ArrayList<BattleState>();
    	initBattleStates();
    	
    	new InitBattleVars(this).initPlayerPos(battleType);
        this.damageCalc = new DamageCalculation(this);
        
        new Thread(this).start();
    }

	@Override
    public void run() {
        battleLoop();
    }

    private void battleLoop() {
    	
        while (!battleOver) {

            if (!battleStates.get(PLAYER).getCurrMove().equals("NONE") && battleStates.get(PLAYER).getMoveTarget() != -1) {
            	
            	
                // choose NPC moves - randomized for now
                for(BattleState bs : battleStates)
                	if(!bs.equals(battleStates.get(PLAYER)))
                		if(bs.isAlive())
                			damageCalc.randomMove(bs);
                
                
                // calculate speeds and order turns
                new SpeedCalculation(this, battleStates).calcSpeed();;
                turnOrderedBattleStates = getSortedBattleStatesBySpeed();
                
                // choose target - in damage for now
                
                
                for(BattleState bs : turnOrderedBattleStates)
		            if(!bs.getEntity().equals(battleStates.get(PLAYER).getEntity()))
		            	if(bs.isAlive())
		            		damageCalc.setTarget(bs);
                
                // do attacks
                for(BattleState bs : turnOrderedBattleStates) {
                	if(bs.isAlive()) {
                		battle.manageAnimations(bs);
                    	bs.setAnimating(true);
                    	damageCalc.damage(bs);	
                	
	                	while(bs.isAnimating()) {
	                		try {
								Thread.sleep(250);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
	                	}
	                	
	                	for(BattleState bs2 : battleStates)
                			if(bs2.isAlive()) {
                				checkIfDead(bs2);
                				checkBattleOver();
                			}
                	}
                }
	             
                
                
                
                battleStates.get(PLAYER).setCurrMove("NONE");
                battleStates.get(PLAYER).setMoveTarget(-1);
            }
        }
            
           // checkBattleOver();
        
    }

 
    private void checkIfDead(BattleState bs) {
    	if(bs.getStats()[HEALTH] <= 0) {
    		System.out.println(bs.getEntity().getName()+" fainted");
    		bs.setAlive(false);
    	}
    }
    

    private void checkBattleOver() {
    	
        if(!battleStates.get(PLAYER).isAlive()) {
            GameState.state = GameState.OVERWORLD;
            return;
        }
        
        switch(battleType) {
        case ONE_VS_ONE:
        	if(!battleStates.get(NPC_1).isAlive())
        		GameState.state = GameState.OVERWORLD;
        	break;
        case TWO_VS_ONE:
        	if(!battleStates.get(NPC_2).isAlive())
        		GameState.state = GameState.OVERWORLD;
        	break;
        case ONE_VS_TWO:
        	if(!battleStates.get(NPC_1).isAlive())
        		if(!battleStates.get(NPC_2).isAlive())
        			GameState.state = GameState.OVERWORLD;
        	break;
        case TWO_VS_TWO:
        	if(!battleStates.get(NPC_2).isAlive())
        		if(!battleStates.get(NPC_3).isAlive())
        			GameState.state = GameState.OVERWORLD;
        	break;
        }
    }
    
    
    private void initBattleStates() {
    	
    	for(Entity e: players)
    		battleStates.add(new BattleState(e));
		
	}
    
    public ArrayList<BattleState> getBattleStates() {
    	return battleStates;
    }
    
    public int getBattleType() {
    	return battleType;
    }
    
    public void setBattleType(int battleType) {
		this.battleType = battleType;
	}

	// Method to get sorted BattleState list by currSpeed in descending order
    public ArrayList<BattleState> getSortedBattleStatesBySpeed() {
        ArrayList<BattleState> sortedBattleStates = new ArrayList<>(battleStates);
        Collections.sort(sortedBattleStates, new Comparator<BattleState>() {
            @Override
            public int compare(BattleState bs1, BattleState bs2) {
                return Double.compare(bs2.getCurrSpeed(), bs1.getCurrSpeed());
            }
        });
        return sortedBattleStates;
    }

}
