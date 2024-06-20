package ui;

import static utilz.Constants.UI.TargetButton.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import battle.BattleState;
import utilz.LoadSave;

public class TargetButton extends Button{
	
	private BattleState battleState;
	
	public TargetButton(int xPos, int yPos, int rowIndex, BattleState battleState) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.rowIndex = rowIndex;
		this.battleState = battleState;
		
		this.width = B_WIDTH;
		this.height = B_HEIGHT;
		
		loadImgs(LoadSave.MENU_BUTTONS, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
		initBounds(B_WIDTH, B_HEIGHT);
	}
	
	public void draw(Graphics g) {
		g.drawImage(imgs[index], xPos, yPos, B_WIDTH, B_HEIGHT, null);
		
	}
	
	protected void loadImgs(String fileName, int defaultWidth, int defaultHeight) {
		imgs = new BufferedImage[3];
		
		imgs[0] = new BufferedImage(B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = imgs[0].createGraphics();
        g2d.setColor(new Color(0, 0, 0, 30));
        g2d.fillRect(0, 0, defaultWidth, defaultHeight);
        
        
        imgs[1] = new BufferedImage(B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT, BufferedImage.TYPE_INT_ARGB);
        g2d = imgs[1].createGraphics();
        g2d.setColor(new Color(0, 0, 0, 60));
        g2d.fillRect(0, 0, defaultWidth, defaultHeight);
        
        
        imgs[2] = new BufferedImage(B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT, BufferedImage.TYPE_INT_ARGB);
        g2d = imgs[2].createGraphics();
        g2d.setColor(new Color(0, 0, 0, 90));
        g2d.fillRect(0, 0, defaultWidth, defaultHeight);
        
        // Dispose the Graphics2D object
        g2d.dispose();
        
        
	}

	public BattleState getBattleState() {
		return battleState;
	}

	public void setBattleState(BattleState battleState) {
		this.battleState = battleState;
	}
	
}
