package gamestates;

import static utilz.Constants.BattleConstants.*;
import static utilz.Constants.PlayerConstants.GetAnimationData;
import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.UI.BattleButton.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import battlelogic.BattleManager;
import battlelogic.BattleState;
import battlelogic.Move;
import entities.Enemy;
import entities.Entity;
import graphics.GraphicsHelp;
import main.Game;
import ui.BattleButton;
import ui.MenuButton;
import utilz.Constants;

public class Battle extends State implements Statemethods{

	
	Enemy test;
	int i;
	
	int enemyX = game.GAME_WIDTH - 300 - 300;
	boolean animating;
	
	boolean updatingButtons = true;
	
	BattleManager battleManager;
	
	public ArrayList<String> turnMoves;

	private ArrayList<BattleButton> buttons;
	
	public Battle(Game game, int i) {
		super(game);
		this.i = i;
		
		this.turnMoves = new ArrayList<String>();
		this.turnMoves.add("NONE");
		this.turnMoves.add("NONE");
		
		
		this.test = new Enemy(300, 300, 100, 100);
		
		this.buttons = new ArrayList<BattleButton>();
		loadButtons();
		
		Entity players[] = {game.getOverworld().getPlayer(), test};
		this.battleManager = new BattleManager(this, players, ONE_VS_ONE);
	}

	@Override
	public void update() {
		updateAniTick();
		
		for (BattleButton bb : buttons)
			bb.update();
	}
	
	
	int buttonW = 120;
	int buttonH = 40;
	
	@Override
	public void draw(Graphics g) {
		
		for(BattleState bs : battleManager.getBattleStates()) {
			
			BufferedImage[][] playerAnimations = bs.getEntity().getAnimations();
			if(bs.isAnimating()) {
				
				//System.out.println(bs.getEntity().getName()+": "+bs.isAnimating());
				
				if(bs.getPosition() == LEFT_MAIN) {
					if(imageMode == DEFAULT_IMAGE) {
						g.drawImage(playerAnimations[playerAction][currFrame], bs.getPlayerX(), 300, 300, 300, null);	
					}else if(imageMode == MIRROR_IMAGE){
						g.drawImage(GraphicsHelp.MirrorImage(playerAnimations[playerAction][currFrame]), bs.getPlayerX(), 300, 300, 300, null);
					}
				}else if(bs.getPosition() == RIGHT_MAIN) {
					if(imageMode == DEFAULT_IMAGE) {
						g.drawImage(GraphicsHelp.MirrorImage(playerAnimations[playerAction][currFrame]),  bs.getPlayerX(), 300, 300, 300, null);	
					}else if(imageMode == MIRROR_IMAGE){
						g.drawImage(playerAnimations[playerAction][currFrame], bs.getPlayerX(), 300, 300, 300, null);
					}
				}
				
			}else {
				if(bs.getPosition() == LEFT_MAIN) {
					g.drawImage(playerAnimations[4][idleAniIndex], bs.getPlayerX(), 300, 300, 300, null);
				}else if(bs.getPosition() == RIGHT_MAIN) {
					g.drawImage(GraphicsHelp.MirrorImage(playerAnimations[4][idleAniIndex]), bs.getPlayerX(), 300, 300, 300, null);
				}
			}
		}
		
		if(imageMode == DEFAULT_IMAGE) {
			
		}else if(imageMode == MIRROR_IMAGE){
			
		}
		
		
		g.drawString("BATTLE " + i, 100, 100);
		g.drawString("HEALTH: "+battleManager.getBattleStates().get(0).getStats()[HEALTH], 100, 150);
		g.drawString("HEALTH: "+battleManager.getBattleStates().get(1).getStats()[HEALTH], 500, 150);
		
		for (BattleButton bb : buttons)
			bb.draw(g);
		
		
	}
	
	
	private void loadButtons() {
		
		buttons.add(new BattleButton("SWING", Game.GAME_WIDTH / 2, 120, 0));
		buttons.add(new BattleButton("JAB", Game.GAME_WIDTH / 2 + B_WIDTH, 120, 0));
		buttons.add(new BattleButton("DOUBLE HIT", Game.GAME_WIDTH / 2, 120 + B_HEIGHT, 0));
		buttons.add(new BattleButton("NULL", Game.GAME_WIDTH / 2 + B_WIDTH, 120 + B_HEIGHT, 0));
	}
	

	private void updateAniTick() {
		if (playerAction != prevAni) {
	        aniIndex = 0;
	        prevAni = playerAction;
	    }
		
		tickCount++;
		aniTick++;
		idleAniTick++;
		
		int[][] aniData = GetAnimationData(animationArrayIndex);
	    int[] frames = aniData[0];
	    int[] durations = aniData[1];
		
	    int[][] idleAniData = GetAnimationData(BATTLE_IDLE);
	    int[] idleFrames = idleAniData[0];
	    int[] idleDurations = idleAniData[1];
	    
	    
	    
	    if(idleAniTick >= idleDurations[idleAniIndex]) {
	    	
	    	idleAniTick = 0;
	    	idleAniIndex++;
	    	if(idleAniIndex >= idleFrames.length)
	    		idleAniIndex = 0;
	    		
	    }
	    
	    
		if(aniTick >= durations[aniIndex]) {
			aniTick = 0;
			aniIndex++;
			if(aniIndex >= frames.length)
				aniIndex  = 0;
		}
		
		currFrame = frames[aniIndex];
		
		for(BattleState bs : battleManager.getBattleStates()) {
			if(bs.isAnimating())
				bs.setPlayerX(bs.getPlayerX()+xSpeed);
		}
		
		if(tickCount == aniLength) {
			aniVars.remove(0);
			if(aniVars.isEmpty()) {
				updatingButtons = true;
				animating = false;
				xSpeed = 0;
				playerAction = BATTLE_IDLE;
				animationArrayIndex = playerAction;
				for(BattleState bs : battleManager.getBattleStates()) {
					bs.setAnimating(false);
				}
			}else {
				setAniVars(aniVars.get(0), battleManager.getBattleStates().get(PLAYER));
			}
		}
	}
	
	int prevAni = -1;
	
	int aniTick = 0;
	int aniIndex = 0;
	
	int idleAniTick = 0;
	int idleAniIndex = 0;
	
	
	int playerAction = BATTLE_IDLE;
	int currFrame;
	
	int tickCount = 0;
	int aniLength = -1;
	
	int xSpeed;
	int imageMode;
	
	int animationArrayIndex = playerAction;

	
	ArrayList<int[]> aniVars = new ArrayList<int[]>();
	
	public void initAni(BattleState bs) {
		
		updatingButtons = false;
		
		int speed = 3;
		
		if(bs.getPosition() == RIGHT_MAIN || bs.getPosition() == RIGHT_1 || bs.getPosition() == RIGHT_2)
			speed *= -1;
		
		animating = true;
		aniVars.add(new int[] {WALKING_SIDEWAYS, 170, speed, DEFAULT_IMAGE});	// use vars
		aniVars.add(new int[] {ATTACK, 0, 0, DEFAULT_IMAGE});
		aniVars.add(new int[] {WALKING_SIDEWAYS, 170, -speed, MIRROR_IMAGE});
		
		setAniVars(aniVars.get(0), bs);
	}
	
	boolean reverseSpeed = false;
	
	public void setAniVars(int aniVars[], BattleState bs) {
		
		prevAni = -1;
		aniTick = 0;
		aniIndex = 0;
		tickCount = 0;
		
		playerAction = aniVars[0];
		aniLength = aniVars[1];
		xSpeed = aniVars[2];
		imageMode = aniVars[3];
		
		animationArrayIndex = playerAction;
		
		//if(xSpeed > 0)
			//if(bs.getPosition() == RIGHT_MAIN || bs.getPosition() == RIGHT_1 || bs.getPosition() == RIGHT_2)
				//xSpeed *= -1;
		
		if(playerAction != WALKING_SIDEWAYS) {
			Move move = new Move(bs.getCurrMove());
			animationArrayIndex = new Move(turnMoves.get(0)).getAnimationID();
			aniLength = 0;
			for(int time : GetAnimationData(animationArrayIndex)[1]){
				aniLength += time;
			}
		}
		
		//System.out.println(turnMoves.get(0));
		//System.out.println(turnMoves.get(1));
		
	}
	
	public void manageAnimations(BattleState bs) {
		
		initAni(bs);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(updatingButtons) {
			for (BattleButton bb : buttons) {
				if(isIn(e, bb)) {	
					bb.setMousePressed(true);
					break;
				}
			}	
		}
		
	}
	
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if(updatingButtons) {
			for (BattleButton bb : buttons) {
				if(isIn(e, bb)) {
					if(bb.isMousePressed()) {
						if(bb.equals(buttons.get(0))) {
							battleManager.getBattleStates().get(0).setCurrMove("SWING");
						}else if(bb.equals(buttons.get(1))) {
							battleManager.getBattleStates().get(0).setCurrMove("JAB");
						}else if(bb.equals(buttons.get(2))) {
							battleManager.getBattleStates().get(0).setCurrMove("DOUBLE HIT");
						}
					
						
						break;
					}
						
				}
			}
			resetButtons();
		}
		
	}

	private void resetButtons() {
		for(BattleButton mb : buttons)
			mb.resetBools();
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		for(BattleButton bb : buttons)
			bb.setMouseOver(false);
		
		for(BattleButton bb : buttons)
			if(isIn(e, bb)) {
				bb.setMouseOver(true);
			break;
			}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_B)
			GameState.state = GameState.OVERWORLD;
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public ArrayList<String> getTurnMoves() {
		return turnMoves;
	}

	public void setTurnMoves(ArrayList<String> turnMoves) {
		this.turnMoves = turnMoves;
	}

	
}
