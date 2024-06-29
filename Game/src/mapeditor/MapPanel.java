package mapeditor;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class MapPanel extends Panel{

	public boolean test = true;
	
	public MapPanel() {
		
		//addMouseListener(new MouseInputs((Panel) this));
		
		setLayout(new GridLayout(MapEditor.HEIGHT, MapEditor.WIDTH));
        setPreferredSize(new Dimension(MapEditor.WIDTH * MapEditor.TILE_SIZE, MapEditor.HEIGHT * MapEditor.TILE_SIZE));
	}
	
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    
	    for(int i = 0; i < MapEditor.TILE_LAYERS.get(0).length; i ++) {
			for(int j = 0; j < MapEditor.TILE_LAYERS.get(0)[0].length; j ++) {
				g.drawImage(MapEditor.TILE_LAYERS.get(0)[i][j].getImage(), j * MapEditor.TILE_SIZE, i * MapEditor.TILE_SIZE, MapEditor.TILE_SIZE, MapEditor.TILE_SIZE, null);
				
				g.drawImage(MapEditor.TILE_LAYERS.get(3)[i][j].getImage(), j * MapEditor.TILE_SIZE, i * MapEditor.TILE_SIZE, MapEditor.TILE_SIZE, MapEditor.TILE_SIZE, null);
				
				g.drawImage(MapEditor.TILE_LAYERS.get(1)[i][j].getImage(), j * MapEditor.TILE_SIZE, i * MapEditor.TILE_SIZE, MapEditor.TILE_SIZE, MapEditor.TILE_SIZE, null);
				
				g.drawImage(MapEditor.TILE_LAYERS.get(2)[i][j].getImage(), j * MapEditor.TILE_SIZE, i * MapEditor.TILE_SIZE, MapEditor.TILE_SIZE, MapEditor.TILE_SIZE, null);
				
				g.drawImage(MapEditor.TILE_LAYERS.get(4)[i][j].getImage(), j * MapEditor.TILE_SIZE, i * MapEditor.TILE_SIZE, MapEditor.TILE_SIZE, MapEditor.TILE_SIZE, null);
			}
		}
	}
	
}
