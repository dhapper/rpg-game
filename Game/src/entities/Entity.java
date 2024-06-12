package entities;

import java.awt.Color; 
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public abstract class Entity {

	protected float x, y;
	protected int width, height;
	protected Rectangle2D.Float hitbox;
	
	protected String name;
	protected int health, strength, speed;
	
	protected BufferedImage[][] animations;
	
	public Entity(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		//initHitbox();
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
	
}
