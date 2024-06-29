package locations;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gamestates.Overworld;
import utilz.LoadSave;

public class Area {

	private Overworld overworld;
	private int areaIndex;
	private ArrayList<ExitZone> exitZones;
	private String areaFileName;
	private ArrayList<int[][]> maps;
	
	public Area(Overworld overworld, int areaIndex) {
		this.overworld = overworld;
		this.areaIndex = areaIndex;
		
		if(areaIndex == 1) {
			this.areaFileName = "res/MAP_DATA_0.txt";
		}else if(areaIndex == 0) {	
			this.areaFileName = "res/MAP_DATA_0.txt";
		}
		
		this.maps = ReadMapDataFromFile(areaFileName);
	}
	
//	public void setOverworldMapSize() {
//		overworld.initMapDimensions(map[0].length, map.length);
//		//overworld.setLocationTilesWide(map[0].length);
//		//overworld.setLocationTilesHigh(map.length);
//	}
	
	public static ArrayList<int[][]> ReadMapDataFromFile(String filePath) {
        ArrayList<int[][]> arrayList = new ArrayList<>();

        try {
            // Read the entire file content into a single string
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            bufferedReader.close();

            // Split the content by empty lines to get individual 2D arrays
            String[] parts = sb.toString().split("\\n\\s*\\n");
            
            for (String part : parts) {
                if (!part.trim().isEmpty()) {
                    // Split each part by lines to get rows
                    String[] lines = part.trim().split("\n");
                    List<int[]> rows = new ArrayList<>();
                    for (String row : lines) {
                        // Split each row by commas to get values
                        String[] values = row.split(",");
                        int[] intValues = new int[values.length];
                        for (int i = 0; i < values.length; i++) {
                            intValues[i] = Integer.parseInt(values[i].trim());
                        }
                        rows.add(intValues);
                    }
                    // Convert the list of rows into a 2D array and add it to the list
                    int[][] array = rows.toArray(new int[rows.size()][]);
                    arrayList.add(array);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return arrayList;
    }
	
	public int getAreaIndex() {
		return areaIndex;
	}

	public ArrayList<ExitZone> getExitZones() {
		return exitZones;
	}

	public void setExitZones(ArrayList<ExitZone> exitZones) {
		this.exitZones = exitZones;
	}

	public String getAreaFileName() {
		return areaFileName;
	}

	public void setAreaFileName(String areaFileName) {
		this.areaFileName = areaFileName;
	}

	public void setAreaIndex(int areaIndex) {
		this.areaIndex = areaIndex;
	}

	public ArrayList<int[][]> getMaps() {
		return maps;
	}

//	public void setMap(int[][] map) {
//		this.maps = maps;
//	}
	
}
