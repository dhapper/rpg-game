package mapeditor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.Sprite;

public class Tile {

	private BufferedImage image;
	int spriteID = -1;
	
	private boolean clicked = false;
	
	public Tile() {
		this.image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
		this.spriteID = spriteID;
		
        // Fill the image with some color
        Graphics2D g2d = image.createGraphics();
        
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
        g2d.setColor(Color.RED);
        g2d.fillRect(1, 1, image.getWidth() - 2, image.getHeight() - 2);
        g2d.dispose();
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public int getSpriteID() {
		return spriteID;
	}

	public void setSpriteID(int spriteID) {
		this.spriteID = spriteID;
	}
	
	public void changeSprite(int index) {
		this.spriteID = index;
		this.image = new SpriteSheet().getSprite(index);
	}
	
	public void changeImage() {
		
		System.out.println("CHANGE");
		
		image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = image.createGraphics();
        
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
        g2d.setColor(Color.BLUE);
        g2d.fillRect(1, 1, image.getWidth() - 2, image.getHeight() - 2);
        g2d.dispose();
	}
	
	public boolean isClicked() {
		return clicked;
	}
	
	public void setClicked() {
		this.clicked = true;
	}
}
