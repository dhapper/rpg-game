package mapeditor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MapEditor extends JFrame{

	//JPanel spritePanel, mapPanel;
	public static SpritePanel spritePanel;
	public static MapPanel mapPanel;
	//public static Tile[][] TILES;
	public static ArrayList<Tile[][]> TILE_LAYERS;
	//public static SpriteSheet spriteSheet;
	//public static int[][] MAP;
	public static final int TILE_SIZE = 32;
	public static int WIDTH, HEIGHT;
	int width, height;
	
	public static BufferedImage[] BaseSprites, OverlapSprites, CollisionStaticSprites, WalkableAnimatedSprites, CollisionAnimatedSprites;
	
//	public static void main(String[] args) {
//		new MapEditor(null, 50, 40);
//    }
	
	public MapEditor(ArrayList<int[][]> mapData, int width, int height) {
		WIDTH = width;
		HEIGHT = height;
		
		BaseSprites = SpriteSheet.GenerateBaseSprites();
		OverlapSprites = SpriteSheet.GenerateOverlapSprites();
		CollisionStaticSprites = SpriteSheet.GenerateCollisionStaticSprites();
		WalkableAnimatedSprites = SpriteSheet.GenerateCollisionAnimatedSprites();
		CollisionAnimatedSprites = SpriteSheet.GenerateWalkableAnimatedSprites();
		
		//spriteSheet = new SpriteSheet();
		
		if(mapData == null) {
		
			TILE_LAYERS = new ArrayList<Tile[][]>();
			TILE_LAYERS.add(new Tile[HEIGHT][WIDTH]);
			TILE_LAYERS.add(new Tile[HEIGHT][WIDTH]);
			TILE_LAYERS.add(new Tile[HEIGHT][WIDTH]);
			TILE_LAYERS.add(new Tile[HEIGHT][WIDTH]);
			TILE_LAYERS.add(new Tile[HEIGHT][WIDTH]);
			
			
			for(int i = 0; i < TILE_LAYERS.get(0).length; i ++) {
				for(int j = 0; j < TILE_LAYERS.get(0)[0].length; j ++) {
					
					for(Tile[][] tiles : TILE_LAYERS) {
						tiles[i][j] = new Tile();
					}
				}
			
			}
		
		}else {
			
			
			TILE_LAYERS = new ArrayList<Tile[][]>();
			TILE_LAYERS.add(new Tile[HEIGHT][WIDTH]);
			TILE_LAYERS.add(new Tile[HEIGHT][WIDTH]);
			TILE_LAYERS.add(new Tile[HEIGHT][WIDTH]);
			TILE_LAYERS.add(new Tile[HEIGHT][WIDTH]);
			TILE_LAYERS.add(new Tile[HEIGHT][WIDTH]);
			
			
			for(int i = 0; i < TILE_LAYERS.get(0).length; i ++) {
				for(int j = 0; j < TILE_LAYERS.get(0)[0].length; j ++) {
					
					for(Tile[][] tiles : TILE_LAYERS) {
						tiles[i][j] = new Tile();
					}
					
					for(int index = 0; index < 5; index++) {
						
						switch(index) {
						case 0:
							SpritePanel.LAYER = 1;
							break;
						case 1:
							SpritePanel.LAYER = 2;
							break;
						case 2:
							SpritePanel.LAYER = 3;
							break;
						case 3:
							SpritePanel.LAYER = 4;
							//index -= index%5;
							break;
						case 4:
							SpritePanel.LAYER = 5;
							//index -= index%5;
							break;
						}
						
						
						if(mapData.get(index)[i][j] != -1)
							TILE_LAYERS.get(index)[i][j].changeSprite(mapData.get(index)[i][j]);
					}
					
					
					
				}
			}
			
			
			
			
//			for each layer
//			for each tile
			
//			int[] layerOrder = {4, 4, 4,4, 4}; 
//			
//			int index = 0;
//			for(int[][] map : mapData) {
//				Tile[][] tiles = TILE_LAYERS.get(2);
//				for(int i = 0; i < map.length; i ++) {
//					for(int j = 0; j < map[0].length; j ++) {
//						if(map[i][j] != -1)
//							tiles[i][j].changeSprite(map[i][j]);
//					}
//				}
//				index++;
//			}
		}
		
		setTitle("Map Editor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize((int) (TILE_SIZE * (32 + 5 + 2 + 0.25)), TILE_SIZE * (16 + 2));
        setLayout(new BorderLayout());
        
        spritePanel = new SpritePanel();
        mapPanel = new MapPanel();
        
        add(new JScrollPane(spritePanel), BorderLayout.WEST);
        add(new JScrollPane(mapPanel), BorderLayout.CENTER);
        
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        
        repaint();
	}

	public static Tile[][] GetTiles(int layerNumber) {
		return TILE_LAYERS.get(layerNumber - 1);
	}
	
	
}
