package battle;

import static utilz.Constants.BattleConstants.*;

import main.Game;
import utilz.Constants.BattleConstants.Graphics.Players;

public class InitBattleVars {

//	private int scale = 300;
//	private int xOffsetLeft = 300;
//	private int xOffsetRight = Game.GAME_WIDTH - xOffsetLeft - scale;
//	private int yOffsetMain = 250;
//	private int yOffset1 = 175;
//	private int yOffset2 = 325;
	
	private BattleManager battleManager;
	
	public InitBattleVars(BattleManager battleManager) {
		this.battleManager = battleManager;
	}
	
	public void initPlayerPos(int battleType) {
		switch(battleType) {
		case ONE_VS_ONE:
			setPlayerPosVars(PLAYER, LEFT_MAIN);
			
			setPlayerPosVars(NPC_1, RIGHT_MAIN);
			break;
		case ONE_VS_TWO:
			setPlayerPosVars(PLAYER, LEFT_MAIN);
			
			setPlayerPosVars(NPC_1, RIGHT_1);
			setPlayerPosVars(NPC_2, RIGHT_2);
			break;
		case TWO_VS_ONE:
			setPlayerPosVars(PLAYER, LEFT_1);
			setPlayerPosVars(NPC_1, LEFT_2);
			
			setPlayerPosVars(NPC_2, RIGHT_MAIN);
			break;	
		case TWO_VS_TWO:
			setPlayerPosVars(PLAYER, LEFT_1);
			setPlayerPosVars(NPC_1, LEFT_2);
			
			setPlayerPosVars(NPC_2, RIGHT_1);
			setPlayerPosVars(NPC_3, RIGHT_2);
			break;	
		}
	}
	
	private void setPlayerPosVars(int playerNum, int positionNum) {
		int xOffset = 0, yOffset = 0;
		
		switch(positionNum) {
		case LEFT_MAIN:
			xOffset = Players.X_OFFSET_LEFT;
			yOffset = Players.Y_OFFSET_MAIN;
			break;
		case RIGHT_MAIN:
			xOffset = Players.X_OFFSET_RIGHT;
			yOffset = Players.Y_OFFSET_MAIN;
			break;
		case LEFT_1:
			xOffset = Players.X_OFFSET_LEFT;
			yOffset = Players.Y_OFFSET_HIGHER;
			break;
		case LEFT_2:
			xOffset = Players.X_OFFSET_LEFT;
			yOffset = Players.Y_OFFSET_LOWER;
			break;
		case RIGHT_1:
			xOffset = Players.X_OFFSET_RIGHT;
			yOffset = Players.Y_OFFSET_HIGHER;
			break;
		case RIGHT_2:
			xOffset = Players.X_OFFSET_RIGHT;
			yOffset = Players.Y_OFFSET_LOWER;
			break;
		}
		
		if(positionNum == LEFT_MAIN || positionNum == LEFT_1 || positionNum == LEFT_2) {
			battleManager.getBattleStates().get(playerNum).setEnemyTeam(false);
		} else {
			battleManager.getBattleStates().get(playerNum).setEnemyTeam(true);
		}
		
		battleManager.getBattleStates().get(playerNum).setPlayerID(playerNum);
		battleManager.getBattleStates().get(playerNum).setPosition(positionNum);
		battleManager.getBattleStates().get(playerNum).setPlayerX(xOffset);
		battleManager.getBattleStates().get(playerNum).setPlayerY(yOffset);
		battleManager.getBattleStates().get(playerNum).setInitialHeight(battleManager.getBattleStates().get(playerNum).getPlayerY());
	}
	
}
