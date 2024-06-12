package ui;

import static utilz.Constants.UI.BattleButton.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import graphics.GraphicsHelp;
import utilz.LoadSave;

public class BattleButton extends Button {

	private String moveName;
	
	public BattleButton(String moveName, int xPos, int yPos, int rowIndex) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.rowIndex = rowIndex;
		this.moveName = moveName;
		
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
        g2d.setFont(new Font("ARIEL", Font.BOLD, 14));
        g2d.setColor(new Color(200, 0, 0));
        g2d.fillRect(0, 0, defaultWidth - 1, defaultHeight - 1);
        GraphicsHelp.borderedText(moveName, B_WIDTH_DEFAULT / 2, B_HEIGHT_DEFAULT / 2, Color.BLACK, Color.WHITE, 1, g2d);
        g2d.drawRect(0, 0, defaultWidth - 1, defaultHeight - 1);
        
        
        imgs[1] = new BufferedImage(B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT, BufferedImage.TYPE_INT_ARGB);
        g2d = imgs[1].createGraphics();
        g2d.setFont(new Font("ARIEL", Font.BOLD, 14));
        g2d.setColor(new Color(200, 100, 100));
        g2d.fillRect(0, 0, defaultWidth - 1, defaultHeight - 1);
        GraphicsHelp.borderedText(moveName, B_WIDTH_DEFAULT / 2, B_HEIGHT_DEFAULT / 2, Color.BLACK, Color.WHITE, 1, g2d);
        g2d.drawRect(0, 0, defaultWidth - 1, defaultHeight - 1);
        
        
        imgs[2] = new BufferedImage(B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT, BufferedImage.TYPE_INT_ARGB);
        g2d = imgs[2].createGraphics();
        g2d.setFont(new Font("ARIEL", Font.BOLD, 14));
        g2d.setColor(new Color(100, 0, 0));
        g2d.fillRect(0, 0, defaultWidth - 1, defaultHeight - 1);
        GraphicsHelp.borderedText(moveName, B_WIDTH_DEFAULT / 2, B_HEIGHT_DEFAULT / 2, Color.BLACK, Color.WHITE, 1, g2d);
        g2d.drawRect(0, 0, defaultWidth - 1, defaultHeight - 1);
        
        // Dispose the Graphics2D object
        g2d.dispose();
        
        
	}
}
