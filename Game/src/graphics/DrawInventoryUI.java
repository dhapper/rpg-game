package graphics;

import static utilz.Constants.UI.InventoryButton.*;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import entities.Armour;
import entities.Item;
import entities.Player;
import entities.Shield;
import entities.Sword;
import gamestates.Inventory;
import main.Game;
import ui.Button;
import ui.ItemButton;
import ui.TypeButton;
import utilz.Constants;

public class DrawInventoryUI {

	private Inventory inventory;
	private Player player;
	
	public DrawInventoryUI(Inventory inventory) {
		this.inventory = inventory;
		this.player = inventory.getGame().getOverworld().getPlayer();
	}
	
	public void drawItemButtons(Graphics g, int itemType) {
		
		drawEmptySlots(g);
		
		switch(itemType) {
		case SWORD:
			drawButton(g, player.getSwords(), inventory.getSwordButtons(), SWORD);
			break;
		case SHIELD:
			drawButton(g, player.getShields(), inventory.getShieldButtons(), SHIELD);
			break;
		case ARMOUR:
			drawButton(g, player.getArmoury(), inventory.getArmourButtons(), ARMOUR);
			break;
		}
	}
	
	private void drawEmptySlots(Graphics g) {
		g.setColor(new Color(110, 110, 110));
		for(int row = 1; row < 4; row++) {
			for(int col = 0; col < 8; col++) {
				g.fillRect(X_OFFSET_ITEM_BUTTON + X_GAP * col, Game.GAME_HEIGHT * row / 4 - B_HEIGHT / 2, B_WIDTH, B_HEIGHT);
			}
		}
	}
	
	private <T> void drawButton(Graphics g, ArrayList<T> items, ArrayList<ItemButton> itemButtons, int itemType) {
	    int row = 1, col = 0;
	    for (T item : items) {
	    	drawButtonHighlight(g, (Item) item, row, col);
	        col++;
	        if (col > 7) {
	            col = 0;
	            row++;
	        }
	    }
	    for(ItemButton itemButton : itemButtons)
			itemButton.draw(g);
	}
	
	private void drawButtonHighlight(Graphics g, Item item, int row, int col) {
		Color active = Color.CYAN;
		Color inactive = Color.BLUE;
		
		g.setColor(Color.BLACK);
    	if(item.equals(player.getActiveSword()) || item.equals(player.getActiveShield()) || item.equals(player.getActiveArmour()))
    		g.setColor(active);
    	else if(item.equals(player.getInactiveSword()))
    		g.setColor(inactive);
    	g.fillRect(X_OFFSET_ITEM_BUTTON + X_GAP * col - BORDER_WIDTH / 2, Game.GAME_HEIGHT * row / 4 - B_HEIGHT / 2 - BORDER_WIDTH / 2,
    			B_WIDTH + BORDER_WIDTH, B_HEIGHT + BORDER_WIDTH);
	}
	
	public void drawBackground(Graphics g){
		Color selectedType = new Color(140, 140, 140);
		Color unselectedType = new Color(100, 100, 100);;
		Color bg = new Color(60, 60, 60);;
		Color outline = Color.BLACK;
		
		g.setColor(bg);
		g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
		
		for(TypeButton tb : inventory.getTypeButton()) {
			g.setColor(outline);
			g.fillRect(tb.getxPos() - TAB_MARGIN/2 - BORDER_WIDTH/2, tb.getyPos() - TAB_MARGIN/2 - BORDER_WIDTH/2,
					B_WIDTH + TAB_MARGIN + BORDER_WIDTH, B_HEIGHT + TAB_MARGIN + BORDER_WIDTH);
			
			g.setColor(unselectedType);
			g.fillRect(tb.getxPos() - TAB_MARGIN/2, tb.getyPos() - TAB_MARGIN/2, B_WIDTH + TAB_MARGIN, B_HEIGHT + TAB_MARGIN);
		}
		
		g.setColor(selectedType);
		g.fillRect(inventory.getTypeButton()[0].getxPos() + B_WIDTH + TAB_MARGIN/2 - 5, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
		g.setColor(outline);
		g.fillRect(inventory.getTypeButton()[0].getxPos() + B_WIDTH + TAB_MARGIN/2 - 5, 0, BORDER_WIDTH/2, Game.GAME_HEIGHT);
		
		for(TypeButton tb : inventory.getTypeButton())
			if(tb.isLastClicked()) {
				g.setColor(selectedType);
				g.fillRect(tb.getxPos() - TAB_MARGIN/2, tb.getyPos() - TAB_MARGIN/2, B_WIDTH + TAB_MARGIN + BORDER_WIDTH, B_HEIGHT + TAB_MARGIN);
			}
		
		g.setColor(outline);
		for(TypeButton tb : inventory.getTypeButton()) {
			g.fillRect(tb.getxPos() - BORDER_WIDTH / 2, tb.getyPos() - BORDER_WIDTH / 2, B_WIDTH + BORDER_WIDTH, B_HEIGHT + BORDER_WIDTH);
		}
		
		g.setFont(GraphicsHelp.loadCustomFont(Constants.GraphicsConstants.PIXEL_FONT, Game.GAME_WIDTH/15));
		g.drawString("INVENTORY", (int) (165 * Game.SCALE), (int) (65 * Game.SCALE));
		
	}
}
