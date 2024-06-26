package entities;

import static utilz.Constants.PlayerConstants.PlayerDimensions.PLAYER_HEIGHT;

import static utilz.Constants.PlayerConstants.PlayerDimensions.PLAYER_WIDTH;
import static utilz.Constants.PlayerConstants.PlayerDimensions.X_DRAW_OFFSET;
import static utilz.Constants.PlayerConstants.PlayerDimensions.Y_DRAW_OFFSET;
import static utilz.Constants.PlayerConstants.CharacterConstants.*;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import graphics.GraphicsHelp;
import main.Game;

public abstract class NPC extends Entity{

	protected ArrayList<BufferedImage> sprites;
	
	protected int direction;
	protected Rectangle interactionBox;
	
	public NPC(float x, float y, int width, int height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}
	
	public void render(Graphics g, int xOffset, int yOffset) {
		if(direction != FACING_LEFT)
			g.drawImage(animations[0][direction], (int) (hitbox.x - X_DRAW_OFFSET) - xOffset, (int) (hitbox.y - Y_DRAW_OFFSET) - yOffset,
				PLAYER_WIDTH, PLAYER_HEIGHT, null);
		else
			g.drawImage(GraphicsHelp.MirrorImage(animations[0][direction]), (int) (hitbox.x - X_DRAW_OFFSET) - xOffset, (int) (hitbox.y - Y_DRAW_OFFSET) - yOffset,
					PLAYER_WIDTH, PLAYER_HEIGHT, null);
		
		//g.drawRect(interactionBox.x, interactionBox.y, interactionBox.width, interactionBox.height);
	}
	
	public void setInteractionBox(int direction) {
		if(direction == FACING_RIGHT)
			interactionBox = new Rectangle((int) getHitbox().x - 200, (int) getHitbox().y, 200, 10);
		else if(direction == FACING_FORWARD)
			interactionBox = new Rectangle((int) getHitbox().getCenterX() - 5, (int) getHitbox().getCenterY(), 10, 200);
		else if(direction == FACING_LEFT)
			interactionBox = new Rectangle((int) getHitbox().getCenterX(), (int) getHitbox().getCenterY(), 200, 10);
	}
	
	public abstract void interact(Game game);
	
	public Rectangle getInteractionBox() {
		return interactionBox;
	}
}
