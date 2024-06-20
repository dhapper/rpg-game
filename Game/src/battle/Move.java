package battle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Move {

    private String name;
    private int damage;
    private int speedModifier;
    private int numOfHits;
    private int critMultiplier;
    private int accuracy;
    private boolean spread;
    private String statusInfliction;
    private int statusInflictionRate;
    private boolean throwWeapon;
    private int animationID;
    private String defaultTarget;
    String animationType;
    boolean neverMiss;
    String weapon;
    int staminaDelta;
    String stat;
    int rate;
    int nullify;
    String target;
    
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
                    damage = Integer.parseInt(moveData[1]);
                    speedModifier = Integer.parseInt(moveData[2]);
                    numOfHits = Integer.parseInt(moveData[3]);
                    critMultiplier = Integer.parseInt(moveData[4]);
                    accuracy = Integer.parseInt(moveData[5]);
                    spread = Boolean.parseBoolean(moveData[6]);
                    statusInfliction = moveData[7];
                    statusInflictionRate = Integer.parseInt(moveData[8]);
                    throwWeapon = Boolean.parseBoolean(moveData[9]);
                    animationID = Integer.parseInt(moveData[10]) + 20;
                    defaultTarget = moveData[11];
                    animationType = moveData[12];
                    neverMiss = Boolean.parseBoolean(moveData[13]);
                    weapon = moveData[14];
                    staminaDelta = Integer.parseInt(moveData[15]);
                    stat = moveData[16];
                    rate = Integer.parseInt(moveData[17]);
                    nullify = Integer.parseInt(moveData[18]);
                    target = moveData[19];
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

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getSpeedModifier() {
		return speedModifier;
	}

	public void setSpeedModifier(int speedModifier) {
		this.speedModifier = speedModifier;
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

	public int getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}

	public boolean isSpread() {
		return spread;
	}

	public void setSpread(boolean spread) {
		this.spread = spread;
	}

	public String getStatusInfliction() {
		return statusInfliction;
	}

	public void setStatusInfliction(String statusInfliction) {
		this.statusInfliction = statusInfliction;
	}

	public int getStatusInflictionRate() {
		return statusInflictionRate;
	}

	public void setStatusInflictionRate(int statusInflictionRate) {
		this.statusInflictionRate = statusInflictionRate;
	}

	public boolean isThrowWeapon() {
		return throwWeapon;
	}

	public void setThrowWeapon(boolean throwWeapon) {
		this.throwWeapon = throwWeapon;
	}

	public int getAnimationID() {
		return animationID;
	}

	public void setAnimationID(int animationID) {
		this.animationID = animationID;
	}

	public String getDefaultTarget() {
		return defaultTarget;
	}

	public void setDefaultTarget(String defaultTarget) {
		this.defaultTarget = defaultTarget;
	}

	public String getAnimationType() {
		return animationType;
	}

	public void setAnimationType(String animationType) {
		this.animationType = animationType;
	}

	public int getStaminaDelta() {
		return staminaDelta;
	}

	public void setStaminaDelta(int staminaDelta) {
		this.staminaDelta = staminaDelta;
	}

	public int getNullify() {
		return nullify;
	}

	public void setNullify(int nullify) {
		this.nullify = nullify;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}


	
    
}