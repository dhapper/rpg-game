package entities;

import static utilz.Constants.PlayerConstants.PlayerDimensions.HITBOX_HEIGHT;
import static utilz.Constants.PlayerConstants.PlayerDimensions.HITBOX_WIDTH;
import static utilz.Constants.PlayerConstants.PlayerDimensions.PLAYER_HEIGHT;
import static utilz.Constants.PlayerConstants.PlayerDimensions.PLAYER_WIDTH;
import static utilz.Constants.PlayerConstants.PlayerDimensions.X_DRAW_OFFSET;
import static utilz.Constants.PlayerConstants.PlayerDimensions.Y_DRAW_OFFSET;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import graphics.GraphicsHelp;
import utilz.LoadSave;

public abstract class Enemy extends NPC{

	protected int battleType;
	protected boolean alreadyInteracted = false;
	protected boolean alerted = false;
	
	public Enemy(float x, float y, int width, int height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}

	public boolean isAlerted() {
		return alerted;
	}

	public void setAlerted(boolean alerted) {
		this.alerted = alerted;
	}

	public boolean isAlreadyInteracted() {
		return alreadyInteracted;
	}

	public void setAlreadyInteracted(boolean alreadyInteracted) {
		this.alreadyInteracted = alreadyInteracted;
	}
	
	
	
//	public Enemy(float x, float y, int width, int height) {
//		super(x, y, width, height);
//		initHitbox(x, y, HITBOX_WIDTH, HITBOX_HEIGHT);
//		
//		this.name = "test bot";
//		
//		this.health = 40;
//		this.strength = 10;
//		this.speed = 9;
//		this.stamina = 50;
//		this.evasiveness = 5;
//		this.attackMultiplier = 0;
//		this.defenseMultiplier = 0;
//		this.speedMultiplier = 0;
//		this.evasivenessMultiplier = 0;
//		
//		this.bodyFileName = LoadSave.BASE_PLAYER_MODEL;
//		this.hairstyleFileName = LoadSave.HAIR_BOY_0;
//		this.hairRGB[0] = 150;
//		this.hairRGB[1] = 200;
//		this.hairRGB[2] = 150;
//		
//		sprites = new ArrayList<BufferedImage>();
//		
//		preLoad();
//		loadAnimations();
//	}
//	
//	public void render(Graphics g, int xOffset, int yOffset) {
//		g.drawImage(animations[0][0], (int) (hitbox.x - X_DRAW_OFFSET) - xOffset, (int) (hitbox.y - Y_DRAW_OFFSET) - yOffset,
//				PLAYER_WIDTH, PLAYER_HEIGHT, null);
//	}
//	
//	private void loadAnimations() {
//		loadNormalCharacterAnimations();
//	}
//	
//	public BufferedImage returnImg() {
//		return sprites.get(0);
//	}
	
	
	
}
