package mapeditor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SaveMap {

	public static boolean IsMapValid(Tile[][] tiles) {
		boolean isValid = true;
		
		for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
            	if(tiles[i][j].getSpriteID() == -1) {
            		System.out.print("X");
            		isValid = false;
            	}else {
            		System.out.print("-");
            	}
			}
            System.out.println();
        }
		
		if(isValid) {
			System.out.println("Map is valid");
			return true;
		}else {
			System.out.println("Invalid Map");
			return false;
		}
	}
	
	public static void WriteToFile(ArrayList<Tile[][]> tileLayers, String fileName) {
        String folderPath = "res";
        String filename = folderPath + "/" + fileName + ".txt";

        // Create the res folder if it doesn't exist
        File resFolder = new File(folderPath);

        try {
            FileWriter fileWriter = new FileWriter(filename);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (Tile[][] tiles : tileLayers) {
                for (int i = 0; i < tiles.length; i++) {
                    for (int j = 0; j < tiles[i].length; j++) {
                        bufferedWriter.write(tiles[i][j].getSpriteID() + ",");
                    }
                    bufferedWriter.newLine();
                }
                // Add a separator between layers if needed, for example a blank line:
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
            fileWriter.close();

            System.out.println("File written successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
}
