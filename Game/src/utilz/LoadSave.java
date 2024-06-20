package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class LoadSave {

	public static final String bg = "bg.png";
	
	public static final String MENU_BUTTONS = "MENU_BUTTONS.png";
	public static final String ALT_BATTLE_BUTTONS = "BATTLE_BUTTONS_ALT.png";
	public static final String INVENTORY_ICONS = "INVENTORY_ICONS.png";
	public static final String BACK_BUTTON = "BACK_BUTTON.png";
	
	public static final String SHADOW = "SHADOW.png";
	
	public static final String SKINTONE_0 = "SKINTONE_0.png";
	public static final String SKINTONE_1 = "SKINTONE_1.png";
	
	public static final String HAIR_BOY_0 = "HAIR_BOY_0.png";
	
	public static final String SWORD_BRONZE_SWORD = "SWORD_BRONZE_SWORD.png";
	public static final String SWORD_IRON_SWORD = "SWORD_IRON_SWORD.png";
	public static final String SWORD_WOODEN_SWORD = "SWORD_WOODEN_SWORD.png";
	
	public static final String SHIELD_IRON_SHIELD = "SHIELD_IRON_SHIELD.png";
	public static final String SHIELD_STEEL_SHIELD = "SHIELD_STEEL_SHIELD.png";
	public static final String SHIELD_WOODEN_SHIELD = "SHIELD_WOODEN_SHIELD.png";
	
	public static final String ARMOUR_M_BASIC = "ARMOUR_M_BASIC.png";
	
	public static final String LAYER_MAP_Lo0_W_S_La1 = "LAYER_MAP_Lo0_W_S_La1.png";
	public static final String LAYER_MAP_Lo0_W_S_La2 = "LAYER_MAP_Lo0_W_S_La2.png";
	public static final String LAYER_MAP_Lo0_C_S_La1 = "LAYER_MAP_Lo0_C_S_La1.png";
	public static final String LAYER_MAP_Lo0_C_A_La1 = "LAYER_MAP_Lo0_C_A_La1.png";
	public static final String LAYER_MAP_Lo0_W_A_La1 = "LAYER_MAP_Lo0_W_A_La1.png";
	
	public static final String LAYER_MAP_Lo1_W_S_La1 = "LAYER_MAP_Lo1_W_S_La1.png";
	public static final String LAYER_MAP_Lo1_W_S_La2 = "LAYER_MAP_Lo1_W_S_La2.png";
	public static final String LAYER_MAP_Lo1_C_S_La1 = "LAYER_MAP_Lo1_C_S_La1.png";
	public static final String LAYER_MAP_Lo1_C_A_La1 = "LAYER_MAP_Lo1_C_A_La1.png";
	public static final String LAYER_MAP_Lo1_W_A_La1 = "LAYER_MAP_Lo1_W_A_La1.png";
	
	public static final ArrayList<String> LAYER_MAPS = new ArrayList<String>();
	
	static {
        // Add layer map strings to LAYER_MAPS
        LAYER_MAPS.add(LAYER_MAP_Lo0_W_S_La1);
        LAYER_MAPS.add(LAYER_MAP_Lo0_W_S_La2);
        LAYER_MAPS.add(LAYER_MAP_Lo0_C_S_La1);
        LAYER_MAPS.add(LAYER_MAP_Lo0_C_A_La1);
        LAYER_MAPS.add(LAYER_MAP_Lo0_W_A_La1);

        LAYER_MAPS.add(LAYER_MAP_Lo1_W_S_La1);
        LAYER_MAPS.add(LAYER_MAP_Lo1_W_S_La2);
        LAYER_MAPS.add(LAYER_MAP_Lo1_C_S_La1);
        LAYER_MAPS.add(LAYER_MAP_Lo1_C_A_La1);
        LAYER_MAPS.add(LAYER_MAP_Lo1_W_A_La1);
    }

	public static final String SPRITESHEET_W_S_La1 = "SPRITESHEET_W_S_La1.png";
	public static final String SPRITESHEET_W_S_La2 = "SPRITESHEET_W_S_La2.png";
	public static final String SPRITESHEET_C_S_La1 = "SPRITESHEET_C_S_La1.png";
	public static final String SPRITESHEET_C_A_La1 = "SPRITESHEET_C_A_La1.png";
	public static final String SPRITESHEET_W_A_La1 = "SPRITESHEET_W_A_La1.png";
	
	public static BufferedImage GetResource(String fileName) {
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
	
	public static BufferedImage GetSprite(BufferedImage spriteSheet, int xIndex, int yIndex, int spriteWidth, int spriteHeight) {
		BufferedImage sprite = spriteSheet.getSubimage(xIndex * spriteWidth, yIndex * spriteHeight, spriteWidth, spriteHeight);
		return sprite;
	}
	
	public static int[][] GetLocationData(String data){
		int numOfSprites = 25;
		
		BufferedImage img = GetResource(data);
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
