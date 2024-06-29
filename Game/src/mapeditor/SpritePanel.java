package mapeditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class SpritePanel extends Panel{

	public static int SPRITE_CHOICE = 0;
	public static int LAYER = 1;
	
	public SpritePanel() {
		
		//addMouseListener(new MouseInputs((Panel) this));
		
		setLayout(new GridLayout(5, 10));
        setPreferredSize(new Dimension(MapEditor.TILE_SIZE * 6, 1000));
        
	}
	
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    
	    switch(LAYER) {
	    case 1:
	    	for(int i = 0; i < 60; i++)
		    	g.drawImage(MapEditor.BaseSprites[i], i%6 * 32, i/6 * 32, 32, 32, null);
	    	break;
	    case 2:
	    	for(int i = 0; i < 60; i++)
		    	g.drawImage(MapEditor.OverlapSprites[i], i%6 * 32, i/6 * 32, 32, 32, null);
	    	break;
	    case 3:
	    	for(int i = 0; i < 25; i++)
		    	g.drawImage(MapEditor.CollisionStaticSprites[i], i%5 * 32, i/5 * 32, 32, 32, null);
	    	break;
	    case 4:
	    	for(int i = 0; i < 25; i++)
		    	g.drawImage(MapEditor.WalkableAnimatedSprites[i], i%5 * 32, i/5 * 32, 32, 32, null);
	    	break;
	    case 5:
	    	for(int i = 0; i < 25; i++)
		    	g.drawImage(MapEditor.CollisionAnimatedSprites[i], i%5 * 32, i/5 * 32, 32, 32, null);
	    	break;
	    }
	    
	    switch(LAYER) {
	    case 1:
	    case 2:
	    	g.setColor(Color.BLUE);
		    g.drawRect(SPRITE_CHOICE%6 * 32, SPRITE_CHOICE/6 * 32, 32, 32);
	    	break;
	    case 3:
	    	g.setColor(Color.BLUE);
		    g.drawRect(SPRITE_CHOICE%5 * 32, SPRITE_CHOICE/5 * 32, 32, 32);
	    	break;
	    case 4:
	    case 5:
	    	g.setColor(Color.BLUE);
		    g.drawRect(0, SPRITE_CHOICE/5 * 32, 32 * 5, 32);
	    	break;
	    }
	}
	
	public int getSpriteChoice() {
		return SPRITE_CHOICE;
	}

	public void setSpriteChoice(int spriteChoice) {
		SpritePanel.SPRITE_CHOICE = spriteChoice;
	}
	
	
	
}