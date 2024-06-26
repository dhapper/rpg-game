package utilz;

import static utilz.Constants.BattleConstants.Graphics.Bars.HEALTH_BAR_HEIGHT; 
import static utilz.Constants.BattleConstants.Graphics.Bars.HEALTH_BAR_WIDTH;
import static utilz.Constants.BattleConstants.Graphics.Bars.STAMINA_BAR_HEIGHT;

import battle.Move;
import main.Game;

public class Constants {
	
	public static class GraphicsConstants {
		public static final String PIXEL_FONT = "res/PIXEL_FONT.ttf";
	}
	
	public static class BattleConstants{
		
		public static class Graphics{
			
			public static class Players{
				public static final int PLAYER_SCALE = (int) (200 * Game.SCALE);
				public static final int X_OFFSET_LEFT = (int) (200 * Game.SCALE);
				public static final int X_OFFSET_RIGHT = Game.GAME_WIDTH - X_OFFSET_LEFT - PLAYER_SCALE;
				public static final int Y_OFFSET_MAIN = (int) (165 * Game.SCALE);;
				public static final int Y_OFFSET_HIGHER = (int) ((116 - 20) * Game.SCALE);
				public static final int Y_OFFSET_LOWER = (int) ((216 + 20) * Game.SCALE);
				
				public static final int Y_DIFF_SHORT = Y_OFFSET_LOWER - Y_OFFSET_MAIN;
				public static final int Y_DIFF_LONG = Y_OFFSET_LOWER - Y_OFFSET_HIGHER;
			}
			
			public static class Bars{
				public static final int HEALTH_BAR_WIDTH = (int) (200 * Game.SCALE);
				public static final int HEALTH_BAR_HEIGHT = (int) (20 * Game.SCALE);
				public static final int STAMINA_BAR_WIDTH = (int) (200 * Game.SCALE);
				public static final int STAMINA_BAR_HEIGHT = (int) (10 * Game.SCALE);
				
				public static final int X_OFFSET_LEFT = (int) (35 * Game.SCALE);
				public static final int X_OFFSET_RIGHT = (int) ((Game.GAME_WIDTH - X_OFFSET_LEFT - HEALTH_BAR_WIDTH) * Game.SCALE/1.5);
				
				public static final int Y_OFFSET_HIGHER = (int) (35 * Game.SCALE);
				public static final int Y_OFFSET_LOWER = (int) ((Y_OFFSET_HIGHER + HEALTH_BAR_HEIGHT * 2 + STAMINA_BAR_HEIGHT) * Game.SCALE/1.5);
			}
		}
		
		// battle type
		public static final int ONE_VS_ONE = 0;
		public static final int ONE_VS_TWO = 1;
		public static final int TWO_VS_ONE = 2;
		public static final int TWO_VS_TWO = 3;
		
		// stats
		public static final int HEALTH = 0;
		public static final int SPEED = 1;
		public static final int STRENGTH = 2;
		public static final int DEFENSE = 3;
		public static final int STAMINA = 4;
		public static final int EVASIVENESS = 5;
		public static final int ATTACK_MULTIPLIER = 6;
		public static final int DEFENSE_MULTIPLIER = 7;
		public static final int SPEED_MULTIPLIER = 8;
		public static final int EVASIVENESS_MULTIPLIER = 9;
		
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
		
		// buttons
		public static final int SWAP = 0;
		public static final int FF = 1;
		public static final int MOVESLOT_1 = 0;
		public static final int MOVESLOT_2 = 1;
		public static final int MOVESLOT_3 = 2;
		public static final int MOVESLOT_4 = 3;
		
		
		public static class Moves{
			public static final Move NONE = new Move("NONE");
			public static final Move SWAP = new Move("SWAP");	
		}
		
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
			public static final int B_WIDTH_DEFAULT = 180;
			public static final int B_HEIGHT_DEFAULT = 40;
			public static final int B_SCALE = 1;
			public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE * B_SCALE);
			public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE * B_SCALE);
		}
		
		public static class AltBattleButton{
			public static final int B_WIDTH_DEFAULT = 15;
			public static final int B_HEIGHT_DEFAULT = 15;
			public static final int B_SCALE = 3;
			public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE * B_SCALE);
			public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE * B_SCALE);
		}
		
		public static class TargetButton{
			public static final int X_OFFSET = 100;
			public static final int Y_OFFSET = 85;
			public static final int B_WIDTH_DEFAULT = 70;
			public static final int B_HEIGHT_DEFAULT = 90;
			public static final int B_SCALE = 1;
			public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE * B_SCALE);
			public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE * B_SCALE);
		}
		
		public static class InventoryButton{
			public static final int B_WIDTH_DEFAULT = 16;
			public static final int B_HEIGHT_DEFAULT = 16;
			public static final int B_SCALE = 4;
			public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE * B_SCALE);
			public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE * B_SCALE);
			
			public static final int SWORD = 0;
			public static final int SHIELD = 1;
			public static final int ARMOUR = 2;
			
			public static final int BORDER_WIDTH = 6;
			public static final int TAB_MARGIN = (int) (10 * Game.SCALE * B_SCALE);
			
			public static int X_OFFSET_ITEM_BUTTON = (int) (50 * Game.SCALE * B_SCALE);
			public static int X_GAP = (int) (B_WIDTH + (8 * Game.SCALE * B_SCALE));
			
			public static final int X_OFFSET = (int) (65 * Game.SCALE);
			public static final int Y_OFFSET_1 = Game.GAME_HEIGHT * 1 / 4 - B_HEIGHT / 2;
			public static final int Y_OFFSET_2 = Game.GAME_HEIGHT * 2 / 4 - B_HEIGHT / 2;
			public static final int Y_OFFSET_3 = Game.GAME_HEIGHT * 3 / 4 - B_HEIGHT / 2;
		}
		
		public static class BackButton{
			public static final int B_WIDTH_DEFAULT = 24;
			public static final int B_HEIGHT_DEFAULT = 13;
			public static final int B_SCALE = 3;
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
			public static int HITBOX_WIDTH = (int) (14 * Game.SCALE * PLAYER_SCALE);
			public static int HITBOX_HEIGHT = (int) (8 * Game.SCALE * PLAYER_SCALE);
			
			public static final float PLAYER_SPEED = 1f * Game.SCALE;
		}
		
		public static class CharacterConstants{
			public static final int FACING_RIGHT = 0;
			public static final int FACING_FORWARD = 1;
			public static final int FACING_LEFT = 0;
		}
		
		public static final int IDLE = 0;
		public static final int WALKING_SIDEWAYS = 1;
		public static final int WALKING_TOWARDS = 2;
		public static final int WALKING_AWAY = 3;	
		public static final int BATTLE_IDLE = 4;
		public static final int ATTACK = 5;
		public static final int BLOCK = 6;
		public static final int RUNNING = 7;
		
		public static final int SWING = 20;
		public static final int JAB = 21;
		public static final int DOUBLE_HIT = 22;
		public static final int BLOCKING = 23;
		public static final int STAT_CHANGE = 24;
		
		public static final int DEFAULT_IMAGE = 0;
		public static final int MIRROR_IMAGE = 1;
		
		public static final int DEFAULT_MOVEMENT_TIME = 30;
		public static final int BATTLE_IDLE_TIME = 60;
		public static final int[] ATK_1_FRAME_TIMES = {20, 10, 40};
		
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
			case BLOCKING:
				return new int[][] {
					{0},
					{240}
				};
			case STAT_CHANGE:
				return new int[][] {
					{2},
					{240}
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
