package battlelogic;

import static utilz.Constants.BattleConstants.*;

import entities.Entity;

public class BattleState {

	private Entity entity;
	
	private int[] stats;
	private String currMove = "NONE";
	private int moveTarget = -1;
	private double currSpeed;
	private int currDamage;
	
	private boolean alive;
	
	// animation variables
	private boolean animating;
	private int position; 
	private float playerX, playerY;
	private float initialHeight;
	
	public BattleState(Entity entity) {
		this.entity = entity;
		this.stats = entity.getStats();
		
		this.alive = true;
		
		//System.out.println(stats[HEALTH]);
		//System.out.println(stats[STRENGTH]);
		//System.out.println(stats[SPEED]);
		//System.out.println();
		
	}
	
	public void removeHealth(int damage) {
		stats[HEALTH] -= damage;
	}
	
	public int getMoveTarget() {
		return moveTarget;
	}
	
	public void setMoveTarget(int moveTarget) {
		this.moveTarget = moveTarget;
	}

	public String getCurrMove() {
		return currMove;
	}
	
	public void setCurrMove(String currMove) {
		this.currMove = currMove;
	}
	
	public double getCurrSpeed() {
		return currSpeed;
	}
	
	public void setCurrSpeed(double currSpeed) {
		this.currSpeed = currSpeed;
	}

	public int[] getStats() {
		return stats;
	}
	
	public Entity getEntity() {
		return entity;
	}

	public double getCurrDamage() {
		return currDamage;
	}

	public void setCurrDamage(int currDamage) {
		this.currDamage = currDamage;
	}

	public boolean isAnimating() {
		return animating;
	}

	public void setAnimating(boolean animating) {
		this.animating = animating;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public float getPlayerX() {
		return playerX;
	}

	public void setPlayerX(float playerX) {
		this.playerX = playerX;
	}
	
	public float getPlayerY() {
		return playerY;
	}

	public void setPlayerY(float playerY) {
		this.playerY = playerY;
	}

	public float getInitialHeight() {
		return initialHeight;
	}

	public void setInitialHeight(float initialHeight) {
		this.initialHeight = initialHeight;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
}
