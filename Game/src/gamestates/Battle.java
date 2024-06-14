package gamestates;

import static utilz.Constants.BattleConstants.*;

import static utilz.Constants.PlayerConstants.GetAnimationData;
import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.UI.*;

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
import ui.Button;
import ui.MenuButton;
import ui.TargetButton;
import utilz.Constants;

public class Battle extends State implements Statemethods{

	Enemy test, test2, test3;
	
	BattleManager battleManager;
	private ArrayList<BattleButton> battleButtons;
	private ArrayList<TargetButton> targetButtons;
	private ArrayList<Button> buttons;
	ArrayList<int[]> aniVars = new ArrayList<int[]>();
	boolean animating;
	boolean updatingBattleButtons = true;
	boolean updatingTargetButtons = false;
	Move[] moves;
	
	int prevAni = -1;
	int aniLength = -1;
	
	int aniTick = 0;
	int aniIndex = 0;
	
	int idleAniTick = 0;
	int idleAniIndex = 0;
	
	int tickCount = 0;
	int currFrame;
	
	int playerAction = BATTLE_IDLE;
	int animationArrayIndex = playerAction;
	
	float xSpeed, ySpeed;
	int imageMode;
	
	boolean walkingBack = false;
	
	public Battle(Game game) {
		super(game);
		
		
		this.test = new Enemy(300, 300, 100, 100);
		this.test2 = new Enemy(300, 300, 100, 100);
		this.test3 = new Enemy(300, 300, 100, 100);
		
		Entity players[] = {game.getOverworld().getPlayer(), test, test2};
		this.battleManager = new BattleManager(this, players, ONE_VS_TWO);
		
		moves = battleManager.getBattleStates().get(0).getEntity().getActiveSword().getActiveMoves();
		
		this.battleButtons = new ArrayList<BattleButton>();
		this.targetButtons = new ArrayList<TargetButton>();
		this.buttons = new ArrayList<Button>();
		loadButtons();
	}

	@Override
	public void update() {
		updateAniTick();
		
		for (Button b : buttons)
			b.update();
	}
	
	
	@Override
	public void draw(Graphics g) {
		
		for(BattleState bs : battleManager.getBattleStates()) {
			switch(bs.getPosition()) {
			case LEFT_MAIN:
			case LEFT_1:
				g.drawString("Health: "+bs.getStats()[HEALTH], 100, 100);
				break;
			case LEFT_2:
				g.drawString("Health: "+bs.getStats()[HEALTH], 100, 150);
				break;
			case RIGHT_MAIN:
			case RIGHT_1:
				g.drawString("Health: "+bs.getStats()[HEALTH], 500, 100);
				break;
			case RIGHT_2:
				g.drawString("Health: "+bs.getStats()[HEALTH], 500, 150);
				break;
			}
		}
		
		for(BattleState bs : battleManager.getBattleStates()) {
			BufferedImage[][] playerAnimations = bs.getEntity().getAnimations();
			if(bs.isAlive()) {
				if(bs.isAnimating()) {
					if(bs.getPosition() == LEFT_MAIN || bs.getPosition() == LEFT_1 || bs.getPosition() == LEFT_2) {
						if(imageMode == DEFAULT_IMAGE) {
							g.drawImage(playerAnimations[playerAction][currFrame], (int) bs.getPlayerX(), (int) bs.getPlayerY(), 300, 300, null);	
						}else if(imageMode == MIRROR_IMAGE){
							g.drawImage(GraphicsHelp.MirrorImage(playerAnimations[playerAction][currFrame]), (int) bs.getPlayerX(), (int) bs.getPlayerY(), 300, 300, null);
						}
					}else if(bs.getPosition() == RIGHT_MAIN || bs.getPosition() == RIGHT_1 || bs.getPosition() == RIGHT_2) {
						if(imageMode == DEFAULT_IMAGE) {
							g.drawImage(GraphicsHelp.MirrorImage(playerAnimations[playerAction][currFrame]),  (int) bs.getPlayerX(), (int) bs.getPlayerY(), 300, 300, null);	
						}else if(imageMode == MIRROR_IMAGE){
							g.drawImage(playerAnimations[playerAction][currFrame], (int) bs.getPlayerX(), (int) bs.getPlayerY(), 300, 300, null);
						}
					}
				}else {
					if(bs.getPosition() == LEFT_MAIN || bs.getPosition() == LEFT_1 || bs.getPosition() == LEFT_2) {
						g.drawImage(playerAnimations[4][idleAniIndex], (int) bs.getPlayerX(), (int) bs.getPlayerY(), 300, 300, null);
					}else if(bs.getPosition() == RIGHT_MAIN || bs.getPosition() == RIGHT_1 || bs.getPosition() == RIGHT_2) {
						g.drawImage(GraphicsHelp.MirrorImage(playerAnimations[4][idleAniIndex]), (int) bs.getPlayerX(), (int)bs.getPlayerY(), 300, 300, null);
					}
				}
			}
		}
			

		if(updatingTargetButtons) 
			for (TargetButton tb : targetButtons) {
				//if(tb.getBattleState() != null)
					if(tb.getBattleState().isAlive())
						tb.draw(g);}
		
		for (BattleButton bb : battleButtons)
			bb.draw(g);
	}
	
	
	private void loadButtons() {
		battleButtons.add(new BattleButton(moves[0].getName(), Game.GAME_WIDTH / 2 - Constants.UI.BattleButton.B_WIDTH, Game.GAME_HEIGHT*3/4, 0));
		battleButtons.add(new BattleButton(moves[1].getName(), Game.GAME_WIDTH / 2, Game.GAME_HEIGHT*3/4, 0));
		battleButtons.add(new BattleButton(moves[2].getName(), Game.GAME_WIDTH / 2 - Constants.UI.BattleButton.B_WIDTH, Game.GAME_HEIGHT*3/4 + Constants.UI.BattleButton.B_HEIGHT, 0));
		battleButtons.add(new BattleButton(moves[3].getName(), Game.GAME_WIDTH / 2, Game.GAME_HEIGHT*3/4 + Constants.UI.BattleButton.B_HEIGHT, 0));
		
		for(BattleState bs : battleManager.getBattleStates()) {
			if(!bs.equals(battleManager.getBattleStates().get(PLAYER)))
				targetButtons.add(new TargetButton((int)bs.getPlayerX() + Constants.UI.TargetButton.X_OFFSET, (int)bs.getPlayerY() + Constants.UI.TargetButton.Y_OFFSET, 0, bs));
		}
		
		buttons.addAll(battleButtons);
		buttons.addAll(targetButtons);
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
			if(bs.isAnimating()) {
				bs.setPlayerX(bs.getPlayerX()+xSpeed);
				bs.setPlayerY(bs.getPlayerY()+ySpeed);
			}
		}
		
		if(tickCount == aniLength) {
			aniVars.remove(0);
			if(aniVars.isEmpty()) {
				updatingBattleButtons = true;
				animating = false;
				xSpeed = 0;
				playerAction = BATTLE_IDLE;
				animationArrayIndex = playerAction;
				for(BattleState bs : battleManager.getBattleStates())
					bs.setAnimating(false);
			}else {
				for(BattleState bs : battleManager.getBattleStates())
					if(bs.isAnimating())
						setAniVars(aniVars.get(0), bs);
			}
		}
	}
	
	public void manageAnimations(BattleState bs) {
		initAni(bs);
	}
	
	public void initAni(BattleState bs) {
		updatingTargetButtons = false;
		updatingBattleButtons = false;
		int speedX = 3;
		if(bs.getPosition() == RIGHT_MAIN || bs.getPosition() == RIGHT_1 || bs.getPosition() == RIGHT_2)
			speedX *= -1;
		animating = true;
		aniVars.add(new int[] {WALKING_SIDEWAYS, 170, speedX, DEFAULT_IMAGE});	// use vars
		aniVars.add(new int[] {ATTACK, 0, 0, DEFAULT_IMAGE});
		aniVars.add(new int[] {WALKING_SIDEWAYS, 170, -speedX, MIRROR_IMAGE});
		setAniVars(aniVars.get(0), bs);
	}
	
	public void setAniVars(int aniVars[], BattleState bs) {
		prevAni = -1;
		aniTick = 0;
		aniIndex = 0;
		tickCount = 0;
		
		playerAction = aniVars[0];
		aniLength = aniVars[1];
		xSpeed = aniVars[2];
		imageMode = aniVars[3];
		ySpeed = 0;
		
		animationArrayIndex = WALKING_SIDEWAYS;
		
		if(playerAction == WALKING_SIDEWAYS) {
			ySpeed = checkHeights(bs);
			
			if(walkingBack) {
				ySpeed = -checkHeights(bs);
			}
			walkingBack = !walkingBack;
		}
		
		
		if(playerAction != WALKING_SIDEWAYS) {
			Move move = new Move(bs.getCurrMove());
			animationArrayIndex = move.getAnimationID();
			aniLength = 0;
			for(int time : GetAnimationData(animationArrayIndex)[1]){
				aniLength += time;
			}
		}
	}
	
	public float checkHeights(BattleState attacker) {
		BattleState target = battleManager.getBattleStates().get(attacker.getMoveTarget());
		if(attacker.getInitialHeight() == target.getInitialHeight() - 75)
			return 0.45f;
		else if(attacker.getInitialHeight() == target.getInitialHeight() - 150)
			return 0.9f;
		else if(attacker.getInitialHeight() == target.getInitialHeight() + 75)
			return -0.45f;
		else if(attacker.getInitialHeight() == target.getInitialHeight() + 150)
			return -0.9f;
		else
			return 0;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(updatingBattleButtons) {
			for (BattleButton bb : battleButtons) {
				if(isIn(e, bb)) {	
					bb.setMousePressed(true);
					break;
				}
			}	
		}
		
		if(updatingTargetButtons) {
			for (TargetButton tb : targetButtons) {
				if(tb.getBattleState().isAlive())
					if(isIn(e, tb)) {
						tb.setMousePressed(true);
						break;
					}
			}
		}
		
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if(updatingBattleButtons) {
			for (BattleButton bb : battleButtons) {
				if(isIn(e, bb)) {
					if(bb.isMousePressed()) {
						if(bb.equals(battleButtons.get(0))) {
							battleManager.getBattleStates().get(0).setCurrMove(moves[0].getName());
							updatingTargetButtons = true;
						}else if(bb.equals(battleButtons.get(1))) {
							battleManager.getBattleStates().get(0).setCurrMove(moves[1].getName());
							updatingTargetButtons = true;
						}else if(bb.equals(battleButtons.get(2))) {
							battleManager.getBattleStates().get(0).setCurrMove(moves[2].getName());
							updatingTargetButtons = true;
						}else if(bb.equals(battleButtons.get(3))) {
							battleManager.getBattleStates().get(0).setCurrMove(moves[3].getName());
							updatingTargetButtons = true;
						}
						break;
					}
						
				}
			}
			resetBattleButtons();
		}
		
		if(updatingTargetButtons) {
			for (TargetButton tb : targetButtons) {
				if(isIn(e, tb)) {
					if(tb.isMousePressed()) { 
						
						if(tb.equals(targetButtons.get(0))) {
							battleManager.getBattleStates().get(PLAYER).setMoveTarget(NPC_1);
						
						}else if(targetButtons.size() > 1) {
							if(tb.equals(targetButtons.get(1)))
								battleManager.getBattleStates().get(PLAYER).setMoveTarget(NPC_2);
						}else if(targetButtons.size() > 2) {
							if(tb.equals(targetButtons.get(2)))
								battleManager.getBattleStates().get(PLAYER).setMoveTarget(NPC_3);
						}
						
						break;
					}	
				}
			}
			resetTargetButtons();
		}
	}

	private void resetBattleButtons() {
		for(BattleButton bb : battleButtons)
			bb.resetBools();
	}
	
	private void resetTargetButtons() {
		for(TargetButton tb : targetButtons)
			tb.resetBools();
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		for(Button b : buttons)
			b.setMouseOver(false);
		
		for(Button b : buttons)
			if(isIn(e, b)) {
				b.setMouseOver(true);
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
}
