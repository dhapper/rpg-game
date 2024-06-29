package locations;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import gamestates.Overworld;
import graphics.Animation;
import main.Game;
import utilz.LoadSave;

public class LocationProcessor {
	
	private Area currentArea;
	private ArrayList<Area> areas;
	
	BufferedImage[] walkableStaticSprites, overlapSprites, collisionStaticSprites, walkableAnimatedSprites, collisionAnimatedSprites;
	ArrayList<int[][]> maps;
	
	public LocationProcessor(Overworld overworld) {
		this.areas = new ArrayList<Area>();
		
		
		walkableStaticSprites = new BufferedImage[60];
		overlapSprites = new BufferedImage[60];
		collisionStaticSprites = new BufferedImage[25];
		walkableAnimatedSprites = new BufferedImage[25];
		collisionAnimatedSprites = new BufferedImage[25];
		for(int i = 0; i < 25; i++) {
			collisionStaticSprites[i] = LoadSave.GetResource(LoadSave.SPRITESHEET_C_S_La1).getSubimage(i%5*32, i/5*32, 32, 32);
			walkableAnimatedSprites[i] = LoadSave.GetResource(LoadSave.SPRITESHEET_W_A_La1).getSubimage(i%5*32, i/5*32, 32, 32);
			collisionAnimatedSprites[i] = LoadSave.GetResource(LoadSave.SPRITESHEET_C_A_La1).getSubimage(i%5*32, i/5*32, 32, 32);
		}
		
		for(int i = 0; i < 60; i++) {
			walkableStaticSprites[i] = LoadSave.GetResource(LoadSave.SPRITESHEET_BASE).getSubimage(i%6*32, i/6*32, 32, 32);
			overlapSprites[i] = LoadSave.GetResource(LoadSave.SPRITESHEET_OVERLAP).getSubimage(i%6*32, i/6*32, 32, 32);	
		}
		
		loadAnimations();
		
		areas.add(new Area(overworld, 0));
		areas.add(new Area(overworld, 1));
		currentArea = areas.get(1);
		
		//this.map = currentArea.getMap();
		//this.map = readMapDataFromFile(currentArea.getAreaFileName());
		loadArea();
		
		//debug();
	}
	
	public void loadArea() {
		this.maps = currentArea.ReadMapDataFromFile(currentArea.getAreaFileName());
	}
	
//	public void debug() {
//		for(int j = 0; j < maps.get(0).length; j++) {
//			for(int i = 0; i < maps.get(0)[0].length; i++) {
//				for(Animation anim: animations) {
//					if(anim.getFileName().equals(LoadSave.SPRITESHEET_W_A_La1)) {
//						if(maps.get(4)[j][i] != -1) {
//							System.out.println("spriteNum: "+maps.get(4)[j][i] / 5);
//							
//							System.out.println("row: "+anim.getRowIndex());
//							if(maps.get(4)[j][i] / 5 == anim.getRowIndex()) {
//								System.out.println("flower");
//								//g.drawImage(anim.getAnimation()[anim.getAniIndex()],
//								//	i * Game.TILES_SIZE - xOffset, j * Game.TILES_SIZE - yOffset, Game.TILES_SIZE, Game.TILES_SIZE, null);
//								
//								
//							}
//						}
//					}
//				}
//				
//			}
//		}
//	}
	
	public void draw(Graphics g, int xOffset, int yOffset) {
		
		for(int j = 0; j < maps.get(0).length; j++) {
			for(int i = 0; i < maps.get(0)[0].length; i++) {
				// land
				g.drawImage(walkableStaticSprites[maps.get(0)[j][i]], i * Game.TILES_SIZE - xOffset, j * Game.TILES_SIZE - yOffset, Game.TILES_SIZE, Game.TILES_SIZE, null);
				
				// water
				for(Animation anim: animations) {
					if(anim.getFileName().equals(LoadSave.SPRITESHEET_C_A_La1)) {
						if(maps.get(3)[j][i] != -1) {
							g.drawImage(anim.getAnimation()[anim.getAniIndex()],
									i * Game.TILES_SIZE - xOffset, j * Game.TILES_SIZE - yOffset, Game.TILES_SIZE, Game.TILES_SIZE, null);
						}
					}
				}
				
				// overlap
				if(maps.get(1)[j][i] != -1) {
					g.drawImage(overlapSprites[maps.get(1)[j][i]], i * Game.TILES_SIZE - xOffset, j * Game.TILES_SIZE - yOffset, Game.TILES_SIZE, Game.TILES_SIZE, null);
				}
				
				// boxes
				if(maps.get(2)[j][i] != -1) {
					g.drawImage(collisionStaticSprites[maps.get(2)[j][i]], i * Game.TILES_SIZE - xOffset, j * Game.TILES_SIZE - yOffset, Game.TILES_SIZE, Game.TILES_SIZE, null);
				}
				
				// flowers
				for(Animation anim: animations) {
					if(anim.getFileName().equals(LoadSave.SPRITESHEET_W_A_La1)) {
						if(maps.get(4)[j][i] != -1) {
							if(maps.get(4)[j][i] / 5 == anim.getRowIndex()) {
								g.drawImage(anim.getAnimation()[anim.getAniIndex()],
									i * Game.TILES_SIZE - xOffset, j * Game.TILES_SIZE - yOffset, Game.TILES_SIZE, Game.TILES_SIZE, null);
							}
						}
					}
				}
				
			}
		}
	}
	
	public void update() {
		updateAniTick();
	}
	
	private void updateAniTick() {
		
		//for (ArrayList<Animation> list : animationsList)
			for(Animation anim: animations) {//list) {
				anim.setAniTick(anim.getAniTick() + 1);
				if(anim.getAniTick() >= anim.getAniSpeed()) {
					anim.setAniTick(0);
					anim.setAniIndex(anim.getAniIndex() + 1);
				}
				if(anim.getAniIndex() >= anim.getNumOfFrames())
					anim.setAniIndex(0);
			}
	}
	
	private ArrayList<ArrayList<Animation>> animationsList;
	private ArrayList<Animation> animations;
	
	private void loadAnimations() {//String fileName) {

		animations = new ArrayList<Animation>();

        //if (fileName == LoadSave.SPRITESHEET_C_A_La1) {
            animations.add(new Animation(LoadSave.SPRITESHEET_C_A_La1, 0, 2, 120));
        //} else if (fileName == LoadSave.SPRITESHEET_W_A_La1) {
            animations.add(new Animation(LoadSave.SPRITESHEET_W_A_La1, 0, 3, 60));
            animations.add(new Animation(LoadSave.SPRITESHEET_W_A_La1, 1, 3, 60));
        //}

        //animationsList.add(animations);
	}

	public Area getCurrentArea() {
		return currentArea;
	}

	public void setCurrentArea(int areaIndex) {
		this.currentArea = areas.get(areaIndex);
	}

	public ArrayList<Area> getAreas() {
		return areas;
	}

	public void setAreas(ArrayList<Area> areas) {
		this.areas = areas;
	}

	public ArrayList<int[][]> getMaps() {
		return maps;
	}

	public void setMaps(ArrayList<int[][]> maps) {
		this.maps = maps;
	}

	
	
}
