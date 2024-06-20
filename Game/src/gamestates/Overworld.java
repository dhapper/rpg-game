package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import entities.Player;
import locations.LocationManager;
import main.Game;
import utilz.LoadSave;

public class Overworld extends State implements Statemethods{

	private Player player;
	private LocationManager locationManager;
	
	private int xLocationOffset, yLocationOffset;
	private int leftBorder = (int) (0.4 * Game.GAME_WIDTH);
	private int rightBorder = (int) (0.6 * Game.GAME_WIDTH);
	private int upperBorder = (int) (0.4 * Game.GAME_HEIGHT);
	private int lowerBorder = (int) (0.6 * Game.GAME_HEIGHT);
	private int locationTilesWide = LoadSave.GetLocationData(LoadSave.LAYER_MAP_Lo1_W_S_La1)[0].length;
	private int locationTilesHigh = LoadSave.GetLocationData(LoadSave.LAYER_MAP_Lo1_W_S_La1).length;
	private int maxTilesOffsetX = locationTilesWide - Game.TILES_IN_WIDTH;
	private int maxTilesOffsetY = locationTilesHigh - Game.TILES_IN_HEIGHT;
	private int maxLocationOffsetX = maxTilesOffsetX * Game.TILES_SIZE;
	private int maxLocationOffsetY = maxTilesOffsetY * Game.TILES_SIZE;
	
	private LinkedList<Character> keyOrder = new LinkedList<>();
	private boolean upPressed, downPressed, leftPressed, rightPressed;

	public Overworld(Game game) {
		super(game);
		initClasses();
		
		loadLocation(1);
	}
	
	private void loadLocation(int locationIndex) {
		// update visuals 
		locationManager.setCurrentLocation(locationIndex);
		for(int index = 0; index < locationManager.getLocations().size(); index++) {
			if(index == locationIndex) {
				player.clearLocationData();
				for(String layer: LoadSave.LAYER_MAPS)
					if(layer.contains("Lo"+index+"_") && layer.contains("_C_")) {
						// update player collisions
						player.addLocationData(layer);
						// update map size
						locationTilesWide = LoadSave.GetLocationData(layer)[0].length;
						locationTilesHigh = LoadSave.GetLocationData(layer).length;
					}
				maxTilesOffsetX = locationTilesWide - Game.TILES_IN_WIDTH;
				maxTilesOffsetY = locationTilesHigh - Game.TILES_IN_HEIGHT;
				maxLocationOffsetX = maxTilesOffsetX * Game.TILES_SIZE;
				maxLocationOffsetY = maxTilesOffsetY * Game.TILES_SIZE;
			}
		}
	}

	private void initClasses() {
		this.locationManager = new LocationManager(game);
		player = new Player(100, 100, (int) (20 * Game.SCALE * 2),(int) (32 * Game.SCALE * 2));
		//player.loadLocationData(locationManager.getCurrentLocation().getLayerData(0));	idk what this does, might be important
	}

	@Override
	public void update() {
		locationManager.update();
		player.update();
		checkCloseToBorder();
		
		updatePlayerMovement();
	}

	private void checkCloseToBorder() {
		int playerX = (int) player.getHitbox().x;
		int playerY = (int) player.getHitbox().y;
		
		int xDiff = playerX - xLocationOffset;
		if(xDiff > rightBorder)
			xLocationOffset += xDiff - rightBorder;
		else if(xDiff < leftBorder)
			xLocationOffset += xDiff - leftBorder;
		
		
		if(xLocationOffset > maxLocationOffsetX)
			xLocationOffset = maxLocationOffsetX;
		else if(xLocationOffset < 0)
			xLocationOffset = 0;
		
		
		// y here
		int yDiff = playerY - yLocationOffset;
		if(yDiff > lowerBorder)
			yLocationOffset += yDiff - lowerBorder;
		else if(yDiff < upperBorder)
			yLocationOffset += yDiff - upperBorder;
		
		if(yLocationOffset > maxLocationOffsetY)
			yLocationOffset = maxLocationOffsetY;
		else if(yLocationOffset < 0)
			yLocationOffset = 0;
		
		
	}

	@Override
	public void draw(Graphics g) {
		locationManager.draw(g, xLocationOffset, yLocationOffset);
		player.render(g, xLocationOffset, yLocationOffset);
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private void updatePlayerMovement() {
	    // Reset all movement flags
	    player.setUp(false);
	    player.setDown(false);
	    player.setLeft(false);
	    player.setRight(false);

	    // Apply movement based on the highest priority key pressed
 		for (char key : keyOrder) {
 			switch (key) {
 				case 'W':
 					if (upPressed) {
 						player.setUp(true);
 						return;
 					}
 					break;
 				case 'A':
 					if (leftPressed) {
 						player.setLeft(true);
 						return;
 					}
 					break;
 				case 'S':
 					if (downPressed) {
 						player.setDown(true);
 						return;
 					}
 					break;
 				case 'D':
 					if (rightPressed) {
 						player.setRight(true);
 						return;
 					}
 					break;
 			}
 		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_W:
			upPressed = true;
			keyOrder.remove(Character.valueOf('W'));
			keyOrder.addFirst('W');
			break;
		case KeyEvent.VK_A:
			leftPressed = true;
			keyOrder.remove(Character.valueOf('A'));
			keyOrder.addFirst('A');
			break;
		case KeyEvent.VK_S:
			downPressed = true;
			keyOrder.remove(Character.valueOf('S'));
			keyOrder.addFirst('S');
			break;
		case KeyEvent.VK_D:
			rightPressed = true;
			keyOrder.remove(Character.valueOf('D'));
			keyOrder.addFirst('D');
			break;
			
		case KeyEvent.VK_Q:
			if(locationManager.getCurrentLocation().getLocationIndex() == locationManager.getLocations().size() - 1)
				loadLocation(0);
			else
				loadLocation(locationManager.getCurrentLocation().getLocationIndex() + 1);
			break;
		
		case KeyEvent.VK_B:
			game.createBattle();
			GameState.state = GameState.BATTLE;
			break;
			
		case KeyEvent.VK_ENTER:
			GameState.state = GameState.MENU;
			break;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			upPressed = false;
			keyOrder.remove(Character.valueOf('W'));
			break;
		case KeyEvent.VK_A:
			leftPressed = false;
			keyOrder.remove(Character.valueOf('A'));
			break;
		case KeyEvent.VK_S:
			downPressed = false;
			keyOrder.remove(Character.valueOf('S'));
			break;
		case KeyEvent.VK_D:
			rightPressed = false;
			keyOrder.remove(Character.valueOf('D'));
			break;
		}
		
	}
	
	public void windowFocusLost() {
		player.resetDirBooleans();
	}
	
	public Player getPlayer() {
		return player;
	}

	public LocationManager getLocationManager() {
		return locationManager;
	}
}
