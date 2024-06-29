package utilz;

import java.util.ArrayList;

import entities.NPC;
import gamestates.Overworld;
import main.Game;

public class HelpMethods {

	public static boolean CanMoveHere(float x, float y, float width, float height, ArrayList<int[][]> locationData, ArrayList<NPC> characters) {
		if(!IsSolid(x, y, locationData, characters))
			if(!IsSolid(x + width, y + height , locationData, characters))
				if(!IsSolid(x + width, y , locationData, characters))
					if(!IsSolid(x, y + height, locationData, characters))
						return true;
		return false;
	}
	
	private static boolean IsSolid(float x, float y, ArrayList<int[][]> locationData, ArrayList<NPC> characters) {
		
		int maxWidth = locationData.get(0)[0].length * Game.TILES_SIZE;
		int maxHeight = locationData.get(0).length * Game.TILES_SIZE;
		if (x < 0 || x >= maxWidth)
			return true;
		if (y < 0 || y >= maxHeight)
			return true;
		
		float xIndex = x / Game.TILES_SIZE;
		float yIndex = y / Game.TILES_SIZE;
		
		for(int[][] mapLayer : locationData) {
			if(mapLayer[(int) yIndex][(int) xIndex] != -1) {
				return true;
			}
			
		}
		
		for(NPC npc : characters) {
			if(npc.getHitbox().contains(x, y))
				return true;
		}
		
		//extra check so player head doesnt exceed screen height
		if(y < 0 + 16 * 2 * 2)
			return true;
		
		return false;
		
	}
}
