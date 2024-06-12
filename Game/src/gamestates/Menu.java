package gamestates;

import java.awt.Graphics;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import main.Game;
import ui.MenuButton;

import static utilz.Constants.UI.MenuButton.*;

public class Menu extends State implements Statemethods{

	private MenuButton[] buttons = new MenuButton[1];
	
	public Menu(Game game) {
		super(game);
		loadButtons();
	}

	private void loadButtons() {
		buttons[0] = new MenuButton(Game.GAME_WIDTH / 2 - B_WIDTH / 2, (int) (100 * Game.SCALE), 0, GameState.OVERWORLD);
		
	}

	@Override
	public void update() {
		for (MenuButton mb : buttons)
			mb.update();
		
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		for (MenuButton mb : buttons)
			mb.draw(g);
		
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
