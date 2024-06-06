package graphics;

import java.awt.image.BufferedImage;

import utilz.LoadSave;

public class Animation {

	// for animations on 5x5 sprite sheet (max 5)
	
	BufferedImage[] animation;
	int aniTick, aniIndex, aniSpeed, rowIndex, numOfFrames;
	
	public Animation(String fileName, int rowIndex, int numOfFrames, int aniSpeed) {
		this.aniTick = 0;
		this.aniIndex = 0;
		this.aniSpeed = aniSpeed;
		this.rowIndex = rowIndex;
		this.numOfFrames = numOfFrames;
		this.animation = new BufferedImage[numOfFrames];
		
		BufferedImage spriteSheet = LoadSave.GetSpriteAtlas(fileName);
		
		for(int i = 0; i < numOfFrames; i++) {
			this.animation[i] = spriteSheet.getSubimage(i * 32, rowIndex * 32,  32, 32);
		}
	}

	public BufferedImage[] getAnimation() {
		return animation;
	}

	public void setAnimation(BufferedImage[] animation) {
		this.animation = animation;
	}

	public int getAniTick() {
		return aniTick;
	}

	public void setAniTick(int aniTick) {
		this.aniTick = aniTick;
	}

	public int getAniIndex() {
		return aniIndex;
	}

	public void setAniIndex(int aniIndex) {
		this.aniIndex = aniIndex;
	}

	public int getAniSpeed() {
		return aniSpeed;
	}

	public void setAniSpeed(int aniSpeed) {
		this.aniSpeed = aniSpeed;
	}

	public int getNumOfFrames() {
		return numOfFrames;
	}
	
	public int getRowIndex() {
		return rowIndex;
	}
	
}
