package battle;

import static utilz.Constants.BattleConstants.*; 

import java.util.Random;

import utilz.Constants;
import utilz.Constants.BattleConstants.Moves;

public class DamageCalculation {

	private BattleManager battleManager;
	
	public DamageCalculation(BattleManager battleManager) {
		this.battleManager = battleManager;
	}
	
	public void setTarget(BattleState bs) {
		
		if(bs.getCurrMove().getTarget().equals("SELF")) {
			
			bs.setMoveTarget(bs.getPlayerID());
			
		}else {
		
			Random random = new Random();
		
			do {
				switch(battleManager.getBattleType()) {
				case ONE_VS_ONE:
					if(bs.getCurrMove().getTarget().equals("ENEMY"))
						bs.setMoveTarget(PLAYER);
					break;
				case ONE_VS_TWO:
					if(bs.getCurrMove().getTarget().equals("ENEMY"))
						bs.setMoveTarget(PLAYER);
					break;
				case TWO_VS_ONE:
					if(bs.isEnemyTeam()) {
						if(bs.getCurrMove().getTarget().equals("ENEMY")) {
							if(random.nextBoolean())
								bs.setMoveTarget(PLAYER);
							else
								bs.setMoveTarget(NPC_1);
						}
					}else {
						if(bs.getCurrMove().getTarget().equals("ENEMY"))
							bs.setMoveTarget(NPC_2);
					}
					break;
				case TWO_VS_TWO:
					if(bs.isEnemyTeam()) {
						if(bs.getCurrMove().getTarget().equals("ENEMY")) {
							if(random.nextBoolean())
								bs.setMoveTarget(PLAYER);
							else
								bs.setMoveTarget(NPC_1);
						}
					}else {
						if(bs.getCurrMove().getTarget().equals("ENEMY")) {
							if(random.nextBoolean())
								bs.setMoveTarget(NPC_2);
							else
								bs.setMoveTarget(NPC_3);
						}
					}
					break;
				}
			}while(!battleManager.getBattleStates().get(bs.getMoveTarget()).isAlive());
		}
		
	}
	
	public boolean isMoveValid(BattleState bs, Move move) {
		if(bs.getStats()[STAMINA] >= -move.getStaminaDelta()) {
			battleManager.getBattle().setMoveErrorMessage("NONE");
			bs.setCurrMove(move);
			battleManager.getBattle().setTargetButtons(true);
			return true;
		}else {
			battleManager.getBattle().setMoveErrorMessage("NOT ENOUGH STAMINA TO USE " + move.getName().toUpperCase());
			return false;
		}
	}
	
	public void randomMove(BattleState bs) {
		Random random = new Random();
		
		do{
			
			int choice = random.nextInt(4);
			
			if(choice == 0) {
				bs.setCurrMove(bs.getEntity().getActiveSword().getActiveMoves()[0]);
			}else if(choice == 1) {
				bs.setCurrMove(bs.getEntity().getActiveSword().getActiveMoves()[1]);
			}else if(choice == 2) {
				bs.setCurrMove(bs.getEntity().getActiveSword().getActiveMoves()[2]);
			}else if(choice == 3) {
				bs.setCurrMove(bs.getEntity().getActiveSword().getActiveMoves()[3]);
			}else if(choice == 4) {
				bs.setCurrMove(Moves.SWAP);
			}
			
		}while (!isMoveValid(bs, bs.getCurrMove()));
		
	}
	
	public void damage(BattleState bs) {
		
		if(bs.getCurrMove().equals(Moves.SWAP)) {
    		bs.getEntity().swapActiveSword();
    		bs.getEntity().loadNormalCharacterAnimations(bs.getEntity().getBodyFileName(), bs.getEntity().getHairFileName(),
    				bs.getEntity().getActiveSword().getFileName(), bs.getEntity().getActiveShield().getFileName(),  bs.getEntity().getActiveArmour().getFileName());
    	}
		
		Move move = bs.getCurrMove();
		
		if(move.getAnimationID() == Constants.PlayerConstants.BLOCKING) {
			bs.setBlockingStance(true);
			bs.setDefensiveMoveQuantity(bs.getCurrMove().getNumOfHits());
		}
		
		double damage = 0;
		BattleState target = battleManager.getBattleStates().get(bs.getMoveTarget());
		
		for(int i = 0; i < move.getNumOfHits(); i++) {
			
			double hitDamage = bs.getStats()[STRENGTH] + bs.getEntity().getActiveSword().getDamage() * move.getDamage() / 100.0;
			System.out.println(bs.getStats()[STRENGTH] + " | " + bs.getEntity().getActiveSword().getDamage() + " | " +move.getDamage());
			if(bs.getCurrSpeed() < target.getCurrSpeed() && bs.getCurrMove().getAnimationType().equals("ATTACKING")) {
				if(target.getDefensiveMoveQuantity() > 0) {
					
					hitDamage -= hitDamage * target.getCurrMove().getNullify() / 100.0;
					
					target.setDefensiveMoveQuantity(battleManager.getBattleStates().get(bs.getMoveTarget()).getDefensiveMoveQuantity() - 1);
					
				}
			}
			
			damage += hitDamage;
			
		}
		
		
		damage *= multiplier(bs.getStats()[ATTACK_MULTIPLIER]);
		
		
		target.setPrevHealth(battleManager.getBattleStates().get(bs.getMoveTarget()).getStats()[HEALTH]);
		damage = Math.min(damage, target.getStats()[HEALTH]);
		target.removeHealth((int) damage);
		
		int staminaUsed = bs.getCurrMove().getStaminaDelta();
		int currStamina = bs.getStats()[STAMINA];
		int finalStamina = currStamina + staminaUsed;
		finalStamina = Math.min(finalStamina, bs.getMaxStamina());
		bs.setPrevStamina(bs.getStats()[STAMINA]);
		bs.changeStat(STAMINA, finalStamina);
		
	}
	
	public float multiplier(int statChangeValue) {
		
		switch(statChangeValue) {
		
		case 4:
			return 3;
		case 3:
			return 2.5f;
		case 2:
			return 2;
		case 1:
			return 1.5f;
		case 0:
			return 1;
		case -1:
			return 0.5f;
		case -2:
			return 0.25f;
		case -3:
			return (float) (1.0/8.0);
		case -4:
			return (float) (1.0/16.0);
		}
		
		return 0;
		
	}
	
}
