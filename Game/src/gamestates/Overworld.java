package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import entities.Enemy;
import entities.Entity;
import entities.NPC;
import entities.Player;
import entities.RandomEnemy;
import graphics.Transitions;
import locations.ExitZone;
import locations.LocationManager;
import main.Game;
import utilz.LoadSave;

import static utilz.Constants.PlayerConstants.CharacterConstants.*;

public class Overworld extends State implements Statemethods{

	private Player player;
	private LocationManager locationManager;
	
	private ArrayList<NPC> characters;
	
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
	
	public void loadLocation(int locationIndex) {
		
		//create enemys here probably
		if(locationIndex == 1) {
			characters = new ArrayList<NPC>();
			characters.add(new RandomEnemy(100, 400, (int) (20 * Game.SCALE * 2),(int) (32 * Game.SCALE * 2), FACING_LEFT));
			characters.add(new RandomEnemy(200, 600, (int) (20 * Game.SCALE * 2),(int) (32 * Game.SCALE * 2), FACING_FORWARD));
		}
		
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
				player.addCharacterData(characters);
				maxTilesOffsetX = locationTilesWide - Game.TILES_IN_WIDTH;
				maxTilesOffsetY = locationTilesHigh - Game.TILES_IN_HEIGHT;
				maxLocationOffsetX = maxTilesOffsetX * Game.TILES_SIZE;
				maxLocationOffsetY = maxTilesOffsetY * Game.TILES_SIZE;
			}
		}
		
	}

	private void initClasses() {
		this.locationManager = new LocationManager(this);
		player = new Player(600, 600, (int) (20 * Game.SCALE * 2),(int) (32 * Game.SCALE * 2));
		
		//enemy1 = new Enemy(300, 300, (int) (20 * Game.SCALE * 2),(int) (32 * Game.SCALE * 2));
		//enemy2 = new Enemy(400, 400, (int) (20 * Game.SCALE * 2),(int) (32 * Game.SCALE * 2));
		//player.loadLocationData(locationManager.getCurrentLocation().getLayerData(0));	idk what this does, might be important
	}

	Enemy enemy;
	ExitZone exitZone;
	
	@Override
	public void update() {
		locationManager.update();
		player.update();
		checkCloseToBorder();
		
		if(!alertedEnemy) {
			updatePlayerMovement();
		}else {
			resetKeyOrder();
		}
		
		
		//if(locationManager.getCurrentLocation().getExitZones() != null) {
			for(ExitZone ez : locationManager.getCurrentLocation().getExitZones()) {
				if(player.getHitbox().intersects(ez.getZone())) {
					enterLocation = true;
					resetKeyOrder();
					exitZone = ez;
//					ez.loadNewLocation();
				}
			}
		//}
		
		
		for(NPC npc : characters) {	
			if(player.getHitbox().intersects(npc.getInteractionBox())) {
				if(npc instanceof Enemy) {
					enemy = (Enemy) npc;
					if(!enemy.isAlreadyInteracted()) {
						((Enemy) npc).setAlerted(true);
						alertedEnemy = true;
					}
				}
				//npc.interact(game);	
			}
		}
		
		
		if(enterLocation || exitLocation) {
			transitionTickCounter++;
			if(transitionTickCounter > transitionDuration / 4) {
				xPosBlackBar += xDeltaBlackBar;
			}
			if(transitionTickCounter > transitionDuration) {
				xPosBlackBar = 0;
				transitionTickCounter = 0;
				if(enterLocation) {
					enterLocation = false;
					exitLocation = true;
					exitZone.loadNewLocation();
				}else {
					exitLocation = false;
				}
			}
		}
		
		if(alertedEnemy) {
			transitionTickCounter++;
			if(transitionTickCounter > transitionDuration / 4) {
				xPosBlackBar += xDeltaBlackBar;
			}
			if(transitionTickCounter > transitionDuration) {
				enemy.interact(game);
				enemy.setAlreadyInteracted(true);
				alertedEnemy = false;
				xPosBlackBar = 0;
				enemy.setAlerted(false);
				transitionTickCounter = 0;
			}
		}
	}
	private boolean alertedEnemy = false, enterLocation = false, exitLocation = false;;
	private int transitionTickCounter = 0;
	private float xPosBlackBar = 0;
	private float xDeltaBlackBar = 2.04f;
	private int transitionDuration = 250;
	
	
	@Override
	public void draw(Graphics g) {
		
		locationManager.draw(g, xLocationOffset, yLocationOffset);
		//player.render(g, xLocationOffset, yLocationOffset);
		
		
		for(ExitZone ez : locationManager.getCurrentLocation().getExitZones()) {
			g.setColor(new Color(0, 0, 255, 50));
			g.fillRect(ez.getZone().x - xLocationOffset, ez.getZone().y - yLocationOffset, ez.getZone().width, ez.getZone().height);
		}
		
		
		ArrayList<Entity> renderOrder = new ArrayList<Entity>();
        //ArrayList<Entity> temp = new ArrayList<Entity>();
        renderOrder.add(player);
        for(NPC npc : characters) {
            renderOrder.add(npc);
        }
        
        // Define a comparator to sort by getY()
        Comparator<Entity> comparator = new Comparator<Entity>() {
            @Override
            public int compare(Entity e1, Entity e2) {
                return Double.compare(e1.getHitbox().getY(), e2.getHitbox().getY());
            }
        };
        
        // Sort temp by getY() values
        Collections.sort(renderOrder, comparator);
		
		
        for (Entity e : renderOrder) {
            if (e instanceof Player) {
                // Perform actions specific to Player
                Player player = (Player) e;
                player.render(g, xLocationOffset, yLocationOffset);
                // Add any other player-specific logic here
            } else if (e instanceof NPC) {
                // Perform actions specific to Enemy
                Enemy enemy = (Enemy) e;
                enemy.render(g, xLocationOffset, yLocationOffset);
                // Add any other enemy-specific logic here
            } 
		}
        
        for(NPC npc : characters) {
        	if (npc instanceof Enemy) {
        		if(((Enemy) npc).isAlerted()) {
					g.drawImage(LoadSave.GetResource(LoadSave.EXCLAMATION_INDICATOR), (int) npc.getHitbox().getCenterX() - xLocationOffset - 20,
							(int) npc.getHitbox().y - yLocationOffset - 120, 40, 40, null);
				}		
				//npc.interact(game);	
			}
		}
        
        if(alertedEnemy || enterLocation) {
        	Transitions.closeCurtains(g, xPosBlackBar);
        }else if(exitLocation) {
        	Transitions.openCurtains(g, xPosBlackBar);
        }
        
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
	
	public void resetKeyOrder() {
		player.setUp(false);
	    player.setDown(false);
	    player.setLeft(false);
	    player.setRight(false);
		keyOrder.clear();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		if(enterLocation || exitLocation)
			return;
		
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
			//game.createBattle();
			//GameState.state = GameState.BATTLE;
			break;
			
		case KeyEvent.VK_M:
			GameState.state = GameState.MENU;
			break;
			
		case KeyEvent.VK_ENTER:
			
			for(NPC npc : characters)
				if(player.getHitbox().intersects(npc.getInteractionBox())) {
					npc.interact(game);
			
//			if(player.getHitbox().intersects(characters.get(0).getInteractionBox())) {
//				System.out.println("Asdads");
//			}
			//System.out.println(player.getHitbox().getCenterX() + " " + player.getHitbox().getCenterX());
			//System.out.println(characters.get(0).getInteractionBox().getCenterX() + " " + characters.get(0).getInteractionBox().getCenterY());
			break;
			}
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

	public ArrayList<NPC> getCharacters() {
		return characters;
	}

	public void setCharacters(ArrayList<NPC> characters) {
		this.characters = characters;
	}
	
	
}
