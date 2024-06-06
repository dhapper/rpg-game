package utilz;

import main.Game;

public class Constants {

	public static class UI{
		public static class Buttons{
			public static final int B_WIDTH_DEFAULT = 120;
			public static final int B_HEIGHT_DEFAULT = 40;
			public static final int B_SCALE = 2;
			public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE * B_SCALE);
			public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE * B_SCALE);
		}
	}
	
	public static class Directions{
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3; 
	}
	
	public static class PlayerConstants{
		
		public static class PlayerDimensions{
			public static final int DEFAULT_PLAYER_WIDTH = 20;
			public static final int DEFAULT_PLAYER_HEIGHT = 32;
			public static final int PLAYER_SCALE = 2;
			public static final int PLAYER_WIDTH = (int) (DEFAULT_PLAYER_WIDTH * Game.SCALE * PLAYER_SCALE);
			public static final int PLAYER_HEIGHT = (int) (DEFAULT_PLAYER_HEIGHT * Game.SCALE * PLAYER_SCALE);
			public static float X_DRAW_OFFSET = PLAYER_WIDTH / 5;
			public static float Y_DRAW_OFFSET = PLAYER_HEIGHT * 3 / 4;
			public static int HITBOX_WIDTH = PLAYER_WIDTH * 3 / 5;
			public static int HITBOX_HEIGHT = PLAYER_HEIGHT / 4;
			
			public static final float PLAYER_SPEED = 2f * Game.SCALE;
		}
		
		public static final int WALKING = 0;
		public static final int IDLE = 1;
		
		public static int GetSpriteAmount(int player_action) {
			switch(player_action) {
			case WALKING:
				return 6;
			case IDLE:
				return 2;
			default:
				return 0;
			}
		}
		
		public static int GetFrameDuration(int player_action) {
			switch(player_action) {
			case WALKING:
				return 30;
			case IDLE:
				return 120;
			default:
				return 0;
			}
		}
	}
	
}
