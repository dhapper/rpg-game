package mapeditor;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import gamestates.GameState;
import main.GamePanel;

public class MouseInputs implements MouseListener, MouseMotionListener{

	private Panel panel;
	
	public MouseInputs(Panel panel) {
		this.panel = panel;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		
		Tile[][] tiles = MapEditor.GetTiles(SpritePanel.LAYER);
			
		int x = e.getX() / MapEditor.TILE_SIZE;
		int y = e.getY() / MapEditor.TILE_SIZE;
		
		if (panel instanceof MapPanel) {
			
			if(x > MapEditor.WIDTH - 1 || x < 0)
				return;
			
			if(y > MapEditor.HEIGHT - 1 || y < 0)
				return;
			
			if ((e.getModifiersEx() & MouseEvent.BUTTON3_DOWN_MASK) != 0) { // Check if the right mouse button is being dragged
				tiles[y][x].resetTile();
				panel.repaint();
	            return;
	        }
			
            tiles[y][x].changeSprite(SpritePanel.SPRITE_CHOICE);
            
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
		
		Tile[][] tiles = MapEditor.GetTiles(SpritePanel.LAYER);
		
		if(e.getX() < 0 || e.getX() > MapEditor.WIDTH * 32)
			return;
		
		if(e.getY() < 0 || e.getY() > MapEditor.HEIGHT * 32)
			return;
		
		
		int x = e.getX() / MapEditor.TILE_SIZE;
		int y = e.getY() / MapEditor.TILE_SIZE;
		
		int spriteID = tiles[y][x].getSpriteID();
		
		if (e.getButton() == MouseEvent.BUTTON3) {
			tiles[y][x].resetTile();
			panel.repaint();
            return;
        }
		
		if(KeyboardInputs.keyHeld) {
			if (KeyboardInputs.key == 's' || KeyboardInputs.key == 'S') {
				if(SaveMap.IsMapValid(MapEditor.GetTiles(1)))
					new NameMapWindow();
					//SaveMap.WriteToFile(MapEditor.TILE_LAYERS);
			return;	
			}
		}
		
		if(KeyboardInputs.keyHeld) {
			switch (KeyboardInputs.key) {
			case '1':
				SpritePanel.LAYER = 1;
				break;
			case '2':
				SpritePanel.LAYER = 2;
				break;
			case '3':
				SpritePanel.LAYER = 3;
				break;
			case '4':
				SpritePanel.LAYER = 4;
				break;
			case '5':
				SpritePanel.LAYER = 5;
				break;
			}
			panel.repaint();
		}
		
//		if(KeyboardInputs.keyHeld) {
//			if (KeyboardInputs.key == '1') {
//				SpritePanel.LAYER = 1;
//			}else if(KeyboardInputs.key == '2') {
//				SpritePanel.LAYER = 2;
//			}
//			panel.repaint();
//		}
		
		if (panel instanceof MapPanel) {
			tiles[y][x].changeSprite(SpritePanel.SPRITE_CHOICE);
        }else if(panel instanceof SpritePanel) {
        	
        	switch(SpritePanel.LAYER) {
        	case 1:
			case 2:
				((SpritePanel) panel).setSpriteChoice(x + 6*y);
				break;
			case 3:
			case 4:
			case 5:
				((SpritePanel) panel).setSpriteChoice(x + 5*y);
				break;
        	}
		}
		
		if(KeyboardInputs.keyHeld) {
			if (KeyboardInputs.key == 'b' || KeyboardInputs.key == 'B') {
				for(int i = 0; i < tiles.length; i ++) {
					for(int j = 0; j < tiles[0].length; j ++) {
						tiles[i][j].changeSprite(SpritePanel.SPRITE_CHOICE); 
					}
				}
		    }
		}
		
		if(KeyboardInputs.keyHeld) {
			if (KeyboardInputs.key == 'c' || KeyboardInputs.key == 'C') {
				for(int i = 0; i < tiles.length; i ++) {
					for(int j = 0; j < tiles[0].length; j ++) {
						if(tiles[i][j].getSpriteID() == spriteID)
							tiles[i][j].changeSprite(SpritePanel.SPRITE_CHOICE); 
					}
				}
		    }
		}
		
		panel.repaint();
		
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
