package graphics;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class GraphicsHelp {
	
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
	
	public static BufferedImage MirrorImage(BufferedImage img) {
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
}
