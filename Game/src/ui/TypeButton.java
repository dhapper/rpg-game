package ui;

import static utilz.Constants.GraphicsConstants.PIXEL_FONT;
import static utilz.Constants.UI.InventoryButton.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import graphics.GraphicsHelp;
import utilz.LoadSave;

public class TypeButton extends Button {

	private int itemType;
	private boolean lastClicked;
	
	public TypeButton(int xPos, int yPos, int itemType) {
		this.xPos = xPos;
		this.yPos = yPos;
		
		this.itemType = itemType;
		
		this.width = B_WIDTH;
		this.height = B_HEIGHT;
		
		loadImgs(B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
		initBounds(B_WIDTH, B_HEIGHT);
	}
	
	private void loadImgs(int defaultWidth, int defaultHeight) {
		
		int x = 0, y = 0;
		
		switch(itemType) {
		case SWORD:
			x = 0;
			y = 0;
			break;
		case SHIELD:
			x = 16;
			y = 0;
			break;
		case ARMOUR:
			x = 0;
			y = 16;
			break;
		}
		
		
		imgs = new BufferedImage[3];
		
		imgs[0] = new BufferedImage(B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = imgs[0].createGraphics();
        g2d.setColor(new Color(130, 110, 90));
        g2d.fillRect(0, 0, B_WIDTH, B_HEIGHT);
        g2d.drawImage(LoadSave.GetResource(LoadSave.INVENTORY_ICONS).getSubimage(x, y, 16, 16), 0, 0, defaultWidth, defaultHeight, null);
        
        
        imgs[1] = new BufferedImage(B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT, BufferedImage.TYPE_INT_ARGB);
        g2d = imgs[1].createGraphics();
        g2d.setColor(new Color(150, 130, 110));
        g2d.fillRect(0, 0, B_WIDTH, B_HEIGHT);
        g2d.drawImage(LoadSave.GetResource(LoadSave.INVENTORY_ICONS).getSubimage(x, y, 16, 16), 0, 0, defaultWidth, defaultHeight, null);
        
        imgs[2] = new BufferedImage(B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT, BufferedImage.TYPE_INT_ARGB);
        g2d = imgs[2].createGraphics();
        g2d.setColor(new Color(110, 90, 70));
        g2d.fillRect(0, 0, B_WIDTH, B_HEIGHT);
        g2d.drawImage(LoadSave.GetResource(LoadSave.INVENTORY_ICONS).getSubimage(x, y, 16, 16), 0, 0, defaultWidth, defaultHeight, null);
        
        g2d.dispose();
        
	}

	public boolean isLastClicked() {
		return lastClicked;
	}

	public void setLastClicked(boolean lastClicked) {
		this.lastClicked = lastClicked;
	}

	public int getItemType() {
		return itemType;
	}
	
	
	
}
