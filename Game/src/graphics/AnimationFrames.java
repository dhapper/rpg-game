package graphics;

import java.awt.image.BufferedImage;

import utilz.LoadSave;

public class AnimationFrames {

	BufferedImage[] animation;
	int aniTick, aniIndex, aniSpeed, rowIndex, numOfFrames;
	String fileName;
	
	public AnimationFrames(String fileName, int rowIndex, int numOfFrames, int aniSpeed) {
		this.aniTick = 0;
		this.aniIndex = 0;
		this.aniSpeed = aniSpeed;
		this.rowIndex = rowIndex;
		this.numOfFrames = numOfFrames;
		this.animation = new BufferedImage[numOfFrames];
		
		BufferedImage spriteSheet = LoadSave.GetResource(fileName);
		
		for(int i = 0; i < numOfFrames; i++) {
			this.animation[i] = spriteSheet.getSubimage(i * 32, rowIndex * 32,  32, 32);
		}
	}
}
