package graphics;

import static utilz.Constants.BattleConstants.*;
import static utilz.Constants.GraphicsConstants.PIXEL_FONT;
import static utilz.Constants.PlayerConstants.DEFAULT_IMAGE;
import static utilz.Constants.PlayerConstants.MIRROR_IMAGE;
import static utilz.Constants.BattleConstants.Graphics.Bars.*;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import battle.BattleState;
import gamestates.Battle;
import main.Game;
import utilz.LoadSave;

public class DrawBattle {

	
	private Battle battle;
	private ArrayList<BattleState> animationOrderedBattleStates;
	
	public DrawBattle(Battle battle) {
		this.battle = battle;
		
		initDynamicVariables();
	}
	
	private void initDynamicVariables() {
		animationOrderedBattleStates = battle.getAnimationOrderedBattleStates();
		
	}
	
	public void drawPlayerInfoPanel(Graphics g, BattleState bs) {
		
		String playerInfo[] = new String[7];
		playerInfo[0] = "NAME: "+bs.getEntity().getName();
		playerInfo[1] = "SWORD: "+bs.getEntity().getActiveSword().getName();
		playerInfo[2] = "SHIELD: "+bs.getEntity().getActiveShield().getName();
		playerInfo[3] = "STRENGTH: "+bs.getEntity().getStats()[STRENGTH];
		playerInfo[4] = "SPEED: "+bs.getEntity().getStats()[SPEED];
		playerInfo[5] = "EVASIVENESS: "+bs.getEntity().getStats()[EVASIVENESS];
		playerInfo[6] = "ITEM: none";
		
		g.setFont(GraphicsHelp.loadCustomFont(PIXEL_FONT, Game.GAME_WIDTH/50));
		FontMetrics metrics = g.getFontMetrics();
		int lineSpace = metrics.getHeight();
		int margin = (int) (7 * Game.SCALE);
		int maxStrLength = metrics.stringWidth("EVASIVENESS: 999");
		int bgWidth = maxStrLength + margin * 2;
		int bgHeight = lineSpace * 7;
		int xOffset = (int) (40 * Game.SCALE);
		int yOffset = (int) (185 * Game.SCALE);
		
		switch(bs.getPosition()) {
		case RIGHT_MAIN:
		case RIGHT_1:
		case RIGHT_2:
			xOffset = Game.GAME_WIDTH - xOffset - bgWidth;
			break;
		}
		
		
		g.setColor(new Color(0, 0, 0, 125));
		g.fillRect(xOffset, yOffset, bgWidth, bgHeight);
		
		g.setColor(Color.WHITE);
		for(int i = 0; i < playerInfo.length; i++) {
			g.drawString(playerInfo[i], xOffset + margin, yOffset + (i + 1) * lineSpace - margin);
		} 
		
	}
	
	public void drawBars(Graphics g) {
		
		initDynamicVariables();
		for(BattleState bs : animationOrderedBattleStates)
			switch(bs.getPosition()) {
			case LEFT_MAIN:
			case LEFT_1:
				drawPlayerBars(g, bs, X_OFFSET_LEFT, Y_OFFSET_HIGHER);
				break;
			case LEFT_2:
				drawPlayerBars(g, bs, X_OFFSET_LEFT, Y_OFFSET_LOWER);
				break;
			case RIGHT_MAIN:
			case RIGHT_1:
				drawPlayerBars(g, bs, X_OFFSET_RIGHT, Y_OFFSET_HIGHER);
				break;
			case RIGHT_2:
				drawPlayerBars(g, bs, X_OFFSET_RIGHT, Y_OFFSET_LOWER);
				break;
			}
	}
	
	private void drawPlayerBars(Graphics g, BattleState bs, int xOffset, int yOffset) {
		int raiseString = 5;
		g.setFont(GraphicsHelp.loadCustomFont(PIXEL_FONT, 20));
		FontMetrics metrics = g.getFontMetrics();
		String staminaStr = "stamina: "+bs.getStats()[STAMINA];
		int textWidth = metrics.stringWidth(staminaStr);
		int margin = 5;
		
		g.setColor(new Color(0, 0, 0, 125));
		g.fillRect(xOffset - margin, yOffset - metrics.getHeight(), HEALTH_BAR_WIDTH + margin * 2, HEALTH_BAR_HEIGHT + STAMINA_BAR_HEIGHT + metrics.getHeight() + margin);
		g.setColor(Color.WHITE);
		
		g.drawString("health: "+bs.getPrevHealth(), xOffset, yOffset - raiseString);
		g.drawString(staminaStr, xOffset + HEALTH_BAR_WIDTH - textWidth, yOffset - raiseString);
		
		g.setColor(Color.GREEN);
		g.fillRect(xOffset, yOffset, (int) (1.0 * bs.getPrevHealth()/bs.getMaxHealth() * HEALTH_BAR_WIDTH), HEALTH_BAR_HEIGHT);
		g.setColor(Color.CYAN);
		g.fillRect(xOffset, yOffset + HEALTH_BAR_HEIGHT, (int) (1.0 * bs.getPrevStamina()/bs.getMaxStamina() * STAMINA_BAR_WIDTH), STAMINA_BAR_HEIGHT);
		
		g.setColor(Color.BLACK);
		g.setColor(Color.WHITE);
		g.drawRect(xOffset, yOffset, (int) (1.0 * bs.getPrevHealth()/bs.getMaxHealth() * HEALTH_BAR_WIDTH), HEALTH_BAR_HEIGHT);
		g.drawRect(xOffset, yOffset + HEALTH_BAR_HEIGHT, (int) (1.0 * bs.getPrevStamina()/bs.getMaxStamina() * STAMINA_BAR_WIDTH), STAMINA_BAR_HEIGHT);
		
		g.drawRect(xOffset, yOffset, HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT);
		g.drawRect(xOffset, yOffset + HEALTH_BAR_HEIGHT, HEALTH_BAR_WIDTH, STAMINA_BAR_HEIGHT);
	}
	
	public void drawMoveErrorMessage(Graphics g, String moveErrorMessage) {
		g.setColor(Color.RED);
		g.setFont(GraphicsHelp.loadCustomFont(PIXEL_FONT, 20));
		FontMetrics metrics = g.getFontMetrics();
		int textWidth = metrics.stringWidth(moveErrorMessage);
		g.drawString(moveErrorMessage, Game.GAME_WIDTH / 2 - textWidth / 2, Game.GAME_HEIGHT / 2);
		g.setColor(Color.BLACK);
	}
	
	public void drawPlayers(Graphics g, BattleState bs, int playerAction, int currFrame, int idleAniIndex) {
		BufferedImage[][] playerAnimations = bs.getEntity().getAnimations();
		if(bs.isAnimating()) {
			if(bs.getPosition() == LEFT_MAIN || bs.getPosition() == LEFT_1 || bs.getPosition() == LEFT_2) {
				if(battle.getImageMode() == DEFAULT_IMAGE)
					g.drawImage(playerAnimations[playerAction][currFrame], (int) bs.getPlayerX(), (int) bs.getPlayerY(), 300, 300, null);	
				else if(battle.getImageMode() == MIRROR_IMAGE)
					g.drawImage(GraphicsHelp.MirrorImage(playerAnimations[playerAction][currFrame]), (int) bs.getPlayerX(), (int) bs.getPlayerY(), 300, 300, null);
			}else if(bs.getPosition() == RIGHT_MAIN || bs.getPosition() == RIGHT_1 || bs.getPosition() == RIGHT_2)
				if(battle.getImageMode() == DEFAULT_IMAGE)
					g.drawImage(GraphicsHelp.MirrorImage(playerAnimations[playerAction][currFrame]),  (int) bs.getPlayerX(), (int) bs.getPlayerY(), 300, 300, null);	
				else if(battle.getImageMode() == MIRROR_IMAGE){
					g.drawImage(playerAnimations[playerAction][currFrame], (int) bs.getPlayerX(), (int) bs.getPlayerY(), 300, 300, null);
			}
		}else {
			if(bs.isBlockingStance()) {
				if(bs.getPosition() == LEFT_MAIN || bs.getPosition() == LEFT_1 || bs.getPosition() == LEFT_2)
					g.drawImage(playerAnimations[6][0], (int) bs.getPlayerX(), (int) bs.getPlayerY(), 300, 300, null);
				else if(bs.getPosition() == RIGHT_MAIN || bs.getPosition() == RIGHT_1 || bs.getPosition() == RIGHT_2)
					g.drawImage(GraphicsHelp.MirrorImage(playerAnimations[6][0]), (int) bs.getPlayerX(), (int)bs.getPlayerY(), 300, 300, null);
			}else {
				if(bs.getPosition() == LEFT_MAIN || bs.getPosition() == LEFT_1 || bs.getPosition() == LEFT_2)
					g.drawImage(playerAnimations[4][idleAniIndex], (int) bs.getPlayerX(), (int) bs.getPlayerY(), 300, 300, null);
				else if(bs.getPosition() == RIGHT_MAIN || bs.getPosition() == RIGHT_1 || bs.getPosition() == RIGHT_2)
					g.drawImage(GraphicsHelp.MirrorImage(playerAnimations[4][idleAniIndex]), (int) bs.getPlayerX(), (int)bs.getPlayerY(), 300, 300, null);
			}
		}
	}
	
	
}
