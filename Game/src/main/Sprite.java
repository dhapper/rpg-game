package main;

import java.awt.image.BufferedImage;

import mapeditor.SpriteSheet;

public class Sprite {

	private int index;
	private BufferedImage sprite;
	
	public Sprite(int index) {
		this.index = index;
		this.sprite = new SpriteSheet().getSprite(index);
	}
}
