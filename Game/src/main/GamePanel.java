package main;

import java.awt.Dimension; 

import java.awt.Graphics;
import javax.swing.JPanel;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;

public class GamePanel extends JPanel{

	private MouseInputs mouseInputs;
	private Game game;
	
	public GamePanel(Game game) {
		
		this.game = game;
		
		setPanelSize();
		
		addKeyListener(new KeyboardInputs(this));
		mouseInputs = new MouseInputs(this);
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);
	}


	private void setPanelSize() {
		Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
		setMinimumSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
		
	}

	
	public void updateGame(){
	
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		game.render(g);
	}

	public Game getGame() {
		return game;
	}
	
}
