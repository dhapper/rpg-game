package entities.items;

import java.awt.image.BufferedImage;

import graphics.ImageBuilder;
import utilz.LoadSave;

public class Sword {

	private Blade blade;
	private Hilt hilt;
	private ForgedElement forgedElement;
	
	private String name;
	
	private BufferedImage[] sprites;
	
	public Sword(Blade blade, Hilt hilt, ForgedElement forgedElement) {
		this.blade = blade;
		this.hilt = hilt;
		this.forgedElement = forgedElement;
		
		if(forgedElement != null)
			this.name = forgedElement.getMaterial() + " forged " + blade.getMaterial() + " " + blade.getType() + " with a " + hilt.getMaterial() + " hilt";
		else
			this.name = blade.getMaterial() + " " + blade.getType() + " with a " + hilt.getMaterial() + " hilt";
		
		this.sprites = new BufferedImage[3];

		
		for(int i = 0; i < sprites.length; i++) {
			if(forgedElement != null) {
				BufferedImage[] swordLayers = {blade.getSprites()[i], hilt.getSprites()[i], forgedElement.getSprites()[i]};
				sprites[i] = ImageBuilder.BuildBufferedImage(swordLayers);
			}else { 
				BufferedImage[] swordLayers = {blade.getSprites()[i], hilt.getSprites()[i]};
				sprites[i] = ImageBuilder.BuildBufferedImage(swordLayers);
			}
		}
		
		
		System.out.println(name);
	}

	public Blade getBlade() {
		return blade;
	}

	public void setBlade(Blade blade) {
		this.blade = blade;
	}

	public Hilt getHilt() {
		return hilt;
	}

	public void setHilt(Hilt hilt) {
		this.hilt = hilt;
	}

	public ForgedElement getForgedElement() {
		return forgedElement;
	}

	public void setForgedElement(ForgedElement forgedElement) {
		this.forgedElement = forgedElement;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BufferedImage[] getSprites() {
		return sprites;
	}

	public void setSprites(BufferedImage[] sprites) {
		this.sprites = sprites;
	}
	
	
}
