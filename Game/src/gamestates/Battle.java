package gamestates;

import static utilz.Constants.BattleConstants.*;
import static utilz.Constants.GraphicsConstants.PIXEL_FONT;
import static utilz.Constants.PlayerConstants.GetAnimationData;
import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.UI.*;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import battle.BattleManager;
import battle.BattleState;
import battle.Move;
import entities.Enemy;
import entities.Entity;
import entities.Sword;
import graphics.DrawBattle;
import graphics.GraphicsHelp;
import main.Game;
import ui.AltBattleButton;
import ui.BattleButton;
import ui.Button;
import ui.HoveringRect;
import ui.MenuButton;
import ui.TargetButton;
import utilz.Constants;
import utilz.LoadSave;

public class Battle extends State implements Statemethods{

	private Enemy test, test2, test3;
	
	private BattleManager battleManager;
	private DrawBattle drawBattle;
	private BattleState player;
	
	ArrayList<BattleState> animationOrderedBattleStates;
	private ArrayList<BattleButton> battleButtons;
	private ArrayList<AltBattleButton> altBattleButtons;
	private ArrayList<TargetButton> targetButtons;
	private ArrayList<HoveringRect> hoveringPlayerButtons;
	private ArrayList<Button> buttons;
	private ArrayList<int[]> aniVars = new ArrayList<int[]>();
	private boolean updatingBattleButtons = true;
	private boolean updatingTargetButtons = false;
	private Move[] moves;
	
	private int prevAni = -1;
	private int aniLength = -1;
	private int aniTick = 0;
	private int aniIndex = 0;
	private int idleAniTick = 0;
	private int idleAniIndex = 0;
	private int tickCount = 0;
	private int currFrame;
	private int playerAction = BATTLE_IDLE;
	private int animationArrayIndex = playerAction;
	private float xSpeed, ySpeed;
	private int imageMode;
	private boolean walkingBack = false;
	
	private String moveErrorMessage = "NONE";
	
	
	public Battle(Game game) {
		super(game);
		
		
		this.test = new Enemy(300, 300, 100, 100);
		this.test2 = new Enemy(300, 300, 100, 100);
		this.test3 = new Enemy(300, 300, 100, 100);
		
		Entity players[] = {game.getOverworld().getPlayer(), test, test2, test3};
		this.battleManager = new BattleManager(this, players, TWO_VS_TWO);
		this.drawBattle = new DrawBattle(this);
		this.player = battleManager.getBattleStates().get(PLAYER);
		this.moves = player.getEntity().getActiveSword().getActiveMoves();
		
		this.battleButtons = new ArrayList<BattleButton>();
		this.targetButtons = new ArrayList<TargetButton>();
		this.altBattleButtons = new ArrayList<AltBattleButton>();
		this.hoveringPlayerButtons = new ArrayList<HoveringRect>();
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
		
		// bg
		g.drawImage(LoadSave.GetResource(LoadSave.bg), 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		
		// render animated player last
		animationOrderedBattleStates = new ArrayList<BattleState>();
		for(BattleState bs : battleManager.getBattleStates())
			if(!bs.isAnimating())
				animationOrderedBattleStates.add(bs);
		for(BattleState bs : battleManager.getBattleStates())
			if(bs.isAnimating())
				animationOrderedBattleStates.add(bs);
		
		for(BattleState bs : animationOrderedBattleStates)
			if(bs.isAlive())
				drawBattle.drawPlayers(g, bs, playerAction, currFrame, idleAniIndex);

		// info
		drawBattle.drawBars(g);
		
		if(!moveErrorMessage.equals("NONE"))
			drawBattle.drawMoveErrorMessage(g, moveErrorMessage);
		
		for(HoveringRect hr : hoveringPlayerButtons) {
			if(hr.isMouseOver())
				drawBattle.drawPlayerInfoPanel(g, hr.getBattleState());
		}
		
		
		// buttons
		if(updatingTargetButtons) 
			for (TargetButton tb : targetButtons) 
				if(tb.getBattleState().isAlive())
					if(player.getCurrMove().getTarget().equals("ENEMY"))
						if(tb.getBattleState().isEnemyTeam())
							tb.draw(g);
		
		for (BattleButton bb : battleButtons)
			bb.draw(g);
		
		for(AltBattleButton abb : altBattleButtons)
			abb.draw(g);
		
	}
	
	
	private void loadButtons() {
		
		battleButtons.add(new BattleButton(moves[0].getName(), Game.GAME_WIDTH / 2 - Constants.UI.BattleButton.B_WIDTH, Game.GAME_HEIGHT*3/4));
		battleButtons.add(new BattleButton(moves[1].getName(), Game.GAME_WIDTH / 2, Game.GAME_HEIGHT*3/4));
		battleButtons.add(new BattleButton(moves[2].getName(),
				Game.GAME_WIDTH / 2 - Constants.UI.BattleButton.B_WIDTH, Game.GAME_HEIGHT*3/4 + Constants.UI.BattleButton.B_HEIGHT));
		battleButtons.add(new BattleButton(moves[3].getName(), Game.GAME_WIDTH / 2, Game.GAME_HEIGHT*3/4 + Constants.UI.BattleButton.B_HEIGHT));
		
		for(BattleState bs : battleManager.getBattleStates()) {
			hoveringPlayerButtons.add(new HoveringRect((int)bs.getPlayerX() + Constants.UI.TargetButton.X_OFFSET,
					(int)bs.getPlayerY() + Constants.UI.TargetButton.Y_OFFSET, 0, bs));
			
			if(!bs.equals(battleManager.getBattleStates().get(PLAYER))) {
				targetButtons.add(new TargetButton((int)bs.getPlayerX() + Constants.UI.TargetButton.X_OFFSET,
						(int)bs.getPlayerY() + Constants.UI.TargetButton.Y_OFFSET, 0, bs));
			}
		}
		
		altBattleButtons.add(new AltBattleButton("SWAP",
				Game.GAME_WIDTH / 2 - Constants.UI.BattleButton.B_WIDTH - Constants.UI.AltBattleButton.B_WIDTH, Game.GAME_HEIGHT*3/4, SWAP));
		altBattleButtons.add(new AltBattleButton("FF", Game.GAME_WIDTH / 2 + Constants.UI.BattleButton.B_WIDTH, Game.GAME_HEIGHT*3/4, FF));
		
		
		
		buttons.addAll(battleButtons);
		buttons.addAll(targetButtons);
		buttons.addAll(altBattleButtons);
		buttons.addAll(hoveringPlayerButtons);
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
				xSpeed = 0;
				playerAction = BATTLE_IDLE;
				animationArrayIndex = playerAction;
				for(BattleState bs : battleManager.getBattleStates()) {
					battleManager.getBattleStates().get(bs.getMoveTarget()).setPrevHealth(battleManager.getBattleStates().get(bs.getMoveTarget()).getStats()[HEALTH]);
					bs.setPrevStamina(bs.getStats()[STAMINA]);
					bs.setAnimating(false);
					if(bs.getDefensiveMoveQuantity() == 0)
						bs.setBlockingStance(false);
				}
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
		
		if(bs.getCurrMove().getAnimationType() != null) {
			if(bs.getCurrMove().getAnimationType().equals("ATTACK")) {
			
				aniVars.add(new int[] {WALKING_SIDEWAYS, 170, speedX, DEFAULT_IMAGE});	// use vars
				aniVars.add(new int[] {ATTACK, 0, 0, DEFAULT_IMAGE});
				aniVars.add(new int[] {WALKING_SIDEWAYS, 170, -speedX, MIRROR_IMAGE});
			
			}else if(bs.getCurrMove().getAnimationType().equals("SWAP")){
				aniVars.add(new int[] {ATTACK, 0, 0, DEFAULT_IMAGE});
			}else if(bs.getCurrMove().getAnimationType().equals("BLOCK")) {
				aniVars.add(new int[] {BLOCK, 0, 0, DEFAULT_IMAGE});
			}
		}
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
				battleManager.getBattleStates().get(bs.getMoveTarget()).setPrevHealth(battleManager.getBattleStates().get(bs.getMoveTarget()).getStats()[HEALTH]);
				bs.setPrevStamina(bs.getStats()[STAMINA]);
				ySpeed = -checkHeights(bs);
			}
			walkingBack = !walkingBack;
		}
		
		
		if(playerAction != WALKING_SIDEWAYS) {
			Move move = bs.getCurrMove();
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
			
			for (AltBattleButton abb : altBattleButtons) {
				if(isIn(e, abb)) {	
					abb.setMousePressed(true);
					break;
				}
			}
		}
		
		if(updatingTargetButtons) {
			for (TargetButton tb : targetButtons) {
				if(tb.getBattleState().isAlive())
					if((player.getCurrMove().getTarget().equals("ENEMY")))
						if(tb.getBattleState().isEnemyTeam())
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
						if(bb.equals(battleButtons.get(MOVESLOT_1)))
							battleManager.getDamageCalc().isMoveValid(player, moves[0]);
						else if(bb.equals(battleButtons.get(MOVESLOT_2)))
							battleManager.getDamageCalc().isMoveValid(player, moves[1]);
						else if(bb.equals(battleButtons.get(MOVESLOT_3)))
							battleManager.getDamageCalc().isMoveValid(player, moves[2]);
						else if(bb.equals(battleButtons.get(MOVESLOT_4)))
							battleManager.getDamageCalc().isMoveValid(player, moves[3]);
						
						if(player.getCurrMove().getTarget() != null) {
							if(player.getCurrMove().getTarget().equals("ENEMY")) {
								updatingTargetButtons = true;
							}else if(player.getCurrMove().getTarget().equals("SELF")) {
								player.setMoveTarget(PLAYER);;
							}
						}
						
						
						break;
					}
						
				}
			}
			resetBattleButtons();
		
			for(AltBattleButton abb : altBattleButtons) {
				if(isIn(e, abb)) {
					if(abb.isMousePressed()) {
						if(abb.equals(altBattleButtons.get(SWAP))) {
							player.setCurrMove(Moves.SWAP);
						}else if(abb.equals(altBattleButtons.get(FF))) {
							GameState.state = GameState.OVERWORLD;
						}
					}
				}
			}
			resetAltBattleButtons();
		}
		
		
		if(updatingTargetButtons) {
			for (TargetButton tb : targetButtons) {
				if(isIn(e, tb)) {
					if(tb.isMousePressed()) {
						if(tb.equals(targetButtons.get(0))) {
							player.setMoveTarget(NPC_1);
						}else if(tb.equals(targetButtons.get(1))) {
							player.setMoveTarget(NPC_2);
						}else if(tb.equals(targetButtons.get(2))) {
							player.setMoveTarget(NPC_3);
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
	
	private void resetAltBattleButtons() {
		for(AltBattleButton abb : altBattleButtons)
			abb.resetBools();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		for(Button b : buttons)
			b.setMouseOver(false);
		
		for(Button b : buttons)
			if(isIn(e, b)) {
				b.setMouseOver(true);
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

	public String getMoveErrorMessage() {
		return this.moveErrorMessage;
	}
	
	public void setMoveErrorMessage(String moveErrorMessage) {
		this.moveErrorMessage = moveErrorMessage;
	}
	
	public void setTargetButtons(boolean updatingTargetButtons) {
		this.updatingTargetButtons = updatingTargetButtons;
	}

	public BattleManager getBattleManager() {
		return battleManager;
	}

	public ArrayList<BattleState> getAnimationOrderedBattleStates() {
		return animationOrderedBattleStates;
	}

	public void setAnimationOrderedBattleStates(ArrayList<BattleState> animationOrderedBattleStates) {
		this.animationOrderedBattleStates = animationOrderedBattleStates;
	}

	public int getImageMode() {
		return imageMode;
	}
	
	
	
	
}
