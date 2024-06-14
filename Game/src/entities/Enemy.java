package entities;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import graphics.GraphicsHelp;
import utilz.LoadSave;

public class Enemy extends NPC{
	
	public Enemy(float x, float y, int width, int height) {
		super(x, y, width, height);
		
		this.name = "test bot";
		this.health = 40;
		this.strength = 10;
		this.speed = 9;
		
		sprites = new ArrayList<BufferedImage>();
		loadAnimations();
	}
	
	private void loadAnimations() {
		
		loadNormalCharacterAnimations(LoadSave.SKINTONE_1, LoadSave.HAIR_BOY_0, activeSword.getFileName(), activeShield.getFileName());
		
//		BufferedImage img = LoadSave.GetResource(LoadSave.SKINTONE_1);
//		
//		animations = new BufferedImage[8][8];
//		for(int j = 0; j < animations.length; j++)
//			for(int i = 0; i < animations[j].length; i++) {
//				animations[j][i] = img.getSubimage(i * 64, j * 64, 64, 64);
//				
//				//keeping player animations consistently facing right
//				if(j == 4 || j == 5)
//					animations[j][i] = GraphicsHelp.MirrorImage(animations[j][i]);
//			}
	}
	
	public BufferedImage returnImg() {
		return sprites.get(0);
	}
	
}
