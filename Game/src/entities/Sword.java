package entities;

import java.util.ArrayList;

import battlelogic.Move;

public class Sword {

	private String name;
	private int damage;
	private int speed;
	
	private String fileName;
	
	private Move[] activeMoves;
	private ArrayList<Move> moveset;
	
	public Sword(String name, String fileName) {
		this.name = name;
		this.damage = 10;
		this.speed = 10;
		
		this.fileName = fileName;
		
		activeMoves = new Move[4];
		activeMoves[0] = new Move("SWING");
		activeMoves[1] = new Move("JAB");
		activeMoves[2] = new Move("DOUBLE HIT");
		activeMoves[3] = new Move("SWING");
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

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Move[] getActiveMoves() {
		return activeMoves;
	}

	public void setActiveMoves(Move[] activeMoves) {
		this.activeMoves = activeMoves;
	}

	public ArrayList<Move> getMoveset() {
		return moveset;
	}

	public void setMoveset(ArrayList<Move> moveset) {
		this.moveset = moveset;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
}
