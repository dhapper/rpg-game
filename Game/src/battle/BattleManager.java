package battle;

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

            if ((!battleStates.get(PLAYER).getCurrMove().equals(Moves.NONE) && battleStates.get(PLAYER).getMoveTarget() != -1)
            		|| battleStates.get(PLAYER).getCurrMove().equals(Moves.SWAP)) {
            	
            	// setting target as player if target value is invalid
            	if(battleStates.get(PLAYER).getMoveTarget() == -1) {
            		battleStates.get(PLAYER).setMoveTarget(PLAYER);
            	}
            	
                // choose NPC moves - randomized for now
            	// swap logic
                for(BattleState bs : battleStates) {
                	if(!bs.equals(battleStates.get(PLAYER)))
                		if(bs.isAlive())
                			damageCalc.randomMove(bs);
                
//                	if(bs.getCurrMove().equals(Moves.SWAP)) {
//                		bs.getEntity().swapActiveSword();
//                		bs.getEntity().loadNormalCharacterAnimations(bs.getEntity().getBodyFileName(), bs.getEntity().getHairFileName(),
//                				bs.getEntity().getActiveSword().getFileName(), bs.getEntity().getActiveShield().getFileName());
//                	}
                		
                }
                
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
	                	
	                	for(BattleState bs2 : battleStates) {
                			if(bs2.isAlive()) {
                				checkIfDead(bs2);
                				checkBattleOver();
                			}
                			
	                	}
                	}
                }
	             
                
                // reset vars
                for(BattleState bs : turnOrderedBattleStates) {
                	//.battleLog(bs);
                	bs.setBlockingStance(false);
                	bs.setDefensiveMoveQuantity(0);
                }
                
                battleStates.get(PLAYER).setCurrMove(Moves.NONE);
                battleStates.get(PLAYER).setMoveTarget(-1);
            }
        }
        
    }

    public void battleLog(BattleState bs) {
    	System.out.println(bs.getEntity().getName());
    	System.out.println("speed: "+bs.getCurrSpeed());
    	System.out.println("move: "+bs.getCurrMove().getName());
    	if(bs.getCurrMove().getAnimationType().equals("ATTACK")) {
    		System.out.println("target: "+battleStates.get(bs.getMoveTarget()).getEntity().getName());
    		System.out.println("hits: "+bs.getCurrMove().getNumOfHits());
    		System.out.println("damage: "+bs.getCurrDamage());
    		
    	}else if(bs.getCurrMove().getAnimationType().equals("BLOCKING")) {
    		System.out.println("defense counter: "+bs.getDefensiveMoveQuantity());
    	}
    	
    	System.out.println();
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

	public DamageCalculation getDamageCalc() {
		return damageCalc;
	}

	public void setDamageCalc(DamageCalculation damageCalc) {
		this.damageCalc = damageCalc;
	}

	public Battle getBattle() {
		return battle;
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
