package entities;

import java.awt.Color; 
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import graphics.GraphicsHelp;
import utilz.LoadSave;

public abstract class Entity {

	protected float x, y;
	protected int width, height;
	protected Rectangle2D.Float hitbox;
	
	protected String name;
	protected int health, strength, speed;
	
	protected BufferedImage[][] animations;
	
	protected Sword activeSword;
	protected Shield activeShield;
	
	public Entity(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		//initHitbox();

		this.activeSword = new Sword("c", LoadSave.SWORD_WOODEN_SWORD);
		this.activeShield = new Shield("d", LoadSave.SHIELD_IRON_SHIELD);
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
		return new int[] {health, strength, speed};
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
	
	public void loadNormalCharacterAnimations(String bodyFileName, String hairFileName, String swordFileName, String shieldFileName) {
		BufferedImage body = LoadSave.GetResource(bodyFileName);
		BufferedImage hair = LoadSave.GetResource(hairFileName);
		BufferedImage sword = LoadSave.GetResource(swordFileName);
		BufferedImage shield = LoadSave.GetResource(shieldFileName);
		
		animations = new BufferedImage[8][8];
		
		for(int j = 0; j < animations.length; j++)
			for(int i = 0; i < animations[j].length; i++) {
				
	            BufferedImage combinedImage = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
	            Graphics2D g2d = combinedImage.createGraphics();

	            g2d.drawImage(shield.getSubimage(i * 64, j * 64, 64, 64), 0, 0, null);
	            g2d.drawImage(body.getSubimage(i * 64, j * 64, 64, 64), 0, 0, null);
	            g2d.drawImage(hair.getSubimage(i * 64, j * 64, 64, 64), 0, 0, null);
	            g2d.drawImage(sword.getSubimage(i * 64, j * 64, 64, 64), 0, 0, null);

	            g2d.dispose();

	            animations[j][i] = combinedImage;
				
				if(j == 4 || j == 5)
					animations[j][i] = GraphicsHelp.MirrorImage(animations[j][i]);
			}
		
		
	}
	
}
