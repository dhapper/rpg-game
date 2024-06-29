package entities.items;

import java.awt.Color;
import java.awt.image.BufferedImage;

public abstract class SwordPart {

	protected String type;
	protected String material;
	
	protected Color[] pallette;
	protected BufferedImage[] sprites;
	
	protected int speed;
	protected int durability;
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getMaterial() {
		return material;
	}
	
	public void setMaterial(String material) {
		this.material = material;
	}
	
	public BufferedImage[] getSprites() {
		return sprites;
	}
	
	public void setSprites(BufferedImage[] sprites) {
		this.sprites = sprites;
	}
	
	public Color[] getPallette() {
		return pallette;
	}
	public void setPallette(Color[] pallette) {
		this.pallette = pallette;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public int getDurability() {
		return durability;
	}
	
	public void setDurability(int durability) {
		this.durability = durability;
	}
	
	
	
}
