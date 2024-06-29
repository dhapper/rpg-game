package graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageBuilder {
	
	//public static BufferedImage[] SwordSprites(Blade blade, Handle handle, ForgedElement forgedElement) {
		//BufferedImage bladeImage = applyColourPallette(blade.image, blade.pallette, Constants.Sword.bladeTemplate);
		//..
		//..
		
//		BufferedImage[] swordParts = {bladeImage, handleImage, forgedElementImage}
//		
//		buildBufferedImage(swordParts);
		
		
	//}
	
	public static Color[] GetShadedPallette(Color colour, int numOfColours, int intensity) {
		Color[] pallette = new Color[numOfColours];
		for(int i = 0; i < pallette.length; i ++)
			pallette[i] = new Color(Clamp(colour.getRed()-i*intensity, 0, 255), Clamp(colour.getGreen()-i*intensity, 0, 255), Clamp(colour.getBlue()-i*intensity, 0, 255));
		return pallette;
	}
	
	public static int Clamp(int value, int min, int max) {
		if(value < min)
			return min;
		else if(value > max)
			return max;
		
		return value;
	}
	
	public static BufferedImage BuildBufferedImage(BufferedImage[] imageLayers) {
		int width = imageLayers[0].getWidth();
		int height = imageLayers[0].getHeight();
		BufferedImage finalImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = finalImage.createGraphics();
		
		for(BufferedImage image : imageLayers) {
			g2d.drawImage(image, 0, 0, width, height, null);	
		}
		
		g2d.dispose();
		return finalImage;
	}
	
	public static BufferedImage ApplyColourPallette(BufferedImage image, Color[] pallette, Color[] template) {
		int width = image.getWidth();
		int height = image.getHeight();
		
		for(int y = 0; y < width; y++) {
			for(int x = 0; x < height; x++) {
				int argb = image.getRGB(x, y);
				int a = (argb >> 24) & 0xFF;
                int r = (argb >> 16) & 0xFF;
                int g = (argb >> 8) & 0xFF;
                int b = argb & 0xFF;
                
                if(a != 0)
	                for (int i = 0; i < template.length; i++)
	                	if(r == template[i].getRed() && g == template[i].getGreen() && b == template[i].getBlue())
	                		image.setRGB(x, y, pallette[i].getRGB());
			}
			
		}
		
		return image;
	}
}
