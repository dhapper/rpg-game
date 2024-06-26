package mapeditor;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JPanel;

import main.Panel;

public class MapPanel extends Panel{

	public boolean test = true;
	
	public MapPanel() {
		
		//addMouseListener(new MouseInputs((Panel) this));
		
		setLayout(new GridLayout(MapEditor.HEIGHT, MapEditor.WIDTH));
        setPreferredSize(new Dimension(MapEditor.WIDTH * MapEditor.TILE_SIZE, MapEditor.HEIGHT * MapEditor.TILE_SIZE));
	}
	
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    
	    for(int i = 0; i < MapEditor.TILES.length; i ++) {
			for(int j = 0; j < MapEditor.TILES[0].length; j ++) {
				g.drawImage(MapEditor.TILES[i][j].getImage(), j * MapEditor.TILE_SIZE, i * MapEditor.TILE_SIZE, MapEditor.TILE_SIZE, MapEditor.TILE_SIZE, null);
			}
		}
	}
	
}
