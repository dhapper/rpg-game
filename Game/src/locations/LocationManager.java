package locations;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import graphics.Animation;
import main.Game;
import utilz.LoadSave;

public class LocationManager {

	private Game game;
	private BufferedImage[] spriteMapLayer1, spriteMapLayer2, spriteMapLayer3;
	private ArrayList<Animation> animations;
	private Location locationOne;
	
	public LocationManager(Game game) {
		this.game = game;
		this.animations = new ArrayList<Animation>();
		
		importLocationSprites();
		locationOne = new Location();
		loadAnimations();
	}
	
	private void importLocationSprites() {
		BufferedImage spriteSheetLayer1 = LoadSave.GetSpriteAtlas(LoadSave.LOCATION_ATLAS_LAYER_1);
		BufferedImage spriteSheetLayer2 = LoadSave.GetSpriteAtlas(LoadSave.LOCATION_ATLAS_LAYER_2);
		BufferedImage spriteSheetLayer3 = LoadSave.GetSpriteAtlas(LoadSave.LOCATION_ATLAS_LAYER_3);
		
		spriteMapLayer1 = new BufferedImage[25];
		spriteMapLayer2 = new BufferedImage[25];
		spriteMapLayer3 = new BufferedImage[25];
		
		int tilesInWidth = 5;
		int tilesInHeight = 5;
		
		for(int j = 0; j < tilesInHeight; j++) {
			for(int i = 0; i < tilesInWidth; i++) {
				int index = j * tilesInWidth + i;
				spriteMapLayer1[index] = spriteSheetLayer1.getSubimage(i * Game.TILES_DEFAULT_SIZE, j * Game.TILES_DEFAULT_SIZE,
						Game.TILES_DEFAULT_SIZE, Game.TILES_DEFAULT_SIZE);
				spriteMapLayer2[index] = spriteSheetLayer2.getSubimage(i * Game.TILES_DEFAULT_SIZE, j * Game.TILES_DEFAULT_SIZE,
						Game.TILES_DEFAULT_SIZE, Game.TILES_DEFAULT_SIZE);
				spriteMapLayer3[index] = spriteSheetLayer3.getSubimage(i * Game.TILES_DEFAULT_SIZE, j * Game.TILES_DEFAULT_SIZE,
						Game.TILES_DEFAULT_SIZE, Game.TILES_DEFAULT_SIZE);
			}
		}
		
	}

	public void draw(Graphics g, int xOffset, int yOffset) {
		
		for(int j = 0; j < locationOne.getLayerData(0).length; j++) {
			for(int i = 0; i < locationOne.getLayerData(0)[0].length; i++) {
				int index1 = locationOne.getSpriteIndex(0, j, i);
				int index2 = locationOne.getSpriteIndex(1, j, i);
				int index3 = locationOne.getSpriteIndex(2, j, i);
				g.drawImage(spriteMapLayer1[index1], i * Game.TILES_SIZE - xOffset, j * Game.TILES_SIZE - yOffset, Game.TILES_SIZE, Game.TILES_SIZE, null);
				g.drawImage(spriteMapLayer2[index2], i * Game.TILES_SIZE - xOffset, j * Game.TILES_SIZE - yOffset, Game.TILES_SIZE, Game.TILES_SIZE, null);
				
				for(Animation anim: animations)
					if(index3 == anim.getRowIndex() * 5)
						g.drawImage(anim.getAnimation()[anim.getAniIndex()], i * Game.TILES_SIZE - xOffset, j * Game.TILES_SIZE - yOffset, Game.TILES_SIZE, Game.TILES_SIZE, null);
			}
		}
	}
	
	public void update() {
		updateAniTick();
	}
	
	private void updateAniTick() {
		for(Animation anim: animations) {
			anim.setAniTick(anim.getAniTick() + 1);
			if(anim.getAniTick() >= anim.getAniSpeed()) {
				anim.setAniTick(0);
				anim.setAniIndex(anim.getAniIndex() + 1);
			}
			if(anim.getAniIndex() >= anim.getNumOfFrames())
				anim.setAniIndex(0);
		}
	}
	
	private void loadAnimations() {
		animations.add(new Animation(LoadSave.LOCATION_ATLAS_LAYER_3, 0, 2, 120));
		animations.add(new Animation(LoadSave.LOCATION_ATLAS_LAYER_3, 1, 3, 60));
		animations.add(new Animation(LoadSave.LOCATION_ATLAS_LAYER_3, 2, 3, 60));
	}
	
	public Location getCurrentLocation() {
		return locationOne;
	}
	
}
