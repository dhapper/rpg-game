package battle;

import static utilz.Constants.BattleConstants.*;

import java.awt.Color;
import java.util.Random;

import utilz.Constants;
import utilz.Constants.BattleConstants.Moves;

public class ProcessMove {

	private BattleManager battleManager;
	
	public ProcessMove(BattleManager battleManager) {
		this.battleManager = battleManager;
	}
	
	public void doMoveAction(BattleState bs) {
		
		Move move = bs.getCurrMove();
		BattleState target = battleManager.getBattleStates().get(bs.getMoveTarget());
		
		// SWAP
		if(bs.getCurrMove().equals(Moves.SWAP)) {
    		bs.getEntity().swapActiveSword();
    		bs.getEntity().loadNormalCharacterAnimations();
    	}
		
		// BLOCKING
		if(move.getMoveType().equals("BLOCKING")) {
			bs.setBlockingStance(true);
			bs.setDefensiveMoveQuantity(move.getNumOfBlocks());
		}
		
		// DAMAGING
		if(move.getMoveType().equals("DAMAGING")) {
			double damage = 0;
			for(int i = 0; i < move.getNumOfHits(); i++) {
				double hitDamage = bs.getStats()[STRENGTH] + bs.getEntity().getActiveSword().getDamage() * move.getDamage() / 100.0;
				if(bs.getCurrSpeed() < target.getCurrSpeed() && bs.getCurrMove().getMoveType().equals("DAMAGING")) {
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
		}
		
		// STAT CHANGING
		if(move.getMoveType().equals("STAT_CHANGE")) {
			if(bs.getEntity().getName() == "dhaarsh")
				System.out.println(bs.getStats()[ATTACK_MULTIPLIER] + " | " + bs.getStats()[DEFENSE_MULTIPLIER] );
			int statIndex = -1;
			if(target.getCurrMove().getStatName().equals("ATTACK")) 
				statIndex = ATTACK_MULTIPLIER;
			else if(target.getCurrMove().getStatName().equals("DEFENSE"))
				statIndex = DEFENSE_MULTIPLIER;
			else if(target.getCurrMove().getStatName().equals("SPEED"))
				statIndex = SPEED_MULTIPLIER;
			else if(target.getCurrMove().getStatName().equals("EVASIVENESS"))
				statIndex = EVASIVENESS_MULTIPLIER;
			
			resetStatMultipliers(target, statIndex);
			
			if(bs.getEntity().getName() == "dhaarsh")
				System.out.println(bs.getStats()[ATTACK_MULTIPLIER] + " | " + bs.getStats()[DEFENSE_MULTIPLIER] );
			
			target.changeStat(statIndex, target.getStats()[statIndex] + bs.getCurrMove().getStatDelta());
			if(target.getStats()[statIndex] > 4) {
				target.changeStat(statIndex, 4);
				System.out.println("attack stat max'd");
			}else if(target.getStats()[statIndex] < -4) {
				target.changeStat(statIndex, -4);
				System.out.println("attack stat min'd");
			}
			
			switch(statIndex) {
			case ATTACK_MULTIPLIER:
				target.getEntity().setStatColour(new Color(255, 0, 0,  20 * Math.abs(target.getStats()[ATTACK_MULTIPLIER])));
				break;
			case DEFENSE_MULTIPLIER:
				target.getEntity().setStatColour(new Color(0, 0, 255,  30 * Math.abs(target.getStats()[DEFENSE_MULTIPLIER])));
				break;
			}
			target.getEntity().loadNormalCharacterAnimations();
			target.getEntity().loadStatChangedAnimation(target);
			if(bs.getEntity().getName() == "dhaarsh")
				System.out.println(bs.getStats()[ATTACK_MULTIPLIER] + " | " + bs.getStats()[DEFENSE_MULTIPLIER] );
			
		}
		
		
		// STAMINA
		int staminaUsed = bs.getCurrMove().getStaminaDelta();
		int currStamina = bs.getStats()[STAMINA];
		int finalStamina = currStamina + staminaUsed;
		finalStamina = Math.min(finalStamina, bs.getMaxStamina());
		bs.setPrevStamina(bs.getStats()[STAMINA]);
		bs.changeStat(STAMINA, finalStamina);
	}
	
	public void resetStatMultipliers(BattleState target, int stat) {
		int[] multipliers = {ATTACK_MULTIPLIER, DEFENSE_MULTIPLIER, SPEED_MULTIPLIER, EVASIVENESS_MULTIPLIER};
		for(int i : multipliers)
			if(i != stat) 
				target.changeStat(i, 0);
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
