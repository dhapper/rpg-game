package battle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Move {

	private String name;
	private String weapon;
	private String moveType;
	private String target;
	private int speed;
	private int accuracy;
	private int staminaDelta;
	private int animationID;
	
	// damaging
	private int damage;
	private int numOfHits;
	private int critMultiplier;
	
	// blocking
	private int numOfBlocks;
	private int nullify;
	private int reflect;
	
	// healing
	private int heal;
	
	// stat change
	private String statName;
	private int statDelta;
	
	public Move(String name) {
		this.name = name;
        readMoveDataFromCSV();
	}
	
	private void readMoveDataFromCSV() {
        String csvFile = "res/moves.csv";
        String line;
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] moveData = line.split(cvsSplitBy);
                if (moveData[0].equalsIgnoreCase(name)) {
                    // Found the move, read its data
                   
                	this.moveType = moveData[1];
                	this.weapon = moveData[2];
                	this.speed = Integer.parseInt(moveData[3]);
                	this.accuracy = Integer.parseInt(moveData[4]);
                	this.target = moveData[5];
                	this.staminaDelta = Integer.parseInt(moveData[6]);
                	this.animationID = Integer.parseInt(moveData[7]);
                    
                	switch(moveType) {
                	case "DAMAGING":
                		this.damage = Integer.parseInt(moveData[8]);
                		this.numOfHits = Integer.parseInt(moveData[9]);
                		this.critMultiplier = Integer.parseInt(moveData[10]); 
                		break;
                	case "BLOCKING":
                		this.numOfBlocks = Integer.parseInt(moveData[8]);
                		this.nullify = Integer.parseInt(moveData[9]);
                		this.reflect = Integer.parseInt(moveData[10]);
                		break;
                	case "HEALING":
                		this.heal = Integer.parseInt(moveData[8]);
                		break;
                	case "STAT_CHANGE":
                		this.statName = moveData[8];
                		this.statDelta = Integer.parseInt(moveData[9]);
                		break;
                	}
                	
                    break; // Stop searching once the move is found
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWeapon() {
		return weapon;
	}

	public void setWeapon(String weapon) {
		this.weapon = weapon;
	}

	public String getMoveType() {
		return moveType;
	}

	public void setMoveType(String moveType) {
		this.moveType = moveType;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}

	public int getStaminaDelta() {
		return staminaDelta;
	}

	public void setStaminaDelta(int staminaDelta) {
		this.staminaDelta = staminaDelta;
	}

	public int getAnimationID() {
		return animationID;
	}

	public void setAnimationID(int animationID) {
		this.animationID = animationID;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getNumOfHits() {
		return numOfHits;
	}

	public void setNumOfHits(int numOfHits) {
		this.numOfHits = numOfHits;
	}

	public int getCritMultiplier() {
		return critMultiplier;
	}

	public void setCritMultiplier(int critMultiplier) {
		this.critMultiplier = critMultiplier;
	}

	public int getNumOfBlocks() {
		return numOfBlocks;
	}

	public void setNumOfBlocks(int numOfBlocks) {
		this.numOfBlocks = numOfBlocks;
	}

	public int getNullify() {
		return nullify;
	}

	public void setNullify(int nullify) {
		this.nullify = nullify;
	}

	public int getReflect() {
		return reflect;
	}

	public void setReflect(int reflect) {
		this.reflect = reflect;
	}

	public int getHeal() {
		return heal;
	}

	public void setHeal(int heal) {
		this.heal = heal;
	}

	public String getStatName() {
		return statName;
	}

	public void setStatName(String statName) {
		this.statName = statName;
	}

	public int getStatDelta() {
		return statDelta;
	}

	public void setStatDelta(int statDelta) {
		this.statDelta = statDelta;
	}

	
	
}
