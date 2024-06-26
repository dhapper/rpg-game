package mapeditor;

import java.awt.image.BufferedImage;

import utilz.LoadSave;

public class SpriteSheet {

	
	BufferedImage spriteSheet;
	public SpriteSheet() {
		this.spriteSheet = LoadSave.GetResource(LoadSave.SPRITESHEET_W_S_La1);
	}
	
	public BufferedImage getSprite(int index) {
		
		int row = index / 5;
		int col = index % 5;
		
		return spriteSheet.getSubimage(32 * col, 32 * row, 32, 32);
	}
}
