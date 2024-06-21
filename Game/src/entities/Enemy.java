package entities;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import graphics.GraphicsHelp;
import utilz.LoadSave;

public class Enemy extends NPC{
	
	public Enemy(float x, float y, int width, int height) {
		super(x, y, width, height);
		
		this.name = "test bot";
		
		this.health = 40;
		this.strength = 10;
		this.speed = 9;
		this.stamina = 50;
		this.evasiveness = 5;
		this.attackMultiplier = 0;
		this.defenseMultiplier = 0;
		this.speedMultiplier = 0;
		this.evasivenessMultiplier = 0;
		
		this.bodyFileName = LoadSave.SKINTONE_1;
		this.hairFileName = LoadSave.HAIR_BOY_0;
		
		sprites = new ArrayList<BufferedImage>();
		
		loadAnimations();
	}
	
	private void loadAnimations() {
		loadNormalCharacterAnimations();
	}
	
	public BufferedImage returnImg() {
		return sprites.get(0);
	}
	
}
