package entities;

import static utilz.Constants.PlayerConstants.PlayerDimensions.*;
import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.CanMoveHere;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import utilz.LoadSave;
import graphics.GraphicsHelp;

public class Player extends Entity {
	
	//private BufferedImage[][] animations;
	private int aniTick, aniIndex, currFrame;
	private int playerAction = IDLE;
	private boolean moving = false;
	private boolean left, up, right, down;
	private ArrayList<int[][]> layersData = new ArrayList<int[][]>();

	private int prevAni = -1;
	private boolean facingRight = true, facingLeft = false, facingForward = false, facingBackward = false;

	protected ArrayList<Sword> swords;
	protected ArrayList<Shield> shields;
	protected ArrayList<Armour> armoury;
	
	public Player(float x, float y, int width, int height) {
		super(x, y, width, height);
		initHitbox(x, y, HITBOX_WIDTH, HITBOX_HEIGHT);
		
		this.name = "dhaarsh";
		
		this.health = 50;
		this.strength = 45;
		this.speed = 20;
		this.stamina = 50;
		this.evasiveness = 5;
		this.attackMultiplier = 0;
		this.defenseMultiplier = 0;
		this.speedMultiplier = 0;
		this.evasivenessMultiplier = 0;
		
		this.activeSword = new Sword("iron", LoadSave.SWORD_IRON_SWORD, 10, 10);
		this.inactiveSword = new Sword("wooden", LoadSave.SWORD_WOODEN_SWORD, 10, 10);
		this.activeShield = new Shield("iron sh", LoadSave.SHIELD_IRON_SHIELD);
		this.activeArmour = new Armour("bb", LoadSave.ARMOUR_M_BASIC);
	
		swords = new ArrayList<Sword>();
		shields = new ArrayList<Shield>();
		armoury = new ArrayList<Armour>();
		
		this.swords.add(activeSword);
		this.swords.add(inactiveSword);
		this.swords.add(new Sword("bronze", LoadSave.SWORD_BRONZE_SWORD, 0, 0));
		
		this.shields.add(activeShield);
		this.shields.add(new Shield("wooden", LoadSave.SHIELD_WOODEN_SHIELD));
		this.shields.add(new Shield("steel", LoadSave.SHIELD_STEEL_SHIELD));
		
		this.armoury.add(activeArmour);
		
		
		this.bodyFileName = LoadSave.SKINTONE_0;
		this.hairFileName = LoadSave.HAIR_BOY_0;
		
		loadAnimations();
	}

	public void update() {
		updatePos();
		updateAniTick();
	}
	
	public void render(Graphics g, int xOffset, int yOffset) {
		
		if(!moving) {
			if(facingRight)
				g.drawImage(animations[0][0], (int) (hitbox.x - X_DRAW_OFFSET) - xOffset, (int) (hitbox.y - Y_DRAW_OFFSET) - yOffset,
					PLAYER_WIDTH, PLAYER_HEIGHT, null);
			if(facingLeft)
				g.drawImage(GraphicsHelp.MirrorImage(animations[0][0]), (int) (hitbox.x - X_DRAW_OFFSET) - xOffset, (int) (hitbox.y - Y_DRAW_OFFSET) - yOffset,
						PLAYER_WIDTH, PLAYER_HEIGHT, null);
			if(facingForward)
				g.drawImage(animations[0][1], (int) (hitbox.x - X_DRAW_OFFSET) - xOffset, (int) (hitbox.y - Y_DRAW_OFFSET) - yOffset,
						PLAYER_WIDTH, PLAYER_HEIGHT, null);
			if(facingBackward)
				g.drawImage(animations[0][2], (int) (hitbox.x - X_DRAW_OFFSET) - xOffset, (int) (hitbox.y - Y_DRAW_OFFSET) - yOffset,
						PLAYER_WIDTH, PLAYER_HEIGHT, null);
		}
		
		if(moving) {
			if(facingRight)
				g.drawImage(animations[playerAction][currFrame], (int) (hitbox.x - X_DRAW_OFFSET) - xOffset, (int) (hitbox.y - Y_DRAW_OFFSET) - yOffset,
					PLAYER_WIDTH, PLAYER_HEIGHT, null);
			if(facingLeft)
				g.drawImage(GraphicsHelp.MirrorImage(animations[playerAction][currFrame]), (int) (hitbox.x - X_DRAW_OFFSET) - xOffset, (int) (hitbox.y - Y_DRAW_OFFSET) - yOffset,
						PLAYER_WIDTH, PLAYER_HEIGHT, null);
			if(facingForward)
				g.drawImage(animations[playerAction][currFrame], (int) (hitbox.x - X_DRAW_OFFSET) - xOffset, (int) (hitbox.y - Y_DRAW_OFFSET) - yOffset,
						PLAYER_WIDTH, PLAYER_HEIGHT, null);
			if(facingBackward)
				g.drawImage(animations[playerAction][currFrame], (int) (hitbox.x - X_DRAW_OFFSET) - xOffset, (int) (hitbox.y - Y_DRAW_OFFSET) - yOffset,
						PLAYER_WIDTH, PLAYER_HEIGHT, null);
		}
		
		//drawHitbox(g);
		
	}
	
	private void updateAniTick() {
	    // Reset aniIndex when playerAction changes
	    if (playerAction != prevAni) {
	        aniIndex = 0;
	        prevAni = playerAction;
	    }
	    
	    aniTick++;
	    
	    int[][] aniData = GetAnimationData(playerAction);
	    int[] frames = aniData[0];
	    int[] durations = aniData[1];
	    
	    if (aniTick >= durations[aniIndex]) {
	        aniTick = 0;
	        aniIndex++;
	        
	        if (aniIndex >= frames.length) {
	            aniIndex = 0;
	        }
	        
	        currFrame = frames[aniIndex];
	    }
	}

	
	private void updatePos() {
		
		moving = false;
		playerAction = IDLE;
		float xSpeed = 0, ySpeed = 0;
		
		if(left && right || up & down)
			return;
		
		if(!left && !right && !up && !down)
			return;
		
		if(left && !right) {
			xSpeed = -PLAYER_SPEED;
			setFacingBoolsFalse();
			facingLeft = true;
			playerAction = WALKING_SIDEWAYS;
		}else if(!left && right) {
			xSpeed = PLAYER_SPEED;
			setFacingBoolsFalse();
			facingRight = true;
			playerAction = WALKING_SIDEWAYS;
		}
		
		if(up && !down) {
			ySpeed = -PLAYER_SPEED;
			setFacingBoolsFalse();
			facingBackward = true;
			playerAction = WALKING_AWAY;
		}else if(!up && down) {
			ySpeed = PLAYER_SPEED;
			setFacingBoolsFalse();
			facingForward = true;
			playerAction = WALKING_TOWARDS;
		}
		
		if(CanMoveHere(hitbox.x + xSpeed, hitbox.y + ySpeed, hitbox.width, hitbox.height, layersData)) {
			hitbox.x += xSpeed;
			hitbox.y += ySpeed;
			moving = true;
		}
	}
	
	// maybe entity?
	private void setFacingBoolsFalse() {
		facingRight = false;
		facingLeft = false;
		facingForward = false;
		facingBackward = false;
	}
	
	public void loadAnimations() {
		
		loadNormalCharacterAnimations(bodyFileName, hairFileName, activeSword.getFileName(), activeShield.getFileName(), activeArmour.getFileName());

	}
	
	
	
	public void addLocationData(String fileName) {
		layersData.add(LoadSave.GetLocationData(fileName));
	}
	
	public void clearLocationData() {
		layersData.clear();
	}
	
	public void resetDirBooleans() {
		up = false;
		down = false;
		left = false;
		right = false;
		
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public ArrayList<Sword> getSwords() {
		return swords;
	}

	public ArrayList<Shield> getShields() {
		return shields;
	}

	public ArrayList<Armour> getArmoury() {
		return armoury;
	}

	
	
	
	
}
