package mapeditor;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import gamestates.GameState;
import main.GamePanel;
import main.Panel;

public class MouseInputs implements MouseListener, MouseMotionListener{

	private Panel panel;
	
	public MouseInputs(Panel panel) {
		this.panel = panel;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		int x = e.getX() / MapEditor.TILE_SIZE;
		int y = e.getY() / MapEditor.TILE_SIZE;
		
		if (panel instanceof MapPanel) {
			
            MapEditor.TILES[y][x].changeSprite(SpritePanel.SPRITE_CHOICE);
            
            panel.repaint();
        }
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		//mousePressed = true;
		
		
		int x = e.getX() / MapEditor.TILE_SIZE;
		int y = e.getY() / MapEditor.TILE_SIZE;
		
		int spriteID = MapEditor.TILES[y][x].getSpriteID();
		
		if (panel instanceof MapPanel) {
            MapEditor.TILES[y][x].changeSprite(SpritePanel.SPRITE_CHOICE);
        }else if(panel instanceof SpritePanel) {
            ((SpritePanel) panel).setSpriteChoice(x + 5*y);
		}
		
		if(KeyboardInputs.keyHeld) {
			if (KeyboardInputs.key == 'b' || KeyboardInputs.key == 'B') {
				BufferedImage sprite = new SpriteSheet().getSprite(SpritePanel.SPRITE_CHOICE);
				for(int i = 0; i < MapEditor.TILES.length; i ++) {
					for(int j = 0; j < MapEditor.TILES[0].length; j ++) {
						MapEditor.TILES[i][j].setImage(sprite); 
					}
				}
		    }
		}
		
		if(KeyboardInputs.keyHeld) {
			if (KeyboardInputs.key == 'c' || KeyboardInputs.key == 'C') {
				BufferedImage sprite = new SpriteSheet().getSprite(SpritePanel.SPRITE_CHOICE);
				System.out.println(spriteID);
				for(int i = 0; i < MapEditor.TILES.length; i ++) {
					for(int j = 0; j < MapEditor.TILES[0].length; j ++) {
						if(MapEditor.TILES[i][j].getSpriteID() == spriteID)
							MapEditor.TILES[i][j].setImage(sprite); 
						System.out.print(MapEditor.TILES[i][j].getSpriteID() + " ");
					}
					System.out.println();
				}
		    }
		}
		
		panel.repaint();
		
		if(KeyboardInputs.keyHeld) {
			if (KeyboardInputs.key == 's' || KeyboardInputs.key == 'S') {
				SaveMap.WriteToFile(MapEditor.TILES);
			}
		}
		
	}

	//private int xTile, yTile;
	//private boolean mousePressed;
	
	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
