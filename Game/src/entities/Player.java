package entities;

import static utilz.Constants.PlayerConstants.PlayerDimensions.*;
import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.CanMoveHere;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import utilz.LoadSave;

public class Player extends Entity {
	
	private BufferedImage[][] animations;
	private int aniTick, aniIndex;//, aniSpeed = 30;
	private int playerAction = IDLE;
	private boolean moving = false;
	private boolean left, up, right, down;
	//private float playerSpeed = 2f * Game.SCALE;
	private int[][] locationData;
	
	private int prevAni = 99;
	
	private boolean facingRight = true;
	
	public Player(float x, float y, int width, int height) {
		super(x, y, width, height);
		loadAnimations();
		initHitbox(x, y, HITBOX_WIDTH, HITBOX_HEIGHT);//(int) (12 * Game.SCALE * playerScale), (int) (playerHeight / 4));
	}

	public void update() {
		updatePos();
		updateAniTick();
		setAnimation();
	}
	
	public void render(Graphics g, int xOffset, int yOffset) {
		
		if(facingRight)
			g.drawImage(animations[playerAction][aniIndex], (int) (hitbox.x - X_DRAW_OFFSET) - xOffset, (int) (hitbox.y - Y_DRAW_OFFSET) - yOffset,
					PLAYER_WIDTH, PLAYER_HEIGHT, null);
		else	
			g.drawImage(mirrorImage(animations[playerAction][aniIndex]), (int) (hitbox.x - X_DRAW_OFFSET) - xOffset, (int) (hitbox.y - Y_DRAW_OFFSET) - yOffset,
					PLAYER_WIDTH, PLAYER_HEIGHT, null);
		
		//drawHitbox(g);
	}
	
	private void updateAniTick() {
		aniTick++;
		if(aniTick >= GetFrameDuration(playerAction)) {
			aniTick = 0;
			aniIndex++;
			if(aniIndex >= GetSpriteAmount(playerAction))
				aniIndex  = 0;
		}
		
		// added this
		if (playerAction != prevAni)
			aniIndex = 0;
		
		prevAni = playerAction;
		
	}

	private void setAnimation() {
		if(moving)
			playerAction = WALKING;
		else
			playerAction = IDLE;
		
	}
	
	private void updatePos() {
		
		moving = false;
		
		if(left && right || up & down)
			return;
		
		if(!left && !right && !up && !down)
			return;
		
		float xSpeed = 0, ySpeed = 0;
		
		if(left && !right) {
			xSpeed = -PLAYER_SPEED;
			facingRight = false;
		}else if(!left && right) {
			xSpeed = PLAYER_SPEED;
			facingRight = true;
		}
		
		if(up && !down) {
			ySpeed = -PLAYER_SPEED;
		}else if(!up && down) {
			ySpeed = PLAYER_SPEED;
		}
		
//		if(CanMoveHere(x + xSpeed, y + ySpeed, width, height, locationData)) {
//			this.x += xSpeed;
//			this.y += ySpeed;
//			moving = true;
//		}
		
		if(CanMoveHere(hitbox.x + xSpeed, hitbox.y + ySpeed, hitbox.width, hitbox.height, locationData)) {
			hitbox.x += xSpeed;
			hitbox.y += ySpeed;
			moving = true;
		}
	}
	
	private void loadAnimations() {
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
		
		animations = new BufferedImage[5][6];
		for(int j = 0; j < animations.length; j++)
			for(int i = 0; i < animations[j].length; i++)
				animations[j][i] = img.getSubimage(i * 20, j * 32, 20, 32);
			
	}
	
	public void loadLocationData(int[][] locationData) {
		this.locationData = locationData;
	}
	
	public BufferedImage mirrorImage(BufferedImage img) {
		int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage mirroredImage = new BufferedImage(width, height, img.getType());
        AffineTransform transform = AffineTransform.getScaleInstance(-1, 1);
        transform.translate(-width, 0);
        Graphics2D g = mirroredImage.createGraphics();
        g.drawImage(img, transform, null);
        g.dispose();
        return mirroredImage;
	}
	
	public void resetDirBooleans() {
		up = false;
		down = false;
		left = false;
		right = false;
		
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}
	
	
	
}
