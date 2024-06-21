package battle;

import static utilz.Constants.BattleConstants.*;

import java.util.Random;

import utilz.Constants.BattleConstants.Moves;

public class MoveMethods {
	
	public static boolean isMoveValid(BattleManager bm, BattleState bs, Move move) {
		if(bs.getStats()[STAMINA] >= -move.getStaminaDelta()) {
			bm.getBattle().setMoveErrorMessage("NONE");
			bs.setCurrMove(move);
			bm.getBattle().setTargetButtons(true);
			return true;
		}else {
			bm.getBattle().setMoveErrorMessage("NOT ENOUGH STAMINA TO USE " + move.getName().toUpperCase());
			return false;
		}
	}
	
	public static void randomMove(BattleManager bm, BattleState bs) {
		Random random = new Random();
		do{
			int choice = random.nextInt(4);
			if(choice == 0)
				bs.setCurrMove(bs.getEntity().getActiveSword().getActiveMoves()[0]);
			else if(choice == 1)
				bs.setCurrMove(bs.getEntity().getActiveSword().getActiveMoves()[1]);
			else if(choice == 2)
				bs.setCurrMove(bs.getEntity().getActiveSword().getActiveMoves()[2]);
			else if(choice == 3)
				bs.setCurrMove(bs.getEntity().getActiveSword().getActiveMoves()[3]);
			else if(choice == 4)
				bs.setCurrMove(Moves.SWAP);
		}while (!isMoveValid(bm, bs, bs.getCurrMove()));
		
	}
	
	public static void setTarget(BattleManager bm, BattleState bs) {
	    if (bs.getCurrMove().getTarget().equals("SELF")) {
	        bs.setMoveTarget(bs.getPlayerID());
	        return;
	    }

	    Random random = new Random();
	    String target = bs.getCurrMove().getTarget();

	    if (target.equals("ENEMY")) {
	        switch (bm.getBattleType()) {
	            case ONE_VS_ONE:
	            case ONE_VS_TWO:
	                bs.setMoveTarget(PLAYER);
	                break;
	            case TWO_VS_ONE:
	                bs.setMoveTarget(bs.isEnemyTeam() ? (random.nextBoolean() ? PLAYER : NPC_1) : NPC_2);
	                break;
	            case TWO_VS_TWO:
	                if (bs.isEnemyTeam()) {
	                    bs.setMoveTarget(random.nextBoolean() ? PLAYER : NPC_1);
	                } else {
	                    bs.setMoveTarget(random.nextBoolean() ? NPC_2 : NPC_3);
	                }
	                break;
	        }
	    }

	    while (!bm.getBattleStates().get(bs.getMoveTarget()).isAlive()) {
	        switch (bm.getBattleType()) {
	            case TWO_VS_ONE:
	                bs.setMoveTarget(bs.getMoveTarget() == PLAYER ? NPC_1 : PLAYER);
	                break;
	            case TWO_VS_TWO:
	                if (bs.isEnemyTeam()) {
	                    bs.setMoveTarget(bs.getMoveTarget() == PLAYER ? NPC_1 : PLAYER);
	                } else {
	                    bs.setMoveTarget(bs.getMoveTarget() == NPC_2 ? NPC_3 : NPC_2);
	                }
	                break;
	            default:
	                break;
	        }
	    }
	}
	
}
