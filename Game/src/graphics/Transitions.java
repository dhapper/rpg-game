package graphics;

import java.awt.Color;
import java.awt.Graphics;

import main.Game;

public class Transitions {

	public static void closeCurtains(Graphics g, float xDelta) {
		g.setColor(Color.BLACK);
		g.fillRect(0, (int) (- Game.GAME_HEIGHT + xDelta), Game.GAME_WIDTH, Game.GAME_HEIGHT);
		g.fillRect(0, (int) (Game.GAME_HEIGHT - xDelta), Game.GAME_WIDTH, Game.GAME_HEIGHT);
	}

	public static void openCurtains(Graphics g, float xDelta) {
		g.setColor(Color.BLACK);
    	g.fillRect(0, (int) (-xDelta), Game.GAME_WIDTH, Game.GAME_HEIGHT / 2);
    	g.fillRect(0, (int) (Game.GAME_HEIGHT / 2 + xDelta), Game.GAME_WIDTH, Game.GAME_HEIGHT / 2);
	}
}
