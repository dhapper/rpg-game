package entities;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class NPC extends Entity{

	protected ArrayList<BufferedImage> sprites;
	
	public NPC(float x, float y, int width, int height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}
	
}
