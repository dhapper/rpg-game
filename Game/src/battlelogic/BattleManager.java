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

    //private Player player;
    private Entity[] players;
    private ArrayList<BattleState> battleStates;
    private ArrayList<BattleState> turnOrderedBattleStates;

    private volatile boolean battleOver = false;
    
    private DamageCalculation damageCalc;
    private int battleType;
    private Battle battle;
    
    
    public BattleManager(Battle battle, Entity[] players, int battleType) {
    	this.battle = battle;
    	this.players = players;
    	this.battleType = battleType;
    	
    	
    	this.battleStates = new ArrayList<BattleState>();
    	
    	
    	initBattleStates();
    	battleStates.get(PLAYER).setPosition(LEFT_MAIN);
    	battleStates.get(NPC_1).setPosition(RIGHT_MAIN);
    	battleStates.get(PLAYER).setPlayerX(300);
    	battleStates.get(NPC_1).setPlayerX(Game.GAME_WIDTH - 300 - 300);
    	
        this.damageCalc = new DamageCalculation(this);
        
        
        // Start the battle loop in a separate thread
        new Thread(this).start();
    }

	@Override
    public void run() {
        battleLoop();
    }

    private void battleLoop() {
    	
        while (!battleOver) {

            if (!battleStates.get(0).getCurrMove().equals("NONE")) {
                
            	System.out.println("1");
            	
                //choose NPC moves
                for(BattleState bs : battleStates)
                	if(!bs.equals(battleStates.get(PLAYER)))
                		damageCalc.randomMove(bs);
                
                System.out.println("2");
                
                // calc speeds and order turns
                new SpeedCalculation(this, battleStates).calcSpeed();;
                turnOrderedBattleStates = getSortedBattleStatesBySpeed();
                
                //choose target - in damage for now
                //battleStates.get(PLAYER_1).setMoveTarget(PLAYER_2);
                
                System.out.println("3");
                
                ArrayList<String> turnMoves =  new ArrayList<String>();
                for(BattleState bs : battleStates) {
                	turnMoves.add(bs.getCurrMove());
                }
                battle.setTurnMoves(turnMoves);
                
                System.out.println("4");
                
	            // turn one damage
                for(BattleState bs : turnOrderedBattleStates) {
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
                }
	                	
                
                System.out.println("5");
                // death check
	        	// break
	    			
	    		// status effect
	    			
	    		// death check
	    		// break
                
                
                // log
                for(BattleState bs : turnOrderedBattleStates) {
                	System.out.println(bs.getEntity().getName()+"'s turn");
                	System.out.println("Speed: "+bs.getCurrSpeed());
                	System.out.println("Move: "+bs.getCurrMove());
                	System.out.println("Damage: "+bs.getCurrDamage());
                	System.out.println("Target: "+battleStates.get(bs.getMoveTarget()).getEntity().getName());

                	System.out.println();
                }
                
                battleStates.get(PLAYER).setCurrMove("NONE");
                battleStates.get(NPC_1).setCurrMove("NONE");
            }
        }
            
           // checkBattleOver();
        
    }

 

    private void checkBattleOver() {
    	
        //if (playerHealth <= 0 || enemyHealth <= 0) {
            battleOver = true;
            System.out.println("Battle Over!");
            GameState.state = GameState.OVERWORLD;
        //}
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
