package entities;

import java.awt.Color;  
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import battle.BattleState;
import graphics.GraphicsHelp;
import utilz.LoadSave;

import static utilz.Constants.BattleConstants.*;

public abstract class Entity {

	protected float x, y;
	protected int width, height;
	protected Rectangle2D.Float hitbox;
	
	protected String name;
	protected int health, strength, speed, stamina, evasiveness, defense;
	protected int attackMultiplier, defenseMultiplier, speedMultiplier, evasivenessMultiplier;
	
	protected BufferedImage[][] animations;
	
	protected Sword activeSword, inactiveSword;
	protected Shield activeShield;
	protected Armour activeArmour;
	
	protected String bodyFileName;
	protected String hairFileName;
	
	protected Color statColour;
	
	public Entity(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		//initHitbox();

		this.activeSword = new Sword("c", LoadSave.SWORD_WOODEN_SWORD, 10, 10);
		this.inactiveSword = new Sword("asd", LoadSave.SWORD_IRON_SWORD, 10, 10);
		this.activeShield = new Shield("d", LoadSave.SHIELD_IRON_SHIELD);
		this.activeArmour = new Armour("bas", LoadSave.ARMOUR_M_BASIC);
		
		
	}

	// doesn't account for screen moving
	protected void drawHitbox(Graphics g) {
		// for debugging purposes
		g.setColor(Color.RED);
		g.drawRect((int) hitbox.x, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
	}
	
	protected void initHitbox(float x, float y, float width, float height) {
		hitbox = new Rectangle2D.Float(x, y, width, height);
	}
	
	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}
	
	public int[] getStats() {
		return new int[] {health, speed, strength, defense, stamina, evasiveness, attackMultiplier, defenseMultiplier, speedMultiplier, evasivenessMultiplier};
	}
	
	public String getName() {
		return name;
	}
	
	public BufferedImage[][] getAnimations() {
		return animations;
	}

	public Sword getActiveSword() {
		return activeSword;
	}

	public void setActiveSword(Sword activeSword) {
		this.activeSword = activeSword;
	}
	
	public void swapActiveSword() {
		Sword temp = activeSword;
		activeSword = inactiveSword;
		inactiveSword = temp;
	}
	
	public Sword getInactiveSword() {
		return inactiveSword;
	}

	public void setInactiveSword(Sword inactiveSword) {
		this.inactiveSword = inactiveSword;
	}

	public Shield getActiveShield() {
		return activeShield;
	}

	public void setActiveShield(Shield activeShield) {
		this.activeShield = activeShield;
	}

	public Armour getActiveArmour() {
		return activeArmour;
	}

	public void setActiveArmour(Armour activeArmour) {
		this.activeArmour = activeArmour;
	}

	public String getBodyFileName() {
		return bodyFileName;
	}

	public void setBodyFileName(String bodyFileName) {
		this.bodyFileName = bodyFileName;
	}

	public String getHairFileName() {
		return hairFileName;
	}

	public void setHairFileName(String hairFileName) {
		this.hairFileName = hairFileName;
	}

	public Color getStatColour() {
		return statColour;
	}

	public void setStatColour(Color statColour) {
		this.statColour = statColour;
	}

	public void loadProtectionBubbleVisual() {
		for(int j = 0; j < animations.length; j++)
			for(int i = 0; i < animations[j].length; i++) {
				BufferedImage combinedImage = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
	            Graphics2D g2d = combinedImage.createGraphics();
    			g2d.drawImage(animations[j][i], 0, 0, 64, 64, null);
    			g2d.drawImage(GraphicsHelp.decreaseAlpha(LoadSave.GetResource(LoadSave.PROTECTION_BUBBLE), 0.2f), 3, 10, 44, 44, null);
    			g2d.dispose();
    			animations[j][i] = combinedImage;
			}
	}
	
	public void loadStatChangedAnimation(BattleState bs) {
		
		int[] stat = {ATTACK_MULTIPLIER, DEFENSE_MULTIPLIER, SPEED_MULTIPLIER, EVASIVENESS_MULTIPLIER};
		boolean displayVisual = false;
		for(int statIndex : stat)
			if(bs.getStats()[statIndex] != 0)
				displayVisual = true;
		
		System.out.println(getName() + " " + displayVisual);
		
		for(int j = 0; j < animations.length; j++)
			for(int i = 0; i < animations[j].length; i++) {
				BufferedImage combinedImage = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
	            Graphics2D g2d = combinedImage.createGraphics();
				if(displayVisual) {
		            g2d.setColor(new Color(255, 255, 255, 25));
	    			g2d.drawRect(17, 13, 30, 38);
		            g2d.setColor(statColour);
	    			g2d.fillRect(17 + 1, 13 + 1, 30 - 1, 38 - 1);
				}
    			g2d.drawImage(animations[j][i], 0, 0, 64, 64, null);
    			g2d.dispose();
    			animations[j][i] = combinedImage;
			}
	}
	
	public void loadPlayerAlterAlpha(float alpha) {
		for (int j = 0; j < animations.length; j++) {
	        for (int i = 0; i < animations[j].length; i++) {
	            animations[j][i] = GraphicsHelp.decreaseAlpha(animations[j][i], alpha); // Use a reasonable factor to see the effect
	        }
	    }
	}
	
	public void loadNormalCharacterAnimations() {
	    BufferedImage body = LoadSave.GetResource(bodyFileName);
	    BufferedImage hair = LoadSave.GetResource(hairFileName);
	    BufferedImage sword = LoadSave.GetResource(activeSword.getFileName());
	    BufferedImage shield = LoadSave.GetResource(activeShield.getFileName());
	    BufferedImage armour = LoadSave.GetResource(activeArmour.getFileName());
	    BufferedImage shadow = LoadSave.GetResource(LoadSave.SHADOW);

	    animations = new BufferedImage[8][8];

	    for (int j = 0; j < animations.length; j++) {
	        for (int i = 0; i < animations[j].length; i++) {
	            BufferedImage combinedImage = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
	            Graphics2D g2d = combinedImage.createGraphics();

	            g2d.drawImage(shadow.getSubimage(i * 64, j * 64, 64, 64), 0, 0, null);

	            if (j == 2 && i != 3 && i != 4) {
	                g2d.drawImage(body.getSubimage(i * 64, j * 64, 64, 64), 0, 0, null);
	                g2d.drawImage(armour.getSubimage(i * 64, j * 64, 64, 64), 0, 0, null);
	                g2d.drawImage(hair.getSubimage(i * 64, j * 64, 64, 64), 0, 0, null);
	                g2d.drawImage(sword.getSubimage(i * 64, j * 64, 64, 64), 0, 0, null);
	                g2d.drawImage(shield.getSubimage(i * 64, j * 64, 64, 64), 0, 0, null);
	            } else if (j == 3) {
	                g2d.drawImage(sword.getSubimage(i * 64, j * 64, 64, 64), 0, 0, null);
	                if (i != 0 && i != 1) {
	                    g2d.drawImage(body.getSubimage(i * 64, j * 64, 64, 64), 0, 0, null);
	                    g2d.drawImage(armour.getSubimage(i * 64, j * 64, 64, 64), 0, 0, null);
	                    g2d.drawImage(hair.getSubimage(i * 64, j * 64, 64, 64), 0, 0, null);
	                    g2d.drawImage(shield.getSubimage(i * 64, j * 64, 64, 64), 0, 0, null);
	                } else {
	                    g2d.drawImage(shield.getSubimage(i * 64, j * 64, 64, 64), 0, 0, null);
	                    g2d.drawImage(body.getSubimage(i * 64, j * 64, 64, 64), 0, 0, null);
	                    g2d.drawImage(armour.getSubimage(i * 64, j * 64, 64, 64), 0, 0, null);
	                    g2d.drawImage(hair.getSubimage(i * 64, j * 64, 64, 64), 0, 0, null);
	                }
	            } else if (j == 6) {
	                g2d.drawImage(body.getSubimage(i * 64, j * 64, 64, 64), 0, 0, null);
	                g2d.drawImage(armour.getSubimage(i * 64, j * 64, 64, 64), 0, 0, null);
	                g2d.drawImage(hair.getSubimage(i * 64, j * 64, 64, 64), 0, 0, null);
	                g2d.drawImage(sword.getSubimage(i * 64, j * 64, 64, 64), 0, 0, null);
	                g2d.drawImage(shield.getSubimage(i * 64, j * 64, 64, 64), 0, 0, null);
	            } else {
	                g2d.drawImage(shield.getSubimage(i * 64, j * 64, 64, 64), 0, 0, null);
	                g2d.drawImage(body.getSubimage(i * 64, j * 64, 64, 64), 0, 0, null);
	                g2d.drawImage(armour.getSubimage(i * 64, j * 64, 64, 64), 0, 0, null);
	                g2d.drawImage(hair.getSubimage(i * 64, j * 64, 64, 64), 0, 0, null);
	                g2d.drawImage(sword.getSubimage(i * 64, j * 64, 64, 64), 0, 0, null);
	            }

	            g2d.dispose();

	            animations[j][i] = combinedImage;

	            if (j == 4 || j == 5 || j == 6) {
	                animations[j][i] = GraphicsHelp.MirrorImage(animations[j][i]);
	            }
	        }
	    }
	}

	
}
