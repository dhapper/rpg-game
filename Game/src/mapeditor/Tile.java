package mapeditor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Tile {

	private BufferedImage image;
	int spriteID = -1;
	
	private boolean clicked = false;
	
	public Tile() {
		this.image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
		this.spriteID = spriteID;
		
		
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(new Color(0, 0, 0, 200));
        g2d.drawRect(0, 0, image.getWidth(), image.getHeight());
        g2d.setColor(new Color(255, 255, 255, 10));
        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
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

//	public void setSpriteID(int spriteID) {
//		this.spriteID = spriteID;
//	}
	
	public void resetTile() {
		
		this.spriteID = -1;
		
		this.image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g2d = image.createGraphics();
        g2d.setColor(new Color(0, 0, 0, 200));
        g2d.drawRect(0, 0, image.getWidth(), image.getHeight());
        g2d.setColor(new Color(255, 255, 255, 10));
        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
        g2d.dispose();
	}
	
	public void changeSprite(int index) {
		this.spriteID = index;
		
		int rowIndex = index - index%5;
		
		System.out.println("sprite " + rowIndex);
		
		switch(SpritePanel.LAYER) {
		case 1:
			this.image = MapEditor.BaseSprites[index];
			break;
		case 2:
			this.image = MapEditor.OverlapSprites[index];
			break;
		case 3:
			this.image = MapEditor.CollisionStaticSprites[index];
			break;
		case 4:
			this.image = MapEditor.WalkableAnimatedSprites[rowIndex];
			break;
		case 5:
			this.image = MapEditor.CollisionAnimatedSprites[rowIndex];
			break;
		}
		
	}
	
//	public void changeImage() {
//		
//		System.out.println("CHANGE");
//		
//		image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
//		Graphics2D g2d = image.createGraphics();
//        
//        g2d.setColor(Color.BLACK);
//        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
//        g2d.setColor(Color.BLUE);
//        g2d.fillRect(1, 1, image.getWidth() - 2, image.getHeight() - 2);
//        g2d.dispose();
//	}
	
//	public boolean isClicked() {
//		return clicked;
//	}
//	
//	public void setClicked() {
//		this.clicked = true;
//	}
}
