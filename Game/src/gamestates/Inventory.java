package gamestates;

import static utilz.Constants.UI.InventoryButton.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.List;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import entities.Armour;
import entities.Player;
import entities.Shield;
import entities.Sword2;
import graphics.DrawInventoryUI;
import main.Game;
import ui.BackButton;
import ui.Button;
import ui.ItemButton;
import ui.TypeButton;
import ui.MenuButton;
import utilz.Constants;

public class Inventory extends State implements Statemethods{

	private TypeButton[] typeButton = new TypeButton[3];
	private ArrayList<ItemButton> swordButtons;
	private ArrayList<ItemButton> shieldButtons;
	private ArrayList<ItemButton> armourButtons;
	private ArrayList<Button> buttons;
	private BackButton backButton;
	
	private Player player;
	private DrawInventoryUI drawInventory;
	
	private boolean updatingSwordButtons = true;
	private boolean updatingShieldButtons = false;
	private boolean updatingArmourButtons = false;
	
	public Inventory(Game game) {
		super(game);
		
		player = game.getOverworld().getPlayer();
		drawInventory = new DrawInventoryUI(this);
		
		loadButtons();
	}

	private void loadButtons() {
		buttons = new ArrayList<Button>();
		swordButtons = new ArrayList<ItemButton>();
		shieldButtons = new ArrayList<ItemButton>();
		armourButtons = new ArrayList<ItemButton>();
		
		typeButton[SWORD] = new TypeButton(X_OFFSET, Y_OFFSET_1, SWORD);
		typeButton[SHIELD] = new TypeButton(X_OFFSET, Y_OFFSET_2, SHIELD);
		typeButton[ARMOUR] = new TypeButton(X_OFFSET, Y_OFFSET_3, ARMOUR);
		
		loadItemButtons(player.getSwords(), swordButtons, SWORD);
	    loadItemButtons(player.getShields(), shieldButtons, SHIELD);
	    loadItemButtons(player.getArmoury(), armourButtons, ARMOUR);
		
	    typeButton[SWORD].setLastClicked(true);
		
		backButton = new BackButton((int) (900 * Game.SCALE), (int) (450 * Game.SCALE), 0, GameState.MENU);
		buttons.add(backButton);
	    
		buttons.add(typeButton[0]);
		buttons.add(typeButton[1]);
		buttons.add(typeButton[2]);
		
		buttons.addAll(swordButtons);
		buttons.addAll(shieldButtons);
		buttons.addAll(armourButtons);
	}
	
	private <T> void loadItemButtons(ArrayList<T> items, ArrayList<ItemButton> itemButtons, int itemType) {
	    int row = 1, col = 0, index = 0;
	    for (T item : items) {
	    	switch(itemType) {
	    	case SWORD:
	    		itemButtons.add(new ItemButton(X_OFFSET_ITEM_BUTTON + X_GAP * col, Game.GAME_HEIGHT * row / 4 - B_HEIGHT / 2, player, (Sword2) item, itemType, index));
	    		break;
	    	case SHIELD:
	    		itemButtons.add(new ItemButton(X_OFFSET_ITEM_BUTTON + X_GAP * col, Game.GAME_HEIGHT * row / 4 - B_HEIGHT / 2, player, (Shield) item, itemType, index));
	    		break;
	    	case ARMOUR:
	    		itemButtons.add(new ItemButton(X_OFFSET_ITEM_BUTTON + X_GAP * col, Game.GAME_HEIGHT * row / 4 - B_HEIGHT / 2, player, (Armour) item, itemType, index));
	    		break;
	    	}
	        col++;
	        index++;
	        if (col > 7) {
	            col = 0;
	            row++;
	        }
	    }
	}
	
	@Override
	public void update() {
		for(TypeButton tb : typeButton)
			tb.update();
		
		if(updatingSwordButtons)
			for(ItemButton ib : swordButtons)
				ib.update();
		
		if(updatingShieldButtons)
			for(ItemButton ib : shieldButtons)
				ib.update();
		
		if(updatingArmourButtons)
			for(ItemButton ib : armourButtons)
				ib.update();
		
		backButton.update();
	}

	
	@Override
	public void draw(Graphics g) {
		
		drawInventory.drawBackground(g);
		
		for(TypeButton tb : typeButton) {
			tb.draw(g);
			if(tb.isLastClicked())
				drawInventory.drawItemButtons(g, tb.getItemType());
		}
		
		backButton.draw(g);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for (TypeButton tb : typeButton) {
			if(isIn(e, tb)) {	
				tb.setMousePressed(true);
				break;
			}
		}
		
		if(updatingSwordButtons) {
			for(ItemButton ib : swordButtons) {
				if(isIn(e, ib)) {	
					ib.setMousePressed(true);
					break;
				}
			}
		}
		
		if(updatingShieldButtons) {
			for(ItemButton ib : shieldButtons) {
				if(isIn(e, ib)) {
					ib.setMousePressed(true);
					break;
				}
			}
		}
		
		if(updatingArmourButtons) {
			for(ItemButton ib : armourButtons) {
				if(isIn(e, ib)) {	
					ib.setMousePressed(true);
					break;
				}
			}
		}
		
		if(isIn(e, backButton))
			backButton.setMousePressed(true);
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		for(ItemButton ib : swordButtons) {
			if(isIn(e, ib)) {
				if(ib.isMousePressed()) {
					if(ib.getItem().equals(player.getActiveSword()) || ib.getItem().equals(player.getInactiveSword())) {
						player.swapActiveSword();
					}else {
						player.setActiveSword((Sword2)ib.getItem());
						player.loadAnimations();
					}
				break;
				} 
			}
		}
		
		for(ItemButton ib : shieldButtons) {
			if(isIn(e, ib)) {
				if(ib.isMousePressed()) {
					if(!ib.getItem().equals(player.getActiveShield())) {
						player.setActiveShield((Shield)ib.getItem());
						player.loadAnimations();
					}
				break;
				}
			}
		}
		
		for(ItemButton ib : armourButtons) {
			if(isIn(e, ib)) {
				if(ib.isMousePressed()) {
					if(!ib.getItem().equals(player.getActiveArmour())) {
						player.setActiveArmour((Armour)ib.getItem());
						player.loadAnimations();
					}
				break;
				}
			}
		}
		
		for (TypeButton tb : typeButton) {
			if(isIn(e, tb)) {
				if(tb.isMousePressed())
					resetLastClicked();
					tb.setLastClicked(true);
					switch(tb.getItemType()) {
					case SWORD:
						updatingSwordButtons = true;
						break;
					case SHIELD:
						updatingShieldButtons = true;
						break;
					case ARMOUR:
						updatingArmourButtons = true;
						break;
					}
				break;
			}
		}
		
		if(isIn(e, backButton))
			if(backButton.isMousePressed())
				backButton.applyGameState();
		
		resetButtons();
		
	}
	
	private void resetLastClicked() {
		for (TypeButton tb : typeButton)
			tb.setLastClicked(false);
		
		updatingSwordButtons = false;
		updatingShieldButtons = false;
		updatingArmourButtons = false;
	}

	private void resetButtons() {
		for(Button b : buttons)
			b.resetBools();
		
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		for(Button b : buttons)
			b.setMouseOver(false);
		
		for(Button b : buttons)
			if(isIn(e, b)) {
				b.setMouseOver(true);
			//break;	commenting this out allows for overlapping buttons
			}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
			GameState.state = GameState.MENU;
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public TypeButton[] getTypeButton() {
		return typeButton;
	}

	public ArrayList<Button> getButtons() {
		return buttons;
	}

	public ArrayList<ItemButton> getSwordButtons() {
		return swordButtons;
	}

	public ArrayList<ItemButton> getShieldButtons() {
		return shieldButtons;
	}

	public ArrayList<ItemButton> getArmourButtons() {
		return armourButtons;
	}


}
