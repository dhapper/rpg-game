package mapeditor;

import java.awt.image.BufferedImage;

import utilz.LoadSave;

public class SpriteSheet {


	
	public static BufferedImage GetSprite(BufferedImage spriteSheet, int index, int rowLength) {
		
		int row = index / rowLength;
		int col = index % rowLength;
		
		return spriteSheet.getSubimage(32 * col, 32 * row, 32, 32);
	}
	
	public static BufferedImage[] GenerateBaseSprites() {
		BufferedImage[] sprites = new BufferedImage[6 * 10];
		BufferedImage spriteSheet = LoadSave.GetResource(LoadSave.SPRITESHEET_BASE);
		
		for(int i = 0; i < 6 * 10; i++) {
			sprites[i] = GetSprite(spriteSheet, i, 6);
		}
		
		return sprites;
	}
	
	public static BufferedImage[] GenerateOverlapSprites() {
		BufferedImage[] sprites = new BufferedImage[6 * 10];
		BufferedImage spriteSheet = LoadSave.GetResource(LoadSave.SPRITESHEET_OVERLAP);
		
		for(int i = 0; i < 6 * 10; i++) {
			sprites[i] = GetSprite(spriteSheet, i, 6);
		}
		
		return sprites;
	}
	
	public static BufferedImage[] GenerateCollisionStaticSprites() {
		BufferedImage[] sprites = new BufferedImage[25];
		BufferedImage spriteSheet = LoadSave.GetResource(LoadSave.SPRITESHEET_C_S_La1);
		
		for(int i = 0; i < 25; i++) {
			sprites[i] = GetSprite(spriteSheet, i, 5);
		}
		
		return sprites;
	}
	
	public static BufferedImage[] GenerateWalkableAnimatedSprites() {
		BufferedImage[] sprites = new BufferedImage[25];
		BufferedImage spriteSheet = LoadSave.GetResource(LoadSave.SPRITESHEET_W_A_La1);
		
		for(int i = 0; i < 25; i++) {
			sprites[i] = GetSprite(spriteSheet, i, 5);
		}
		
		return sprites;
	}
	
	public static BufferedImage[] GenerateCollisionAnimatedSprites() {
		BufferedImage[] sprites = new BufferedImage[25];
		BufferedImage spriteSheet = LoadSave.GetResource(LoadSave.SPRITESHEET_C_A_La1);
		
		for(int i = 0; i < 25; i++) {
			sprites[i] = GetSprite(spriteSheet, i, 5);
		}
		
		return sprites;
	}
	
}
