package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gamestates.GameState;
import utilz.LoadSave;

import static utilz.Constants.UI.MenuButton.*;

public class MenuButton extends Button{

	private GameState state;
	
	public MenuButton(int xPos, int yPos, int rowIndex, GameState state) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.rowIndex = rowIndex;
		this.state = state;
		
		loadImgs(LoadSave.MENU_BUTTONS, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
		initBounds(B_WIDTH, B_HEIGHT);
	}
	
	public void draw(Graphics g) {
		g.drawImage(imgs[index], xPos, yPos, B_WIDTH, B_HEIGHT, null);
	}
	
	public void applyGameState() {
		GameState.state = state;
	}
	
	private void loadImgs(String fileName, int defaultWidth, int defaultHeight) {
		imgs = new BufferedImage[3];
		BufferedImage temp = LoadSave.GetResource(fileName);
		
		for(int i = 0; i < imgs.length; i++)
			imgs[i] = temp.getSubimage(i * defaultWidth, rowIndex * defaultHeight, defaultWidth, defaultHeight);
	}
}
