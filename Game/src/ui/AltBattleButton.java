package ui;
import static utilz.Constants.UI.AltBattleButton.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import graphics.GraphicsHelp;
import utilz.LoadSave;

public class AltBattleButton extends Button{

	private String buttonName;
	
	public AltBattleButton(String buttonName, int xPos, int yPos, int rowIndex) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.rowIndex = rowIndex;
		this.buttonName = buttonName;
		
		this.width = B_WIDTH;
		this.height = B_HEIGHT;
		
		loadImgs(LoadSave.ALT_BATTLE_BUTTONS, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
		initBounds(B_WIDTH, B_HEIGHT);
	}
	
	private void loadImgs(String fileName, int defaultWidth, int defaultHeight) {
		imgs = new BufferedImage[3];
		BufferedImage temp = LoadSave.GetResource(fileName);
		
		for(int i = 0; i < imgs.length; i++)
			imgs[i] = temp.getSubimage(i * defaultWidth, rowIndex * defaultHeight, defaultWidth, defaultHeight);
	}
}
