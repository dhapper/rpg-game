package ui;

import static utilz.Constants.GraphicsConstants.PIXEL_FONT;
import static utilz.Constants.UI.InventoryButton.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entities.Entity;
import entities.Item;
import entities.Player;
import entities.Sword;
import graphics.GraphicsHelp;
import utilz.LoadSave;

public class ItemButton extends Button {

	private boolean lastClicked;
	private Player player;
	private Item item;
	
	public ItemButton(int xPos, int yPos, Player player, Item item, int itemType, int index) {
		this.xPos = xPos;
		this.yPos = yPos;
		
		this.player = player;
		this.item = item;
		
		this.width = B_WIDTH;
		this.height = B_HEIGHT;
		
		
		loadImgs(B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT, itemType, index);
		initBounds(B_WIDTH, B_HEIGHT);
	}
	
	private void loadImgs(int defaultWidth, int defaultHeight, int itemType, int index) {
		
		BufferedImage item = null;
		
		switch(itemType) {
		case SWORD:
			item = LoadSave.GetResource(player.getSwords().get(index).getFileName()).getSubimage(3 * 64, 0, 16, 16);
			break;
		case SHIELD:
			item = LoadSave.GetResource(player.getShields().get(index).getFileName()).getSubimage(3 * 64, 0, 16, 16);;
			break;
		case ARMOUR:
			item = LoadSave.GetResource(player.getArmoury().get(index).getFileName()).getSubimage(3 * 64, 0, 16, 16);;
			break;
			
		}
		
		
		imgs = new BufferedImage[3];
		
		imgs[0] = new BufferedImage(B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = imgs[0].createGraphics();
        g2d.setColor(new Color(130, 110, 90));
        g2d.fillRect(0, 0, B_WIDTH, B_HEIGHT);
        g2d.drawImage(item, 0, 0, defaultWidth, defaultHeight, null);
        
        
        imgs[1] = new BufferedImage(B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT, BufferedImage.TYPE_INT_ARGB);
        g2d = imgs[1].createGraphics();
        g2d.setColor(new Color(150, 130, 110));
        g2d.fillRect(0, 0, B_WIDTH, B_HEIGHT);
        g2d.drawImage(item, 0, 0, defaultWidth, defaultHeight, null);
        
        imgs[2] = new BufferedImage(B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT, BufferedImage.TYPE_INT_ARGB);
        g2d = imgs[2].createGraphics();
        g2d.setColor(new Color(110, 90, 70));
        g2d.fillRect(0, 0, B_WIDTH, B_HEIGHT);
        g2d.drawImage(item, 0, 0, defaultWidth, defaultHeight, null);
        
        g2d.dispose();
        
	}

	public boolean isLastClicked() {
		return lastClicked;
	}

	public void setLastClicked(boolean lastClicked) {
		this.lastClicked = lastClicked;
	}

	public Item getItem() {
		return item;
	}
	
}

