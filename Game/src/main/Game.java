package main;

import java.awt.Graphics;

import entities.Player;
import gamestates.Battle;
import gamestates.GameState;
import gamestates.Inventory;
import gamestates.Menu;
import gamestates.Overworld;

public class Game implements Runnable{

	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;
	
	private Overworld overworld;
	private Menu menu;
	private Battle battle;
	private Inventory inventory;
	
	public final static int TILES_DEFAULT_SIZE = 32;
	public final static float SCALE = 1.5f;
	public final static int TILES_IN_WIDTH = 32;
	public final static int TILES_IN_HEIGHT = 16;
	public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
	public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
	public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
	
	public Game() {
		initClasses();
		gamePanel = new GamePanel(this);
		gameWindow = new GameWindow(gamePanel);
		gamePanel.requestFocus();

		startGameLoop();
	}

	private void initClasses() {
		menu = new Menu(this);
		overworld = new Overworld(this);
		inventory = new Inventory(this);
		//battle = new Battle(this);
	}
	
	int i = 0;
	public void createBattle() {
		i++;
		battle = new Battle(this);
	}

	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	private void update() {
		
		switch(GameState.state) {
		case MENU:
			menu.update();
			break;
		case OVERWORLD:
			overworld.update();
			break;
		case BATTLE:
			battle.update();
			break;
		case INVENTORY:
			inventory.update();
			break;
		default:
			break;
		
		}
	}
	
	public void render(Graphics g) {
		
		switch(GameState.state) {
		case MENU:
			menu.draw(g);
			break;
		case OVERWORLD:
			overworld.draw(g);
			break;
		case BATTLE:	
			battle.draw(g);
			break;
		case INVENTORY:
			inventory.draw(g);
			break;
		default:
			break;
		
		}
	}
	
	@Override
	public void run() {
		
		double timePerFrame = 1000000000.0 / FPS_SET;
		double timePerUpdate = 1000000000.0 / UPS_SET;
		
		long previousTime = System.nanoTime();
		
		int frames = 0;
		int updates = 0;
		long lastCheck = System.currentTimeMillis();
		
		double deltaU = 0;
		double deltaF = 0;
		
		while(true) {
			long currentTime = System.nanoTime();
			
			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;
			
			if(deltaU >= 1) {
				update();
				updates++;
				deltaU--;
			}
			
			if(deltaF >= 1) {
				gamePanel.repaint();
				frames++;
				deltaF--;
			}
			
			if(System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				//System.out.println("FPS: " + frames + " | UPS: " + updates);
				frames = 0;
				updates = 0;
			}
			
		}
	}	
	
	public void windowFocusLost() {
		if(GameState.state == GameState.OVERWORLD)
			overworld.getPlayer().resetDirBooleans();
	}
	
	public Menu getMenu() {
		return menu;
	}
	
	public Overworld getOverworld() {
		return overworld;
	}
	
	public Battle getBattle() {
		return battle;
	}

	public Inventory getInventory() {
		return inventory;
	}

	
}
