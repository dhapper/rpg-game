package ui;

import static utilz.Constants.UI.BattleButton.*; 
import static utilz.Constants.GraphicsConstants.*;  

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import battle.Move;
import graphics.GraphicsHelp;
import utilz.LoadSave;

public class BattleButton extends Button {

	private Move move;
	
	public BattleButton(Move move, int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.move = move;
		
		this.width = B_WIDTH;
		this.height = B_HEIGHT;
		
		loadImgs(B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
		initBounds(B_WIDTH, B_HEIGHT);
	}
	
	private void loadImgs(int defaultWidth, int defaultHeight) {
		imgs = new BufferedImage[3];
		String moveName = move.getName();
		int red = 100, green = 100, blue = 100;
		//System.out.println(move.getWeapon());
		switch (move.getWeapon()) {
		case "SWORD":
			red = 200;
			green = 100;
			blue = 100;
			break;
		case "SHIELD":
			red = 100;
			green = 100;
			blue = 200;
			break;
		}
		
		imgs[0] = new BufferedImage(B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = imgs[0].createGraphics();
        g2d.setFont(GraphicsHelp.loadCustomFont(PIXEL_FONT, Math.min(35 - moveName.length(), 20)));
        //g2d.setFont(new Font("ARIEL", Font.BOLD, 16));
        g2d.setColor(new Color(red, green, blue));
        g2d.fillRect(0, 0, defaultWidth - 1, defaultHeight - 1);
        GraphicsHelp.centeredText(moveName, B_WIDTH_DEFAULT / 2, B_HEIGHT_DEFAULT / 2, Color.BLACK, g2d);
        g2d.drawRect(0, 0, defaultWidth - 1, defaultHeight - 1);
        
        
        imgs[1] = new BufferedImage(B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT, BufferedImage.TYPE_INT_ARGB);
        g2d = imgs[1].createGraphics();
        //g2d.setFont(new Font("ARIEL", Font.BOLD, 16));
        g2d.setFont(GraphicsHelp.loadCustomFont(PIXEL_FONT, Math.min(35 - moveName.length(), 20)));
        g2d.setColor(new Color(red + 20, green + 20, blue + 20));
        g2d.fillRect(0, 0, defaultWidth - 1, defaultHeight - 1);
        GraphicsHelp.centeredText(moveName, B_WIDTH_DEFAULT / 2, B_HEIGHT_DEFAULT / 2, Color.BLACK, g2d);
        g2d.drawRect(0, 0, defaultWidth - 1, defaultHeight - 1);
        
        
        imgs[2] = new BufferedImage(B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT, BufferedImage.TYPE_INT_ARGB);
        g2d = imgs[2].createGraphics();
        //g2d.setFont(new Font("ARIEL", Font.BOLD, 16));
        g2d.setFont(GraphicsHelp.loadCustomFont(PIXEL_FONT, Math.min(35 - moveName.length(), 20)));
        g2d.setColor(new Color(red - 20, green - 20, blue - 20));
        g2d.fillRect(0, 0, defaultWidth - 1, defaultHeight - 1);
        GraphicsHelp.centeredText(moveName, B_WIDTH_DEFAULT / 2, B_HEIGHT_DEFAULT / 2, Color.BLACK, g2d);
        g2d.drawRect(0, 0, defaultWidth - 1, defaultHeight - 1);
        
        // Dispose the Graphics2D object
        g2d.dispose();
        
        
	}
}
