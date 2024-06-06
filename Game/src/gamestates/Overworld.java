package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

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
	
	private int locationTilesWide = LoadSave.GetLocationData(LoadSave.LOCATION_ONE_DATA_LAYER_1)[0].length;
	private int locationTilesHigh = LoadSave.GetLocationData(LoadSave.LOCATION_ONE_DATA_LAYER_1).length;
	
	private int maxTilesOffsetX = locationTilesWide - Game.TILES_IN_WIDTH;
	private int maxTilesOffsetY = locationTilesHigh - Game.TILES_IN_HEIGHT;
	private int maxLocationOffsetX = maxTilesOffsetX * Game.TILES_SIZE;
	private int maxLocationOffsetY = maxTilesOffsetY * Game.TILES_SIZE;
	

	public Overworld(Game game) {
		super(game);
		initClasses();
	}
	
	private void initClasses() {
		locationManager = new LocationManager(game);
		player = new Player(400, 400, (int) (20 * Game.SCALE * 2),(int) (32 * Game.SCALE * 2));
		player.loadLocationData(locationManager.getCurrentLocation().getLayerData(0));	//locationManager.getCurrentLocation().getLocationData()
	}

	@Override
	public void update() {
		locationManager.update();
		player.update();
		checkCloseToBorder();
		
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

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_W:
			player.setUp(true);
			break;
		case KeyEvent.VK_A:
			player.setLeft(true);
			break;
		case KeyEvent.VK_S:
			player.setDown(true);
			break;
		case KeyEvent.VK_D:
			player.setRight(true);
			break;
			
		case KeyEvent.VK_ENTER:
			GameState.state = GameState.MENU;
			break;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_W:
			player.setUp(false);
			break;
		case KeyEvent.VK_A:
			player.setLeft(false);
			break;
		case KeyEvent.VK_S:
			player.setDown(false);
			break;
		case KeyEvent.VK_D:
			player.setRight(false);
			break;
		}
		
	}
	
	public void windowFocusLost() {
		player.resetDirBooleans();
		
	}
	
	public Player getPlayer() {
		return player;
	}
}
