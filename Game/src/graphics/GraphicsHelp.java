package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GraphicsHelp {
	
	public static Font loadCustomFont(String path, float size) {
		try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(path));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            return customFont.deriveFont(size);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            return new Font("Serif", Font.PLAIN, 20);
        }
	}
	
	public static void borderedText(String text, int x, int y, Color letter, Color border, int borderSize, Graphics2D g) {
		FontMetrics metrics = g.getFontMetrics(g.getFont());
		
		x -= metrics.stringWidth(text) / 2;
		y += metrics.getHeight() / 3;
		
		g.setColor(border);
		g.drawString(text, x + borderSize, y);
		g.drawString(text, x - borderSize, y);
		g.drawString(text, x, y + borderSize);
		g.drawString(text, x, y - borderSize);
		
		g.setColor(letter);
		g.drawString(text, x, y);
	}
	
	public static void centeredText(String text, int x, int y, Color letter, Graphics2D g) {
		FontMetrics metrics = g.getFontMetrics(g.getFont());
		
		x -= metrics.stringWidth(text) / 2;
		y += metrics.getHeight() / 3;

		g.setColor(letter);
		g.drawString(text, x, y);
	}
	
	public static BufferedImage MirrorImage(BufferedImage img) {
		if(img != null) {
			int width = img.getWidth();
	        int height = img.getHeight();
	        BufferedImage mirroredImage = new BufferedImage(width, height, img.getType());
	        AffineTransform transform = AffineTransform.getScaleInstance(-1, 1);
	        transform.translate(-width, 0);
	        Graphics2D g = mirroredImage.createGraphics();
	        g.drawImage(img, transform, null);
	        g.dispose();
	        return mirroredImage;
		}
		return null;
	}
	
	public static BufferedImage decreaseAlpha(BufferedImage image, float alphaDecreaseFactor) {
	    int width = image.getWidth();
	    int height = image.getHeight();
	    BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

	    for (int y = 0; y < height; y++) {
	        for (int x = 0; x < width; x++) {
	            int argb = image.getRGB(x, y);
	            Color color = new Color(argb, true);

	            int alpha = color.getAlpha();
	            int newAlpha = Math.min((int) (alpha * alphaDecreaseFactor), 255); // Ensure newAlpha is in range 0-255

	            Color newColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), newAlpha);
	            newImage.setRGB(x, y, newColor.getRGB());
	        }
	    }
	    return newImage;
	}

}
