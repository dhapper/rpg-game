package battlelogic;

import static utilz.Constants.BattleConstants.*;

import java.util.Random;

public class DamageCalculation {

	private BattleManager battleManager;
	
	public DamageCalculation(BattleManager battleManager) {
		this.battleManager = battleManager;
	}
	
	public void randomMove(BattleState battleState) {
		Random random = new Random();
		
		int choice = random.nextInt(3);
		
		if(choice == 0) {
			battleState.setCurrMove("SWING");
		}else if(choice == 1) {
			battleState.setCurrMove("JAB");
		}else if(choice == 2) {
			battleState.setCurrMove("DOUBLE HIT");
		}
	}
	
	public void damage(BattleState battleState) {
		
		Random random = new Random();
		
		if(battleManager.getBattleType() == ONE_VS_ONE) {
			battleManager.getBattleStates().get(PLAYER).setMoveTarget(NPC_1);
			battleManager.getBattleStates().get(NPC_1).setMoveTarget(PLAYER);
			
			
			
		}else if(battleManager.getBattleType() == ONE_VS_TWO) {
			//battleManager.getBattleStates().get(PLAYER_2).setMoveTarget(PLAYER_1);
			//battleManager.getBattleStates().get(PLAYER_3).setMoveTarget(PLAYER_1);
			
			if(random.nextBoolean()) {
				//battleManager.getBattleStates().get(PLAYER_1).setMoveTarget(PLAYER_2);
			}else {
				//battleManager.getBattleStates().get(PLAYER_1).setMoveTarget(PLAYER_3);
			}
				
			
		}else if(battleManager.getBattleType() == TWO_VS_ONE) {
			//battleManager.getBattleStates().get(PLAYER_1).setMoveTarget(PLAYER_3);
			//battleManager.getBattleStates().get(PLAYER_2).setMoveTarget(PLAYER_3);
			
			if(random.nextBoolean()) {
				//battleManager.getBattleStates().get(PLAYER_3).setMoveTarget(PLAYER_1);
			}else {
				//battleManager.getBattleStates().get(PLAYER_3).setMoveTarget(PLAYER_2);
			}
		}
		
		
		Move move = new Move(battleState.getCurrMove());
				
				
		double damage = battleState.getStats()[STRENGTH] * move.getDamage() / 100.0;
		battleState.setCurrDamage((int) damage);
		
		battleManager.getBattleStates().get(battleState.getMoveTarget()).removeHealth((int) damage);
		
		//System.out.println(battleState.getEntity().getName()+"'s Move: "+move.getName());
		//System.out.println("Damage Dealt: "+damage);
		//System.out.println();
	}
	
}
