package locations;

import java.awt.Graphics; 
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import graphics.Animation;
import main.Game;
import utilz.LoadSave;

public class LocationManager {

	private Game game;
	//private Location locationOne;
	private ArrayList<Location> locations;
	private BufferedImage[] spriteMapLayer1, spriteMapLayer2, spriteMapLayer3, spriteMapLayer4, spriteMapLayer5;
	private Location currLocation;
	
	private ArrayList<ArrayList<Animation>> animationsList;
	private ArrayList<Animation> animations;
	
	public LocationManager(Game game) {
		this.game = game;
		this.animationsList = new ArrayList<ArrayList<Animation>>();
		this.locations = new ArrayList<Location>();
		
		importLocationSprites();
		loadAnimations(LoadSave.SPRITESHEET_C_A_La1);
		loadAnimations(LoadSave.SPRITESHEET_W_A_La1);
		//locationOne = new Location(1);
		
		locations.add(new Location(0));
		locations.add(new Location(1));
		currLocation = locations.get(1);
	}
	
	private void importLocationSprites() {
		BufferedImage spriteSheetLayer1 = LoadSave.GetResource(LoadSave.SPRITESHEET_W_S_La1);
		BufferedImage spriteSheetLayer2 = LoadSave.GetResource(LoadSave.SPRITESHEET_W_S_La2);
		BufferedImage spriteSheetLayer3 = LoadSave.GetResource(LoadSave.SPRITESHEET_C_S_La1);
		BufferedImage spriteSheetLayer4 = LoadSave.GetResource(LoadSave.SPRITESHEET_C_A_La1);
		BufferedImage spriteSheetLayer5 = LoadSave.GetResource(LoadSave.SPRITESHEET_W_A_La1);
		
		spriteMapLayer1 = new BufferedImage[25];
		spriteMapLayer2 = new BufferedImage[25];
		spriteMapLayer3 = new BufferedImage[25];
		spriteMapLayer4 = new BufferedImage[25];
		spriteMapLayer5 = new BufferedImage[25];
		
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
				spriteMapLayer4[index] = spriteSheetLayer4.getSubimage(i * Game.TILES_DEFAULT_SIZE, j * Game.TILES_DEFAULT_SIZE,
						Game.TILES_DEFAULT_SIZE, Game.TILES_DEFAULT_SIZE);
				spriteMapLayer5[index] = spriteSheetLayer5.getSubimage(i * Game.TILES_DEFAULT_SIZE, j * Game.TILES_DEFAULT_SIZE,
						Game.TILES_DEFAULT_SIZE, Game.TILES_DEFAULT_SIZE);
			}
		}
		
	}

	public void draw(Graphics g, int xOffset, int yOffset) {
		
		for(int j = 0; j < currLocation.getLayerData(0).length; j++) {
			for(int i = 0; i < currLocation.getLayerData(0)[0].length; i++) {
				int index1 = currLocation.getSpriteIndex(0, j, i);
				int index2 = currLocation.getSpriteIndex(1, j, i);
				int index3 = currLocation.getSpriteIndex(2, j, i);
				int index4 = currLocation.getSpriteIndex(3, j, i);
				int index5 = currLocation.getSpriteIndex(4, j, i);
				g.drawImage(spriteMapLayer1[index1], i * Game.TILES_SIZE - xOffset, j * Game.TILES_SIZE - yOffset, Game.TILES_SIZE, Game.TILES_SIZE, null);
				g.drawImage(spriteMapLayer2[index2], i * Game.TILES_SIZE - xOffset, j * Game.TILES_SIZE - yOffset, Game.TILES_SIZE, Game.TILES_SIZE, null);
				g.drawImage(spriteMapLayer3[index3], i * Game.TILES_SIZE - xOffset, j * Game.TILES_SIZE - yOffset, Game.TILES_SIZE, Game.TILES_SIZE, null);
				
				for (ArrayList<Animation> list : animationsList)
					for(Animation anim: list) {
						if(index4 == anim.getRowIndex() * 5 && list == animationsList.get(0))
							g.drawImage(anim.getAnimation()[anim.getAniIndex()], i * Game.TILES_SIZE - xOffset, j * Game.TILES_SIZE - yOffset, Game.TILES_SIZE, Game.TILES_SIZE, null);
						if(index5 == anim.getRowIndex() * 5 && list == animationsList.get(1))
							g.drawImage(anim.getAnimation()[anim.getAniIndex()], i * Game.TILES_SIZE - xOffset, j * Game.TILES_SIZE - yOffset, Game.TILES_SIZE, Game.TILES_SIZE, null);
	
					}
			}
		}
	}
	
	public void update() {
		updateAniTick();
	}
	
	private void updateAniTick() {
		
		for (ArrayList<Animation> list : animationsList)
			for(Animation anim: list) {
				anim.setAniTick(anim.getAniTick() + 1);
				if(anim.getAniTick() >= anim.getAniSpeed()) {
					anim.setAniTick(0);
					anim.setAniIndex(anim.getAniIndex() + 1);
				}
				if(anim.getAniIndex() >= anim.getNumOfFrames())
					anim.setAniIndex(0);
			}
	}
	
	private void loadAnimations(String fileName) {

		animations = new ArrayList<Animation>();

        if (fileName == LoadSave.SPRITESHEET_C_A_La1) {
            animations.add(new Animation(LoadSave.SPRITESHEET_C_A_La1, 0, 2, 120));
        } else if (fileName == LoadSave.SPRITESHEET_W_A_La1) {
            animations.add(new Animation(LoadSave.SPRITESHEET_W_A_La1, 0, 3, 60));
            animations.add(new Animation(LoadSave.SPRITESHEET_W_A_La1, 1, 3, 60));
        }

        animationsList.add(animations);
	}
	
	public Location getCurrentLocation() {
		return currLocation;
	}
	
	public void setCurrentLocation(int locationIndex) {
		currLocation = locations.get(locationIndex);
	}

	public ArrayList<Location> getLocations() {
		return locations;
	}
	
	
}
