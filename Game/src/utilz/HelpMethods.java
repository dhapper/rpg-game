package utilz;

import java.util.ArrayList;

import main.Game;

public class HelpMethods {

	public static boolean CanMoveHere(float x, float y, float width, float height, ArrayList<int[][]> layersData) {
		if(!IsSolid(x, y, layersData))
			if(!IsSolid(x + width, y + height , layersData))
				if(!IsSolid(x + width, y , layersData))
					if(!IsSolid(x, y + height, layersData))
						return true;
		return false;
	}
	
	private static boolean IsSolid(float x, float y, ArrayList<int[][]> layersData) {
		
		int maxWidth = layersData.get(0)[0].length * Game.TILES_SIZE;
		int maxHeight = layersData.get(0).length * Game.TILES_SIZE;
		if (x < 0 || x >= maxWidth)
			return true;
		if (y < 0 || y >= maxHeight)
			return true;
		
		float xIndex = x / Game.TILES_SIZE;
		float yIndex = y / Game.TILES_SIZE;
		
		for(int[][] layer : layersData) {
			int value = layer[(int) yIndex][(int) xIndex];
			if(value != 24)
				return true;
		}
		
		//extra check so player head doesnt exceed screen height
		if(y < 0 + 16 * 2 * 2)
			return true;
		
		return false;
		
	}
}
