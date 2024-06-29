package gamestates;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import entities.items.Blade;
import entities.items.ForgedElement;
import entities.items.Hilt;
import entities.items.Sword;
import graphics.ImageBuilder;
import main.Game;
import ui.MenuButton;
import utilz.LoadSave;

import static utilz.Constants.UI.MenuButton.*;

public class Menu extends State implements Statemethods{

	private MenuButton[] buttons = new MenuButton[2];
	
	public Menu(Game game) {
		super(game);
		loadButtons();
	}

	private void loadButtons() {
		int gap = 25;
		int yOffsetPlayButton = (int) (100 * Game.SCALE);
		int yOffsetInventoryButton = yOffsetPlayButton + B_HEIGHT + gap; 
		
		buttons[0] = new MenuButton(Game.GAME_WIDTH / 2 - B_WIDTH / 2, yOffsetPlayButton, 0, GameState.OVERWORLD);
		buttons[1] = new MenuButton(Game.GAME_WIDTH / 2 - B_WIDTH / 2, yOffsetInventoryButton, 1, GameState.INVENTORY);
		
	}

	@Override
	public void update() {
		for (MenuButton mb : buttons)
			mb.update();
		
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
		
		for (MenuButton mb : buttons)
			mb.draw(g);
		
		
//		Color a = new Color(255, 255, 255);
//		Color b = new Color(200, 200, 200);
//		Color c = new Color(100, 100, 100);
//		Color d = new Color(0, 0, 0);
//		Color[] ab = {a, b, c, d};
//		
//		Color aa = new Color(250, 150, 150);
//		Color bb = new Color(200, 100, 100);
//		Color cc = new Color(150, 50, 50);
//		Color dd = new Color(100, 0, 0);
//		Color[] cd = {aa, bb, cc, dd};
//		
//		BufferedImage[] img = new BufferedImage[3];
//		img[0] = LoadSave.GetSprite(LoadSave.GetResource(LoadSave.SWORD_PARTS), 2, 0, 32, 32);
//		img[2] = LoadSave.GetSprite(ImageBuilder.ApplyColourPallette(LoadSave.GetResource(LoadSave.SWORD_PARTS), cd, ab), 2, 1, 32, 32);
//		img[1] = LoadSave.GetSprite(LoadSave.GetResource(LoadSave.SWORD_PARTS), 2, 2, 32, 32);
		
		g.drawImage(new Sword(new Blade(), new Hilt(), null).getSprites()[2], 0, 0, 200, 200, null);
		
		//g.drawImage(ImageBuilder.BuildBufferedImage(img), 0, 0, 400, 400, null);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for (MenuButton mb : buttons) {
			if(isIn(e, mb)) {	
				mb.setMousePressed(true);
				break;
			}
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for (MenuButton mb : buttons) {
			if(isIn(e, mb)) {
				if(mb.isMousePressed())
					mb.applyGameState();
				break;
			}
		}
		resetButtons();
		
	}

	private void resetButtons() {
		for(MenuButton mb : buttons)
			mb.resetBools();
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		for(MenuButton mb : buttons)
			mb.setMouseOver(false);
		
		for(MenuButton mb : buttons)
			if(isIn(e, mb)) {
				mb.setMouseOver(true);
			break;
			}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
			GameState.state = GameState.OVERWORLD;
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
