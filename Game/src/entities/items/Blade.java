package entities.items;

import java.awt.Color;
import java.awt.image.BufferedImage;

import graphics.ImageBuilder;
import utilz.LoadSave;

public class Blade extends SwordPart{
	
	private int damage;
	
	public static final Color[] SWORD_TEMPLATE = {new Color(255, 255, 255), new Color(200, 200, 200, 200), new Color(100, 100, 100), new Color(0, 0, 0)};
	
	public Blade() {
		this.type = "BROAD_SWORD";
		this.material = "WOOD";
		
		this.pallette = ImageBuilder.GetShadedPallette(new Color(200, 200, 200), 4, 30);
		this.sprites = new BufferedImage[3];
		for(int i = 0; i < sprites.length; i++) {
			sprites[i] = LoadSave.GetSprite(LoadSave.GetResource(LoadSave.SWORD_PARTS), i, 0, 32, 32);
			sprites[i] = ImageBuilder.ApplyColourPallette(sprites[i], pallette, SWORD_TEMPLATE);
		}
		this.speed = 10;
		this.durability = 10;
		
		
		this.damage = 10;
		
	}
}
