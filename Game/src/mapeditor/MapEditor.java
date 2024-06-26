package mapeditor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MapEditor extends JFrame{

	//JPanel spritePanel, mapPanel;
	public static SpritePanel spritePanel;
	public static MapPanel mapPanel;
	public static Tile[][] TILES;
	public static int[][] MAP;
	public static final int TILE_SIZE = 32;
	public static int WIDTH, HEIGHT;
	int width, height;
	
	public static void main(String[] args) {
		new MapEditor(10, 10);
    }
	
	public MapEditor(int width, int height) {
		WIDTH = width;
		HEIGHT = height;
		
		TILES = new Tile[HEIGHT][WIDTH];
		
		MAP = new int[HEIGHT][WIDTH];
		
		for(int i = 0; i < TILES.length; i ++) {
			for(int j = 0; j < TILES[0].length; j ++) {
				TILES[i][j] = new Tile();
				MAP[i][j] = 0;
			}
		}
		
		setTitle("Map Editor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLayout(new BorderLayout());
        
        spritePanel = new SpritePanel();
        mapPanel = new MapPanel();
        
        add(new JScrollPane(spritePanel), BorderLayout.WEST);
        add(new JScrollPane(mapPanel), BorderLayout.CENTER);
        
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        
        repaint();
	}
	
}
