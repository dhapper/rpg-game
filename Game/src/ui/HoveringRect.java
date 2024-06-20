package ui;

import static utilz.Constants.UI.TargetButton.B_HEIGHT;
import static utilz.Constants.UI.TargetButton.B_WIDTH;

import battle.BattleState;

public class HoveringRect extends Button{

	private BattleState battleState;
	private boolean isHovered;
	
	public HoveringRect(int xPos, int yPos, int rowIndex, BattleState battleState) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.rowIndex = rowIndex;
		this.battleState = battleState;
		
		this.width = B_WIDTH;
		this.height = B_HEIGHT;
		
		initBounds(B_WIDTH, B_HEIGHT);
	}
	
	public BattleState getBattleState() {
		return battleState;
	}

	public boolean isHovered() {
		return isHovered;
	}

	public void setHovered(boolean isHovered) {
		this.isHovered = isHovered;
	}
	
	

}
