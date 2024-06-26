package graphics;

import java.awt.Color;
import java.awt.image.BufferedImage;

import utilz.LoadSave;

public class ColourChanger {

	public static BufferedImage ChangeEyeColour(BufferedImage baseImage, Color pupil, Color sclera) {
		Color newColour = Color.BLACK;
		for(int i = 0; i < baseImage.getHeight(); i++) {
			for(int j = 0; j < baseImage.getWidth(); j++) {
				int rgb = baseImage.getRGB(j, i);
                int alpha = (rgb >> 24) & 0xFF;
                if (alpha == 0) {
                    continue;
                }
                int shade = (rgb >> 16) & 0xFF;
				switch(shade) {
				case 0:
                    newColour = pupil;
                    break;
                case 255:
                	newColour = sclera;
                	break;
				}
				baseImage.setRGB(j, i, newColour.getRGB());
			}
		}
		
		return baseImage;
	}
	
	public static BufferedImage ChangeSkinTone(BufferedImage baseImage, int skinToneIndex) {
		Color[] shades = null;
		switch(skinToneIndex) {
		case 0:
			// brown
			shades = createShades(170, 135, 100);
			break;
		case 1:
			shades = createShades(255, 215, 175);
			break;
		case 2:
			shades = createShades(255, 238, 175);
			break;
		case 3:
			shades = createShades(230, 175, 100);
			break;
		case 4:
			shades = createShades(135, 120, 100);
			break;
		}
		
		for(int i = 0; i < baseImage.getHeight(); i++) {
			for(int j = 0; j < baseImage.getWidth(); j++) {
				int rgb = baseImage.getRGB(j, i);
                int alpha = (rgb >> 24) & 0xFF;
                if (alpha == 0)
                    continue;
                int shade = (rgb >> 16) & 0xFF;
				Color newColour = Color.BLACK;
				switch(shade) {
				case 20:
                    newColour = shades[3];
                    break;
                case 80:
                    newColour = shades[2];
                    break;
                case 160:
                    newColour = shades[1];
                    break;
                case 220:
                    newColour = shades[0];
                    break;
				}
				baseImage.setRGB(j, i, newColour.getRGB());
			}
		}
		return baseImage;
	}
	
	public static Color[] createShades(int r, int g, int b) {
		Color shades[] = new Color[4];
		
		shades[0] = new Color(r, g, b);
		shades[1] = new Color(r - 30, g - 30, b - 30);
		shades[2] = new Color(r - 60, g - 60, b - 60);
		shades[3] = new Color(r - 90, g - 90, b - 90);
		
		return shades;
	}		
	
	public static BufferedImage ChangeHairColour(String hairFileName, int r, int g, int b) {
		Math.max(r, 90);
		Math.max(g, 90);
		Math.max(b, 90);
		
		BufferedImage hair = LoadSave.GetResource(hairFileName);
		int imageWidth = hair.getWidth();
		int imageHeight = hair.getHeight();
		
		for(int i = 0; i < imageHeight; i++) {
			for(int j = 0; j < imageWidth; j++) {
				int rgb = hair.getRGB(j, i);
                int alpha = (rgb >> 24) & 0xFF;
                if (alpha == 0) {
                    continue;
                }
                int shade = (rgb >> 16) & 0xFF;
				Color newColour = Color.BLACK;
				switch(shade) {
				case 20:
                    newColour = new Color(clamp(r - 90), clamp(g - 90), clamp(b - 90));
                    break;
                case 80:
                    newColour = new Color(clamp(r - 70), clamp(g - 70), clamp(b - 70));
                    break;
                case 160:
                    newColour = new Color(clamp(r - 50), clamp(g - 50), clamp(b - 50));
                    break;
                case 255:
                    newColour = new Color(clamp(r), clamp(g), clamp(b));
                    break;
				}
				hair.setRGB(j, i, newColour.getRGB());
			}
		}
		return hair;
	}
	
	private static int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }
}
