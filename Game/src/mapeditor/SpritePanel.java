package mapeditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Panel;

public class SpritePanel extends Panel{

	public static int SPRITE_CHOICE = 0;
	
	public SpritePanel() {
		
		//addMouseListener(new MouseInputs((Panel) this));
		
		setLayout(new GridLayout(5, 10));
        setPreferredSize(new Dimension(MapEditor.TILE_SIZE * 6, 1000));
        
	}
	
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);

	    for(int i = 0; i < 25; i++) {
	    	g.drawImage(new SpriteSheet().getSprite(i), i%5 * 32, i/5 * 32, 32, 32, null);
	    }
	    g.setColor(Color.BLUE);
	    g.drawRect(SPRITE_CHOICE%5 * 32, SPRITE_CHOICE/5 * 32, 32, 32);
	}
	
	public int getSpriteChoice() {
		return SPRITE_CHOICE;
	}

	public void setSpriteChoice(int spriteChoice) {
		this.SPRITE_CHOICE = spriteChoice;
	}
	
	
	
}