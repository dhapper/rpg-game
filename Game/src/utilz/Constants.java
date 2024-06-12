package utilz;

import main.Game;

public class Constants {
	
	public static class BattleConstants{
		
		// battle type
		public static final int ONE_VS_ONE = 0;
		public static final int ONE_VS_TWO = 0;
		public static final int TWO_VS_ONE = 0;
		public static final int TWO_VS_TWO = 0;
		
		// stats
		public static final int HEALTH = 0;
		public static final int STRENGTH = 1;
		public static final int SPEED = 2;
		
		public static final int INCOMING = 0;
		public static final int OUTGOING = 1;
		
		public static final int FASTER = 0;
		public static final int SLOWER = 1;
		
		// players
		public static final int PLAYER = 0;
		public static final int NPC_1 = 1;
		public static final int NPC_2 = 2;
		public static final int NPC_3 = 3;
		
		// positions
		public static final int LEFT_MAIN = 0;
		public static final int RIGHT_MAIN = 1;
		public static final int LEFT_1 = 2;
		public static final int LEFT_2 = 3;
		public static final int RIGHT_1 = 4;
		public static final int RIGHT_2 = 5;
		
	}
	
	public static class UI{
		public static class MenuButton{
			public static final int B_WIDTH_DEFAULT = 120;
			public static final int B_HEIGHT_DEFAULT = 40;
			public static final int B_SCALE = 2;
			public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE * B_SCALE);
			public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE * B_SCALE);
		}
		
		public static class BattleButton{
			public static final int B_WIDTH_DEFAULT = 120;
			public static final int B_HEIGHT_DEFAULT = 40;
			public static final int B_SCALE = 1;
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
			public static final int DEFAULT_PLAYER_WIDTH = 64;
			public static final int DEFAULT_PLAYER_HEIGHT = 64;
			public static final int PLAYER_SCALE = 2;
			public static final int PLAYER_WIDTH = (int) (DEFAULT_PLAYER_WIDTH * Game.SCALE * PLAYER_SCALE);
			public static final int PLAYER_HEIGHT = (int) (DEFAULT_PLAYER_HEIGHT * Game.SCALE * PLAYER_SCALE);
			public static float X_DRAW_OFFSET = PLAYER_WIDTH * 2 / 5 ;
			public static float Y_DRAW_OFFSET = PLAYER_HEIGHT / 2 + PLAYER_HEIGHT / 7;
			public static int HITBOX_WIDTH = PLAYER_WIDTH / 5;
			public static int HITBOX_HEIGHT = PLAYER_HEIGHT / 10;
			
			public static final float PLAYER_SPEED = 1f * Game.SCALE;
		}
		
		public static final int IDLE = 0;
		public static final int WALKING_SIDEWAYS = 1;
		public static final int WALKING_TOWARDS = 2;
		public static final int WALKING_AWAY = 3;	
		public static final int BATTLE_IDLE = 4;
		public static final int ATTACK = 5;
		
		public static final int SWING = 20;
		public static final int JAB = 21;
		public static final int DOUBLE_HIT = 22;
		
		public static final int DEFAULT_IMAGE = 0;
		public static final int MIRROR_IMAGE = 1;
		
		public static final int DEFAULT_MOVEMENT_TIME = 30;
		public static final int BATTLE_IDLE_TIME = 60;
		public static final int[] ATK_1_FRAME_TIMES = {20, 10, 40};
		//public static final int TOTAL_ATK_1_TIME = ATK_1_FRAME_TIMES[0] + ATK_1_FRAME_TIMES[1] + ATK_1_FRAME_TIMES[2];
		
		public static int[][] GetAnimationData(int player_action) {
			
			switch(player_action) {
			case WALKING_SIDEWAYS:
			case WALKING_TOWARDS:
			case WALKING_AWAY:
				return new int[][] {
					{0,1,2,3,4,5},
					{DEFAULT_MOVEMENT_TIME, DEFAULT_MOVEMENT_TIME, DEFAULT_MOVEMENT_TIME, DEFAULT_MOVEMENT_TIME, DEFAULT_MOVEMENT_TIME, DEFAULT_MOVEMENT_TIME}
				};
			case BATTLE_IDLE:
				return new int[][] {
					{0,1,2,3},
					{BATTLE_IDLE_TIME, BATTLE_IDLE_TIME, BATTLE_IDLE_TIME, BATTLE_IDLE_TIME}
				};
			case SWING:
				return new int[][] {
					{0,1,2,2,2},
					{ATK_1_FRAME_TIMES[0], ATK_1_FRAME_TIMES[1], ATK_1_FRAME_TIMES[2], ATK_1_FRAME_TIMES[2], ATK_1_FRAME_TIMES[2]}
				};
			case JAB:
				return new int[][] {
					{0,1,2},
					{ATK_1_FRAME_TIMES[0], ATK_1_FRAME_TIMES[1], ATK_1_FRAME_TIMES[2]}
				};
			case DOUBLE_HIT:
				return new int[][] {
					{0,1,2,0,1,2},
					{ATK_1_FRAME_TIMES[0], ATK_1_FRAME_TIMES[1], ATK_1_FRAME_TIMES[2], ATK_1_FRAME_TIMES[0], ATK_1_FRAME_TIMES[1], ATK_1_FRAME_TIMES[2]}
				};
			case IDLE:
				return new int[][] {
					{0},
					{DEFAULT_MOVEMENT_TIME}
				};
			default:
				return null;
			}
		}
	}
	
}
