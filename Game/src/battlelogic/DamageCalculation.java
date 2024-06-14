package battlelogic;

import static utilz.Constants.BattleConstants.*;

import java.util.Random;

public class DamageCalculation {

	private BattleManager battleManager;
	
	public DamageCalculation(BattleManager battleManager) {
		this.battleManager = battleManager;
	}
	
	public void setTarget(BattleState battleState) {
		battleState.setMoveTarget(PLAYER);
	}
	
	public void randomMove(BattleState battleState) {
		Random random = new Random();
		
		int choice = random.nextInt(3);
		
		if(choice == 0) {
			battleState.setCurrMove(battleState.getEntity().getActiveSword().getActiveMoves()[0].getName());
		}else if(choice == 1) {
			battleState.setCurrMove(battleState.getEntity().getActiveSword().getActiveMoves()[1].getName());
		}else if(choice == 2) {
			battleState.setCurrMove(battleState.getEntity().getActiveSword().getActiveMoves()[2].getName());
		}else if(choice == 3) {
			battleState.setCurrMove(battleState.getEntity().getActiveSword().getActiveMoves()[3].getName());
		}
	}
	
	public void damage(BattleState battleState) {
		
		Random random = new Random();
		  
//		if(battleManager.getBattleType() == ONE_VS_ONE) {
//			battleManager.getBattleStates().get(PLAYER).setMoveTarget(NPC_1);
//			battleManager.getBattleStates().get(NPC_1).setMoveTarget(PLAYER);
//			
//		}else if(battleManager.getBattleType() == ONE_VS_TWO) {
//			battleManager.getBattleStates().get(PLAYER).setMoveTarget(NPC_1);
//			
//			battleManager.getBattleStates().get(NPC_1).setMoveTarget(PLAYER);
//			battleManager.getBattleStates().get(NPC_2).setMoveTarget(PLAYER);
//		}else if(battleManager.getBattleType() == TWO_VS_ONE) {
//			
//		}
		
		
		Move move = new Move(battleState.getCurrMove());
				
				
		double damage = battleState.getStats()[STRENGTH] * move.getDamage() / 100.0;
		battleState.setCurrDamage((int) damage);
		
		battleManager.getBattleStates().get(battleState.getMoveTarget()).removeHealth((int) damage);
		
		//System.out.println(battleState.getEntity().getName()+"'s Move: "+move.getName());
		//System.out.println("Damage Dealt: "+damage);
		//System.out.println();
	}
	
}
