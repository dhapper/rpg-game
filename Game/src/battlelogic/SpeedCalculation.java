package battlelogic;

import static utilz.Constants.BattleConstants.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SpeedCalculation {

	private BattleManager battleManager;
	ArrayList<BattleState> battleStates;
	
	public SpeedCalculation(BattleManager battleManager, ArrayList<BattleState> battleStates) {
		this.battleManager = battleManager;
		this.battleStates = battleStates;
	}
	
	public void calcSpeed() {
		Random random = new Random();
		ArrayList<Integer> speeds = new ArrayList<Integer>();
		
		for(BattleState bs : battleStates) {
			// if player dead, then skip
			double speed = bs.getStats()[SPEED] * new Move(bs.getCurrMove()).getSpeedModifier() / 10;
			speed *=  0.8 + (1.2 - 0.8) * random.nextDouble();
			bs.setCurrSpeed(speed);
		}
	}
}
