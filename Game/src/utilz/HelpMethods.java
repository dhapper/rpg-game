package utilz;

import main.Game;

public class HelpMethods {

	public static boolean CanMoveHere(float x, float y, float width, float height, int[][] locationData) {
		if(!IsSolid(x, y, locationData))
			if(!IsSolid(x + width, y + height , locationData))
				if(!IsSolid(x + width, y , locationData))
					if(!IsSolid(x, y + height, locationData))
						return true;
		return false;
	}
	
	private static boolean IsSolid(float x, float y, int[][] locationData) {
		
		int maxWidth = locationData[0].length * Game.TILES_SIZE;
		int maxHeight = locationData.length * Game.TILES_SIZE;
		if (x < 0 || x >= maxWidth)
			return true;
		if (y < 0 || y >= maxHeight)
			return true;
		
		float xIndex = x / Game.TILES_SIZE;
		float yIndex = y / Game.TILES_SIZE;
		
		int value = locationData[(int) yIndex][(int) xIndex];
		
		//if(value >= 25 || value < 0 || value == 14 || value == 10 || value == 15 || value == 20)
		//	return true;
		
		//extra check so player head doesnt exceed screen height
		if(y < 0 + 16 * 2 * 2)
			return true;
		
		return false;
		
	}
}
