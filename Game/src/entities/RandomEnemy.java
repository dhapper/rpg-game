package entities;

import static utilz.Constants.PlayerConstants.PlayerDimensions.HITBOX_HEIGHT;
import static utilz.Constants.PlayerConstants.PlayerDimensions.HITBOX_WIDTH;
import static utilz.Constants.PlayerConstants.PlayerDimensions.PLAYER_HEIGHT;
import static utilz.Constants.PlayerConstants.PlayerDimensions.PLAYER_WIDTH;
import static utilz.Constants.PlayerConstants.PlayerDimensions.X_DRAW_OFFSET;
import static utilz.Constants.PlayerConstants.PlayerDimensions.Y_DRAW_OFFSET;
import static utilz.Constants.PlayerConstants.CharacterConstants.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import gamestates.Battle;
import gamestates.GameState;
import main.Game;
import utilz.Constants;
import utilz.LoadSave;

public class RandomEnemy extends Enemy{
	
	public RandomEnemy(float x, float y, int width, int height, int direction) {
		super(x, y, width, height);
		initHitbox(x, y, HITBOX_WIDTH, HITBOX_HEIGHT);
		
		this.name = "test bot";
		
		this.direction = direction;
		
		this.battleType = Constants.BattleConstants.ONE_VS_ONE;
		
		this.health = 40;
		this.strength = 10;
		this.speed = 9;
		this.stamina = 50;
		this.evasiveness = 5;
		this.attackMultiplier = 0;
		this.defenseMultiplier = 0;
		this.speedMultiplier = 0;
		this.evasivenessMultiplier = 0;
		
		this.bodyFileName = LoadSave.BASE_PLAYER_MODEL;
		this.hairstyleFileName = LoadSave.HAIR_BOY_0;
		this.hairRGB[0] = 150;
		this.hairRGB[1] = 200;
		this.hairRGB[2] = 150;
		
		sprites = new ArrayList<BufferedImage>();
		
		setInteractionBox(direction);
		
		randomize();
		preLoad();
		loadAnimations();
	}
	
	private void randomize() {
		Random random = new Random();
		// randomize gender?
		
		// randomize hair colour
		hairRGB[0] = random.nextInt(150) + 100;
		hairRGB[1] = random.nextInt(150) + 100;
		hairRGB[2] = random.nextInt(150) + 100;
		
		// randomize skin tone
		int r = random.nextInt(150);
		int g = random.nextInt(150);
		int b = random.nextInt(150);
		pupilColour = new Color(r, g, b);
		scleraColour = Color.WHITE;
		
		skinToneIndex = random.nextInt(5);
	}
	
	private void loadAnimations() {
		loadNormalCharacterAnimations();
	}
	
	public void interact(Game game) {
		if(!alreadyInteracted) {
			ArrayList<Entity> players = new ArrayList<Entity>();
			players.add(this);
			game.createBattle(players, battleType);
			GameState.state = GameState.BATTLE;
		}
		
		alreadyInteracted = true;
	}

	
}
