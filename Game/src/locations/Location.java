package locations;

import java.awt.Rectangle;
import java.util.ArrayList;

import gamestates.Overworld;
import main.Game;
import utilz.LoadSave;

public class Location {

	private ArrayList<int[][]> layers;
	
	private int locationIndex;
	
	private ArrayList<ExitZone> exitZones;
	
	public Location(Overworld overworld, int locationIndex) {
		
		this.locationIndex = locationIndex;
		
		this.layers = new ArrayList<>();
		
		String layerOrder[] = {"W_S", "C_S", "C_A", "W_A"};
		
		for(String order : layerOrder)
			for(String layer : LoadSave.LAYER_MAPS) {
				if(layer.contains("Lo"+locationIndex) && layer.contains(order)) {}
					//this.addLocationLayer(layer);
			}
		
		this.exitZones = new ArrayList<ExitZone>();
		if(locationIndex == 1) {
			exitZones.add(new ExitZone(overworld, new Rectangle(0, 0, 500, 500), 0, 1000, 100));
		}
		
//		if(locationIndex == 0) {
//			this.addLocationLayer(LoadSave.LAYER_MAP_Lo0_W_S_La1);
//			this.addLocationLayer(LoadSave.LAYER_MAP_Lo0_W_S_La2);
//			this.addLocationLayer(LoadSave.LAYER_MAP_Lo0_C_S_La1);
//			this.addLocationLayer(LoadSave.LAYER_MAP_Lo0_C_A_La1);
//		}
//		
//		if(locationIndex == 1) {
//			this.addLocationLayer(LoadSave.LAYER_MAP_Lo1_W_S_La1);
//			this.addLocationLayer(LoadSave.LAYER_MAP_Lo1_W_S_La2);
//			this.addLocationLayer(LoadSave.LAYER_MAP_Lo1_C_S_La1);
//			this.addLocationLayer(LoadSave.LAYER_MAP_Lo1_C_A_La1);
//			this.addLocationLayer(LoadSave.LAYER_MAP_Lo1_W_A_La1);
//		}
		
	}
	
//	public void addLocationLayer(String layerName) {
//		this.layers.add(LoadSave.GetLocationData(layerName));
//	}
	
	public int getSpriteIndex(int layerIndex, int x, int y) {
		return layers.get(layerIndex)[x][y];
	}
	
	public ArrayList<int[][]> getLayers() {
		return layers;
	}
	
	public int[][] getLayerData(int layerIndex) {
		return layers.get(layerIndex);
	}
	
	public int getLocationIndex() {
		return locationIndex;
	}

	public ArrayList<ExitZone> getExitZones() {
		return exitZones;
	}

	public void setExitZones(ArrayList<ExitZone> exitZones) {
		this.exitZones = exitZones;
	}
	

}
