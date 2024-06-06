package locations;

import java.util.ArrayList;

import utilz.LoadSave;

public class Location {

	private ArrayList<int[][]> layers;
	
	
	public Location() {
		this.layers = new ArrayList<>();
		this.addLocationLayer(LoadSave.LOCATION_ONE_DATA_LAYER_1);
		this.addLocationLayer(LoadSave.LOCATION_ONE_DATA_LAYER_2);
		this.addLocationLayer(LoadSave.LOCATION_ONE_DATA_LAYER_3);
	}
	
	public void addLocationLayer(String layerName) {
		this.layers.add(LoadSave.GetLocationData(layerName));
	}
	
	public int getSpriteIndex(int layerIndex, int x, int y) {
		return layers.get(layerIndex)[x][y];
	}
	
	public ArrayList<int[][]> getLayers() {
		return layers;
	}
	
	public int[][] getLayerData(int layerIndex) {
		return layers.get(layerIndex);
	}

}
