package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;


public class LoadSave {

	public static final String PLAYER_ATLAS = "player.png";
	public static final String MENU_BUTTONS = "menu buttons.png";
	
	public static final String LOCATION_ONE_DATA_LAYER_1 = "LAYER 1.png";
	public static final String LOCATION_ONE_DATA_LAYER_2 = "LAYER 2.png";
	public static final String LOCATION_ONE_DATA_LAYER_3 = "LAYER 3.png";

	public static final String LOCATION_ATLAS_LAYER_1 = "SPRITES - TILES 1.png";
	public static final String LOCATION_ATLAS_LAYER_2 = "SPRITES - TILES 2.png";
	public static final String LOCATION_ATLAS_LAYER_3 = "SPRITES - ANIMATED.png";
	
	public static BufferedImage GetSpriteAtlas(String fileName) {
		BufferedImage img = null;
		InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
		try {
			img = ImageIO.read(is);
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return img;
	}
	
	public static int[][] GetLocationData(String LOCATION_DATA){
		int numOfSprites = 25;
		
		BufferedImage img = GetSpriteAtlas(LOCATION_DATA);
		int [][] locationData = new int[img.getHeight()][img.getWidth()];
		
		for(int j = 0 ; j < img.getHeight(); j ++) {
			for(int i = 0 ; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getRed();
				if(value >= numOfSprites)
					value = 0;
				locationData[j][i] = value;
			}
		}
		return locationData;
	}
	
}
