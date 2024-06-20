package battle;

import static utilz.Constants.BattleConstants.*;

import entities.Entity;

public class BattleState {

	private Entity entity;
	
	private int[] stats;
	//private String currMove = "NONE";
	private Move currMove = Moves.NONE;
	private int moveTarget = -1;
	private double currSpeed;
	private int currDamage;
	
	private boolean alive;
	private boolean blockingStance;
	
	private int maxHealth;
	private int prevHealth;
	private int maxStamina;
	private int prevStamina;
	
	private boolean contactMade = false;
	
	// animation variables
	private boolean animating;
	private int position; 
	private float playerX, playerY;
	private float initialHeight;
	
	private int defensiveMoveQuantity = 0;
	
	private boolean enemyTeam;
	private int playerID;
	
	public BattleState(Entity entity) {
		this.entity = entity;
		this.stats = entity.getStats();
		
		
		this.alive = true;
		
		this.setMaxHealth(stats[HEALTH]);
		this.setPrevHealth(stats[HEALTH]);
		
		this.setMaxStamina(stats[STAMINA]);
		this.setPrevStamina(stats[STAMINA]);
		
	}
	
	public void removeHealth(int damage) {
		stats[HEALTH] -= damage;
	}
	
	public void changeStat(int statIndex, int value) {
		stats[statIndex] = value;
	}
	
	public int getMoveTarget() {
		return moveTarget;
	}
	
	public void setMoveTarget(int moveTarget) {
		this.moveTarget = moveTarget;
	}

//	public String getCurrMove() {
//		return currMove;
//	}
//	
//	public void setCurrMove(String currMove) {
//		this.currMove = currMove;
//	}
	
	public Move getCurrMove() {
		return currMove;
	}
	
	public void setCurrMove(Move currMove) {
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

	public int getDefensiveMoveQuantity() {
		return defensiveMoveQuantity;
	}

	public void setDefensiveMoveQuantity(int defensiveMoveQuantity) {
		this.defensiveMoveQuantity = defensiveMoveQuantity;
	}

	public void setStats(int[] stats) {
		this.stats = stats;
	}

	public boolean isBlockingStance() {
		return blockingStance;
	}

	public void setBlockingStance(boolean blockingStance) {
		this.blockingStance = blockingStance;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public boolean isContactMade() {
		return contactMade;
	}

	public void setContactMade(boolean contactMade) {
		this.contactMade = contactMade;
	}

	public int getPrevHealth() {
		return prevHealth;
	}

	public void setPrevHealth(int prevHealth) {
		this.prevHealth = prevHealth;
	}

	public boolean isEnemyTeam() {
		return enemyTeam;
	}

	public void setEnemyTeam(boolean enemyTeam) {
		this.enemyTeam = enemyTeam;
	}

	public int getPlayerID() {
		return playerID;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	public int getMaxStamina() {
		return maxStamina;
	}

	public void setMaxStamina(int maxStamina) {
		this.maxStamina = maxStamina;
	}

	public int getPrevStamina() {
		return prevStamina;
	}

	public void setPrevStamina(int preStamina) {
		this.prevStamina = preStamina;
	}
	
}
