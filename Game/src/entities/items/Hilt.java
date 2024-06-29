package entities.items;

import java.awt.Color;
import java.awt.image.BufferedImage;

import graphics.ImageBuilder;
import utilz.LoadSave;

public class Hilt extends SwordPart{

	public static final Color[] HILT_TEMPLATE = {new Color(255, 255, 255), new Color(100, 100, 100), new Color(0, 0, 0)};
	
	public Hilt() {
		this.type = "DEFAULT";
		this.material = "PLASTIC";
		
		this.pallette = ImageBuilder.GetShadedPallette(new Color(250, 200, 200), 3, 30);
		this.sprites = new BufferedImage[3];
		for(int i = 0; i < sprites.length; i++) {
			sprites[i] = LoadSave.GetSprite(LoadSave.GetResource(LoadSave.SWORD_PARTS), i, 2, 32, 32);
			sprites[i] = ImageBuilder.ApplyColourPallette(sprites[i], pallette, HILT_TEMPLATE);
		}
		this.speed = 10;
		this.durability = 10;
		
	}
}
